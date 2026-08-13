package com.mastering;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.dataformat.csv.CsvMapper;
import com.fasterxml.jackson.dataformat.csv.CsvSchema;
import com.mastering.csv.Delimiter;
import org.apache.pdfbox.Loader;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDDocumentCatalog;
import org.apache.pdfbox.pdmodel.PDDocumentInformation;
import org.apache.pdfbox.pdmodel.common.PDMetadata;
import org.apache.xmpbox.XMPMetadata;
import org.apache.xmpbox.schema.DublinCoreSchema;
import org.apache.xmpbox.type.BadFieldValueException;

import lombok.extern.slf4j.Slf4j;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.Year;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Slf4j
public class BookUtils {

    private static final Delimiter DELIMITER = Delimiter.get(',');
    private static final int CAPACITY = 7000;
    private static final String LINE = "\"%s\"" + DELIMITER + "\"%s\"" + DELIMITER + "\"%s\"";
    private static final int LENGTH_FIELD = ClassUtils.getFieldLength(BookUtils.class);
    private static final int EMPTY_LENGTH = LENGTH_FIELD - 3;
    private static final String REPEATED = DELIMITER.toString().repeat(EMPTY_LENGTH);
    private static final Pattern PATTERN = Pattern.compile("(.+?) - (.+?)(?: - \\[(\\d{4})\\])?(?:-(\\d+)с)?");

    private static final CsvMapper MAPPER = new CsvMapper();
    //    private static final CsvSchema BOOK_SCHEMA = CsvSchema.emptySchema()
    //            .withHeader()
    //            .withColumnSeparator(DELIMITER.getValue());
    private static final CsvSchema BOOK_SCHEMA = CsvSchema.builder()
            .addColumn("path", CsvSchema.ColumnType.STRING)
            .addColumn("fileName", CsvSchema.ColumnType.STRING)
            .addColumn("type", CsvSchema.ColumnType.STRING)
            .addColumn("title", CsvSchema.ColumnType.STRING)
            .addColumn("author", CsvSchema.ColumnType.STRING)
            .addColumn("pages", CsvSchema.ColumnType.NUMBER_OR_STRING)
            .addColumn("year", CsvSchema.ColumnType.NUMBER_OR_STRING)
            .addColumn("language", CsvSchema.ColumnType.STRING)
            .addColumn("isbn", CsvSchema.ColumnType.STRING)
            .addColumn("publisher", CsvSchema.ColumnType.STRING)
            .addColumn("edition", CsvSchema.ColumnType.NUMBER_OR_STRING)
            .addColumn("volume", CsvSchema.ColumnType.NUMBER_OR_STRING)
            .addColumn("volume-name", CsvSchema.ColumnType.STRING)
            .addColumn("series", CsvSchema.ColumnType.STRING)
            .addColumn("description", CsvSchema.ColumnType.STRING)
            .addColumn("keywords", CsvSchema.ColumnType.STRING)
            .addColumn("category", CsvSchema.ColumnType.STRING)
            .addColumn("annotate", CsvSchema.ColumnType.STRING)
            .setUseHeader(true)
            .setColumnSeparator(DELIMITER.getValue())
            .setNullValue("")
            .build();

    static {
        MAPPER.findAndRegisterModules();
    }

    private static Path toPath(String filePathString) throws IOException {
        Path path = Paths.get(filePathString);
        return validatePath(path);
    }

    private static Path toPath(Path path) throws IOException {
        return validatePath(path);
    }

    private static Path validatePath(Path path) throws IOException {
        if (!Files.exists(path)) {
            throw new IOException("File does not exist: " + path);
        }
        if (!Files.isRegularFile(path)) {
            throw new IOException("Path is not a regular file: " + path);
        }
        if (!Files.isReadable(path)) {
            throw new IOException("File is not readable: " + path);
        }
        return path;
    }

    private static Iterator<Book> iterator(Path filePath) throws IOException {
        return MAPPER.readerFor(Book.class)
                .with(BOOK_SCHEMA)
                .readValues(filePath.toFile());
    }

    public static List<Book> loadALLBooks(String filePath) throws IOException {
        return load(toPath(filePath), Type.ALL, Integer.MAX_VALUE);
    }

    public static List<Book> loadBooks(String filePath, Type type) throws IOException {
        return load(toPath(filePath), type, Integer.MAX_VALUE);
    }

    private static List<Book> loadBooks(Path filePath, Type type) throws IOException {
        return load(toPath(filePath), type, Integer.MAX_VALUE);
    }

    public static List<Book> loadBooks(String filePath, Type type, int size) throws IOException {
        return load(toPath(filePath), type, size);
    }

    private static List<Book> load(Path filePath, Type type, int size) throws IOException {
        List<Book> books = new ArrayList<>(CAPACITY);
        Iterator<Book> iterator = iterator(filePath);

        while (iterator.hasNext() && size > 0) {
            Book book = iterator.next();
            if (type == null || type == Type.ALL || type == book.getType()) {
                size--;
                books.add(ClassUtils.cleanEmptyStrings(book));
            }
        }
        return books;
    }

    public static String write(String filePathString) throws IOException {
        return writeCsv(toPath(filePathString));
    }

    public static String write(Path path) throws IOException {
        return writeCsv(toPath(path));
    }

    private static String writeCsv(Path path) throws IOException {
        try (Stream<Path> stream = Files.walk(path.getParent())) {
            Set<Path> files = stream
                    .filter(Files::isRegularFile)
                    .collect(Collectors.toUnmodifiableSet());
            try (BufferedWriter writer = new BufferedWriter(new FileWriter(path.toFile()))) {
                writer.write(ClassUtils.generateCsvHead(
                        Book.class,
                        JsonProperty.class,
                        ClassUtils.getMethod(JsonProperty.class, "value"),
                        DELIMITER
                ));
                writer.newLine();
                files.forEach(file -> {
                    String bookFile = file.getFileName().toString();
                    int index = bookFile.lastIndexOf('.');
                    if (index > 0 && index < bookFile.length() - 1) {
                        String title = bookFile.substring(0, index);
                        String type = bookFile.substring(index + 1);
                        if (Type.isValid(type)) {
                            try {
                                writer.write(String.format(LINE, file.toAbsolutePath(), title, type) + REPEATED);
                                writer.newLine();
                            } catch (IOException e) {
                                log.error("Failed to process file: {}", file, e);
                            }
                        }
                    }
                });
            }
        }
        return path.toFile().getAbsolutePath();
    }

    public static String writeBooksToCsv(List<Book> books, String outputPath) throws IOException {
        Path path = Paths.get(outputPath);
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(path.toFile()))) {
            MAPPER.writer(BOOK_SCHEMA).writeValue(writer, books);
        }
        return path.toFile().getAbsolutePath();
    }

    public static Book readPdfMetadata(Book book) throws IOException {
        Path path = toPath(book.getPath());
        File file = path.toFile();
        Matcher matcher = PATTERN.matcher(book.getFileName());
        if (matcher.matches()) {
            book.setAuthor(matcher.group(1));
            book.setTitle(matcher.group(2));
            if (matcher.group(3) != null) {
                book.setYear(Year.parse(matcher.group(3)));
            }
            if (matcher.group(4) != null) {
                book.setPages(Integer.parseInt(matcher.group(4)));
            }
        }
        try (PDDocument document = Loader.loadPDF(file)) {
            if (document.isEncrypted()) {
                return book;
            }
            PDDocumentInformation info = document.getDocumentInformation();
            if (info != null) {
                book.setTitle(info.getTitle() != null ? info.getTitle() : book.getTitle());
                book.setAuthor(info.getAuthor() != null ? info.getAuthor() : book.getAuthor());
                book.setKeywords(info.getKeywords());
            }
            book.setPages(document.getNumberOfPages());
            PDDocumentCatalog catalog = document.getDocumentCatalog();
            PDMetadata meta = catalog.getMetadata();
            if (meta != null) {
                try {
                    XMPMetadata xmp = XMPMetadata.createXMPMetadata();
                    DublinCoreSchema dc = xmp.getDublinCoreSchema();
                    if (dc != null) {
                        book.setDescription(dc.getDescription());
                        if (dc.getIdentifier() != null && !dc.getIdentifier().isEmpty()) {
                            book.setIsbn(dc.getIdentifier());
                        }
                        if (dc.getPublishers() != null && !dc.getPublishers().isEmpty()) {
                            book.setPublisher(String.join(", ", dc.getPublishers()));
                        }
                    }
                } catch (BadFieldValueException e) {
                    log.warn("Failed to read XMP metadata from PDF: {}", file, e);
                }
            }
        } catch (IOException e) {
            log.error("Failed to read metadata from PDF: {}", file, e);
        } catch (Exception e) {
        }
        return book;
    }
}

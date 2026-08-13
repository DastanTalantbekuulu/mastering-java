package com.mastering;

import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Slf4j
// https://www.googleapis.com/books/v1/volumes?q=isbn:9780306406157
public class Renamer {

    private static final String DIR = "D:\\library_";
    private static final String FILE = "book.csv";
    private static final String PATH = DIR + "\\" + FILE;

    public static void main(String[] args) throws IOException {
//        BookUtils.write(PATH);
        run();
//        List<Book> books = BookUtils.loadBooks(PATH, Type.PDF);
//        books.forEach(book -> {
//            try {
//                BookUtils.readPdfMetadata(book);
//            } catch (IOException ioe) {
//                log.error("Book {}:{}", book, ioe.getMessage(), ioe);
//            }
//        });
//        BookUtils.writeBooksToCsv(books, DIR + "\\book_metadata.csv");
    }

    private static void run() throws IOException {
        try (Stream<Path> stream = Files.walk(Paths.get("D:\\library_"))) {
            Set<Path> files = stream
                    .filter(Files::isDirectory)
                    .collect(Collectors.toUnmodifiableSet());
            files.forEach(System.out::println);
        }
    }
}
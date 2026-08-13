//package file.monitoring.apache;
//
//
//import java.io.File;
//import java.io.FileFilter;
//import java.io.IOException;
//import java.io.Serializable;
//import java.util.ArrayList;
//import java.util.Arrays;
//import java.util.Comparator;
//import java.util.List;
//import java.util.Objects;
//import java.util.concurrent.CopyOnWriteArrayList;
//import java.util.stream.Stream;
//
//public class FileAlterationObserver implements Serializable {
//    public static final class Builder extends AbstractOriginSupplier<FileAlterationObserver, Builder> {
//
//        private FileEntry rootEntry;
//        private FileFilter fileFilter;
//        private IOCase ioCase;
//
//        private Builder() {
//            // empty
//        }
//        public FileAlterationObserver get() throws IOException {
//            return new FileAlterationObserver(rootEntry != null ? rootEntry : new FileEntry(checkOrigin().getFile()), fileFilter, toComparator(ioCase));
//        }
//        public Builder setFileFilter(final FileFilter fileFilter) {
//            this.fileFilter = fileFilter;
//            return asThis();
//        }
//        public Builder setIOCase(final IOCase ioCase) {
//            this.ioCase = ioCase;
//            return asThis();
//        }
//        public Builder setRootEntry(final FileEntry rootEntry) {
//            this.rootEntry = rootEntry;
//            return asThis();
//        }
//
//    }
//
//    private static final long serialVersionUID = 1185122225658782848L;
//    public static Builder builder() {
//        return new Builder();
//    }
//
//    private static Comparator<File> toComparator(final IOCase ioCase) {
//        switch (IOCase.value(ioCase, IOCase.SYSTEM)) {
//        case SYSTEM:
//            return NameFileComparator.NAME_SYSTEM_COMPARATOR;
//        case INSENSITIVE:
//            return NameFileComparator.NAME_INSENSITIVE_COMPARATOR;
//        default:
//            return NameFileComparator.NAME_COMPARATOR;
//        }
//    }
//    private transient final List<FileAlterationListener> listeners = new CopyOnWriteArrayList<>();
//    private final FileEntry rootEntry;
//    private transient final FileFilter fileFilter;
//    private final Comparator<File> comparator;
//
//    public FileAlterationObserver(final File directory) {
//        this(directory, null);
//    }
//    public FileAlterationObserver(final File directory, final FileFilter fileFilter) {
//        this(directory, fileFilter, null);
//    }
//
//    public FileAlterationObserver(final File directory, final FileFilter fileFilter, final IOCase ioCase) {
//        this(new FileEntry(directory), fileFilter, ioCase);
//    }
//
//    private FileAlterationObserver(final FileEntry rootEntry, final FileFilter fileFilter, final Comparator<File> comparator) {
//        Objects.requireNonNull(rootEntry, "rootEntry");
//        Objects.requireNonNull(rootEntry.getFile(), "rootEntry.getFile()");
//        this.rootEntry = rootEntry;
//        this.fileFilter = fileFilter != null ? fileFilter : TrueFileFilter.INSTANCE;
//        this.comparator = Objects.requireNonNull(comparator, "comparator");
//    }
//
//    protected FileAlterationObserver(final FileEntry rootEntry, final FileFilter fileFilter, final IOCase ioCase) {
//        this(rootEntry, fileFilter, toComparator(ioCase));
//    }
//
//    public FileAlterationObserver(final String directoryName) {
//        this(new File(directoryName));
//    }
//
//    public FileAlterationObserver(final String directoryName, final FileFilter fileFilter) {
//        this(new File(directoryName), fileFilter);
//    }
//
//    public FileAlterationObserver(final String directoryName, final FileFilter fileFilter, final IOCase ioCase) {
//        this(new File(directoryName), fileFilter, ioCase);
//    }
//    public void addListener(final FileAlterationListener listener) {
//        if (listener != null) {
//            listeners.add(listener);
//        }
//    }
//    private void checkAndFire(final FileEntry parentEntry, final FileEntry[] previousEntries, final File[] currentEntries) {
//        int c = 0;
//        final FileEntry[] actualEntries = currentEntries.length > 0 ? new FileEntry[currentEntries.length] : FileEntry.EMPTY_FILE_ENTRY_ARRAY;
//        for (final FileEntry previousEntry : previousEntries) {
//            while (c < currentEntries.length && comparator.compare(previousEntry.getFile(), currentEntries[c]) > 0) {
//                actualEntries[c] = createFileEntry(parentEntry, currentEntries[c]);
//                fireOnCreate(actualEntries[c]);
//                c++;
//            }
//            if (c < currentEntries.length && comparator.compare(previousEntry.getFile(), currentEntries[c]) == 0) {
//                fireOnChange(previousEntry, currentEntries[c]);
//                checkAndFire(previousEntry, previousEntry.getChildren(), listFiles(currentEntries[c]));
//                actualEntries[c] = previousEntry;
//                c++;
//            } else {
//                checkAndFire(previousEntry, previousEntry.getChildren(), FileUtils.EMPTY_FILE_ARRAY);
//                fireOnDelete(previousEntry);
//            }
//        }
//        for (; c < currentEntries.length; c++) {
//            actualEntries[c] = createFileEntry(parentEntry, currentEntries[c]);
//            fireOnCreate(actualEntries[c]);
//        }
//        parentEntry.setChildren(actualEntries);
//    }
//
//    public void checkAndNotify() {
//
//        // fire onStart()
//        listeners.forEach(listener -> listener.onStart(this));
//
//        // fire directory/file events
//        final File rootFile = rootEntry.getFile();
//        if (rootFile.exists()) {
//            checkAndFire(rootEntry, rootEntry.getChildren(), listFiles(rootFile));
//        } else if (rootEntry.isExists()) {
//            checkAndFire(rootEntry, rootEntry.getChildren(), FileUtils.EMPTY_FILE_ARRAY);
//        }
//        // Else: Didn't exist and still doesn't
//
//        // fire onStop()
//        listeners.forEach(listener -> listener.onStop(this));
//    }
//    private FileEntry createFileEntry(final FileEntry parent, final File file) {
//        final FileEntry entry = parent.newChildInstance(file);
//        entry.refresh(file);
//        entry.setChildren(listFileEntries(file, entry));
//        return entry;
//    }
//
//    public void destroy() throws Exception {
//
//    }
//
//    private void fireOnChange(final FileEntry entry, final File file) {
//        if (entry.refresh(file)) {
//            listeners.forEach(listener -> {
//                if (entry.isDirectory()) {
//                    listener.onDirectoryChange(file);
//                } else {
//                    listener.onFileChange(file);
//                }
//            });
//        }
//    }
//
//    private void fireOnCreate(final FileEntry entry) {
//        listeners.forEach(listener -> {
//            if (entry.isDirectory()) {
//                listener.onDirectoryCreate(entry.getFile());
//            } else {
//                listener.onFileCreate(entry.getFile());
//            }
//        });
//        Stream.of(entry.getChildren()).forEach(this::fireOnCreate);
//    }
//
//    private void fireOnDelete(final FileEntry entry) {
//        listeners.forEach(listener -> {
//            if (entry.isDirectory()) {
//                listener.onDirectoryDelete(entry.getFile());
//            } else {
//                listener.onFileDelete(entry.getFile());
//            }
//        });
//    }
//
//    Comparator<File> getComparator() {
//        return comparator;
//    }
//    public File getDirectory() {
//        return rootEntry.getFile();
//    }
//
//    public FileFilter getFileFilter() {
//        return fileFilter;
//    }
//    public Iterable<FileAlterationListener> getListeners() {
//        return new ArrayList<>(listeners);
//    }
//
//    public void initialize() throws Exception {
//        rootEntry.refresh(rootEntry.getFile());
//        rootEntry.setChildren(listFileEntries(rootEntry.getFile(), rootEntry));
//    }
//
//    private FileEntry[] listFileEntries(final File file, final FileEntry entry) {
//        return Stream.of(listFiles(file)).map(f -> createFileEntry(entry, f)).toArray(FileEntry[]::new);
//    }
//    private File[] listFiles(final File directory) {
//        return directory.isDirectory() ? sort(directory.listFiles(fileFilter)) : FileUtils.EMPTY_FILE_ARRAY;
//    }
//    public void removeListener(final FileAlterationListener listener) {
//        if (listener != null) {
//            listeners.removeIf(listener::equals);
//        }
//    }
//
//    private File[] sort(final File[] files) {
//        if (files == null) {
//            return FileUtils.EMPTY_FILE_ARRAY;
//        }
//        if (files.length > 1) {
//            Arrays.sort(files, comparator);
//        }
//        return files;
//    }
//
//    public String toString() {
//        final StringBuilder builder = new StringBuilder();
//        builder.append(getClass().getSimpleName());
//        builder.append("[file='");
//        builder.append(getDirectory().getPath());
//        builder.append('\'');
//        builder.append(", ");
//        builder.append(fileFilter.toString());
//        builder.append(", listeners=");
//        builder.append(listeners.size());
//        builder.append("]");
//        return builder.toString();
//    }
//
//}

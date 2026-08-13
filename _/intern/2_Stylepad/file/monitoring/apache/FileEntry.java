///*
// * Licensed to the Apache Software Foundation (ASF) under one or more
// * contributor license agreements.  See the NOTICE file distributed with
// * this work for additional information regarding copyright ownership.
// * The ASF licenses this file to You under the Apache License, Version 2.0
// * (the "License"); you may not use this file except in compliance with
// * the License.  You may obtain a copy of the License at
// *
// *      http://www.apache.org/licenses/LICENSE-2.0
// *
// * Unless required by applicable law or agreed to in writing, software
// * distributed under the License is distributed on an "AS IS" BASIS,
// * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
// * See the License for the specific language governing permissions and
// * limitations under the License.
// */
//package file.monitoring.apache;
//
//
//import java.io.File;
//import java.io.IOException;
//import java.io.Serializable;
//import java.nio.file.Files;
//import java.nio.file.attribute.FileTime;
//import java.util.Objects;
//
//public class FileEntry implements Serializable {
//
//    private static final long serialVersionUID = -2505664948818681153L;
//
//    static final FileEntry[] EMPTY_FILE_ENTRY_ARRAY = {};
//    private final FileEntry parent;
//    private FileEntry[] children;
//    private final File file;
//    private String name;
//    private boolean exists;
//    private boolean directory;
//    private SerializableFileTime lastModified = SerializableFileTime.EPOCH;
//    private long length;
//    public FileEntry(final File file) {
//        this(null, file);
//    }
//    public FileEntry(final FileEntry parent, final File file) {
//        this.file = Objects.requireNonNull(file, "file");
//        this.parent = parent;
//        this.name = file.getName();
//    }
//    public FileEntry[] getChildren() {
//        return children != null ? children : EMPTY_FILE_ENTRY_ARRAY;
//    }
//    public File getFile() {
//        return file;
//    }
//    public long getLastModified() {
//        return lastModified.toMillis();
//    }
//    public FileTime getLastModifiedFileTime() {
//        return lastModified.unwrap();
//    }
//    public long getLength() {
//        return length;
//    }
//    public int getLevel() {
//        return parent == null ? 0 : parent.getLevel() + 1;
//    }
//    public String getName() {
//        return name;
//    }
//    public FileEntry getParent() {
//        return parent;
//    }
//    public boolean isDirectory() {
//        return directory;
//    }
//    public boolean isExists() {
//        return exists;
//    }
//    public FileEntry newChildInstance(final File file) {
//        return new FileEntry(this, file);
//    }
//    public boolean refresh(final File file) {
//        // cache original values
//        final boolean origExists = exists;
//        final SerializableFileTime origLastModified = lastModified;
//        final boolean origDirectory = directory;
//        final long origLength = length;
//
//        // refresh the values
//        name = file.getName();
//        exists = Files.exists(file.toPath());
//        directory = exists && file.isDirectory();
//        try {
//            setLastModified(exists ? FileUtils.lastModifiedFileTime(file) : FileTimes.EPOCH);
//        } catch (final IOException e) {
//            setLastModified(SerializableFileTime.EPOCH);
//        }
//        length = exists && !directory ? file.length() : 0;
//
//        // Return if there are changes
//        return exists != origExists || !lastModified.equals(origLastModified) || directory != origDirectory
//            || length != origLength;
//    }
//    public void setChildren(final FileEntry... children) {
//        this.children = children;
//    }
//    public void setDirectory(final boolean directory) {
//        this.directory = directory;
//    }
//    public void setExists(final boolean exists) {
//        this.exists = exists;
//    }
//    public void setLastModified(final FileTime lastModified) {
//        setLastModified(new SerializableFileTime(lastModified));
//    }
//    public void setLastModified(final long lastModified) {
//        setLastModified(FileTime.fromMillis(lastModified));
//    }
//
//    void setLastModified(final SerializableFileTime lastModified) {
//        this.lastModified = lastModified;
//    }
//    public void setLength(final long length) {
//        this.length = length;
//    }
//    public void setName(final String name) {
//        this.name = name;
//    }
//}

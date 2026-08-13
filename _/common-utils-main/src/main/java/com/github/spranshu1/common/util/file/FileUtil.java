/*
 * Created By: Pranshu Shrivastava

 * All rights reserved.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.github.spranshu1.common.util.file;

import com.github.spranshu1.common.util.Messages;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.Path;
import java.nio.file.Paths;

/**
 * Holds utility methods for handling file related operations
 */
public final class FileUtil {

    /**
     * Gets filename from filepath.
     * The filename is the farthest element from the root in file directory
     * <pre class="code">
     *     FileUtil.getFileName("D:\data\test.csv") = test.csv
     *     FileUtil.getFileName("D:\data\) = FileNotFoundException
     * </pre>
     *
     * @param filePath the path where file exist
     * @return String representation of file name
     * @throws FileNotFoundException the exception
     */
    public static String getFileName(String filePath) throws FileNotFoundException {
        Path path = Paths.get(filePath);

        Path fileName = path.getFileName();

        if (fileName == null) {
            throw new FileNotFoundException(Messages.ERR_FILE_NOT_FOUND);
        }

        return fileName.toString();
    }


    /**
     * Get absolute path string.
     *
     * @param filePath the file path
     * @return the string
     * @throws URISyntaxException the uri syntax exception
     */
    public static String getAbsolutePath(URL filePath) throws URISyntaxException {
        File file = Paths.get(filePath.toURI()).toFile();
        return file.getAbsolutePath();
    }

    /**
     * Checks if file exist at the filePath.
     * <pre class=code>
     *      FileUtil.fileExist("D:\data\test.csv") = true // if test.csv present at location
     * </pre>
     *
     * @param filePath the path where file exist
     * @return True if a file with valid extension exist, False otherwise
     */
    public static boolean fileExist(String filePath) {
        Path path = Paths.get(filePath);

        Path fileName = path.getFileName();

        return fileName != null;
    }


    /**
     * Gets the file extension from file name
     * <pre class=code>
     *      FileUtil.getFileExtension("D:\data\test.csv") = csv
     *      FileUtil.getFileExtension("D:\data\test.xlsx") = xlsx
     * </pre>
     *
     * @param filename the file name
     * @return extension of file eg: .csv or .xlsx
     */
    public static String getFileExtension(final String filename) {

        return filename.substring(filename.lastIndexOf("."));

    }

    /**
     * Deletes a file. If file is a directory, delete it and all sub-directories.
     * <p>
     * The difference between File.delete() and this method are:
     * <ul>
     * <li>A directory to be deleted does not have to be empty.</li>
     * <li>You get exceptions when a file or directory cannot be deleted.
     * (java.io.File methods returns a boolean)</li>
     * </ul>
     *
     * @param file file or directory to delete, must not be {@code null}
     * @throws NullPointerException  if the directory is {@code null}
     * @throws FileNotFoundException if the file was not found
     * @throws IOException           in case deletion is unsuccessful
     */
    public static void forceDelete(final File file) throws IOException {
        if (file.isDirectory()) {
            deleteDirectory(file);
        } else {
            final boolean filePresent = file.exists();
            if (!file.delete()) {
                if (!filePresent) {
                    throw new FileNotFoundException("File does not exist: " + file);
                }
                final String message =
                        "Unable to delete file: " + file;
                throw new IOException(message);
            }
        }
    }

    /**
     * Cleans a directory without deleting it.
     *
     * @param directory directory to clean
     * @throws IOException              in case cleaning is unsuccessful
     * @throws IllegalArgumentException if {@code directory} does not exist or is not a directory
     */
    public static void cleanDirectory(final File directory) throws IOException {
        final File[] files = verifiedListFiles(directory);

        IOException exception = null;
        for (final File file : files) {
            try {
                forceDelete(file);
            } catch (final IOException ioe) {
                exception = ioe;
            }
        }

        if (exception != null) {
            throw exception;
        }
    }


    /**
     * Deletes a directory recursively.
     *
     * @param directory directory to delete
     * @throws IOException              in case deletion is unsuccessful
     * @throws IllegalArgumentException if {@code directory} does not exist or is not a directory
     */
    public static void deleteDirectory(final File directory) throws IOException {
        if (!directory.exists()) {
            return;
        }

        if (!isSymlink(directory)) {
            cleanDirectory(directory);
        }

        if (!directory.delete()) {
            final String message =
                    "Unable to delete directory " + directory + ".";
            throw new IOException(message);
        }
    }

    /**
     * Determines whether the specified file is a Symbolic Link rather than an actual file.
     * <p>
     * Will not return true if there is a Symbolic Link anywhere in the path,
     * only if the specific file is.
     *
     * @param file the file to check
     * @return true if the file is a Symbolic Link
     * @throws IOException if an IO error occurs while checking the file
     */
    public static boolean isSymlink(final File file) throws IOException {
        if (file == null) {
            throw new NullPointerException(Messages.ERR_FILE_NULL);
        }
        File fileInCanonicalDir = null;
        if (file.getParent() == null) {
            fileInCanonicalDir = file;
        } else {
            final File canonicalDir = file.getParentFile().getCanonicalFile();
            fileInCanonicalDir = new File(canonicalDir, file.getName());
        }

        if (fileInCanonicalDir.getCanonicalFile().equals(fileInCanonicalDir.getAbsoluteFile())) {
            return isBrokenSymlink(file);
        } else {
            return true;
        }
    }

    /**
     * Determines if the specified file is possibly a broken symbolic link.
     *
     * @param file the file to check
     * @return true if the file is a Symbolic Link
     * @throws IOException if an IO error occurs while checking the file
     */
    private static boolean isBrokenSymlink(final File file) throws IOException {
        // if file exists then it is not broken
        if (file.exists()) {
            return false;
        }
        // a broken symlink will show up in the list of files of its parent directory
        final File canon = file.getCanonicalFile();
        File parentDir = canon.getParentFile();
        if (parentDir == null || !parentDir.exists()) {
            return false;
        }

        File[] fileInDir = parentDir.listFiles(
                aFile -> aFile.equals(canon)
        );
        return fileInDir != null && fileInDir.length > 0;
    }

    /**
     * Lists files in a directory, asserting that the supplied directory satisfies exists and is a directory
     *
     * @param directory The directory to list
     * @return The files in the directory, never null.
     * @throws IOException if an I/O error occurs
     */
    private static File[] verifiedListFiles(File directory) throws IOException {
        if (!directory.exists()) {
            final String message = directory + " does not exist";
            throw new IllegalArgumentException(message);
        }

        if (!directory.isDirectory()) {
            final String message = directory + " is not a directory";
            throw new IllegalArgumentException(message);
        }

        final File[] files = directory.listFiles();
        if (files == null) {  // null if security restricted
            throw new IOException("Failed to list contents of " + directory);
        }
        return files;
    }


}
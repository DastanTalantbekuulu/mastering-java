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
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;
import java.util.*;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;
import java.util.zip.ZipOutputStream;

/**
 * The type Zip util.
 */
public final class ZipUtil {

    /** The constant logger */
    private static final Logger log = LoggerFactory.getLogger(ZipUtil.class);

    private static List<String> filesListInDir;

    /**
     * Used for zipping file or folder
     * It takes source file/folder and target file path as a String
     * <pre class="code">
     *      ZipUtil.zip("D:\testfolder\test.txt","D:\test.zip") // creates test.zip
     *      ZipUtil.zip("D:\testfolder","D:\testFolder.zip") // creates testFolder.zip
     * </pre>
     *
     * @param source the source destination
     * @param output the output destination
     * @return True on success
     * @throws IOException the io exception
     */
    public static boolean zip(String source, String output) throws IOException {
        File sourceFile = new File(source);

        if (sourceFile.isFile()) {
            return zipSingleFile(sourceFile, output);
        } else {
            return zipDirectory(sourceFile, output);
        }
    }

    /**
     * Zips the directory
     *
     * @param sourceFile the source file
     * @param zippedFile the zipped file
     * @return True on success
     * @throws IOException the io exception
     */
    private static boolean zipDirectory(final File sourceFile, final String zippedFile) throws IOException {
        boolean isZipped = false;
        try (FileOutputStream fos = new FileOutputStream(zippedFile, false);
             ZipOutputStream zos = new ZipOutputStream(fos)
        ) {
            filesListInDir = new ArrayList<>();
            populateFilesList(sourceFile);
            for (String filePath : filesListInDir) {
                final ZipEntry ze = new ZipEntry(filePath.substring(sourceFile.getAbsolutePath().length() + 1));
                zos.putNextEntry(ze);
                final FileInputStream fis = new FileInputStream(filePath);
                final byte[] buffer = new byte[1024];
                int len;
                while ((len = fis.read(buffer)) > 0) {
                    zos.write(buffer, 0, len);
                }
                zos.closeEntry();
                fis.close();
            }
            isZipped = true;
        } catch (IOException e) {
            log.error(Messages.ERR_DIR_ZIP, e);
            throw e;
        }
        return isZipped;
    }

    /**
     * This method populates all the files in a directory to a List recursively
     *
     * @param dir the directory
     */
    private static void populateFilesList(File dir) {
        File[] files = dir.listFiles();

        for (File file : files) {
            if (file.isFile())
                filesListInDir.add(file.getAbsolutePath());
            else
                populateFilesList(file);
        }

    }

    /**
     * Zips multiple input streams
     * Files created from input stream will have .txt extension
     *
     * @param lis        List of inputstreams to be zipped together
     * @param zippedFile Target file path as a String
     * @throws IOException the io exception
     */
    public static void zipStream(List<InputStream> lis, String zippedFile) throws IOException {
        try (FileOutputStream fos = new FileOutputStream(zippedFile, false);
             ZipOutputStream zos = new ZipOutputStream(fos)) {
            int i = 0;
            for (InputStream file : lis) {
                ZipEntry ze = new ZipEntry(i + ".txt");
                zos.putNextEntry(ze);
                byte[] buffer = new byte[1024];
                int len;
                while ((len = file.read(buffer)) > 0) {
                    zos.write(buffer, 0, len);
                }
                i++;
                zos.closeEntry();
                file.close();
            }
        } catch (IOException e) {
            log.error(Messages.ERR_DIR_ZIP, e);
            throw e;
        }
    }

    /**
     * Zips a single file
     *
     * @param sourceFile the source file
     * @param zippedFile the zipped file
     * @return True on success
     * @throws IOException the io exception
     */
    private static boolean zipSingleFile(File sourceFile, String zippedFile) throws IOException {
        boolean isZipped = false;
        try (FileOutputStream fos = new FileOutputStream(zippedFile);
             ZipOutputStream zos = new ZipOutputStream(fos);
             FileInputStream fis = new FileInputStream(sourceFile)) {
            ZipEntry ze = new ZipEntry(sourceFile.getName());
            zos.putNextEntry(ze);

            byte[] buffer = new byte[1024];
            int len;
            while ((len = fis.read(buffer)) > 0) {
                zos.write(buffer, 0, len);
            }
            zos.closeEntry();
            isZipped = true;
        } catch (IOException e) {
            log.error(Messages.ERR_DIR_ZIP, e);
            log.error(Messages.ERR_FILE_ZIP);
            throw e;
        }
        return isZipped;
    }


    /**
     * Unzips the compressed file
     *
     * @param zipFile           the zip file path
     * @param destinationFolder the destination folder
     * @throws IOException the io exception
     */
    public static void unzip(String zipFile, String destinationFolder) throws IOException {
        File directory = new File(destinationFolder);
        if (!directory.exists())
            directory.mkdirs();
        byte[] buffer = new byte[2048];
        try (FileInputStream fInput = new FileInputStream(zipFile);
             ZipInputStream zipInput = new ZipInputStream(fInput)) {
            ZipEntry entry = zipInput.getNextEntry();
            while (entry != null) {
                String entryName = entry.getName();
                File file = new File(destinationFolder + File.separator + entryName);
                if (entry.isDirectory()) {
                    File newDir = new File(file.getAbsolutePath());
                    if (!newDir.exists()) {
                        newDir.mkdirs();
                    }
                } else {
                    FileOutputStream fOutput = new FileOutputStream(file);
                    int count;
                    while ((count = zipInput.read(buffer)) > 0) {
                        fOutput.write(buffer, 0, count);
                    }
                    fOutput.close();
                }
                zipInput.closeEntry();
                entry = zipInput.getNextEntry();
            }
            zipInput.closeEntry();
        } catch (IOException e) {
            log.error(Messages.ERR_FILE_UNZIP, e);
            throw e;
        }
    }

}
package com.github.spranshu1.common.util.test.file;


import com.github.spranshu1.common.util.file.ZipUtil;
import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.Test;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

/**
 * The type Zip util test.
 */
public class ZipUtilTest {

    private static final Path resourceDirectory = Paths.get("src","test","resources");

    private static final String absolutePath = resourceDirectory.toFile().getAbsolutePath();

    private static final String SRC_FILE_PATH = absolutePath + "/test_data/dummy.txt";

    private static final String SRC_DIR_PATH = absolutePath + "/test_data";

    private static final String DESTINATION = absolutePath + "/test_data/";

    /**
     * Cleanup.
     *
     * @throws IOException the io exception
     */
    @AfterClass
    public static void cleanup() throws IOException {
        Path file1 = Paths.get("src/test/resources/test_data/dummy.zip");
        Path file2 = Paths.get("src/test/resources/test_data.zip");
        Path file3 = Paths.get("src/test/resources/test_data/stream.zip");

        Files.delete(file1);
        Files.delete(file2);
        Files.delete(file3);
    }


    /**
     * Test file zip.
     *
     * @throws IOException the io exception
     */
    @Test
    public void testFileZip() throws IOException {
        boolean result = ZipUtil.zip(SRC_FILE_PATH,DESTINATION+"/dummy.zip");
        Assert.assertTrue(result);
    }

    /**
     * Test directory zip.
     *
     * @throws IOException the io exception
     */
    @Test
    public void testDirZip() throws IOException {
        boolean result = ZipUtil.zip(SRC_DIR_PATH,absolutePath+"/test_data.zip");
        Assert.assertTrue(result);
    }

    /**
     * Test stream zip.
     *
     * @throws IOException the io exception
     */
    @Test
    public void testStreamZip() throws IOException {
        List<InputStream> lis = new ArrayList<>();

        InputStream is = new FileInputStream(SRC_FILE_PATH);
        lis.add(is);

        ZipUtil.zipStream(lis,DESTINATION+"/stream.zip");
    }

    /**
     * Test file zip negative case.
     */
    @Test(expected = FileNotFoundException.class)
    public void testFileZipNeg() throws IOException {

        ZipUtil.zip(SRC_FILE_PATH,DESTINATION);

    }

    /**
     * Test dir zip neg.
     */
    @Test(expected = FileNotFoundException.class)
    public void testDirZipNeg() throws IOException {
        ZipUtil.zip(SRC_DIR_PATH+"/",DESTINATION);
    }

    /**
     * Test unzip.
     *
     * @throws IOException the io exception
     */
    @Test
    public void testUnzip() throws IOException {
        ZipUtil.unzip(SRC_DIR_PATH+"/unzipme.zip",DESTINATION+"unzipme");
    }

}

package com.github.spranshu1.common.util.test.file;

import com.github.spranshu1.common.util.file.FileUtil;
import org.junit.Assert;
import org.junit.Test;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.Path;
import java.nio.file.Paths;

/**
 * The type File util test.
 */
public class FileUtilTest {

    private static final Path resourceDirectory = Paths.get("src","test","resources");

    private static final String absolutePath = resourceDirectory.toFile().getAbsolutePath();

    private static final String SLASH = "/";

    /**
     * Test get file name.
     *
     * @throws FileNotFoundException the file not found exception
     */
    @Test
    public void testGetFileName() throws FileNotFoundException {
        final String fileName = FileUtil.getFileName(absolutePath+SLASH+"test_data"+SLASH+"dummy.txt");
        Assert.assertEquals("Filename does not match",fileName,"dummy.txt");
    }

    /**
     * Test absolute path.
     *
     * @throws MalformedURLException the malformed url exception
     * @throws URISyntaxException    the uri syntax exception
     */
    @Test
    public void testAbsolutePath() throws MalformedURLException, URISyntaxException {
        URL url = new File(absolutePath+SLASH+"test_data"+SLASH+"dummy.txt").toURL();
        final String path = FileUtil.getAbsolutePath(url);
        Assert.assertEquals(absolutePath+SLASH+"test_data"+SLASH+"dummy.txt",path);
    }

    @Test
    public void testFileExist(){
        boolean result = FileUtil.fileExist(absolutePath+SLASH+"test_data"+SLASH+"dummy.txt");
        Assert.assertTrue(result);
    }

    @Test
    public void testFileExtension(){
        String ext = FileUtil.getFileExtension(absolutePath+SLASH+"test_data"+SLASH+"dummy.txt");
        Assert.assertEquals(".txt",ext);
    }

    @Test
    public void testIsSymLink() throws IOException {
        File file = new File(absolutePath+SLASH+"test_data"+SLASH+"dummy.txt");
        boolean isSymLink = FileUtil.isSymlink(file);
        Assert.assertFalse(isSymLink);
    }


}

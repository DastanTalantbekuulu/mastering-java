package com.github.spranshu1.common.util.test.hex;

import com.github.spranshu1.common.util.hex.HexUtil;
import org.junit.Assert;
import org.junit.Test;

import java.io.UnsupportedEncodingException;
import java.util.regex.Pattern;

/**
 * The type Hex util test.
 */
public class HexUtilTest {

    /**
     * Test to string.
     *
     * @throws UnsupportedEncodingException the unsupported encoding exception
     */
    @Test
    public void testToString() throws UnsupportedEncodingException {
        String test = "Go Corona, Go Corona....";
        String dump = HexUtil.toString(test.getBytes("UTF-8"));
        Pattern pattern = Pattern.compile("\\p{XDigit}+");
        Assert.assertTrue(dump.matches(pattern.toString()));
    }



}

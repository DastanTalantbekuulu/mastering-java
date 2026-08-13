package com.github.spranshu1.common.util.test.string;

import com.github.spranshu1.common.util.string.StringUtil;
import org.junit.Assert;
import org.junit.Test;

import java.util.Arrays;
import java.util.List;

/**
 * The type String util test.
 */
public class StringUtilTest {

    /**
     * Test is empty.
     */
    @Test
    public void testIsEmpty(){
        String field = " ";
        boolean isEmpty = StringUtil.strFieldIsEmpty(field);
        Assert.assertTrue(isEmpty);
    }

    /**
     * Test is not empty.
     */
    @Test
    public void testIsNotEmpty(){
        String field = "lorem ipsum";
        boolean isEmpty = StringUtil.strFieldIsNotEmpty(field);
        Assert.assertTrue(isEmpty);
    }

    /**
     * Test has length.
     */
    @Test
    public void testHasLength(){
        String field = "lorem ipsum";
        boolean hasLength = StringUtil.hasLength(field);
        Assert.assertTrue(hasLength);
    }

    /**
     * Test equals.
     */
    @Test
    public void testEquals(){
        String field1 = "lorem ipsum";
        String field2 = "lorem ipsum";
        boolean isEqual = StringUtil.equals(field1,field2);
        Assert.assertTrue(isEqual);
    }

    /**
     * Test equals ignore case.
     */
    @Test
    public void testEqualsIgnoreCase(){
        String field1 = "lorem ipsum";
        String field2 = "LOREM IPSUM";
        boolean isEqual = StringUtil.equalsIgnoreCase(field1,field2);
        Assert.assertTrue(isEqual);
    }

    /**
     * Test match against regex lov.
     */
    @Test
    public void testMatchAgainstRegexLOV(){
        String field = "lorem ipsum 1234";
        List<String> listOfRegex = Arrays.asList("[a-h]","[a-z]","^[a-zA-Z0-9 ]*$");
        boolean isMatch = StringUtil.matchAgainstRegexLOV(field,listOfRegex);
        Assert.assertTrue("Not a match",isMatch);
    }

}

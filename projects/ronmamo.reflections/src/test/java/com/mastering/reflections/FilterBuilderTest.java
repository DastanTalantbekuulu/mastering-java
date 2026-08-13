package com.mastering.reflections;

import org.junit.jupiter.api.Test;
import com.mastering.reflections.util.FilterBuilder;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class FilterBuilderTest {

    @Test
    public void includeExcludePackage() {
        FilterBuilder filter = new FilterBuilder()
            .includePackage("com.mastering.reflections")
            .excludePackage("com.mastering.reflections.exclude")
            .includePackage("com.foo");

        doAssert(filter);
    }

    @Test
    public void parsePackages() {
        FilterBuilder filter = FilterBuilder
            .parsePackages("+com.mastering.reflections ,  -com.mastering.reflections.exclude,+com.foo"); // not trimmed

        doAssert(filter);
    }

    @Test
    public void includeExcludePattern() {
        FilterBuilder filter = new FilterBuilder()
            .includePattern("com\\.mastering\\.reflections\\..*")
            .excludePattern("com\\.mastering\\.reflections\\.exclude\\..*")
            .includePattern("com\\.mastering\\.foo\\..*");

        doAssert(filter);
    }

    private void doAssert(FilterBuilder filter) {
        assertFalse(filter.test(""));
        assertFalse(filter.test("com"));
        assertFalse(filter.test("com."));
        assertFalse(filter.test("com.mastering.reflections"));
        assertTrue(filter.test("com.mastering.reflections."));
        assertTrue(filter.test("com.mastering.reflections.Reflections"));
        assertTrue(filter.test("com.mastering.reflections.foo.Reflections"));
        assertFalse(filter.test("com.mastering.reflections.exclude.it"));
        assertFalse(filter.test("com.foo"));
        assertTrue(filter.test("com.foo."));
        assertTrue(filter.test("com.foo.bar"));
        assertFalse(filter.test("com.bar.Reflections"));
    }

}

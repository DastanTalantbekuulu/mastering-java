package org.jqurantree.core.resource;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;

import org.jqurantree.core.error.Errors;
import org.jqurantree.core.error.QuranException;

public class ResourceReader {

    private final BufferedReader reader;
    private static final String DEFAULT_ENCODING = "UTF-8";

    public ResourceReader(String resourcePath) {
        try {
            InputStream stream = ResourceUtil.open(resourcePath);
            reader = new BufferedReader(new InputStreamReader(stream, DEFAULT_ENCODING));
        } catch (UnsupportedEncodingException exception) {
            throw new QuranException(Errors.INVALID_ENCODING_TYPE, exception);
        }
    }

    public String readLine() {
        String line;
        try {
            line = reader.readLine();
        } catch (IOException exception) {
            throw new QuranException(Errors.RESOURCE_READ_FAILED, exception);
        }
        return line;
    }

    public void close() {
        try {
            reader.close();
        } catch (IOException exception) {
            throw new QuranException(Errors.RESOURCE_CLOSE_FAILED, exception);
        }
    }
}

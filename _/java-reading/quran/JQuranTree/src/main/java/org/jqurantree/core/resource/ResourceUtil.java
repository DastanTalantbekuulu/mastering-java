package org.jqurantree.core.resource;

import java.io.InputStream;
import org.jqurantree.core.error.Errors;
import org.jqurantree.core.error.QuranException;

public class ResourceUtil {

    private static final Class resourceType = new ResourceUtil().getClass();

    private ResourceUtil() {
    }

    public static InputStream open(String resourcePath) {
        InputStream stream = resourceType.getResourceAsStream(resourcePath);
        if (stream == null) {
            throw new QuranException(Errors.RESOURCE_NOT_FOUND);
        }
        return stream;
    }

    public static String getText(String resourcePath) {

        ResourceReader reader = new ResourceReader(resourcePath);

        StringBuilder text = new StringBuilder();
        String line;
        while ((line = reader.readLine()) != null) {
            text.append(line);
            text.append("\r\n");
        }

        reader.close();

        return text.toString();
    }
}

import java.io.InputStream;
import java.io.BufferedInputStream;

public final class ResourceLoaderUtil {

    public static InputStream getInputStreamFromFile(String path) {
        InputStream inputStream = null;
        InputStream bufferedInputStream = null;
        inputStream = ResourceLoaderUtil.class.getResourceAsStream(path);
        bufferedInputStream = new BufferedInputStream(inputStream);

        return bufferedInputStream;
    }


}

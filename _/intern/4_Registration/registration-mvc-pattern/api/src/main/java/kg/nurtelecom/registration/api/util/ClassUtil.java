package kg.nurtelecom.registration.api.util;

public class ClassUtil {

    public static Class<?> getClass(String clazz) {
        try {
            return Class.forName(clazz);
        } catch (ClassNotFoundException cnfe) {
            return Class.class;
        }
    }

}

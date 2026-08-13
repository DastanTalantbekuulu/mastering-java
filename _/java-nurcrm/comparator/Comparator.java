package kg.nurtelecom.util.comparator;

@FunctionalInterface
public interface Comparator<T, R> {

    R compare(T oldObject, T newObject);
}

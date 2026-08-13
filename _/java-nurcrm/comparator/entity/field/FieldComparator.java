package kg.nurtelecom.util.comparator.entity.field;

import kg.nurtelecom.util.comparator.Comparator;

import java.util.Objects;

public interface FieldComparator<T> extends Comparator<T, Boolean> {

    FieldComparator<T> getInstance();

    @Override
    default Boolean compare(T oldObject, T newObject) {
        return Objects.equals(oldObject, newObject);
    }

}

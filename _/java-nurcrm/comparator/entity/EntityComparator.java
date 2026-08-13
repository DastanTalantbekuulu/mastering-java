package kg.nurtelecom.util.comparator.entity;

import kg.nurtelecom.util.comparator.Comparator;
import kg.nurtelecom.util.comparator.entity.field.Difference;

import java.util.List;

@FunctionalInterface
public interface EntityComparator<E> extends Comparator<E, List<Difference>> {

    default boolean isEqual(E obj1, E obj2) {
        if (obj1 == obj2) return true;
        if (obj1 == null || obj2 == null || !obj1.getClass().equals(obj2.getClass())) return false;
        return compare(obj1, obj2).isEmpty();
    }
}

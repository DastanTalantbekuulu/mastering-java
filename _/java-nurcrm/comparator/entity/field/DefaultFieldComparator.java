package kg.nurtelecom.util.comparator.entity.field;

public final class DefaultFieldComparator implements FieldComparator<Object> {

    private DefaultFieldComparator() {
    }

    private static final class InstanceHolder {
        static final FieldComparator<Object> INSTANCE = new DefaultFieldComparator();
    }

    public static FieldComparator<Object> me() {
        return InstanceHolder.INSTANCE;
    }

    public FieldComparator<Object> getInstance() {
        return me();
    }

}

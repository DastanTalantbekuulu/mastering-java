
public class ArrayUtil<T> {
    public static <T> T[] get(int capacity) {
        return (T[]) new Object[capacity];
    }

    public static <T> T[] get(T[] src, int add) {
        return get(src.length + add, src);
    }

    public static <T> T[] get(int capacity, T[] src) {
        T[] newArray = get(capacity);
        for (int i = 0; i < capacity; i++) {
            newArray[i] = src[i];
        }
        return newArray;
    }
}

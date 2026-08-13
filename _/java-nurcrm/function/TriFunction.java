package kg.nurtelecom.util.function;
import java.util.Objects;
import java.util.function.Function;

/**
 * Представляет функцию, которая принимает три аргумента и возвращает результат.
 * Это функциональный аналог для методов с тремя параметрами.
 *
 * <p>Это {@link FunctionalInterface},
 * функциональный метод которого - {@link #apply(Object, Object, Object)}.
 *
 * @param <F> тип первого аргумента функции
 * @param <S> тип второго аргумента функции
 * @param <T> тип третьего аргумента функции
 * @param <R> тип результата функции
 */
@FunctionalInterface
public interface TriFunction<F, S, T, R> {

    /**
     * Применяет эту функцию к заданным аргументам.
     *
     * @param f первый аргумент функции
     * @param s второй аргумент функции
     * @param t третий аргумент функции
     * @return результат выполнения функции
     */
    R apply(F f, S s, T t);

    /**
     * Возвращает композитную функцию, которая сначала применяет эту функцию
     * к своим аргументам, а затем применяет функцию {@code after} к результату.
     * Если вычисление любой из функций вызывает исключение, оно передается вызывающей
     * стороне композитной функции.
     *
     * @param <V> тип результата функции {@code after} и новой композитной функции
     * @param after функция, которую нужно применить после этой функции
     * @return композитная функция, которая сначала применяет эту функцию, а затем
     *         применяет функцию {@code after}
     * @throws NullPointerException если {@code after} равно null
     */
    default <V> TriFunction<F, S, T, V> andThen(Function<? super R, ? extends V> after) {
        Objects.requireNonNull(after);
        return (F f, S s, T t) -> after.apply(apply(f, s, t));
    }
}
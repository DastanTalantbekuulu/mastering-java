/*
 * Created By: Pranshu Shrivastava

 * All rights reserved.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.github.spranshu1.common.util.collection;

import com.github.spranshu1.common.util.Assert;
import com.github.spranshu1.common.util.object.ObjectUtil;

import java.io.Serializable;
import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * The Class CollectionUtils.
 */
public final class CollectionUtil {

    /**
     * Converts list of one type T to another type U, useful when converting large list of objects to another list.<br>
     * As this function utilizes the power of lambda function with stream api supported in java 1.8
     * <p>
     * Example,
     * <pre><code>
     * 	// Create converter function
     * 	private static Function&lt;Employee, User&gt; empToUser = new Function&lt;Employee, User&gt;() {
     * 		public User apply(final Employee emp) {
     * 	 		final User user = new User();
     * 	 		user.setName(emp.getName());
     * 	 		user.setEmail(emp.getEmail());
     * 	 		return user;
     *        }
     *
     *    };
     *
     * 	// Create Employee list
     * 	List&lt;Employee&gt; empList = new ArrayList&lt;&gt;();
     * 	empList.add(new Employee("Ravi","ravi@example.com"));
     *
     * 	// Convert list of Employees to list of Users
     * 	List&lt;User&gt; users = CollectionUtils.<b>convertList</b>(empList, empToUser);
     *
     * 	</code></pre>
     *
     * @param <T>  the type of the value
     * @param <U>  the result type
     * @param from target list
     * @param func converter function
     * @return List of U type
     * @see Function
     * @see Stream
     */
    public static <T, U> List<U> convertList(final List<T> from, final Function<T, U> func) {
        return from.stream().map(func).collect(Collectors.toList());
    }

    /**
     * Checks if the given List is null or empty.
     *
     * @param list input list to be checked
     * @return boolean, <br>
     * true - if given list is null or empty,<br>
     * false - otherwise
     */
    public static boolean listIsEmpty(final List<?> list) {
        return list == null || list.isEmpty();
    }


    /**
     * Checks if the given List contains any node (i.e. not null and not empty).
     *
     * @param list input list to be checked
     * @return boolean, <br>
     * true - if given list contains any node,<br>
     * false - otherwise
     */
    public static boolean listIsNotEmpty(final List<?> list) {
        return !listIsEmpty(list);
    }

    /**
     * Return {@code true} if the supplied Collection is {@code null} or empty.
     * Otherwise, return {@code false}.
     *
     * @param collection the Collection to check
     * @return whether the given Collection is empty
     */
    public static boolean isEmpty(Collection<?> collection) {
        return (collection == null || collection.isEmpty());
    }

    /**
     * Return {@code true} if the supplied Map is {@code null} or empty.
     * Otherwise, return {@code false}.
     *
     * @param map the Map to check
     * @return whether the given Map is empty
     */
    public static boolean isEmpty(Map<?, ?> map) {
        return (map == null || map.isEmpty());
    }

    /**
     * Convert the supplied array into a List. A primitive array gets converted
     * into a List of the appropriate wrapper type.
     * <p><b>NOTE:</b> Generally prefer the standard {@link Arrays#asList} method.
     * This {@code arrayToList} method is just meant to deal with an incoming Object
     * value that might be an {@code Object[]} or a primitive array at runtime.
     * <p>A {@code null} source value will be converted to an empty List.
     *
     * @param source the (potentially primitive) array
     * @return the converted List result
     * @see ObjectUtil#toObjectArray(Object)
     * @see Arrays#asList(Object[])
     */
    @SuppressWarnings("rawtypes")
    public static List arrayToList(Object source) {
        return Arrays.asList(ObjectUtil.toObjectArray(source));
    }

    /**
     * Merge the given array into the given Collection.
     *
     * @param <E>        the element type
     * @param array      the array to merge (may be {@code null})
     * @param collection the target Collection to merge the array into
     */
    @SuppressWarnings("unchecked")
    public static <E> void mergeArrayIntoCollection(Object array, Collection<E> collection) {
        if (collection == null) {
            throw new IllegalArgumentException("Collection must not be null");
        }
        Object[] arr = ObjectUtil.toObjectArray(array);
        for (Object elem : arr) {
            collection.add((E) elem);
        }
    }

    /**
     * Merge the given Properties instance into the given Map,
     * copying all properties (key-value pairs) over.
     * <p>Uses {@code Properties.propertyNames()} to even catch
     * default properties linked into the original Properties instance.
     *
     * @param <K>   the key type
     * @param <V>   the value type
     * @param props the Properties instance to merge (may be {@code null})
     * @param map   the target Map to merge the properties into
     */
    @SuppressWarnings("unchecked")
    public static <K, V> void mergePropertiesIntoMap(Properties props, Map<K, V> map) {
        if (map == null) {
            throw new IllegalArgumentException("Map must not be null");
        }
        if (props != null) {
            for (Enumeration<?> en = props.propertyNames(); en.hasMoreElements(); ) {
                String key = (String) en.nextElement();
                Object value = props.get(key);
                if (value == null) {
                    // Allow for defaults fallback or potentially overridden accessor...
                    value = props.getProperty(key);
                }
                map.put((K) key, (V) value);
            }
        }
    }

    /**
     * Check whether the given Collection contains the given element instance.
     * <p>Enforces the given instance to be present, rather than returning
     * {@code true} for an equal element as well.
     *
     * @param collection the Collection to check
     * @param element    the element to look for
     * @return {@code true} if found, {@code false} else
     */
    public static boolean containsInstance(Collection<?> collection, Object element) {
        if (collection != null) {
            for (Object candidate : collection) {
                if (candidate == element || candidate.equals(element)) {
                    return true;
                }
            }
        }
        return false;
    }

    /**
     * Return {@code true} if any element in '{@code candidates}' is
     * contained in '{@code source}'; otherwise returns {@code false}.
     *
     * @param source     the source Collection
     * @param candidates the candidates to search for
     * @return whether any of the candidates has been found
     */
    public static boolean containsAny(Collection<?> source, Collection<?> candidates) {
        if (isEmpty(source) || isEmpty(candidates)) {
            return false;
        }
        for (Object candidate : candidates) {
            if (source.contains(candidate)) {
                return true;
            }
        }
        return false;
    }

    /**
     * Return the first element in '{@code candidates}' that is contained in
     * '{@code source}'. If no element in '{@code candidates}' is present in
     * '{@code source}' returns {@code null}. Iteration order is
     * {@link Collection} implementation specific.
     *
     * @param <E>        the element type
     * @param source     the source Collection
     * @param candidates the candidates to search for
     * @return the first present object, or {@code null} if not found
     */
    @SuppressWarnings("unchecked")
    public static <E> E findFirstMatch(Collection<?> source, Collection<E> candidates) {
        if (isEmpty(source) || isEmpty(candidates)) {
            return null;
        }
        for (Object candidate : candidates) {
            if (source.contains(candidate)) {
                return (E) candidate;
            }
        }
        return null;
    }

    /**
     * Find a single value of the given type in the given Collection.
     *
     * @param <T>        the generic type
     * @param collection the Collection to search
     * @param type       the type to look for
     * @return first value of the given type found if there is a clear match,
     * or {@code null} if none
     */
    @SuppressWarnings("unchecked")
    public static <T> T findInstanceOfType(Collection<?> collection, Class<T> type) {
        if (isEmpty(collection)) {
            return null;
        }
        T value = null;
        for (Object element : collection) {
            if (type == null || type.isInstance(element)) {
                if (value != null) {
                    // value found.
                    break;
                }
                value = (T) element;
            }
        }
        return value;
    }

    /**
     * Find a single value of the given type in the given Collection.
     *
     * @param <T>        the generic type
     * @param collection the Collection to search
     * @param type       the type to look for
     * @return a value of the given type found if there is a clear match,
     * or {@code null} if none or more than one such value found
     */
    @SuppressWarnings("unchecked")
    public static <T> T findValueOfType(Collection<?> collection, Class<T> type) {
        if (isEmpty(collection)) {
            return null;
        }
        T value = null;
        for (Object element : collection) {
            if (type == null || type.isInstance(element)) {
                if (value != null) {
                    // More than one value found... no clear single value.
                    return null;
                }
                value = (T) element;
            }
        }
        return value;
    }

    /**
     * Adapt a {@code Map<K, List<V>>} to an {@code MultiValueMap<K, V>}.
     *
     * @param <K> the key type
     * @param <V> the value type
     * @param map the original map
     * @return the multi-value map
     * @since 3.1
     */
    public static <K, V> MultiValueMap<K, V> toMultiValueMap(Map<K, List<V>> map) {
        return new MultiValueMapAdapter<>(map);
    }

    /**
     * Adapts a Map to the MultiValueMap contract.
     *
     * @param <K> the key type
     * @param <V> the value type
     */
    @SuppressWarnings("serial")
    private static class MultiValueMapAdapter<K, V> implements MultiValueMap<K, V>, Serializable {

        /**
         * The map.
         */
        private final Map<K, List<V>> map;

        /**
         * Instantiates a new multi value map adapter.
         *
         * @param map the map
         */
        public MultiValueMapAdapter(Map<K, List<V>> map) {
            Assert.notEmpty(map, "'map' must not be null");
            this.map = map;
        }

        /**
         * Adds the.
         *
         * @param key   the key
         * @param value the value
         */
        @Override
        public void add(K key, V value) {
            List<V> values = this.map.computeIfAbsent(key, k -> new LinkedList<V>());
            values.add(value);
        }

        /**
         * Gets the first.
         *
         * @param key the key
         * @return the first
         */
        @Override
        public V getFirst(K key) {
            List<V> values = this.map.get(key);
            return (values != null ? values.get(0) : null);
        }

        /**
         * Sets the.
         *
         * @param key   the key
         * @param value the value
         */
        @Override
        public void set(K key, V value) {
            List<V> values = new LinkedList<>();
            values.add(value);
            this.map.put(key, values);
        }

        /**
         * Sets the all.
         *
         * @param values the values
         */
        @Override
        public void setAll(Map<K, V> values) {
            for (Entry<K, V> entry : values.entrySet()) {
                set(entry.getKey(), entry.getValue());
            }
        }

        /**
         * To single value map.
         *
         * @return the map
         */
        @Override
        public Map<K, V> toSingleValueMap() {
            LinkedHashMap<K, V> singleValueMap = new LinkedHashMap<>(this.map.size());
            for (Entry<K, List<V>> entry : map.entrySet()) {
                singleValueMap.put(entry.getKey(), entry.getValue().get(0));
            }
            return singleValueMap;
        }

        /**
         * Size.
         *
         * @return the int
         */
        @Override
        public int size() {
            return this.map.size();
        }

        /**
         * Checks if is empty.
         *
         * @return true, if is empty
         */
        @Override
        public boolean isEmpty() {
            return this.map.isEmpty();
        }

        /**
         * Contains key.
         *
         * @param key the key
         * @return true, if successful
         */
        @Override
        public boolean containsKey(Object key) {
            return this.map.containsKey(key);
        }

        /**
         * Contains value.
         *
         * @param value the value
         * @return true, if successful
         */
        @Override
        public boolean containsValue(Object value) {
            return this.map.containsValue(value);
        }

        /**
         * Gets the.
         *
         * @param key the key
         * @return the list
         */
        @Override
        public List<V> get(Object key) {
            return this.map.get(key);
        }

        /**
         * Put.
         *
         * @param key   the key
         * @param value the value
         * @return the list
         */
        @Override
        public List<V> put(K key, List<V> value) {
            return this.map.put(key, value);
        }

        /**
         * Removes the.
         *
         * @param key the key
         * @return the list
         */
        @Override
        public List<V> remove(Object key) {
            return this.map.remove(key);
        }

        /**
         * Put all.
         *
         * @param map the map
         */
        @Override
        public void putAll(Map<? extends K, ? extends List<V>> map) {
            this.map.putAll(map);
        }

        /**
         * Clear.
         */
        @Override
        public void clear() {
            this.map.clear();
        }

        /**
         * Key set.
         *
         * @return the sets the
         */
        @Override
        public Set<K> keySet() {
            return this.map.keySet();
        }

        /**
         * Values.
         *
         * @return the collection
         */
        @Override
        public Collection<List<V>> values() {
            return this.map.values();
        }

        /**
         * Entry set.
         *
         * @return the sets the
         */
        @Override
        public Set<Entry<K, List<V>>> entrySet() {
            return this.map.entrySet();
        }

        /**
         * Equals.
         *
         * @param other the other
         * @return true, if successful
         */
        @Override
        public boolean equals(final Object other) {
            if (this == other) {
                return true;
            }
            return map.equals(other);
        }

        /**
         * Hash code.
         *
         * @return the int
         */
        @Override
        public int hashCode() {
            return this.map.hashCode();
        }

        /**
         * To string.
         *
         * @return the string
         */
        @Override
        public String toString() {
            return this.map.toString();
        }
    }


}

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
package com.github.spranshu1.common.util.test.collection;

import com.github.spranshu1.common.util.collection.CollectionUtil;
import org.junit.Assert;
import org.junit.Test;


import java.util.*;
import java.util.function.Function;
import java.util.function.Predicate;


/**
 * The type Collection utils test.
 */
public class CollectionUtilsTest {


    /**
     * Convert String to Integer.
     */
    private static final Function<String, Integer> strToInt = new Function<String, Integer>() {

        /**
         * Apply conversion of String to Integer
         */
        @Override
        public Integer apply(final String value) {
            return Integer.parseInt(value);
        }

    };

    /**
     * Test convert list.
     */
    @Test
    public void testConvertList() {
        List<String> strings = new ArrayList<>();
        strings.add("1");
        strings.add("13");
        strings.add("15");

        List<Integer> numbers = CollectionUtil.convertList(strings, strToInt);

        Assert.assertNotNull("Unable to convert given list",numbers);
    }

    /**
     * Tes list is empty.
     */
    @Test
    public void tesListIsEmpty() {
        List<String> strings = new ArrayList<>();

        Assert.assertTrue(CollectionUtil.listIsEmpty(strings));
    }

    /**
     * Tes list is not empty.
     */
    @Test
    public void tesListIsNotEmpty() {
        List<String> strings = new ArrayList<>();
        strings.add("1");
        strings.add("13");
        strings.add("15");
        Assert.assertTrue(CollectionUtil.listIsNotEmpty(strings));
    }

    /**
     * Test collection is empty.
     */
    @Test
    public void testCollectionIsEmpty() {
        List<String> strings = new ArrayList<>();
        Set<String> data = new HashSet<>();

        Assert.assertTrue(CollectionUtil.isEmpty(strings));
        Assert.assertTrue(CollectionUtil.isEmpty(data));
    }

    /**
     * Test array to list.
     */
    @Test
    public void testArrayToList() {
        Object[] obj = new Object[3];
        obj[0] = "first";
        obj[1] = "second";
        obj[2] = "third";
        List list = CollectionUtil.arrayToList(obj);

        Predicate<List> check = x -> x.size() == 3;

        Assert.assertNotNull(list);
        Assert.assertTrue(check.test(list));
    }

    /**
     * Test merge array to collection.
     */
    @Test
    public void testMergeArrayToCollection() {
        int[] arr = new int[3];
        arr[0] = 1;
        arr[1] = 2;
        arr[2] = 3;

        Set data = new HashSet();

        CollectionUtil.mergeArrayIntoCollection(arr, data);
        Predicate<Set> check = x -> x.size() == 3;
        Assert.assertTrue(check.test(data));
    }

    /**
     * Test merge property map.
     */
    @Test
    public void testMergePropertyMap(){
        Properties prop = new Properties(System.getProperties());

        Map<String, String> data = new HashMap<>();

        CollectionUtil.mergePropertiesIntoMap(prop, data);

        Predicate<Map<String, String>> check = x -> x.containsKey("java.specification.version");

        Assert.assertTrue(check.test(data));
    }

    /**
     * Test contains instance.
     */
    @Test
    public void testContainsInstance(){
        Set<String> data = new HashSet<>();
        data.add("test");
        boolean result = CollectionUtil.containsInstance(data,"test");

        Assert.assertTrue(result);
    }

    /**
     * Test contains any.
     */
    @Test
    public void testContainsAny(){
        Set<String> source = new HashSet<>();
        source.add("one");
        source.add("two");

        List<String> candidate = new ArrayList<>();
        candidate.add("two");
        candidate.add("three");

        boolean result = CollectionUtil.containsAny(source,candidate);

        Assert.assertTrue(result);
    }


    /**
     * Test find first match.
     */
    @Test
    public void testFindFirstMatch(){
        Set<String> source = new HashSet<>();
        source.add("one");
        source.add("two");
        source.add("three");

        List<String> candidate = new ArrayList<>();
        candidate.add("two");
        candidate.add("three");

        String result = CollectionUtil.findFirstMatch(source,candidate);

        Assert.assertEquals("two",result);
    }



}

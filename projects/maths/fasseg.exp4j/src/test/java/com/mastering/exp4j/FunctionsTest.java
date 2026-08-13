/*
 * Copyright 2014 Frank Asseg
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.mastering.exp4j;

import com.mastering.exp4j.function.Function;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

public class FunctionsTest {

    @Test
    public void testFunctionNameNull() {
        assertThatThrownBy(() -> new Function(null) {
            @Override
            public double apply(double... args) {
                return 0;
            }
        }).isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    public void testFunctionNameEmpty() {
        assertThatThrownBy(() -> new Function("") {
            @Override
            public double apply(double... args) {
                return 0;
            }
        }).isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    public void testFunctionNameZeroArgs() {
        Function f = new Function("foo", 0) {
            @Override
            public double apply(double... args) {
                return 0;
            }
        };
        assertThat(f.apply()).isEqualTo(0.0);
    }

    @Test
    public void testFunctionNameNegativeArgs() {
        assertThatThrownBy(() -> new Function("foo", -1) {
            @Override
            public double apply(double... args) {
                return 0;
            }
        }).isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    public void testIllegalFunctionName1() {
        assertThatThrownBy(() -> new Function("1foo") {
            @Override
            public double apply(double... args) {
                return 0;
            }
        }).isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    public void testIllegalFunctionName2() {
        assertThatThrownBy(() -> new Function("_&oo") {
            @Override
            public double apply(double... args) {
                return 0;
            }
        }).isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    public void testIllegalFunctionName3() {
        assertThatThrownBy(() -> new Function("o+o") {
            @Override
            public double apply(double... args) {
                return 0;
            }
        }).isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    public void testGetAllowedFunctionChars() {
        char[] chars = Function.getAllowedFunctionCharacters();

        // Проверяем общее количество разрешенных символов
        assertThat(chars).hasSize(53);

        // Проверяем наличие всех строчных букв
        for (char c = 'a'; c <= 'z'; c++) {
            assertThat(chars).contains(c);
        }

        // Проверяем наличие всех заглавных букв
        for (char c = 'A'; c <= 'Z'; c++) {
            assertThat(chars).contains(c);
        }

        // Проверяем наличие знака подчеркивания
        assertThat(chars).contains('_');
    }

    @Test
    public void testCheckFunctionNames() {
        // Valid names
        assertThat(Function.isValidFunctionName("log")).isTrue();
        assertThat(Function.isValidFunctionName("sin")).isTrue();
        assertThat(Function.isValidFunctionName("abz")).isTrue();
        assertThat(Function.isValidFunctionName("alongfunctionnamecanhappen")).isTrue();
        assertThat(Function.isValidFunctionName("_log")).isTrue();
        assertThat(Function.isValidFunctionName("__blah")).isTrue();
        assertThat(Function.isValidFunctionName("foox")).isTrue();
        assertThat(Function.isValidFunctionName("aZ")).isTrue();
        assertThat(Function.isValidFunctionName("Za")).isTrue();
        assertThat(Function.isValidFunctionName("ZZaa")).isTrue();
        assertThat(Function.isValidFunctionName("_")).isTrue();
        assertThat(Function.isValidFunctionName("log2")).isTrue();
        assertThat(Function.isValidFunctionName("lo32g2")).isTrue();
        assertThat(Function.isValidFunctionName("_o45g2")).isTrue();

        // Invalid names
        assertThat(Function.isValidFunctionName("&")).isFalse();
        assertThat(Function.isValidFunctionName("_+log")).isFalse();
        assertThat(Function.isValidFunctionName("_k&l")).isFalse();
        assertThat(Function.isValidFunctionName("k&l")).isFalse();
        assertThat(Function.isValidFunctionName("+log")).isFalse();
        assertThat(Function.isValidFunctionName("fo-o")).isFalse();
        assertThat(Function.isValidFunctionName("log+")).isFalse();
        assertThat(Function.isValidFunctionName("perc%")).isFalse();
        assertThat(Function.isValidFunctionName("del$a")).isFalse();
    }
}
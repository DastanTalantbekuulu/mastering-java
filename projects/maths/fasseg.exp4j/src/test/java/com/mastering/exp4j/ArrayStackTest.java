/*
 * Copyright 2015 Federico Vera
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

import org.junit.jupiter.api.Test;

import java.util.EmptyStackException;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

/**
 * @author Federico Vera (dktcoding [at] gmail)
 */
public class ArrayStackTest {

    @Test
    public void testConstructor_InvalidSize() {
        assertThatThrownBy(() -> new ArrayStack(-1))
                .isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    public void testPushNoSize() {
        ArrayStack stack = new ArrayStack();

        stack.push(0);
        stack.push(1);
        stack.push(3);

        assertThat(stack.size()).isEqualTo(3);
    }

    @Test
    public void testPushLessSize() {
        ArrayStack stack = new ArrayStack(5);

        for (int i = 0; i < 5; i++) {
            stack.push(i);
        }

        assertThat(stack.size()).isEqualTo(5);
    }

    @Test
    public void testPeek() {
        ArrayStack stack = new ArrayStack(5);

        for (int i = 0; i < 5; i++) {
            stack.push(i);
        }

        assertThat(stack.peek()).isEqualTo(4d);
        assertThat(stack.peek()).isEqualTo(4d);
        assertThat(stack.peek()).isEqualTo(4d);
    }

    @Test
    public void testPeek2() {
        ArrayStack stack = new ArrayStack(5);
        stack.push(-1);
        double old = -1;

        for (int i = 0; i < 5; i++) {
            assertThat(stack.peek()).isEqualTo(old);
            stack.push(i);
            old = i;
            assertThat(stack.peek()).isEqualTo(old);
        }
    }

    @Test
    public void testPeekNoData() {
        ArrayStack stack = new ArrayStack(5);

        assertThatThrownBy(stack::peek)
                .isInstanceOf(EmptyStackException.class);
    }

    @Test
    public void testPop() {
        ArrayStack stack = new ArrayStack(5);

        for (int i = 0; i < 5; i++) {
            stack.push(i);
        }

        while (!stack.isEmpty()) {
            stack.pop();
        }

        assertThat(stack.isEmpty()).isTrue();
    }

    @Test
    public void testPop2() {
        ArrayStack stack = new ArrayStack(5);

        for (int i = 0; i < 5; i++) {
            stack.push(i);
        }

        // Пытаемся извлечь больше элементов, чем есть
        assertThatThrownBy(() -> {
            while (true) {
                stack.pop();
            }
        }).isInstanceOf(EmptyStackException.class);
    }

    @Test
    public void testPop3() {
        ArrayStack stack = new ArrayStack(5);

        for (int i = 0; i < 5; i++) {
            stack.push(i);
            assertThat(stack.size()).isEqualTo(1);
            assertThat(stack.pop()).isEqualTo((double) i);
        }

        assertThat(stack.size()).isZero();
        assertThat(stack.isEmpty()).isTrue();
    }

    @Test
    public void testPopNoData() {
        ArrayStack stack = new ArrayStack(5);

        assertThatThrownBy(stack::pop)
                .isInstanceOf(EmptyStackException.class);
    }

    @Test
    public void testIsEmpty() {
        ArrayStack stack = new ArrayStack(5);
        assertThat(stack.isEmpty()).isTrue();

        stack.push(4);
        assertThat(stack.isEmpty()).isFalse();

        stack.push(4);
        assertThat(stack.isEmpty()).isFalse();

        stack.push(4);
        assertThat(stack.isEmpty()).isFalse();

        stack.pop();
        stack.pop();
        stack.pop();
        assertThat(stack.isEmpty()).isTrue();

        stack.push(4);
        assertThat(stack.isEmpty()).isFalse();

        stack.peek();
        assertThat(stack.isEmpty()).isFalse();

        stack.pop();
        assertThat(stack.isEmpty()).isTrue();
    }

    @Test
    public void testSize() {
        ArrayStack stack = new ArrayStack(5);
        assertThat(stack.size()).isZero();

        stack.push(4);
        assertThat(stack.size()).isEqualTo(1);

        stack.peek();
        assertThat(stack.size()).isEqualTo(1);

        stack.pop();
        assertThat(stack.size()).isZero();
    }
}
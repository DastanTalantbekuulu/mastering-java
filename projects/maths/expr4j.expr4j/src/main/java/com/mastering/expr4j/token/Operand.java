/**
 * Copyright 2023 Pratanu Mandal
 * <p>
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * <p>
 * http://www.apache.org/licenses/LICENSE-2.0
 * <p>
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *
 */

package com.mastering.expr4j.token;

/**
 * The <code>Operand&lt;T&gt;</code> class represents operands in the expression.<br>
 * It acts as a wrapper for value of type <code>T</code>.
 *
 * @param <T>   The type of operand
 * @param value Value of the operand.
 * @author Pratanu Mandal
 * @since 1.0
 */
public record Operand<T>(T value) implements Token {
}

/**
 * Copyright 2023 Pratanu Mandal
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
 *
 */

package com.mastering.expr4j.expression;

import lombok.RequiredArgsConstructor;

import java.util.Map;

/**
 * The <code>ExpressionParameter&lt;T&gt;</code> class represents a parameter of an operation.<br><br>
 *
 * @author Pratanu Mandal
 * @since 1.0
 *
 */
@RequiredArgsConstructor
public class ExpressionParameter<T> {

    /**
     * Expression to which this parameter belongs.
     */
    private final Expression<T> expression;

    /**
     * Node of the expression related to this parameter.
     */
    private final ExpressionNode node;

    /**
     * Map of variables.
     */
    private final Map<String, T> variables;

    /**
     * Result of evaluating this parameter.
     */
    private T result;

    /**
     * Get the evaluated result of this parameter.
     *
     * @return The evaluated result
     */
    public T value() {
        if (result == null) {
            result = expression.evaluate(node, variables).value();
        }
        return result;
    }

}

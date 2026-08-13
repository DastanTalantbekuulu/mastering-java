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


import com.mastering.expr4j.exception.Expr4jException;
import com.mastering.expr4j.expression.ExpressionParameter;

import java.util.List;

/**
 * The <code>Function&lt;T&gt;</code> class represents functions in the expression.
 *
 * @param <T>        The type of operand
 * @param label      Label of the function.
 * @param parameters Number of parameters.
 * @param operation  Operation performed by the function.
 * @author Pratanu Mandal
 * @since 1.0
 */
public record Function<T>(String label, int parameters, Operation<T> operation) implements Token {

    /**
     * Constant to indicate that the function supports variable number of parameters.
     */
    public static final int VARIABLE_PARAMETERS = -1;

    /**
     * Parameterized constructor.
     *
     * @param label      Label of the function
     * @param parameters Number of parameters
     * @param operation  Operation performed by the function
     */
    public Function(String label, int parameters, Operation<T> operation) {
        this.label = label;
        this.parameters = parameters;
        this.operation = operation;

        if (this.parameters < Function.VARIABLE_PARAMETERS) {
            throw new Expr4jException("Invalid number of parameters: " + this.parameters);
        }
    }

    /**
     * Parameterized constructor.<br>
     * This constructor creates a function with variable number of parameters.
     *
     * @param label     Label of the function
     * @param operation Operation performed by the function
     */
    public Function(String label, Operation<T> operation) {
        this(label, VARIABLE_PARAMETERS, operation);
    }

    /**
     * Evaluate the function lazily.
     *
     * @param parameters List of parameters
     * @return Evaluated result
     */
    public T evaluate(List<ExpressionParameter<T>> parameters) {
        return operation.execute(parameters);
    }
}

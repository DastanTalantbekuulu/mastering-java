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
 * The <code>Operator&lt;T&gt;</code> class represents operators in the expression.
 *
 * @param <T>        The type of operand
 * @param label      Label of the operator.
 * @param type       The type of operator.
 * @param precedence The precedence of this operator.<br>
 *                   Precedence ranges from 1 to MAX_INT, in ascending order, i.e, with 1 being lowest precedence possible.
 * @param operation  Operation performed by the operation.
 * @author Pratanu Mandal
 * @since 1.0
 */
public record Operator<T>(String label,
                          OperatorType type,
                          int precedence,
                          Operation<T> operation) implements Token, Comparable<Operator<T>> {

    /**
     * Parameterized constructor.
     *
     * @param label      Label of the operator
     * @param type       Type of the operator
     * @param precedence Precedence of the operator
     * @param operation  Operation performed by the operator
     */
    public Operator(String label, OperatorType type, int precedence, Operation<T> operation) {
        this.label = label;
        this.type = type;
        this.precedence = precedence;
        this.operation = operation;

        if (this.precedence < 1) {
            throw new Expr4jException("Invalid precedence: " + this.precedence);
        }
    }

    /**
     * Evaluate the function lazily.
     *
     * @param parameters List of parameters
     * @return Evaluated result
     */
    public T evaluate(List<ExpressionParameter<T>> parameters) {
        return this.operation.execute(parameters);
    }

    /**
     * Method to compare this operator to another operator on the basis of precedence.
     */
    @Override
    public int compareTo(Operator<T> other) {
        // compare the precedences and associativity of the two operators
        return (precedence == other.precedence) ?
                (type == OperatorType.INFIX || type == OperatorType.POSTFIX ? 1 : -1)
                : other.precedence - precedence;
    }

    @Override
    public String toString() {
        return "Operator{" +
                "label='" + label + '\'' +
                ", type=" + type +
                ", precedence=" + precedence +
                '}';
    }

}

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

import com.mastering.exp4j.function.Functions;
import com.mastering.exp4j.operator.Operator;
import com.mastering.exp4j.operator.Operators;
import com.mastering.exp4j.tokenizer.FunctionToken;
import com.mastering.exp4j.tokenizer.NumberToken;
import com.mastering.exp4j.tokenizer.OperatorToken;
import com.mastering.exp4j.tokenizer.Token;
import com.mastering.exp4j.tokenizer.VariableToken;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

public class ExpressionTest {

    @Test
    public void testExpression1() {
        Token[] tokens = new Token[]{
                new NumberToken(3d),
                new NumberToken(2d),
                new OperatorToken(Operators.getBuiltinOperator('+', 2))
        };
        Expression exp = new Expression(tokens);
        assertThat(exp.evaluate()).isEqualTo(5d);
    }

    @Test
    public void testExpression2() {
        Token[] tokens = new Token[]{
                new NumberToken(1d),
                new FunctionToken(Functions.getBuiltinFunction("log")),
        };
        Expression exp = new Expression(tokens);
        assertThat(exp.evaluate()).isEqualTo(0d);
    }

    @Test
    public void testGetVariableNames1() {
        Token[] tokens = new Token[]{
                new VariableToken("a"),
                new VariableToken("b"),
                new OperatorToken(Operators.getBuiltinOperator('+', 2))
        };
        Expression exp = new Expression(tokens);

        assertThat(exp.getVariableNames()).hasSize(2);
    }

    @Test
    public void testFactorial() {
        Operator factorial = new Operator("!", 1, true, Operator.PRECEDENCE_POWER + 1) {
            @Override
            public double apply(double... args) {
                final int arg = (int) args[0];
                if ((double) arg != args[0]) {
                    throw new IllegalArgumentException("Operand for factorial has to be an integer");
                }
                if (arg < 0) {
                    throw new IllegalArgumentException("The operand of the factorial can not be less than zero");
                }
                double result = 1;
                for (int i = 1; i <= arg; i++) {
                    result *= i;
                }
                return result;
            }
        };

        Expression e = new ExpressionBuilder("2!+3!")
                .operator(factorial)
                .build();
        assertThat(e.evaluate()).isEqualTo(8d);

        e = new ExpressionBuilder("3!-2!")
                .operator(factorial)
                .build();
        assertThat(e.evaluate()).isEqualTo(4d);

        e = new ExpressionBuilder("3!")
                .operator(factorial)
                .build();
        assertThat(e.evaluate()).isEqualTo(6d);

        e = new ExpressionBuilder("3!!")
                .operator(factorial)
                .build();
        assertThat(e.evaluate()).isEqualTo(720d);

        e = new ExpressionBuilder("4 + 3!")
                .operator(factorial)
                .build();
        assertThat(e.evaluate()).isEqualTo(10d);

        e = new ExpressionBuilder("3! * 2")
                .operator(factorial)
                .build();
        assertThat(e.evaluate()).isEqualTo(12d);

        e = new ExpressionBuilder("3!")
                .operator(factorial)
                .build();
        assertThat(e.validate().valid()).isTrue();
        assertThat(e.evaluate()).isEqualTo(6d);

        e = new ExpressionBuilder("3!!")
                .operator(factorial)
                .build();
        assertThat(e.validate().valid()).isTrue();
        assertThat(e.evaluate()).isEqualTo(720d);

        e = new ExpressionBuilder("4 + 3!")
                .operator(factorial)
                .build();
        assertThat(e.validate().valid()).isTrue();
        assertThat(e.evaluate()).isEqualTo(10d);

        e = new ExpressionBuilder("3! * 2")
                .operator(factorial)
                .build();
        assertThat(e.validate().valid()).isTrue();
        assertThat(e.evaluate()).isEqualTo(12d);

        e = new ExpressionBuilder("2 * 3!")
                .operator(factorial)
                .build();
        assertThat(e.validate().valid()).isTrue();
        assertThat(e.evaluate()).isEqualTo(12d);

        e = new ExpressionBuilder("4 + (3!)")
                .operator(factorial)
                .build();
        assertThat(e.validate().valid()).isTrue();
        assertThat(e.evaluate()).isEqualTo(10d);

        e = new ExpressionBuilder("4 + 3! + 2 * 6")
                .operator(factorial)
                .build();
        assertThat(e.validate().valid()).isTrue();
        assertThat(e.evaluate()).isEqualTo(22d);
    }

    @Test
    public void testCotangent1() {
        Expression e = new ExpressionBuilder("cot(1)")
                .build();
        assertThat(e.evaluate()).isEqualTo(1 / Math.tan(1));
    }

    @Test
    public void testInvalidCotangent1() {
        Expression e = new ExpressionBuilder("cot(0)")
                .build();

        assertThatThrownBy(e::evaluate)
                .isInstanceOf(ArithmeticException.class);
    }

    @Test
    public void testOperatorFactorial2() {
        Operator factorial = new Operator("!", 1, true, Operator.PRECEDENCE_POWER + 1) {
            @Override
            public double apply(double... args) {
                return 0; // Simplified for this test case
            }
        };

        // Expect exception either during build or validation
        assertThatThrownBy(() -> {
            Expression e = new ExpressionBuilder("!3").operator(factorial).build();
            if (!e.validate().valid()) {
                throw new IllegalArgumentException("Invalid expression");
            }
        }).isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    public void testInvalidFactorial2() {
        Operator factorial = new Operator("!", 1, true, Operator.PRECEDENCE_POWER + 1) {
            @Override
            public double apply(double... args) {
                return 0; // Simplified
            }
        };

        assertThatThrownBy(() -> {
            Expression e = new ExpressionBuilder("!!3").operator(factorial).build();
            if (!e.validate().valid()) {
                throw new IllegalArgumentException("Invalid expression");
            }
        }).isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    public void testClearVariables() {
        ExpressionBuilder builder = new ExpressionBuilder("x + y");
        builder.variable("x");
        builder.variable("y");

        Expression expression = builder.build();
        Map<String, Double> values = new HashMap<>();
        values.put("x", 1.0);
        values.put("y", 2.0);
        expression.setVariables(values);

        double result = expression.evaluate();
        assertThat(result).isEqualTo(3.0);

        expression.clearVariables();

        // Should fail as there aren't values in the expression
        assertThatThrownBy(expression::evaluate)
                .isInstanceOf(IllegalArgumentException.class); // Assuming IllegalArgumentException for missing vars

        Map<String, Double> emptyMap = new HashMap<>();
        expression.setVariables(emptyMap);

        // Should fail as there aren't values in the expression
        assertThatThrownBy(expression::evaluate)
                .isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    @Disabled("If Expression should be thread safe this test must pass. Currently it is not thread safe.")
    public void evaluateFamily() {
        final Expression e = new ExpressionBuilder("sin(x)")
                .variable("x")
                .build();
        ExecutorService executor = Executors.newFixedThreadPool(100);

        try {
            for (int i = 0; i < 100000; i++) {
                executor.execute(() -> {
                    double x = Math.random();
                    e.setVariable("x", x);
                    try {
                        Thread.sleep(100);
                    } catch (InterruptedException e1) {
                        e1.printStackTrace();
                    }
                    assertThat(e.evaluate()).isEqualTo(Math.sin(x));
                });
            }
        } finally {
            executor.shutdown();
        }
    }
}
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

package com.mastering.expr4j;

import com.mastering.expr4j.expression.Expression;
import com.mastering.expr4j.expression.ExpressionBuilder;
import com.mastering.expr4j.expression.ExpressionConfig;
import com.mastering.expr4j.expression.ExpressionDictionary;
import com.mastering.expr4j.token.Function;
import com.mastering.expr4j.token.Operator;
import com.mastering.expr4j.token.OperatorType;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import lombok.Getter;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CompositeTest {

    public static double DELTA = 0.00000000001;

    static class Composite {

        @Getter
        private final Type type;
        private Double number;
        private boolean condition;

        public Composite(Integer number) {
            type = Type.NUMBER;
            this.number = number.doubleValue();
        }

        public Composite(Double number) {
            type = Type.NUMBER;
            this.number = number;
        }

        public Composite(boolean condition) {
            type = Type.CONDITION;
            this.condition = condition;
        }

        public int intValue() {
            return number.intValue();
        }

        public double doubleValue() {
            return number;
        }

        public Boolean booleanValue() {
            return condition;
        }

        @Override
        public String toString() {
            if (this.getType() == Type.NUMBER) {
                return this.doubleValue() == this.intValue() ?
                        String.valueOf(this.intValue()) :
                        String.valueOf(this.doubleValue());
            } else if (this.getType() == Type.CONDITION) {
                return String.valueOf(this.booleanValue());
            }
            return null;
        }
    }

    enum Type {
        NUMBER,
        CONDITION
    }

    private final ExpressionBuilder<Composite> builder;

    public CompositeTest() {
        builder = new ExpressionBuilder<>(new ExpressionConfig<>() {
            @Override
            protected Composite stringToOperand(String operand) {
                if (operand.equals("true")) {
                    return new Composite(true);
                } else if (operand.equals("false")) {
                    return new Composite(false);
                } else {
                    return new Composite(Double.parseDouble(operand));
                }
            }

            @Override
            protected String operandToString(Composite operand) {
                return operand.toString();
            }

            @Override
            protected List<String> getOperandPattern() {
                List<String> list = super.getOperandPattern();
                list.addAll(Arrays.asList("true", "false"));
                return list;
            }
        });

        ExpressionDictionary<Composite> expressionDictionary = builder.getExpressionDictionary();
//        ExpressionConfig<Composite> expressionConfig = builder.getExpressionConfig();

        expressionDictionary.addOperator(new Operator<>("+", OperatorType.INFIX, 1, (parameters) ->
                new Composite(parameters.getFirst().value().doubleValue() + parameters.get(1).value().doubleValue())));

        expressionDictionary.addOperator(new Operator<>("-", OperatorType.INFIX, 1, (parameters) ->
                new Composite(parameters.getFirst().value().doubleValue() - parameters.get(1).value().doubleValue())));

        expressionDictionary.addOperator(new Operator<>("<", OperatorType.INFIX, 1, (parameters) ->
                new Composite(parameters.getFirst().value().doubleValue() < parameters.get(1).value().doubleValue())));

        expressionDictionary.addFunction(new Function<>("if", 3, (parameters) -> {
            Composite choice = parameters.getFirst().value();
            if (choice.booleanValue()) return parameters.get(1).value();
            else return parameters.get(2).value();
        }));

        expressionDictionary.addFunction(new Function<>("switch", (parameters) -> {
            Composite choice = parameters.getFirst().value();
            return parameters.get(choice.intValue()).value();
        }));
    }

    private void assertEquals(double expected, double actual) {
        Assertions.assertEquals(expected, actual, DELTA);
    }

    @Test
    public void test1() {
        Map<String, Composite> variables = new HashMap<>();
        variables.put("x", new Composite(0.6));

        Composite expected = new Composite(1.0);
        String expectedString = "if(x < 0.5, 0, 1)";

        Expression<Composite> expression = builder.build("if(x < 0.5, 0, 1)");

        Composite actual = expression.evaluate(variables);
        String actualString = expression.toString();

        this.assertEquals(expected.doubleValue(), actual.doubleValue());
        Assertions.assertEquals(expectedString, actualString);
    }

    @Test
    public void test2() {
        Map<String, Composite> variables = new HashMap<>();
        variables.put("x", new Composite(3));

        Composite expected = new Composite(8);
        String expectedString = "switch(x, x - 5, x, x + 5)";

        Expression<Composite> expression = builder.build("switch(x, x - 5, x, x + 5)");

        Composite actual = expression.evaluate(variables);
        String actualString = expression.toString();

        this.assertEquals(expected.doubleValue(), actual.doubleValue());
        Assertions.assertEquals(expectedString, actualString);
    }

    @Test
    public void test3() {
        Map<String, Composite> variables = new HashMap<>();
        variables.put("x", new Composite(0.6));

        Composite expected = new Composite(1.0);
        String expectedString = "if(x < 0.5, y, 1)";

        Expression<Composite> expression = builder.build("if(x < 0.5, y, 1)");

        Composite actual = expression.evaluate(variables);
        String actualString = expression.toString();

        this.assertEquals(expected.doubleValue(), actual.doubleValue());
        Assertions.assertEquals(expectedString, actualString);
    }

}

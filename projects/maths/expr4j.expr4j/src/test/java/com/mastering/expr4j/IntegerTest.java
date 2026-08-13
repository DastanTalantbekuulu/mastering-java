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
import com.mastering.expr4j.expression.ExpressionParameter;
import com.mastering.expr4j.token.Function;
import com.mastering.expr4j.token.Operator;
import com.mastering.expr4j.token.OperatorType;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class IntegerTest {

    protected ExpressionBuilder<Integer> builder;
    protected ExpressionDictionary<Integer> expressionDictionary;
    protected ExpressionConfig<Integer> expressionConfig;

    public IntegerTest() {
        builder = new ExpressionBuilder<>(new ExpressionConfig<>() {
            @Override
            protected Integer stringToOperand(String operand) {
                return Integer.parseInt(operand);
            }

            @Override
            protected String operandToString(Integer operand) {
                return String.valueOf(operand);
            }

            @Override
            protected List<String> getOperandPattern() {
                return List.of("\\d+");
            }
        });

        expressionDictionary = builder.getExpressionDictionary();
        expressionConfig = builder.getExpressionConfig();

        expressionDictionary.addOperator(new Operator<>("+", OperatorType.PREFIX, Integer.MAX_VALUE, (parameters) -> parameters.getFirst().value()));
        expressionDictionary.addOperator(new Operator<>("-", OperatorType.PREFIX, Integer.MAX_VALUE, (parameters) -> -parameters.getFirst().value()));

        expressionDictionary.addOperator(new Operator<>("+", OperatorType.INFIX, 1, (parameters) -> parameters.getFirst().value() + parameters.get(1).value()));
        expressionDictionary.addOperator(new Operator<>("-", OperatorType.INFIX, 1, (parameters) -> parameters.getFirst().value() - parameters.get(1).value()));

        expressionDictionary.addOperator(new Operator<>("*", OperatorType.INFIX, 2, (parameters) -> parameters.getFirst().value() * parameters.get(1).value()));
        expressionDictionary.addOperator(new Operator<>("/", OperatorType.INFIX, 2, (parameters) -> parameters.getFirst().value() / parameters.get(1).value()));
        expressionDictionary.addOperator(new Operator<>("%", OperatorType.INFIX, 2, (parameters) -> parameters.getFirst().value() % parameters.get(1).value()));

        expressionDictionary.addOperator(new Operator<>("^", OperatorType.INFIX_RTL, 3, (parameters) -> (int) Math.pow(parameters.getFirst().value(), parameters.get(1).value())));

        expressionDictionary.addFunction(new Function<>("add", (parameters) -> parameters.stream().map(ExpressionParameter::value).mapToInt(Integer::intValue).sum()));
    }

    @Test
    public void test1() {
        Integer expected = 0;
        String expectedString = "add(5 + 6, -11)";

        Expression<Integer> expression = builder.build("add(5 + 6, -11)");

        Integer actual = expression.evaluate();
        String actualString = expression.toString();

        Assertions.assertEquals(expected, actual);
        Assertions.assertEquals(expectedString, actualString);
    }

    @Test
    public void test2() {
        Integer expected = 2;
        String expectedString = "-2 * add(5 / 6, -1)";

        Expression<Integer> expression = builder.build("-2 add(5 / 6, -1)");

        Integer actual = expression.evaluate();
        String actualString = expression.toString();

        Assertions.assertEquals(expected, actual);
        Assertions.assertEquals(expectedString, actualString);
    }

    @Test
    public void test3() {
        Integer expected = -5;
        String expectedString = "---5";

        Expression<Integer> expression = builder.build("- - - 5");

        Integer actual = expression.evaluate();
        String actualString = expression.toString();

        Assertions.assertEquals(expected, actual);
        Assertions.assertEquals(expectedString, actualString);
    }

    @Test
    public void test4() {
        Integer expected = 0;
        String expectedString = "---5 - -add(--3, --2)";

        Expression<Integer> expression = builder.build("- - - 5 - - add ( - - 3 , - - 2 )");

        Integer actual = expression.evaluate();
        String actualString = expression.toString();

        Assertions.assertEquals(expected, actual);
        Assertions.assertEquals(expectedString, actualString);
    }

    @Test
    public void test5() {
        Integer expected = 11;
        String expectedString = "++5 + ++6";

        Expression<Integer> expression = builder.build("+ + 5 + + + 6");

        Integer actual = expression.evaluate();
        String actualString = expression.toString();

        Assertions.assertEquals(expected, actual);
        Assertions.assertEquals(expectedString, actualString);
    }

    @Test
    public void test6() {
        Integer expected = 9;
        String expectedString = "add(5, -add(-1, -3))";

        Expression<Integer> expression = builder.build("add (5 ,- add(- 1,-   3)    )");

        Integer actual = expression.evaluate();
        String actualString = expression.toString();

        Assertions.assertEquals(expected, actual);
        Assertions.assertEquals(expectedString, actualString);
    }

    @Test
    public void test7() {
        Integer expected = 12;
        String expectedString = "123 / 10";

        Expression<Integer> expression = builder.build("123/10");

        Integer actual = expression.evaluate();
        String actualString = expression.toString();

        Assertions.assertEquals(expected, actual);
        Assertions.assertEquals(expectedString, actualString);
    }

    @Test
    public void test8() {
        Map<String, Integer> variables = new HashMap<>();
        variables.put("x", 5);

        int expected = 8;
        String expectedString = "5 * x / 3";

        Expression<Integer> expression = builder.build("5x/3");

        int actual = expression.evaluate(variables);
        String actualString = expression.toString();

        Assertions.assertEquals(expected, actual);
        Assertions.assertEquals(expectedString, actualString);
    }

    @Test
    public void test9() {
        Map<String, Integer> variables = new HashMap<>();
        variables.put("x", 5);

        int expected = 6;
        String expectedString = "5 + x / 3";

        Expression<Integer> expression = builder.build("5+x/3");

        int actual = expression.evaluate(variables);
        String actualString = expression.toString();

        Assertions.assertEquals(expected, actual);
        Assertions.assertEquals(expectedString, actualString);
    }

    @Test
    public void test10() {
        Map<String, Integer> variables = new HashMap<>();
        variables.put("x", 5);

        int expected = -3118;
        String expectedString = "-add(x ^ x, 5 % 3, 0 - 9)";

        Expression<Integer> expression = builder.build("-add(x ^ x, 5 % 3, 0 - 9)");

        int actual = expression.evaluate(variables);
        String actualString = expression.toString();

        Assertions.assertEquals(expected, actual);
        Assertions.assertEquals(expectedString, actualString);
    }

}

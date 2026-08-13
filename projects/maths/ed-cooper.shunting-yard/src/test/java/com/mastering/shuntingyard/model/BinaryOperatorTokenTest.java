package com.mastering.shuntingyard.model;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class BinaryOperatorTokenTest {

    @Test
    public void testGetOperation() {
        BinaryOperator expectedOperator = new BinaryOperator("+", 1, true, Double::sum);
        BinaryOperatorToken token = new BinaryOperatorToken("+", expectedOperator);

        Operator actualOperator = token.getOperation();

        assertThat(actualOperator).isEqualTo(expectedOperator);
    }

    @Test
    public void testGetBinaryOperation() {
        BinaryOperator expectedOperator = new BinaryOperator("+", 1, true, Double::sum);
        BinaryOperatorToken token = new BinaryOperatorToken("+", expectedOperator);

        BinaryOperator actualOperator = token.getBinaryOperation();

        assertThat(actualOperator).isEqualTo(expectedOperator);
    }
}
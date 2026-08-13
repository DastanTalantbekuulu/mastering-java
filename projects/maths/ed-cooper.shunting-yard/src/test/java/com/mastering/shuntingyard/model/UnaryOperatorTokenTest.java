package com.mastering.shuntingyard.model;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class UnaryOperatorTokenTest {

    @Test
    public void testGetOperation() {
        UnaryOperator expectedOperator = new UnaryOperator("sin", Math::sin);
        UnaryOperatorToken token = new UnaryOperatorToken("sin", expectedOperator);

        Operator actualOperator = token.getOperation();

        assertThat(actualOperator).isEqualTo(expectedOperator);
    }

    @Test
    public void testGetUnaryOperation() {
        UnaryOperator expectedOperator = new UnaryOperator("sin", Math::sin);
        UnaryOperatorToken token = new UnaryOperatorToken("sin", expectedOperator);

        UnaryOperator actualOperator = token.getUnaryOperation();

        assertThat(actualOperator).isEqualTo(expectedOperator);
    }
}
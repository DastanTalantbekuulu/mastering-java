package com.mastering.shuntingyard.model;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class OperatorTokenTest {

    @Test
    public void testEquals_true() {
        BinaryOperator operator = new BinaryOperator("+", 2, true, Double::sum);
        BinaryOperatorToken token1 = new BinaryOperatorToken("+", operator);
        BinaryOperatorToken token2 = new BinaryOperatorToken("+", operator);

        assertThat(token1).isEqualTo(token2);
    }

    @Test
    public void testEquals_false_literal() {
        BinaryOperator operator = new BinaryOperator("+", 2, true, Double::sum);
        BinaryOperatorToken token1 = new BinaryOperatorToken("+", operator);
        BinaryOperatorToken token2 = new BinaryOperatorToken("-", operator);

        assertThat(token1).isNotEqualTo(token2);
    }

    @Test
    public void testEquals_false_operator() {
        BinaryOperator operator1 = new BinaryOperator("+", 2, true, Double::sum);
        BinaryOperator operator2 = new BinaryOperator("-", 2, true, (x, y) -> x - y);

        BinaryOperatorToken token1 = new BinaryOperatorToken("+", operator1);
        BinaryOperatorToken token2 = new BinaryOperatorToken("+", operator2);

        assertThat(token1).isNotEqualTo(token2);
    }
}
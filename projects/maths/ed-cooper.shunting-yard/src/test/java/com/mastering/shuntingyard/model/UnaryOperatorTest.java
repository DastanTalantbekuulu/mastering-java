package com.mastering.shuntingyard.model;

import org.junit.jupiter.api.Test;

import java.util.function.DoubleUnaryOperator;

import static org.assertj.core.api.Assertions.assertThat;

public class UnaryOperatorTest {

    @Test
    public void testGetAction() {
        DoubleUnaryOperator expectedAction = Math::sin;
        UnaryOperator operator = new UnaryOperator("sin", expectedAction);

        DoubleUnaryOperator actualAction = operator.getAction();

        assertThat(actualAction).isEqualTo(expectedAction);
    }

    @Test
    public void testGetToken() {
        UnaryOperator operator = new UnaryOperator("sin", Math::sin);
        UnaryOperatorToken expectedToken = new UnaryOperatorToken("sin", operator);

        OperatorToken actualToken = operator.getToken();

        assertThat(actualToken).isEqualTo(expectedToken);
    }
}
package com.mastering.shuntingyard.model;

import org.junit.jupiter.api.Test;

import java.util.function.DoubleBinaryOperator;

import static org.assertj.core.api.Assertions.assertThat;

public class BinaryOperatorTest {

    @Test
    public void testGetAction() {
        // Define a specific lambda instance
        DoubleBinaryOperator expectedAction = Double::sum;
        BinaryOperator operator = new BinaryOperator("+", 1, true, expectedAction);

        DoubleBinaryOperator actualAction = operator.getAction();

        // Verify that the getter returns the exact same instance passed to the constructor
        assertThat(actualAction).isEqualTo(expectedAction);
    }

    @Test
    public void testGetLeftAssociative_True() {
        BinaryOperator operator = new BinaryOperator("+", 1, true, Double::sum);

        assertThat(operator.getLeftAssociative()).isTrue();
    }

    @Test
    public void testGetLeftAssociative_False() {
        BinaryOperator operator = new BinaryOperator("^", 3, false, Math::pow);

        assertThat(operator.getLeftAssociative()).isFalse();
    }

    @Test
    public void testGetPrecedence() {
        int expectedPrecedence = 5;
        BinaryOperator operator = new BinaryOperator("*", expectedPrecedence, true, (x, y) -> x * y);

        assertThat(operator.getPrecedence()).isEqualTo(expectedPrecedence);
    }

    @Test
    public void testGetToken() {
        BinaryOperator operator = new BinaryOperator("+", 1, true, Double::sum);
        BinaryOperatorToken expectedToken = new BinaryOperatorToken("+", operator);

        OperatorToken actualToken = operator.getToken();

        // Verify that the token generated corresponds to the operator.
        // This assumes BinaryOperatorToken implements equals() correctly.
        assertThat(actualToken).isEqualTo(expectedToken);
    }
}
package com.mastering.shuntingyard.model;

import org.junit.jupiter.api.Test;

import java.util.Arrays;

import static org.assertj.core.api.Assertions.assertThat;

public class MultiOutputUnaryOperatorTokenTest {

    @Test
    public void testGetOperation() {
        MultiOutputUnaryOperator expectedOperator = new MultiOutputUnaryOperator("±", (x) -> Arrays.asList(x, -x));
        MultiOutputUnaryOperatorToken token = new MultiOutputUnaryOperatorToken("±", expectedOperator);

        Operator actualOperator = token.getOperation();

        assertThat(actualOperator).isEqualTo(expectedOperator);
    }

    @Test
    public void testGetMultiOutputUnaryOperation() {
        MultiOutputUnaryOperator expectedOperator = new MultiOutputUnaryOperator("±", (x) -> Arrays.asList(x, -x));
        MultiOutputUnaryOperatorToken token = new MultiOutputUnaryOperatorToken("±", expectedOperator);

        MultiOutputUnaryOperator actualOperator = token.getMultiOutputUnaryOperation();

        assertThat(actualOperator).isEqualTo(expectedOperator);
    }
}
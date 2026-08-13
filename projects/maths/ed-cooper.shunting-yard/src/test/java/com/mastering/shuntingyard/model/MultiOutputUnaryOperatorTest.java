package com.mastering.shuntingyard.model;

import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;
import java.util.function.Function;

import static org.assertj.core.api.Assertions.assertThat;

public class MultiOutputUnaryOperatorTest {

    @Test
    public void testGetAction() {
        Function<Double, List<Double>> expectedAction = (x) -> Arrays.asList(x, -x);
        MultiOutputUnaryOperator operator = new MultiOutputUnaryOperator("±", expectedAction);

        Function<Double, List<Double>> actualAction = operator.getAction();

        assertThat(actualAction).isEqualTo(expectedAction);
    }

    @Test
    public void testGetToken() {
        MultiOutputUnaryOperator operator = new MultiOutputUnaryOperator("±", (x) -> Arrays.asList(x, -x));
        MultiOutputUnaryOperatorToken expectedToken = new MultiOutputUnaryOperatorToken(operator.getSymbol(), operator);

        OperatorToken actualToken = operator.getToken();

        assertThat(actualToken).isEqualTo(expectedToken);
    }
}
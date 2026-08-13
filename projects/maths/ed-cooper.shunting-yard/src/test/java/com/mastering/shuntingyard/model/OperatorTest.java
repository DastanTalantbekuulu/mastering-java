package com.mastering.shuntingyard.model;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class OperatorTest {

    @Test
    public void getSymbol() {
        String expectedSymbol = "+";

        Operator operator = new Operator(expectedSymbol) {
            @Override
            public OperatorToken getToken() {
                throw new UnsupportedOperationException();
            }
        };

        String actualSymbol = operator.getSymbol();

        assertThat(actualSymbol).isEqualTo(expectedSymbol);
    }
}
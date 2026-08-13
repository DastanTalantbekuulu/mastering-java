package com.mastering.shuntingyard.model;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class ConstantTokenTest {

    @Test
    public void testGetValue() {
        double expected = 27d;
        ConstantToken token = new ConstantToken("a", expected);

        double actual = token.getValue();

        // AssertJ automatically handles double comparison
        assertThat(actual).isEqualTo(expected);
    }

    @Test
    public void testEquals_true() {
        ConstantToken token1 = new ConstantToken("a", 1d);
        ConstantToken token2 = new ConstantToken("a", 1d);

        // Verifies that token1.equals(token2) returns true
        assertThat(token1).isEqualTo(token2);
    }

    @Test
    public void testEquals_false_literal() {
        ConstantToken token1 = new ConstantToken("a", 1d);
        ConstantToken token2 = new ConstantToken("b", 1d);

        // Verifies that token1.equals(token2) returns false
        assertThat(token1).isNotEqualTo(token2);
    }

    @Test
    public void testEquals_false_value() {
        ConstantToken token1 = new ConstantToken("a", 1d);
        ConstantToken token2 = new ConstantToken("a", 2d);

        // Verifies that token1.equals(token2) returns false
        assertThat(token1).isNotEqualTo(token2);
    }
}
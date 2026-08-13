package com.mastering.shuntingyard.model;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class TokenTest {

    @Test
    public void testGetLiteral() {
        String expected = "x";
        Token token = new Token(expected) {};

        String actual = token.getLiteral();

        assertThat(actual).isEqualTo(expected);
    }

    @Test
    public void testEquals_true() {
        Token token1 = new VariableToken("x");
        Token token2 = new VariableToken("x");

        assertThat(token1).isEqualTo(token2);
    }

    @Test
    public void testEquals_false_literal() {
        Token token1 = new VariableToken("x");
        Token token2 = new VariableToken("y");

        assertThat(token1).isNotEqualTo(token2);
    }

    @Test
    public void testEquals_false_class() {
        Token token1 = new VariableToken("x");
        Token token2 = new ConstantToken("x", 10);

        assertThat(token1).isNotEqualTo(token2);
    }

    @Test
    public void testHashCode() {
        Token token1 = new Token("x") {};
        Token token2 = new Token("y") {};

        assertThat(token1.hashCode()).isNotEqualTo(token2.hashCode());
    }

    @Test
    public void testToString() {
        Token token = new VariableToken("x");
        String expected = "VariableToken[literal=\"x\"]";

        assertThat(token.toString()).isEqualTo(expected);
    }
}
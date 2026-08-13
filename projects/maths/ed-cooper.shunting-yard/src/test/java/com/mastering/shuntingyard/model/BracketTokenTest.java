package com.mastering.shuntingyard.model;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class BracketTokenTest {

    @Test
    public void testGetIsOpenBracket_true() {
        BracketToken token = new BracketToken("(", true);

        assertThat(token.getIsOpenBracket()).isTrue();
    }

    @Test
    public void testGetIsOpenBracket_false() {
        BracketToken token = new BracketToken(")", false);

        assertThat(token.getIsOpenBracket()).isFalse();
    }

    @Test
    public void testEquals_true() {
        BracketToken token1 = new BracketToken("(", true);
        BracketToken token2 = new BracketToken("(", true);

        assertThat(token1).isEqualTo(token2);
    }

    @Test
    public void testEquals_false_literal() {
        BracketToken token1 = new BracketToken("(", true);
        BracketToken token2 = new BracketToken("[", true);

        assertThat(token1).isNotEqualTo(token2);
    }

    @Test
    public void testEquals_false_isOpenBracket() {
        BracketToken token1 = new BracketToken("(", true);
        BracketToken token2 = new BracketToken("(", false);

        assertThat(token1).isNotEqualTo(token2);
    }
}
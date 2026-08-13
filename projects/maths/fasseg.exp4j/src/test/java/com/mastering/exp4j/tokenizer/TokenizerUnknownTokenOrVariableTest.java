package com.mastering.exp4j.tokenizer;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

/**
 * This test is to check if {@link UnknownFunctionOrVariableException} generated when expression
 * contains unknown function or variable contains necessary expected details.
 *
 * @author Bartosz Firyn (sarxos)
 */
public class TokenizerUnknownTokenOrVariableTest {

    @Test
    public void testTokenizationOfUnknownVariable() {
        final Tokenizer tokenizer = new Tokenizer("3 + x", null, null, null);

        assertThatThrownBy(() -> {
            while (tokenizer.hasNext()) {
                tokenizer.nextToken();
            }
        }).isInstanceOf(UnknownFunctionOrVariableException.class);
    }

    @Test
    public void testTokenizationOfUnknownVariable1Details() {
        final Tokenizer tokenizer = new Tokenizer("3 + x", null, null, null);
        tokenizer.nextToken(); // 3
        tokenizer.nextToken(); // +

        assertThatThrownBy(tokenizer::nextToken)
                .isInstanceOf(UnknownFunctionOrVariableException.class)
                .satisfies(e -> {
                    UnknownFunctionOrVariableException ex = (UnknownFunctionOrVariableException) e;
                    assertThat(ex.getToken()).isEqualTo("x");
                    assertThat(ex.getPosition()).isEqualTo(4);
                    assertThat(ex.getExpression()).isEqualTo("3 + x");
                });
    }

    @Test
    public void testTokenizationOfUnknownVariable2Details() {
        final Tokenizer tokenizer = new Tokenizer("x + 3", null, null, null);

        assertThatThrownBy(tokenizer::nextToken)
                .isInstanceOf(UnknownFunctionOrVariableException.class)
                .satisfies(e -> {
                    UnknownFunctionOrVariableException ex = (UnknownFunctionOrVariableException) e;
                    assertThat(ex.getToken()).isEqualTo("x");
                    assertThat(ex.getPosition()).isEqualTo(0);
                    assertThat(ex.getExpression()).isEqualTo("x + 3");
                });
    }

    @Test
    public void testTokenizationOfUnknownFunction() {
        final Tokenizer tokenizer = new Tokenizer("3 + p(1)", null, null, null);

        assertThatThrownBy(() -> {
            while (tokenizer.hasNext()) {
                tokenizer.nextToken();
            }
        }).isInstanceOf(UnknownFunctionOrVariableException.class);
    }

    @Test
    public void testTokenizationOfUnknownFunction1Details() {
        final Tokenizer tokenizer = new Tokenizer("3 + p(1)", null, null, null);
        tokenizer.nextToken(); // 3
        tokenizer.nextToken(); // +

        assertThatThrownBy(tokenizer::nextToken)
                .isInstanceOf(UnknownFunctionOrVariableException.class)
                .satisfies(e -> {
                    UnknownFunctionOrVariableException ex = (UnknownFunctionOrVariableException) e;
                    assertThat(ex.getToken()).isEqualTo("p");
                    assertThat(ex.getPosition()).isEqualTo(4);
                    assertThat(ex.getExpression()).isEqualTo("3 + p(1)");
                });
    }

    @Test
    public void testTokenizationOfUnknownFunction2Details() {
        final Tokenizer tokenizer = new Tokenizer("p(1) + 3", null, null, null);

        assertThatThrownBy(tokenizer::nextToken)
                .isInstanceOf(UnknownFunctionOrVariableException.class)
                .satisfies(e -> {
                    UnknownFunctionOrVariableException ex = (UnknownFunctionOrVariableException) e;
                    assertThat(ex.getToken()).isEqualTo("p");
                    assertThat(ex.getPosition()).isEqualTo(0);
                    assertThat(ex.getExpression()).isEqualTo("p(1) + 3");
                });
    }
}
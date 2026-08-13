package com.mastering.shuntingyard.lexers;

import com.mastering.shuntingyard.model.ConstantToken;
import com.mastering.shuntingyard.model.Token;
import com.mastering.shuntingyard.model.TokenNotRecognisedException;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;

public class LexerBaseTest {

    @Test
    public void testReadTokens() throws TokenNotRecognisedException {
        // Create an anonymous implementation of LexerBase for testing purposes
        LexerBase lexer = new LexerBase() {
            @Override
            public Token readToken(String input, int start) throws TokenNotRecognisedException {
                // Detect only constant tokens for simple example

                // Create set of constants
                Map<String, Double> constants = new HashMap<>();
                constants.put("a", 10d);
                constants.put("bcd", 20d);
                constants.put("ff", 30d);

                // Check input string against each known constant
                for (String constant : constants.keySet()) {
                    if (input.startsWith(constant, start)) {
                        return new ConstantToken(constant, constants.get(constant));
                    }
                }

                // Token not recognised
                throw new TokenNotRecognisedException(input, start);
            }
        };

        String input = "aabcdff";
        List<Token> expected = Arrays.asList(
                new ConstantToken("a", 10d),
                new ConstantToken("a", 10d),
                new ConstantToken("bcd", 20d),
                new ConstantToken("ff", 30d)
        );

        List<Token> actual = lexer.readTokens(input);

        // Verify the list content.
        // usingRecursiveComparison() ensures we check the fields of the Token objects
        // rather than just object references.
        assertThat(actual)
                .usingRecursiveComparison()
                .isEqualTo(expected);
    }

    @Test
    public void testPreprocess() {
        LexerBase lexer = new LexerBase() {
            @Override
            public Token readToken(String input, int start) throws TokenNotRecognisedException {
                throw new TokenNotRecognisedException(input, start);
            }
        };
        String input = "a + b";

        String actual = lexer.preprocess(input);

        // Verify that preprocess returns the string as is (default behavior)
        assertThat(actual).isEqualTo(input);
    }
}
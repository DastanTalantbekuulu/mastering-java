package com.mastering.shuntingyard.lexers;

import com.mastering.shuntingyard.model.Token;
import com.mastering.shuntingyard.model.TokenNotRecognisedException;

import java.util.List;

/**
 * Provides methods to split an input string into a series of tokens.
 */
public interface Lexer {
    /**
     * Splits an input string into a series of tokens.
     * @param input         The input string to split.
     * @return              A list of <code>Token</code> objects representing the input string.
     */
    List<Token> readTokens(String input) throws TokenNotRecognisedException;
}

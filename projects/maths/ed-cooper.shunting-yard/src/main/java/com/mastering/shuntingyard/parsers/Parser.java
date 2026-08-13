package com.mastering.shuntingyard.parsers;

import com.mastering.shuntingyard.model.InvalidSyntaxException;
import com.mastering.shuntingyard.model.Token;
import com.mastering.shuntingyard.model.UnsupportedTokenException;

import java.util.List;

/**
 * Provides methods to convert an equation into RPN form.
 */
public interface Parser {
    /**
     * Parses an equation and returns the RPN form of it, expressed as a sequence of Tokens.
     * @param equation       The collection of tokens that represent the original equation.
     * @return               The RPN form of the input equation.
     */
    List<Token> parse(List<Token> equation) throws UnsupportedTokenException, InvalidSyntaxException;
}

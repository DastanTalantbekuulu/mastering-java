package com.mastering.shuntingyard.evaluators;


import com.mastering.shuntingyard.model.InvalidSyntaxException;
import com.mastering.shuntingyard.model.Token;
import com.mastering.shuntingyard.model.UnsupportedTokenException;

import java.util.List;

/**
 * Provides methods to evaluate an RPN equation.
 */
public interface Evaluator {
    /**
     * Evaluates the given RPN equation to produce a collection of output values.
     * @param equation          The equation to evaluate.
     * @return                  The collection of evluated output values.
     */
    List<Double> evaluate(List<Token> equation) throws UnsupportedTokenException, InvalidSyntaxException;
}

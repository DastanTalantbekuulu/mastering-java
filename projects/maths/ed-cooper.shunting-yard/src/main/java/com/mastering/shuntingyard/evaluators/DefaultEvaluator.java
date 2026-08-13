package com.mastering.shuntingyard.evaluators;

import com.mastering.shuntingyard.model.BinaryOperatorToken;
import com.mastering.shuntingyard.model.ConstantToken;
import com.mastering.shuntingyard.model.InvalidSyntaxException;
import com.mastering.shuntingyard.model.MultiOutputUnaryOperatorToken;
import com.mastering.shuntingyard.model.Token;
import com.mastering.shuntingyard.model.UnaryOperatorToken;
import com.mastering.shuntingyard.model.UnsupportedTokenException;
import com.mastering.shuntingyard.model.VariableToken;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Stack;

/**
 * The default evaluator for evaluating maths expressions.
 * <p>
 * The supported input token types of this class are: <code>BinaryOperatorToken</code>, <code>UnaryOperatorToken</code>,
 * <code>MultiOutputOperatorToken</code>, <code>ConstantToken</code>, <code>VariableToken</code>.
 */
public class DefaultEvaluator extends EvaluatorBase {
    private final HashMap<String, Double> variables = new HashMap<>();

    @Override
    public List<Double> evaluate(List<Token> equation) throws UnsupportedTokenException, InvalidSyntaxException {
        // If RPN empty, return no result
        if (equation.isEmpty()) {
            return Collections.emptyList();
        }

        // Create stack to hold intermediary values
        Stack<List<Double>> values = new Stack<>();

        // For each RPN token
        for (Token token : equation) {
            switch (token) {
                case BinaryOperatorToken binaryOperatorToken ->  // Binary ops
                        evaluateBinaryOperator(binaryOperatorToken, values);
                case UnaryOperatorToken unaryOperatorToken ->  // Unary ops
                        evaluateUnaryOperator(unaryOperatorToken, values);
                case MultiOutputUnaryOperatorToken multiOutputUnaryOperatorToken ->  // Multi-output unary ops
                        evaluateMultiOutputUnaryOperator(multiOutputUnaryOperatorToken, values);
                case ConstantToken constantToken ->  // Constant
                        evaluateConstantToken(constantToken, values);
                case VariableToken variableToken ->  // Variable
                        evaluateVariableToken(variableToken, values);
                case null, default -> throw new UnsupportedTokenException(equation, token);
            }
        }

        // There should be a single item remaining in values - the final output
        if (values.size() > 1) {
            throw new InvalidSyntaxException("Invalid RPN");
        }

        return values.pop();
    }

    /**
     * Evaluates a binary operator token.
     *
     * @param token  The current token being evaluated.
     * @param values The current stack of intermediary values.
     */
    protected void evaluateBinaryOperator(BinaryOperatorToken token, Stack<List<Double>> values)
            throws InvalidSyntaxException {
        // Check operands exist
        if (values.size() < 2) {
            throw new InvalidSyntaxException("Missing operands for " + token.toString());
        }

        // Get operands
        List<Double> operand2 = values.pop();
        List<Double> operand1 = values.pop();

        // Get cartesian product of operands, for all possible combinations of inputs
        List<Double[]> cartesian = this.cartesian(operand1, operand2);

        // Get outputs
        ArrayList<Double> outputs = new ArrayList<>();
        for (Double[] pair : cartesian) {
            // Get output for each pair of operands
            outputs.add(token.getBinaryOperation().getAction().applyAsDouble(pair[0], pair[1]));
        }

        // Push outputs to stack
        values.push(outputs);
    }

    /**
     * Evaluates a unary operator token.
     *
     * @param token  The current token being evaluated.
     * @param values The current stack of intermediary values.
     */
    protected void evaluateUnaryOperator(UnaryOperatorToken token, Stack<List<Double>> values)
            throws InvalidSyntaxException {
        // Check operands exist
        if (values.isEmpty()) {
            throw new InvalidSyntaxException("Missing operands for " + token.toString());
        }

        // Get operands
        List<Double> operand1 = values.pop();

        // Apply operator to each value in first operand
        ArrayList<Double> outputs = new ArrayList<>();
        for (Double value : operand1) {
            outputs.add(token.getUnaryOperation().getAction().applyAsDouble(value));
        }

        // Push outputs to stack
        values.push(outputs);
    }

    /**
     * Evaluates a multi-output unary operator token.
     *
     * @param token  The current token being evaluated.
     * @param values The current stack of intermediary values.
     */
    protected void evaluateMultiOutputUnaryOperator(MultiOutputUnaryOperatorToken token, Stack<List<Double>> values)
            throws InvalidSyntaxException {
        // Check operands exist
        if (values.isEmpty()) {
            throw new InvalidSyntaxException("Missing operands for " + token.toString());
        }

        // Get operands
        List<Double> operand1 = values.pop();

        // Apply operator to each value in first operand
        ArrayList<Double> outputs = new ArrayList<>();
        for (Double value : operand1) {
            outputs.addAll(token.getMultiOutputUnaryOperation().getAction().apply(value));
        }

        // Push outputs to stack
        values.push(outputs);
    }

    /**
     * Evaluates a constant token.
     *
     * @param token  The current token being evaluated.
     * @param values The current stack of intermediary values.
     */
    protected void evaluateConstantToken(ConstantToken token, Stack<List<Double>> values) {
        // Push immediately to output
        values.push(List.of(token.getValue()));
    }

    /**
     * Evaluates a constant token.
     *
     * @param token  The current token being evaluated.
     * @param values The current stack of intermediary values.
     */
    protected void evaluateVariableToken(VariableToken token, Stack<List<Double>> values) {
        // Get actual value and push to output
        values.push(Collections.singletonList(variables.get(token.getLiteral())));
    }

    /**
     * Gets the map of variable names to values.
     *
     * @return The map of variable names to values.
     */
    public Map<String, Double> getVariables() {
        return variables;
    }
}

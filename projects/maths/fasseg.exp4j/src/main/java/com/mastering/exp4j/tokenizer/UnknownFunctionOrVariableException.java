package com.mastering.exp4j.tokenizer;

import lombok.Getter;

import java.io.Serial;

/**
 * This exception is being thrown whenever {@link Tokenizer} finds unknown function or variable.
 *
 * @author Bartosz Firyn (sarxos)
 */
class UnknownFunctionOrVariableException extends IllegalArgumentException {

    /**
     * Serial version UID.
     */
    @Serial
    private static final long serialVersionUID = 1L;

    private final String message;

    @Getter
    private final String expression;

    @Getter
    private final String token;

    @Getter
    private final int position;

    public UnknownFunctionOrVariableException(String expression, int position, int length) {
        this.expression = expression;
        this.position = position;
        token = token(expression, position, length);
        message = "Unknown function or variable '" + token + "' at pos " + position + " in expression '" + expression + "'";
    }

    private static String token(String expression, int position, int length) {

        int len = expression.length();
        int end = position + length - 1;

        if (len < end) {
            end = len;
        }

        return expression.substring(position, end);
    }

    @Override
    public String getMessage() {
        return message;
    }

}

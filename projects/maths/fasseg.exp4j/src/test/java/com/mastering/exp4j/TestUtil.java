/*
 * Copyright 2014 Frank Asseg
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.mastering.exp4j;

import com.mastering.exp4j.tokenizer.*;

import static org.assertj.core.api.Assertions.assertThat;

public abstract class TestUtil {

    public static void assertVariableToken(Token token, String name) {
        assertThat(token.getType()).isEqualTo(Token.TOKEN_VARIABLE);
        assertThat(((VariableToken) token).getName()).isEqualTo(name);
    }

    public static void assertOpenParenthesesToken(Token token) {
        assertThat(token.getType()).isEqualTo(Token.TOKEN_PARENTHESES_OPEN);
    }

    public static void assertCloseParenthesesToken(Token token) {
        assertThat(token.getType()).isEqualTo(Token.TOKEN_PARENTHESES_CLOSE);
    }

    public static void assertFunctionToken(Token token, String name, int numArgs) {
        assertThat(token.getType()).isEqualTo(Token.TOKEN_FUNCTION);
        FunctionToken f = (FunctionToken) token;
        assertThat(f.getFunction().getNumArguments()).isEqualTo(numArgs);
        assertThat(f.getFunction().getName()).isEqualTo(name);
    }

    public static void assertOperatorToken(Token token, String symbol, int numArgs, int precedence) {
        assertThat(token.getType()).isEqualTo(Token.TOKEN_OPERATOR);
        OperatorToken op = (OperatorToken) token;
        assertThat(op.getOperator().getNumOperands()).isEqualTo(numArgs);
        assertThat(op.getOperator().getSymbol()).isEqualTo(symbol);
        assertThat(op.getOperator().getPrecedence()).isEqualTo(precedence);
    }

    public static void assertNumberToken(Token token, double value) {
        assertThat(token.getType()).isEqualTo(Token.TOKEN_NUMBER);
        assertThat(((NumberToken) token).getValue()).isEqualTo(value);
    }

    public static void assertFunctionSeparatorToken(Token token) {
        assertThat(token.getType()).isEqualTo(Token.TOKEN_SEPARATOR);
    }
}
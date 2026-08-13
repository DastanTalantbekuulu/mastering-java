/**
 * Copyright 2023 Pratanu Mandal
 * <p>
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * <p>
 * http://www.apache.org/licenses/LICENSE-2.0
 * <p>
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *
 */

package com.mastering.expr4j.expression;

import com.mastering.expr4j.token.Function;
import com.mastering.expr4j.token.Operator;
import com.mastering.expr4j.token.Token;

import lombok.Builder;

import java.util.ArrayList;
import java.util.List;

/**
 * The <code>ExpressionNode</code> class represents a node of the expression tree.<br><br>
 *
 * @param children Children of this node.
 * @param token    Token contained in this node.<br>
 *                 A token can be an operand, operator, function, variable, or constant.
 * @author Pratanu Mandal
 * @since 1.0
 */
@Builder
public record ExpressionNode(List<ExpressionNode> children, Token token) {

    /**
     * Parameterized constructor.
     *
     * @param token The token in this node
     */
    public ExpressionNode(Token token) {
        this((token instanceof Function || token instanceof Operator) ? new ArrayList<>() : null, token);
    }
}

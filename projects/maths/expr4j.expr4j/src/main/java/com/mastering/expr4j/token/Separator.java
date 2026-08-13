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

package com.mastering.expr4j.token;

import com.mastering.expr4j.exception.Expr4jException;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

/**
 * The <code>Separator</code> class represents the separators in the expression.<br>
 *
 * @author Pratanu Mandal
 * @since 1.0
 *
 */
@Getter
@RequiredArgsConstructor
public enum Separator implements Token {

    /** Open bracket */
    OPEN_BRACKET("("),

    /** Close bracket */
    CLOSE_BRACKET(")"),

    /** Comma */
    COMMA(",");

    private final String label;

    /**
     * Get the separator with specified label.
     *
     * @param label The label
     * @return The separator
     */
    public static Separator getSeparator(String label) {
        return switch (label) {
            case "(" -> OPEN_BRACKET;
            case ")" -> CLOSE_BRACKET;
            case "," -> COMMA;
            default -> throw new Expr4jException("Invalid separator");
        };
    }

}

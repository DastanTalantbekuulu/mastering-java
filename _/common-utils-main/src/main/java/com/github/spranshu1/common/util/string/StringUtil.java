/*
 * Created By: Pranshu Shrivastava

 * All rights reserved.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.github.spranshu1.common.util.string;

import java.util.List;

/**
 * The Class StringUtils.
 */
public final class StringUtil {

    /**
     * Checks if the given String field is null or empty (or contains only spaces).
     *
     * @param field String parameter
     * @return boolean, <br>
     * true - if given String field is null or empty (or contains only spaces),<br>
     * false - otherwise
     */
    public static boolean strFieldIsEmpty(final String field) {
        return field == null || field.trim().equals("");
    }

    /**
     * Checks if the given String field contains any useful data (i.e. not null, not empty, not contain only spaces).
     *
     * @param field String parameter
     * @return boolean, <br>
     * true - if given String field contains useful data,<br>
     * false - otherwise
     */
    public static boolean strFieldIsNotEmpty(final String field) {
        return !strFieldIsEmpty(field);
    }

    /**
     * Check that the given {@code CharSequence} is neither {@code null} nor
     * of length 0.
     * <p>Note: this method returns {@code true} for a {@code CharSequence}</p>
     * that purely consists of whitespace.
     * <pre class="code">
     *     StringUtils.hasLength(null) = false
     *     StringUtils.hasLength("") = false
     *     StringUtils.hasLength(" ") = true
     *     StringUtils.hasLength("Hello") = true
     * </pre>
     *
     * @param str the {@code CharSequence} to check (may be {@code null})
     * @return {@code true} if the {@code CharSequence} is not {@code null} and has length
     */
    public static boolean hasLength(final CharSequence str) {
        return (str != null && str.length() > 0);
    }


    /**
     * Checks if the given String params are not null/empty and match with each other (Case sensitive).
     *
     * @param field1 String first parameter to be compared
     * @param field2 String second parameter to be compared
     * @return boolean, <br>
     * true - if both the fields are not null/empty and their values match (using equals() operation),<br>
     * false - otherwise
     */
    public static boolean equals(final String field1, final String field2) {
        return strFieldIsNotEmpty(field1) && strFieldIsNotEmpty(field2) && field1.equals(field2);
    }

    /**
     * Checks if the given String params are not null/empty and match with each other (Case insensitive).
     *
     * @param field1 String first parameter to be compared
     * @param field2 String second parameter to be compared
     * @return boolean, <br>
     * true - if both the fields are not null/empty and their values match (using equalsIgnoreCase() operation),<br>
     * false - otherwise
     */
    public static boolean equalsIgnoreCase(final String field1, final String field2) {
        return strFieldIsNotEmpty(field1) && strFieldIsNotEmpty(field2) && field1.equalsIgnoreCase(field2);
    }


    /**
     * Matches given string value against List Of Regex Values.
     *
     * @param value    String value to be matched
     * @param regexLov List of regex strings
     * @return boolean, <br>
     * true - if given string matches with one of the regex values in list,<br>
     * false - otherwise.
     */
    public static boolean matchAgainstRegexLOV(final String value, final List<String> regexLov) {

        if (strFieldIsEmpty(value) || regexLov == null) {
            return false;
        }

        for (final String regex : regexLov) {
            if (value.matches(regex)) {
                return true;
            }
        }

        return false;
    }

    /**
     * Ends with separator.
     *
     * @param msg the msg
     * @return true, if successful
     */
    public static boolean endsWithSeparator(final String msg) {
        return (msg.endsWith(":") || msg.endsWith(";") || msg.endsWith(",") || msg.endsWith("."));
    }

    /**
     * Message with type name.
     *
     * @param msg      the msg
     * @param typeName the type name
     * @return the string
     */
    public static String messageWithTypeName(String msg, Object typeName) {
        return msg + (msg.endsWith(" ") ? "" : ": ") + typeName;
    }


    /**
     * Check if a string is numeric
     * <pre class="code">
     * StringUtils.isNumeric("50") = true
     * StringUtils.isNumeric("101.12") = true
     * StringUtils.hasLength("") = false
     * StringUtils.hasLength(" ") = false
     * StringUtils.hasLength("Hello") = false
     * </pre>
     *
     * @param str the string to check
     * @return True if numeric False otherwise
     */
    public static boolean isNumeric(final String str) {

        if (str == null || str.length() == 0) {
            return false;
        }

        return str.chars().allMatch(Character::isDigit);
    }

}

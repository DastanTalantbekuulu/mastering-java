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
package com.github.spranshu1.common.util.hex;

/**
 * The type Hex util.
 */
public final class HexUtil {

    private static final char[] hexDigits = {
            '0', '1', '2', '3', '4', '5', '6', '7',
            '8', '9', 'A', 'B', 'C', 'D', 'E', 'F'
    };

    /**
     * Returns a string of hexadecimal digits from a byte array. Each byte is
     * converted to 2 hex symbols.
     * <p>
     * If offset and length are omitted, the entire array is used.
     *
     * @param ba     the ba
     * @param offset the offset
     * @param length the length
     * @return string string
     */
    public static String toString(final byte[] ba, final int offset, final int length) {
        char[] buf = new char[length * 2];
        int j = 0;
        int k;

        for (int i = offset; i < offset + length; i++) {
            k = ba[i];
            buf[j++] = hexDigits[(k >>> 4) & 0x0F];
            buf[j++] = hexDigits[k & 0x0F];
        }

        return new String(buf);
    }

    /**
     * Returns a string of hexadecimal digits from a byte array. Each byte is
     * converted to 2 hex symbols.
     * <p>
     * If offset and length are omitted, the entire array is used.
     *
     * @param ba the source buffer.
     * @return a hexadecimal string.
     */
    public static String toString(final byte[] ba) {
        return toString(ba, 0, ba.length);
    }

    /**
     * Returns a byte array from a string of hexadecimal digits.
     *
     * @param hex the string to convert to a byte array.
     * @return byte [ ]
     */
    public static byte[] fromString(final String hex) {
        int len = hex.length();
        byte[] buf = new byte[((len + 1) / 2)];
        int i = 0;
        int j = 0;
        if ((len % 2) == 1) {
            buf[j++] = (byte) fromDigit(hex.charAt(i++));
        }

        while (i < len) {
            buf[j++] = (byte) ((fromDigit(hex.charAt(i++)) << 4) |
                    fromDigit(hex.charAt(i++)));
        }

        return buf;
    }

    /**
     * Returns the hex digit corresponding to a number <i>n</i>, from 0 to 15.
     *
     * @param n the n
     * @return char char
     */
    public static char toDigit(int n) {
        try {
            return hexDigits[n];
        } catch (ArrayIndexOutOfBoundsException e) {
            throw new IllegalArgumentException(n
                    + " is out of range for a hex digit");
        }
    }

    /**
     * Returns the number from 0 to 15 corresponding to the hex digit <i>character</i>.
     *
     * @param character the character
     * @return int int
     */
    public static int fromDigit(char character) {
        if ((character >= '0') && (character <= '9'))
            return character - '0';
        if ((character >= 'A') && (character <= 'F'))
            return character - 'A' + 10;
        if ((character >= 'a') && (character <= 'f'))
            return 'a' + 10;

        throw new IllegalArgumentException("invalid hex digit '" + character + "'");
    }
}

public class Tokenizer {
    private final MyStack postfix;

    public Tokenizer() {
        postfix = new MyStack();
    }

    public MyStack readTokens(String str) throws TokenNotRecognisedException, InvalidSyntaxException {
        postfix.clear();
        String preprocessed = preprocess(str);

        int index = 0;
        while (index < preprocessed.length()) {
            String token = readToken(preprocessed, index);
            postfix.push(token);
            index = index + token.length();
        }
        return postfix;
    }

    public MyStack readPostfix(String str) throws TokenNotRecognisedException {
        postfix.clear();
        int index = 0;

        while (index < str.length()) {
            String token = readToken(str, index);
            postfix.push(token);
            index = index + token.length();
        }
        return postfix;
    }

    private String preprocess(String str) throws InvalidSyntaxException {
        str = str.replace(" ", "");
        if (!validateBrackets(str)) {
            throw new InvalidSyntaxException("Wrong brackets");
        }
        return findNegate(str);
    }
    private String findNegate(String str) {
        StringBuilder result = new StringBuilder();
        for (int i = 0; i < str.length(); i++) {
            char currentChar = str.charAt(i);
            if (currentChar == '-' && (i == 0 || (str.charAt(i - 1) != ')' && !Character.isDigit(str.charAt(i - 1))))) {
                // replace with minus sign "−" −x unicode u2212
                result.append('\u2212');
            } else {
                result.append(currentChar);
            }
        }
        return result.toString();
    }

    public String readToken(String tokens, int start) throws TokenNotRecognisedException {
        String token = tokens.charAt(start) + "";
        if (Arithmetic.isOperatorAll(token) || token.equals("\u2212")) {
            return token;
        }

        String digit = findNumbers(tokens, start);
        if (!digit.isEmpty()) {
            return digit;
        }
        throw new TokenNotRecognisedException(tokens, start);
    }

    private String findNumbers(String tokens, int start) {
        StringBuilder digit = new StringBuilder();
        for (int i = start; i < tokens.length(); i++) {
            if (Arithmetic.isNumber(tokens.charAt(i)) || Arithmetic.isPoint(tokens.charAt(i))) {
                digit.append(tokens.charAt(i));
            } else {
                break;
            }
        }
        return digit.toString();
    }

    private boolean validateBrackets(String infix) {
        int bracketCount = 0;
        for (char ch : infix.toCharArray()) {
            if (ch == '(') {
                bracketCount++;
            } else if (ch == ')') {
                if (--bracketCount < 0) {
                    return false;
                }
            }
        }
        return bracketCount == 0;
    }
}

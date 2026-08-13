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
        if(!validateBrackets(str)){
            throw new InvalidSyntaxException("Wrong brackets");
        }
        return findNegate(str);
    }

    private String findNegate(String str) {
        String result = "";
        for (int i = 0; i < str.length(); i++) {
            // if there is a hyphen-minus '-' x-y unicode u002D ASCII code 45
            if (i == 0 && str.charAt(i) == '-' ) {
                // replace with minus sign "−" −x unicode u2212
                result = result + "\u2212"; // "−";
            } else if (str.charAt(i) == '-' && str.charAt(i - 1) != ')' &&
                    !('0' <= str.charAt(i - 1) && str.charAt(i - 1) <= '9')) {
                // replace with minus sign "−" −x unicode u2212
                result = result + "\u2212"; // "−";
            } else  {
                result = result + str.charAt(i);
            }
        }
        return result;
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
        String digit = "";
        for (int i = start; i < tokens.length(); i++) {
            if (Arithmetic.isNumber(tokens.charAt(i)) || Arithmetic.isPoint(tokens.charAt(i))) {
                digit = digit + tokens.charAt(i);
            } else {
                return digit;
            }
        }
        return digit;
    }
    private boolean validateBrackets(String infix) {
        int bracket = 0;
        for (int i = 0; i < infix.length(); i++) {
            if (infix.charAt(i) == '(') {
                bracket = bracket + 1;
            } else if (infix.charAt(i) == ')') {
                bracket = bracket - 1;
                if (bracket < 0) {
                    return false;
                }
            }
        }
        if (bracket != 0) {
            return false;
        }
        return true;
    }
}

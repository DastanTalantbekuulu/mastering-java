
public class Tokenizer {
    private final BracketToken[] brackets;
    private final MyStack tokens;

    public Tokenizer() {
        tokens = new MyStack();
        brackets = new BracketToken[]{
                new BracketToken("(", true),
                new BracketToken(")", false),
                new BracketToken("[", true),
                new BracketToken("]", false),
                new BracketToken("{", true),
                new BracketToken("}", false)
        };
    }

    public MyStack readTokens(String input) throws TokenNotRecognisedException, InvalidSyntaxException {
        tokens.clear();
        String preprocessed = preprocess(input);

        int position = 0;

        while (position < preprocessed.length()) {
            Token token = readToken(preprocessed, position);
            tokens.push(token);
            position = position + token.getLiteral().length();
        }
        return tokens;
    }

    public Token readToken(String input, int start) throws TokenNotRecognisedException {
        for (OperatorToken operatorToken : Operator.operatorTokens) {
            if (input.startsWith(operatorToken.getLiteral(), start)) {
                return operatorToken;
            }
        }
        for (BracketToken bracket : brackets) {
            if (input.startsWith(bracket.getLiteral(), start)) {
                return bracket;
            }
        }
        String digit = findNumbers(input, start);
        if (!digit.isEmpty()) {
            return new ConstantToken(digit, Double.parseDouble(digit));
        }
        throw new TokenNotRecognisedException(input, start);
    }

    private String preprocess(String input) throws InvalidSyntaxException {
        if(!validateBrackets(input)){
            throw new InvalidSyntaxException("Wrong brackets");
        }
        input = input.replace(" ", "");
        return findNegate(input);
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

    private String findNumbers(String input, int start) {
        String digit = "";
        for (int i = start; i < input.length(); i++) {
            if (input.charAt(i) == '.' || '0' <= input.charAt(i) && input.charAt(i) <= '9') {
                digit = digit + input.charAt(i);
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
    public BracketToken[] getBrackets() {
        return brackets;
    }
}

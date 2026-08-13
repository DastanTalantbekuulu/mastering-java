public class RPN {
    private MyStack infix;
    private MyStack postfix;
    private MyStack stack;

    public RPN() {
        infix = new MyStack();
        postfix = new MyStack();
        stack = new MyStack();
    }
    public String calculate (String input){
        try {
            input = preprocess(input);
            int index = 0;
            infix.clear();
            while (index < input.length()) {
                String token = readToken(input, index);
                infix.push(token);
                index = index + token.length();
            }
            postfix = toPostfix(infix, postfix, stack);
            String result = evaluate(postfix, stack);
            return postprocess(result);

        } catch (Exception e) {
            return e.getMessage();
        }
    }

    public MyStack toPostfix(MyStack infix, MyStack postfix, MyStack stack) throws Exception {
        postfix.clear();
        stack.clear();

        for (int i = 0; i < infix.size(); i++) {
            String token = infix.get(i);
            if (Arithmetic.isNumber(token)) {
                postfix.push(token);
            } else if (Arithmetic.isBracket(token)) {
                if (token.equals("(")) {
                    stack.push(token);
                } else if (token.equals(")")) {
                    while (!stack.empty() && !stack.peek().equals("(")) {
                        postfix.push(stack.pop());
                    }
                    stack.pop();
                }
                // minus sign "−" −x unicode u2212
            } else if (token.equals("\u2212")) {
                stack.push(token);
            } else if (Arithmetic.isOperator(token)) {
                while (!stack.empty()) {
                    String tokenFromStack = stack.peek();
                    if (!Arithmetic.isOperator(tokenFromStack)) {
                        break;
                    }
                    if (0 >= Arithmetic.getPriority(token) - Arithmetic.getPriority(tokenFromStack)) {
                        postfix.push(stack.pop());
                    } else {
                        break;
                    }
                }
                stack.push(token);
            } else {
                throw new Exception(token + " Not Recognised");
            }
        }
        while (!stack.empty()) {
            postfix.push(stack.pop());
        }
        return postfix;
    }
    public String readToken(String tokens, int start) throws Exception {
        String token = tokens.charAt(start) + "";
        if (Arithmetic.isOperatorAll(token) || token.equals("\u2212")) {
            return token;
        }

        String digit = findNumbers(tokens, start);
        if (!digit.isEmpty()) {
            return digit;
        }
        throw new Exception(token + " Not Recognised");
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

    private String preprocess(String str) throws Exception {
        str = str.replace(" ", "");
        if (!validateBrackets(str)) {
            throw new Exception("Wrong brackets");
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

    public String evaluate(MyStack postfix, MyStack stack) throws Exception {
        if (postfix.empty()) {
            throw new Exception("Empty expression");
        }
        stack.clear();
        for (int i = 0; i < postfix.size(); i++) {
            String token = postfix.get(i);
            if (Arithmetic.isNumber(token)) {
                stack.push(token);
            } else if (Arithmetic.isUnaryOperator(token)) {
                if (stack.empty()) {
                    throw new Exception("Missing operands for " + token);
                }
                double result = Arithmetic.makeOperation(stack.pop(), token);
                stack.push(result + "");
            } else if (Arithmetic.isOperator(token)) {
                if (stack.size() < 2) {
                    throw new Exception("Missing operands for " + token);
                }
                String operand2 = stack.pop();
                String operand1 = stack.pop();
                double result = Arithmetic.makeOperation(operand1, operand2, token);
                stack.push(result + "");
            } else {
                throw new Exception(token + " not unsupported");
            }
        }
        if (stack.size() > 1) {
            throw new Exception("Invalid Reverse Polish Notation");
        }

        return stack.pop();
    }
    private String postprocess(String result) {
        if (result.equals("Infinity") || result.equals("-Infinity")) {
            return "Division by Zero";
        }
        if (result.equals("NaN")) {
            return "0";
        }
        char point = result.charAt(result.length() - 2);
        char zero = result.charAt(result.length() - 1);
        if ((point == '.') && (zero == '0')) {
            result = result.substring(0, result.indexOf("."));
        }
        return result;
    }
}

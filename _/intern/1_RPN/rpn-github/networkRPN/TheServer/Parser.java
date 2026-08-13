
public class Parser {

    public static String convertToPostfix(String str) {
        if(!validateBrackets(str)){
            return "Invalid brackets " + str;
        }
        if (str != null && !str.equals("")){
            MyStack postfix = new MyStack();
            MyStack operators = new MyStack();
            char[] characters = str.toCharArray();
            for (int i = 0; i < characters.length; i++) {
                char token = characters[i];
                if (Arithmetic.isNumber(token) || Arithmetic.isPoint(token)) {
                    String digit = "";
                    if (Arithmetic.isPoint(token)) {
                        digit = digit + "0.";
                        i = i + 1;
                        token = str.charAt(i);
                    }
                    while (!Arithmetic.isOperator(token)
                    & !Arithmetic.isBracket(token)) {
                        if (Arithmetic.isPoint(token)) {
                            digit = digit + ".";
                        } else {
                            digit = digit + token;
                        }
                        i = i + 1;
                        if (i == characters.length) {
                            break;
                        }
                        token = characters[i];
                    }
                    postfix.push(digit);
                    i = i - 1;
                } else if (token == '(') {
                    operators.push(token + "");
                } else if (token == ')') {
                    while (!operators.empty() && !operators.peek().equals("(")) {
                        postfix.push(operators.pop());
                    }
                    operators.pop();
                } else if (Arithmetic.getPriority(token) > 0) {
                    if (i == 0 || Arithmetic.getPriority(characters[i - 1]) > 0 || characters[i - 1] == '(') {
                        if (token != '-') {
                            continue;
                        }
                        operators.push("~");
                    } else {
                        while (!operators.empty()
                        && Arithmetic.getPriority(operators.peek().charAt(0)) >= Arithmetic.getPriority(token)) {
                            postfix.push(operators.pop());
                        }
                        operators.push(token + "");
                    }
                }
            }
            while (!operators.empty()) {
                postfix.push(operators.pop());
            }
            return postfix.toString();
        }
        return str;
    }
    private static boolean validateBrackets(String infix) {
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

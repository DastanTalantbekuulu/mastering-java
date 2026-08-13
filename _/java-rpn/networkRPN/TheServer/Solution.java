public class Solution {

    public static String evaluatePostfix(String postfix) {
        MyStack solution = new MyStack();
        if (postfix != null && !postfix.isEmpty() && !postfix.startsWith("Invalid")) {
            int size = postfix.length();

            for (int i = 0; i < size; i++) {
                char token = postfix.charAt(i);
                if (token == ' ') {
                    continue;
                } else if (Arithmetic.isNumber(token)) {
                    String digit = "";
                    while (token != ' ' || Arithmetic.isOperator(token)) {
                        digit = digit + token;
                        i = i + 1;
                        if (size <= i) {
                            break;
                        }
                        token = postfix.charAt(i);
                    }
                    i = i - 1;
                    solution.push(digit);
                } else if (Arithmetic.isOperator(token)) {
                    int priority = Arithmetic.getPriority(token);
                    if (100 <= priority) {
                        String a = solution.pop();
                        solution.push(Arithmetic.makeOperation(a, "0", token) + "");
                    } else if (priority > 0) {
                        String a = solution.pop();
                        String b;
                        if (solution.empty()) {
                          b = "0";
                        } else {
                          b = solution.pop();
                        }
                        solution.push(Arithmetic.makeOperation(b, a, token) + "");
                    }
                }
            }
            double result  = Double.parseDouble(solution.pop());
            if(Double.isNaN(result) || Double.isInfinite(result)){
                return "Can't divide by 0";
            }
            return result + "";
        }
        return postfix;
    }
}

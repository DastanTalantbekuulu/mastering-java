
import java.util.Stack;

//input: expression string, there may be multiple spaces
//return: reverse polish notation string with one space between each element
class ReversePolishNotation_ {

    public static String convertToReversePolish(String exp) {
        if (exp == null) {
            return null;
        }
        String res = "";
        int len = exp.length();
        Stack<Character> operator = new Stack<>();
        Stack<String> reversePolish = new Stack<>();
        //avoid checking empty
        operator.push('#');
        for (int i = 0; i < len;) {
            //deal with space
            while (i < len && exp.charAt(i) == ' ') {
                i++;
            }
            if (i == len) {
                break;
            }
            //if is number
            if (isNum(exp.charAt(i))) {
                String num = "";
                while (i < len && isNum(exp.charAt(i))) {
                    num += exp.charAt(i++);
                }
                reversePolish.push(num);
                //is operator
            } else if (isOperator(exp.charAt(i))) {
                char op = exp.charAt(i);
                switch (op) {
                    case '(' ->
                        operator.push(op);
                    case ')' -> {
                        while (operator.peek() != '(') {
                            reversePolish.push(Character.toString(operator.pop()));
                        }
                        operator.pop();
                    }
                    case '+', '-' -> {
                        if (operator.peek() == '(') {
                            operator.push(op); 
                        }else {
                            while (operator.peek() != '#' && operator.peek() != '(') {
                                reversePolish.push(Character.toString(operator.pop()));
                            }
                            operator.push(op);
                        }
                    }
                    case '*', '/' -> {
                        if (operator.peek() == '(') {
                            operator.push(op); 
                        }else {
                            while (operator.peek() != '#' && operator.peek() != '+'
                                    && operator.peek() != '-' && operator.peek() != '(') {
                                reversePolish.push(Character.toString(operator.pop()));
                            }
                            operator.push(op);
                        }
                    }
                }
                i++;
            }
        }
        while (operator.peek() != '#') {
            reversePolish.push(Character.toString(operator.pop()));
        }
        while (!reversePolish.isEmpty()) {
            res = res.length() == 0 ? reversePolish.pop() + res : reversePolish.pop() + " " + res;
        }
        return res;
    }

    private static boolean isOperator(char c) {
        return c == '+' || c == '-' || c == '*' || c == '/' || c == '(' || c == ')';
    }

    private static boolean isNum(char c) {
        return c - '0' >= 0 && c - '0' <= 9;
    }
}

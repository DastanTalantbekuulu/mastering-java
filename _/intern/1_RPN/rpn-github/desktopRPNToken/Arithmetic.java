public class Arithmetic {
    public static final String operators = "+-*/−";

    public static double makeOperation(String operand1, String operand2, String operator) {
        double da = Double.parseDouble(operand1);
        double db = Double.parseDouble(operand2);
        switch (operator) {
            case "+":
                return da + db;
            case "-":
                return da - db;
            case "*":
                return da * db;
            case "/":
                return da / db;
            default:
                return 0;
        }
    }
    public static double makeOperation(String operand,String operator) {
        double da = Double.parseDouble(operand);
        switch (operator) {
            case "−":
                return (-Math.abs(da));
            default:
                return 0;
        }
    }

    public static int getPriority(String token) {
        switch (token) {
            case "+":
                // if there is a hyphen-minus "-" x-y unicode u002D ASCII code 45
            case "-":
                return 1;
            case "*":
            case "/":
                return 2;
            // replace with minus sign "−" −x unicode u2212
            case "−":
                return 101;
            default:
                return 0;
        }
    }

    public static boolean isUnaryOperator(String token) {
        return token.equals("−");
    }

    public static boolean isNumber(char c) {
        return '0' <= c && c <= '9';
    }

    public static boolean isNumber(String token) {
        try {
            Double.parseDouble(token);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    public static boolean isPoint(char c) {
        return '.' == c || ',' == c;
    }

    public static boolean isOperator(char c) {
        return c == '+' || c == '-' || c == '*' || c == '/' ;
    }

    public static boolean isOperator(String token) {
        return token.equals("+") || token.equals("-") || token.equals("*") || token.equals("/");
    }
    public static boolean isOperatorAll(char c){
        return c == '+' || c == '-' || c == '*' || c == '/' || c == '(' || c == ')';
    }
    public static boolean isOperatorAll(String token){
        return token.equals("+") || token.equals("-") || token.equals("*")
                || token.equals("/") || token.equals("(") || token.equals(")");
    }
    public static boolean isBracket(char c) {
        return c == '(' || c == ')';
    }

    public static boolean isBracket(String token) {
        return token.equals("(") || token.equals(")");
    }

}


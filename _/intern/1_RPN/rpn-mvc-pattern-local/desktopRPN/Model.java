
public class Model {

    private final Viewer viewer;
    private final Service service;
    private String expression;

    public Model(Viewer viewer) {
        this.viewer = viewer;
        service = new Service();
        expression = "";
    }

    public void doAction(String command) {
        switch (command) {
            case "Clear":
                expression = "";
                break;
            case "Equal":
                expression = service.calculate(expression);
                break;
            case "ToggleSign":
                expression = toggleSign(expression);
                break;
            case ".":
                expression = appendPoint(expression);
                break;
            case "Bracket":
                expression = handleBracket(expression);
                break;
            case "Delete":
                expression = deleteLast(expression);
                break;
            default:
                expression = processCommand(command, expression);
        }
        viewer.update(expression);
    }

    private String toggleSign(String expression) {
        if (expression.isEmpty()) {
            return expression;
        }

        if (expression.endsWith(")")) {
            return removeNegativeSign(expression);
        } else {
            return addNegativeSign(expression);
        }
    }

    private String removeNegativeSign(String expression) {
        int lastIndex = expression.length() - 1;
        for (int i = lastIndex - 1; i >= 0; i--) {
            if (expression.charAt(i) == '-' && expression.charAt(i - 1) == '(') {
                return expression.substring(0, i - 1) + expression.substring(i + 1, lastIndex);
            }
        }
        return expression;
    }

    private String addNegativeSign(String expression) {
        int lastIndex = expression.length() - 1;
        for (int i = lastIndex; i >= 0; i--) {
            if (Arithmetic.isOperatorAll(expression.charAt(i))) {
                return expression.substring(0, i + 1) + "(-" + expression.substring(i + 1) + ")";
            } else if (i == 0) {
                return "(-" + expression + ")";
            }
        }
        return expression;
    }

    private String appendPoint(String expression) {
        if (expression.isEmpty()) {
            return "0.";
        }
        if (expression.contains(".") && !isAfterOperator(expression)) {
            return expression;
        }
        if (expression.endsWith(".") || isAfterOperator(expression)) {
            return expression + "0.";
        } else {
            return expression + ".";
        }
    }

    private String processCommand(String command, String expression) {
        if (expression.isEmpty()) {
            return expression + command;
        }
        if (Arithmetic.isOperator(command)) {
            if (expression.charAt(0) == '-') {
                expression = "(" + expression + ")";
            }
            return replaceOrAppendOperator(expression, command);
        }


        return expression + command;
    }

    private String replaceOrAppendOperator(String expression, String command) {
        if (Arithmetic.isOperator(expression.substring(expression.length() - 1))) {
            return expression.substring(0, expression.length() - 1) + command;
        } else {
            return expression + command;
        }
    }

    private boolean isAfterOperator(String expression) {
        for (int i = expression.length() - 1; i >= 0; i--) {
            if (Arithmetic.isOperatorAll(expression.charAt(i))) {
                return true;
            }
            if (expression.charAt(i) == '.') {
                return false;
            }
        }
        return false;
    }

    private String handleBracket(String expression) {
        if (expression.isEmpty()) {
            return "(";
        } else if ((hasOpenBrackets(expression) && !lastCharIsOperator(expression))) {
            return expression + ")";
        } else {
            return expression + "(";
        }
    }

    private boolean hasOpenBrackets(String expression) {
        int openBrackets = 0;
        int closeBrackets = 0;
        for (char c : expression.toCharArray()) {
            if (c == '(') {
                openBrackets++;
            } else if (c == ')') {
                closeBrackets++;
            }
        }
        return openBrackets > closeBrackets;
    }

    private boolean lastCharIsOperator(String expression) {
        return !expression.isEmpty() && Arithmetic.isOperator(expression.substring(expression.length() - 1));
    }

    private String deleteLast(String expression) {
        return expression.isEmpty() ? expression : expression.substring(0, expression.length() - 1);
    }
}

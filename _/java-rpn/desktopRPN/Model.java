public class Model {

    private Viewer viewer;
    private Service service;
    private String expression;

    public Model(Viewer viewer) {
        this.viewer = viewer;
        service = new Service();
        expression = "";
    }

    public void doAction(String command) {

        if (command.equals("Clear")) {
            expression = "";
        } else if (command.equals("ToggleSign")) {
            expression = setToggleSign(expression);
        } else if (command.equals("Equal")) {
            expression = service.calculate(expression);
        } else if (command.equals(".")) {
            expression = setPoint(command, expression);
        } else {
            expression = otherCommand(command, expression);
        }
        viewer.update(expression);
    }

    private String otherCommand(String command, String expression) {
        if (!expression.isEmpty() && Arithmetic.isOperator(command)
                && Arithmetic.isOperator(expression.substring(expression.length() - 1))) {
            return expression.substring(0, expression.length() - 1) + command;
        } else if (!expression.isEmpty() && expression.charAt(0) == '-') {
            return "(" + expression + ")" + command;
        } else {
            return expression + command;

        }
    }

    private String setPoint(String command, String expression) {
        if (expression.isEmpty()) {
            return "0.";
        } else {
            for (int i = expression.length() - 1; i >= 0; i--) {
                if (expression.charAt(i) == '.') {
                    return expression;
                }
                if (Arithmetic.isOperatorAll(expression.charAt(i))) {
                    if (expression.length() - 1 == i) {
                        expression = expression + "0.";
                    } else {
                        expression = expression + ".";
                    }
                    return expression;
                }
                if (i == 0) {
                    return expression + ".";
                }
            }
            return expression;
        }
    }

    private String setToggleSign(String expression) {
        if (expression.isEmpty()) {
            return expression;
        }
        int lastIndex = expression.length() - 1;
        // change to negative
        if (lastIndex == 0) {
            return "(-" + expression + ")";
        }
        // change to positive
        if (expression.charAt(lastIndex) == ')') {
            for (int j = lastIndex - 1; j >= 0; j--) {
                if (expression.charAt(j) == '-' && expression.charAt(j - 1) == '(') {
                    return expression.substring(0, j - 1) + expression.substring(j + 1, lastIndex);
                }
            }
        }
        // change to negative
        for (int i = lastIndex; i >= 0; i--) {
            if (Arithmetic.isOperatorAll(expression.charAt(i))) {
                return expression.substring(0, i + 1) + "(-" + expression.substring(i + 1) + ")";
            } else if(i == 0) {
                return "(-" + expression + ")";
            }
        }
        return expression;
    }
}


public class OperatorService {

    private OperatorModel[] operators;

    OperatorService() {

        OperatorModel[] oper = {
            new OperatorModel("+", 1, 2),
            new OperatorModel("-", 1, 2),
            new OperatorModel("*", 2, 2),
            new OperatorModel("/", 2, 2),
            new OperatorModel("^", 3, 1),
            new OperatorModel("~", 4, 1)
        };

        this.operators = oper;
    }

    public OperatorModel[] get() {
        return operators;
    }

    public void add(OperatorModel operator) {
        
        int len = operators.length + 1;
        OperatorModel[] newOperators = new OperatorModel[len];
        int i = 0;
        do {
            newOperators[i] = this.operators[i];
            if (i == (len - 1)) {
                newOperators[i]=operator;
            }
        } while (len > i++);
        this.operators = newOperators;
    }

    public int getPriority(String operator) {
        for (OperatorModel node : operators) {
            if (operator.equals(node.getOperator())) {
                return node.getPriority();
            }
        }
        return -1;
    }

    public int getArity(String operator) {
        for (OperatorModel node : operators) {
            if (operator.equals(node.getOperator())) {
                return node.getArity();
            }
        }
        return -1;
    }

    public boolean isOperator(String operator) {
        for (OperatorModel node : operators) {
            if (operator.equals(node.getOperator())) {
                return true;
            }
        }
        return false;
    }


}

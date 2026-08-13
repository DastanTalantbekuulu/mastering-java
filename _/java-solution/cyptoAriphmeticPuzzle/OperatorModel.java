class OperatorModel {

    private final String operator;
    private final int priority;
    private final int arity;

    OperatorModel(String operator, int priority, int arity) {
        this.operator = operator;
        this.priority = priority;
        this.arity = arity;
    }

    public String getOperator() {
        return operator;
    }

    public int getPriority() {
        return priority;
    }

    public int getArity() {
        return arity;
    }
}
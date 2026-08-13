
class Model {

    private final String infix;
    private final String prefix;
    private final String postfix;

    public Model(String infix, String prefix, String postfix) {
        this.infix = infix;
        this.prefix = prefix;
        this.postfix = postfix;
    }

    public String getInfix() {
        return infix;
    }

    public String getPrefix() {
        return prefix;
    }

    public String getPostfix() {
        return postfix;
    }
}

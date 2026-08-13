public class Service {

    private final Tokenizer tokenizer;
    private final Converter converter;
    private final Solution solution;

    public Service() {
        tokenizer = new Tokenizer();
        converter = new Converter();
        solution = new Solution();
    }

    public String calculate(String expression) {
        try {
            MyStack tokens = tokenizer.readTokens(expression);

            MyStack postfix = converter.toPostfix(tokens);

            String result = solution.evaluate(postfix);

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

        } catch (InvalidSyntaxException ise) {
            return ise.getMessage();
        } catch (TokenNotRecognisedException tnre) {
            return tnre.getMessage();
        } catch (UnsupportedTokenException ute) {
            return ute.getMessage();
        }
    }

}

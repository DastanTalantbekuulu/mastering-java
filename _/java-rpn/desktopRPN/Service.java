public class Service {

    private final Tokenizer tokenizer;
    private final Converter converter;
    private final Solution solution;
    private MyStack tokens;
    private MyStack postfix;

    public Service() {
        tokenizer = new Tokenizer();
        converter = new Converter();
        solution = new Solution();
    }

    public String calculate(String expression) {
        try {
            System.out.println("input: " + expression);

            tokens = tokenizer.readTokens(expression);
            System.out.println("infix: " + tokens);

            postfix = converter.toPostfix(tokens);
            System.out.println("postfix: " + postfix);

            String result = solution.evaluate(postfix);
            System.out.println("result: " + result);

            if (result.equals("NaN") || result.equals("Infinity") ||
                    result.equals("-Infinity")) {
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
            System.out.println("output: " + result);
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

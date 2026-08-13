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

            ConstantToken result = (ConstantToken) solution.evaluate(postfix);
            System.out.println("result: " + result);

            if (Double.isInfinite(result.getValue())) {
                return "Division by Zero";
            }
            if (Double.isNaN(result.getValue())) {
                return "0";
            }

            String str = result + "";
            char point = str.charAt(str.length() - 2);
            char zero = str.charAt(str.length() - 1);

            if ((point == '.') && (zero == '0')) {
                str = str.substring(0, str.indexOf("."));
            }
            System.out.println("output: " + str);
            return str;

        } catch (InvalidSyntaxException ise) {
            return ise.getMessage();
        } catch (TokenNotRecognisedException tnre) {
            return tnre.getMessage();
        } catch (UnsupportedTokenException ute) {
            return ute.getMessage();
        }
    }


}

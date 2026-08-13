package com.mastering.shuntingyard.evaluators;

import com.mastering.shuntingyard.lexers.DefaultLexer;
import com.mastering.shuntingyard.model.ConstantToken;
import com.mastering.shuntingyard.model.InvalidSyntaxException;
import com.mastering.shuntingyard.model.Token;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

public class DefaultEvaluatorTest {

    @Test
    public void testEvaluate() throws Exception {
        // Test equation ±(2+1*3)+√(2^2)
        DefaultLexer lexer = new DefaultLexer();
        DefaultEvaluator evaluator = new DefaultEvaluator();

        List<Token> rpn = Arrays.asList(
                new ConstantToken("2", 2),               // 2
                new ConstantToken("1", 1),               // 1
                new ConstantToken("3", 3),               // 3
                lexer.getOperators().get(2).getToken(),  // *
                lexer.getOperators().get(0).getToken(),  // +
                lexer.getOperators().get(28).getToken(), // ±
                new ConstantToken("2", 2),               // 2
                new ConstantToken("2", 2),               // 2
                lexer.getOperators().get(6).getToken(),  // ^
                lexer.getOperators().get(9).getToken(),  // √
                lexer.getOperators().get(0).getToken()   // +
        );
        List<Double> expected = Arrays.asList(7d, -3d);

        List<Double> actual = evaluator.evaluate(rpn);

        assertThat(actual)
                .as("Результат вычисления выражения должен совпадать с ожидаемым")
                .isEqualTo(expected);
    }

    @Test
    public void testEvaluate_UnexpectedTokenException_Operators() {
        // Test equation 2 1 3 (i.e. missing operators)
        DefaultEvaluator evaluator = new DefaultEvaluator();
        List<Token> rpn = Arrays.asList(
                new ConstantToken("2", 2),  // 2
                new ConstantToken("1", 1),  // 1
                new ConstantToken("3", 3)   // 3
        );

        // AssertJ проверка исключения
        assertThatThrownBy(() -> evaluator.evaluate(rpn))
                .isInstanceOf(InvalidSyntaxException.class);
//                .hasMessageContaining("Syntax error");
    }

    @Test
    public void testEvaluate_UnexpectedTokenException_Operands() {
        // Test equation * + (i.e. missing operands)
        DefaultLexer lexer = new DefaultLexer();
        DefaultEvaluator evaluator = new DefaultEvaluator();
        List<Token> rpn = Arrays.asList(
                lexer.getOperators().get(2).getToken(),  // *
                lexer.getOperators().get(0).getToken()   // +
        );

        assertThatThrownBy(() -> evaluator.evaluate(rpn))
                .isInstanceOf(InvalidSyntaxException.class);
    }
}
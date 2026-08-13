package com.mastering.shuntingyard.parsers;

import com.mastering.shuntingyard.lexers.DefaultLexer;
import com.mastering.shuntingyard.model.ConstantToken;
import com.mastering.shuntingyard.model.InvalidSyntaxException;
import com.mastering.shuntingyard.model.Token;
import com.mastering.shuntingyard.model.UnsupportedTokenException;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

public class DefaultParserTest {

    @Test
    public void testParse() throws UnsupportedTokenException, InvalidSyntaxException {
        // Test equation: ±(2)+√e
        DefaultLexer lexer = new DefaultLexer();
        DefaultParser parser = new DefaultParser();
        List<Token> tokens = Arrays.asList(
                lexer.getOperators().get(28).getToken(),              // Plus-minus
                lexer.getBrackets().getFirst(),                       // Left parenthesis
                new ConstantToken("2", 2),            // 2
                lexer.getBrackets().get(1),                           // Right parenthesis
                lexer.getOperators().getFirst().getToken(),           // +
                lexer.getOperators().get(9).getToken(),               // Square root
                new ConstantToken("e", lexer.getConstants().get("e")) // e
        );

        // Check order в RPN (Reverse Polish Notation)
        List<Token> expected = Arrays.asList(
                tokens.get(2), // 2
                tokens.get(0), // Plus-minus
                tokens.get(6), // e
                tokens.get(5), // Square root
                tokens.get(4)  // +
        );

        List<Token> actual = parser.parse(tokens);

        assertThat(actual)
                .as("Парсер должен корректно преобразовывать выражения в RPN")
                .isEqualTo(expected);
    }

    @Test
    public void testParse_BinaryOperators() throws UnsupportedTokenException, InvalidSyntaxException {
        // Test equation: 4+2*3-6/5^1
        // Checking the priority of operations
        DefaultLexer lexer = new DefaultLexer();
        DefaultParser parser = new DefaultParser();
        List<Token> tokens = Arrays.asList(
                new ConstantToken("4", 4),              // 4
                lexer.getOperators().getFirst().getToken(),             // +
                new ConstantToken("2", 2),              // 2
                lexer.getOperators().get(2).getToken(),                 // *
                new ConstantToken("3", 3),              // 3
                lexer.getOperators().get(1).getToken(),                 // -
                new ConstantToken("6", 6),              // 6
                lexer.getOperators().get(4).getToken(),                 // /
                new ConstantToken("5", 5),              // 5
                lexer.getOperators().get(6).getToken(),                 // ^
                new ConstantToken("1", 5)               // 1
        );
        List<Token> expected = Arrays.asList(
                tokens.get(0),  // 4
                tokens.get(2),  // 2
                tokens.get(4),  // 3
                tokens.get(3),  // *
                tokens.get(1),  // +
                tokens.get(6),  // 6
                tokens.get(8),  // 5
                tokens.get(10), // 1
                tokens.get(9),  // ^
                tokens.get(7),  // /
                tokens.get(5)   // -
        );

        List<Token> actual = parser.parse(tokens);

        assertThat(actual).isEqualTo(expected);
    }

    @Test
    public void testParse_Brackets() throws UnsupportedTokenException, InvalidSyntaxException {
        // Test equation: 4+(2*(3-6)/5)^1
        // Checking nested parentheses
        DefaultLexer lexer = new DefaultLexer();
        DefaultParser parser = new DefaultParser();
        List<Token> tokens = Arrays.asList(
                new ConstantToken("4", 4),              // 4
                lexer.getOperators().get(0).getToken(),                 // +
                lexer.getBrackets().get(0),                             // (
                new ConstantToken("2", 2),              // 2
                lexer.getOperators().get(2).getToken(),                 // *
                lexer.getBrackets().get(0),                             // (
                new ConstantToken("3", 3),              // 3
                lexer.getOperators().get(1).getToken(),                 // -
                new ConstantToken("6", 6),              // 6
                lexer.getBrackets().get(1),                             // )
                lexer.getOperators().get(4).getToken(),                 // /
                new ConstantToken("5", 5),              // 5
                lexer.getBrackets().get(1),                             // )
                lexer.getOperators().get(6).getToken(),                 // ^
                new ConstantToken("1", 5)               // 1
        );
        List<Token> expected = Arrays.asList(
                tokens.get(0),  // 4
                tokens.get(3),  // 2
                tokens.get(6),  // 3
                tokens.get(8),  // 6
                tokens.get(7),  // -
                tokens.get(4),  // *
                tokens.get(11), // 5
                tokens.get(10), // /
                tokens.get(14), // 1
                tokens.get(13), // ^
                tokens.get(1)   // +
        );

        List<Token> actual = parser.parse(tokens);

        assertThat(actual).isEqualTo(expected);
    }

    @Test
    public void testParse_InvalidSyntaxException_Operators() {
        // Test equation: ± +
        DefaultLexer lexer = new DefaultLexer();
        DefaultParser parser = new DefaultParser();
        List<Token> tokens = Arrays.asList(
                lexer.getOperators().get(28).getToken(),     // ±
                lexer.getOperators().getFirst().getToken()   // +
        );

        assertThatThrownBy(() -> parser.parse(tokens))
                .isInstanceOf(InvalidSyntaxException.class);
    }

    @Test
    public void testParse_InvalidSyntaxException_Brackets() {
        // Test equation: 4+2)
        DefaultLexer lexer = new DefaultLexer();
        DefaultParser parser = new DefaultParser();
        List<Token> tokens = Arrays.asList(
                new ConstantToken("4", 4),              // 4
                lexer.getOperators().getFirst().getToken(),             // +
                new ConstantToken("2", 2),              // 2
                lexer.getBrackets().get(1)                              // )
        );

        assertThatThrownBy(() -> parser.parse(tokens))
                .isInstanceOf(InvalidSyntaxException.class);
    }
}
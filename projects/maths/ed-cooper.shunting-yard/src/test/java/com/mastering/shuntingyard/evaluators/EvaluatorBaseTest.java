package com.mastering.shuntingyard.evaluators;

import com.mastering.shuntingyard.model.InvalidSyntaxException;
import com.mastering.shuntingyard.model.Token;
import com.mastering.shuntingyard.model.UnsupportedTokenException;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

public class EvaluatorBaseTest {

    // Create a concrete instance of the abstract class for testing.
    // The evaluate() method is not used in these tests, so it throws an exception if called.
    private final EvaluatorBase evaluator = new EvaluatorBase() {
        @Override
        public List<Double> evaluate(List<Token> equation) throws UnsupportedTokenException, InvalidSyntaxException {
            throw new UnsupportedOperationException("Not needed for Cartesian test");
        }
    };

    @Test
    public void testCartesian_3_3() {
        List<Double> set1 = Arrays.asList(1d, 2d, 3d);
        List<Double> set2 = Arrays.asList(4d, 5d, 6d);

        List<Double[]> expected = Arrays.asList(
                new Double[]{1d, 4d}, new Double[]{1d, 5d}, new Double[]{1d, 6d},
                new Double[]{2d, 4d}, new Double[]{2d, 5d}, new Double[]{2d, 6d},
                new Double[]{3d, 4d}, new Double[]{3d, 5d}, new Double[]{3d, 6d}
        );

        List<Double[]> actual = evaluator.cartesian(set1, set2);

        // Verify the result.
        // usingRecursiveComparison() is required because we are comparing a List of Arrays.
        // Standard equals() on arrays checks reference equality, but we need value equality.
        assertThat(actual)
                .as("Cartesian product of 3x3 sets should return 9 pairs in correct order")
                .usingRecursiveComparison()
                .isEqualTo(expected);
    }

    @Test
    public void testCartesian_3_2() {
        List<Double> set1 = Arrays.asList(1d, 2d, 3d);
        List<Double> set2 = Arrays.asList(4d, 5d);

        List<Double[]> expected = Arrays.asList(
                new Double[]{1d, 4d}, new Double[]{1d, 5d},
                new Double[]{2d, 4d}, new Double[]{2d, 5d},
                new Double[]{3d, 4d}, new Double[]{3d, 5d}
        );

        List<Double[]> actual = evaluator.cartesian(set1, set2);

        assertThat(actual)
                .as("Cartesian product of 3x2 sets should return 6 pairs")
                .usingRecursiveComparison()
                .isEqualTo(expected);
    }

    @Test
    public void testCartesian_2_3() {
        List<Double> set1 = Arrays.asList(1d, 2d);
        List<Double> set2 = Arrays.asList(4d, 5d, 6d);

        List<Double[]> expected = Arrays.asList(
                new Double[]{1d, 4d}, new Double[]{1d, 5d}, new Double[]{1d, 6d},
                new Double[]{2d, 4d}, new Double[]{2d, 5d}, new Double[]{2d, 6d}
        );

        List<Double[]> actual = evaluator.cartesian(set1, set2);

        assertThat(actual)
                .as("Cartesian product of 2x3 sets should return 6 pairs")
                .usingRecursiveComparison()
                .isEqualTo(expected);
    }
}
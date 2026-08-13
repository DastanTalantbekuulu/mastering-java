/*
 * Copyright 2014 Frank Asseg
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.mastering.exp4j;

import com.mastering.exp4j.function.Function;
import com.mastering.exp4j.operator.Operator;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

import static java.lang.Math.E;
import static java.lang.Math.PI;
import static java.lang.Math.cos;
import static java.lang.Math.log;
import static java.lang.Math.log1p;
import static java.lang.Math.pow;
import static java.lang.Math.sin;
import static java.lang.Math.sqrt;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

public class ExpressionBuilderTest {

    @Test
    public void testExpressionBuilder1() {
        double result = new ExpressionBuilder("2+1")
                .build()
                .evaluate();
        assertThat(result).isEqualTo(3d);
    }

    @Test
    public void testExpressionBuilder2() {
        double result = new ExpressionBuilder("cos(x)")
                .variables("x")
                .build()
                .setVariable("x", Math.PI)
                .evaluate();
        assertThat(result).isEqualTo(-1d);
    }

    @Test
    public void testExpressionBuilder3() {
        double x = Math.PI;
        double result = new ExpressionBuilder("sin(x)-log(3*x/4)")
                .variables("x")
                .build()
                .setVariable("x", x)
                .evaluate();

        double expected = sin(x) - log(3 * x / 4);
        assertThat(result).isEqualTo(expected);
    }

    @Test
    public void testExpressionBuilder4() {
        Function log2 = new Function("log2", 1) {
            @Override
            public double apply(double... args) {
                return Math.log(args[0]) / Math.log(2);
            }
        };
        double result = new ExpressionBuilder("log2(4)")
                .function(log2)
                .build()
                .evaluate();

        assertThat(result).isEqualTo(2d);
    }

    @Test
    public void testExpressionBuilder5() {
        Function avg = new Function("avg", 4) {
            @Override
            public double apply(double... args) {
                double sum = 0;
                for (double arg : args) {
                    sum += arg;
                }
                return sum / args.length;
            }
        };
        double result = new ExpressionBuilder("avg(1,2,3,4)")
                .function(avg)
                .build()
                .evaluate();

        assertThat(result).isEqualTo(2.5d);
    }

    @Test
    public void testExpressionBuilder6() {
        Operator factorial = new Operator("!", 1, true, Operator.PRECEDENCE_POWER + 1) {
            @Override
            public double apply(double... args) {
                final int arg = (int) args[0];
                if ((double) arg != args[0]) {
                    throw new IllegalArgumentException("Operand for factorial has to be an integer");
                }
                if (arg < 0) {
                    throw new IllegalArgumentException("The operand of the factorial can not be less than zero");
                }
                double result = 1;
                for (int i = 1; i <= arg; i++) {
                    result *= i;
                }
                return result;
            }
        };

        double result = new ExpressionBuilder("3!")
                .operator(factorial)
                .build()
                .evaluate();

        assertThat(result).isEqualTo(6d);
    }

    @Test
    public void testExpressionBuilder7() {
        ValidationResult res = new ExpressionBuilder("x")
                .variables("x")
                .build()
                .validate();
        assertThat(res.valid()).isFalse();
        assertThat(res.errors()).hasSize(1);
    }

    @Test
    public void testExpressionBuilder8() {
        ValidationResult res = new ExpressionBuilder("x*y*z")
                .variables("x", "y", "z")
                .build()
                .validate();
        assertThat(res.valid()).isFalse();
        assertThat(res.errors()).hasSize(3);
    }

    @Test
    public void testExpressionBuilder9() {
        ValidationResult res = new ExpressionBuilder("x")
                .variables("x")
                .build()
                .setVariable("x", 1d)
                .validate();
        assertThat(res.valid()).isTrue();
    }

    @Test
    public void testValidationDocExample() {
        Expression e = new ExpressionBuilder("x")
                .variables("x")
                .build();
        ValidationResult res = e.validate();
        assertThat(res.valid()).isFalse();
        assertThat(res.errors()).hasSize(1);

        e.setVariable("x", 1d);
        res = e.validate();
        assertThat(res.valid()).isTrue();
    }

    @Test
    public void testExpressionBuilder10() {
        double result = new ExpressionBuilder("1e1")
                .build()
                .evaluate();
        assertThat(result).isEqualTo(10d);
    }

    @Test
    public void testExpressionBuilder11() {
        double result = new ExpressionBuilder("1.11e-1")
                .build()
                .evaluate();
        assertThat(result).isEqualTo(0.111d);
    }

    @Test
    public void testExpressionBuilder12() {
        double result = new ExpressionBuilder("1.11e+1")
                .build()
                .evaluate();
        assertThat(result).isEqualTo(11.1d);
    }

    @Test
    public void testExpressionBuilder13() {
        double result = new ExpressionBuilder("-3^2")
                .build()
                .evaluate();
        assertThat(result).isEqualTo(-9d);
    }

    @Test
    public void testExpressionBuilder14() {
        double result = new ExpressionBuilder("(-3)^2")
                .build()
                .evaluate();
        assertThat(result).isEqualTo(9d);
    }

    @Test
    public void testExpressionBuilder15() {
        assertThatThrownBy(() -> new ExpressionBuilder("-3/0")
                .build()
                .evaluate())
                .isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    public void testExpressionBuilder16() {
        // Smoke test: should not throw exception
        new ExpressionBuilder("log(x) - y * (sqrt(x^cos(y)))")
                .variables("x", "y")
                .build()
                .setVariable("x", 1d)
                .setVariable("y", 2d)
                .evaluate();
    }

    @Test
    public void testExpressionBuilder17() {
        Expression e = new ExpressionBuilder("x-y*")
                .variables("x", "y")
                .build();
        ValidationResult res = e.validate(false);
        assertThat(res.valid()).isFalse();
        assertThat(res.errors()).hasSize(1);
        assertThat(res.errors().get(0)).isEqualTo("Too many operators");
    }

    @Test
    public void testExpressionBuilder18() {
        Expression e = new ExpressionBuilder("log(x) - y *")
                .variables("x", "y")
                .build();
        ValidationResult res = e.validate(false);
        assertThat(res.valid()).isFalse();
        assertThat(res.errors()).hasSize(1);
        assertThat(res.errors().get(0)).isEqualTo("Too many operators");
    }

    @Test
    public void testExpressionBuilder19() {
        Expression e = new ExpressionBuilder("x - y *")
                .variables("x", "y")
                .build();
        ValidationResult res = e.validate(false);
        assertThat(res.valid()).isFalse();
        assertThat(res.errors()).hasSize(1);
        assertThat(res.errors().get(0)).isEqualTo("Too many operators");
    }

    /* legacy tests from earlier exp4j versions */

    @Test
    public void testFunction1() {
        Function custom = new Function("timespi") {
            @Override
            public double apply(double... values) {
                return values[0] * Math.PI;
            }
        };
        Expression e = new ExpressionBuilder("timespi(x)")
                .function(custom)
                .variables("x")
                .build()
                .setVariable("x", 1);
        double result = e.evaluate();
        assertThat(result).isEqualTo(PI);
    }

    @Test
    public void testFunction2() {
        Function custom = new Function("loglog") {
            @Override
            public double apply(double... values) {
                return Math.log(Math.log(values[0]));
            }
        };
        Expression e = new ExpressionBuilder("loglog(x)")
                .variables("x")
                .function(custom)
                .build()
                .setVariable("x", 1);
        double result = e.evaluate();
        assertThat(result).isEqualTo(log(log(1)));
    }

    @Test
    public void testFunction3() {
        Function custom1 = new Function("foo") {
            @Override
            public double apply(double... values) {
                return values[0] * Math.E;
            }
        };
        Function custom2 = new Function("bar") {
            @Override
            public double apply(double... values) {
                return values[0] * Math.PI;
            }
        };
        Expression e = new ExpressionBuilder("foo(bar(x))")
                .function(custom1)
                .function(custom2)
                .variables("x")
                .build()
                .setVariable("x", 1);
        double result = e.evaluate();
        assertThat(result).isEqualTo(1 * E * PI);
    }

    @Test
    public void testFunction4() {
        Function custom1 = new Function("foo") {
            @Override
            public double apply(double... values) {
                return values[0] * Math.E;
            }
        };
        double varX = 32.24979131d;
        Expression e = new ExpressionBuilder("foo(log(x))")
                .variables("x")
                .function(custom1)
                .build()
                .setVariable("x", varX);
        double result = e.evaluate();
        assertThat(result).isEqualTo(log(varX) * E);
    }

    @Test
    public void testFunction5() {
        Function custom1 = new Function("foo") {
            @Override
            public double apply(double... values) {
                return values[0] * Math.E;
            }
        };
        Function custom2 = new Function("bar") {
            @Override
            public double apply(double... values) {
                return values[0] * Math.PI;
            }
        };
        double varX = 32.24979131d;
        Expression e = new ExpressionBuilder("bar(foo(log(x)))")
                .variables("x")
                .function(custom1)
                .function(custom2)
                .build()
                .setVariable("x", varX);
        double result = e.evaluate();
        assertThat(result).isEqualTo(log(varX) * E * PI);
    }

    @Test
    public void testFunction6() {
        Function custom1 = new Function("foo") {
            @Override
            public double apply(double... values) {
                return values[0] * Math.E;
            }
        };
        Function custom2 = new Function("bar") {
            @Override
            public double apply(double... values) {
                return values[0] * Math.PI;
            }
        };
        double varX = 32.24979131d;
        Expression e = new ExpressionBuilder("bar(foo(log(x)))")
                .variables("x")
                .functions(custom1, custom2)
                .build()
                .setVariable("x", varX);
        double result = e.evaluate();
        assertThat(result).isEqualTo(log(varX) * E * PI);
    }

    @Test
    public void testFunction7() {
        Function custom1 = new Function("half") {
            @Override
            public double apply(double... values) {
                return values[0] / 2;
            }
        };
        Expression e = new ExpressionBuilder("half(x)")
                .variables("x")
                .function(custom1)
                .build()
                .setVariable("x", 1d);
        assertThat(e.evaluate()).isEqualTo(0.5d);
    }

    @Test
    public void testFunction10() {
        Function custom1 = new Function("max", 2) {
            @Override
            public double apply(double... values) {
                return values[0] < values[1] ? values[1] : values[0];
            }
        };
        Expression e =
                new ExpressionBuilder("max(x,y)")
                        .variables("x", "y")
                        .function(custom1)
                        .build()
                        .setVariable("x", 1d)
                        .setVariable("y", 2d);
        assertThat(e.evaluate()).isEqualTo(2d);
    }

    @Test
    public void testFunction11() {
        Function custom1 = new Function("power", 2) {
            @Override
            public double apply(double... values) {
                return Math.pow(values[0], values[1]);
            }
        };
        Expression e =
                new ExpressionBuilder("power(x,y)")
                        .variables("x", "y")
                        .function(custom1)
                        .build()
                        .setVariable("x", 2d)
                        .setVariable("y", 4d);
        assertThat(e.evaluate()).isEqualTo(pow(2, 4));
    }

    @Test
    public void testFunction12() {
        Function custom1 = new Function("max", 5) {
            @Override
            public double apply(double... values) {
                double max = values[0];
                for (int i = 1; i < numArguments; i++) {
                    if (values[i] > max) {
                        max = values[i];
                    }
                }
                return max;
            }
        };
        Expression e = new ExpressionBuilder("max(1,2.43311,51.13,43,12)")
                .function(custom1)
                .build();
        assertThat(e.evaluate()).isEqualTo(51.13d);
    }

    @Test
    public void testFunction13() {
        Function custom1 = new Function("max", 3) {
            @Override
            public double apply(double... values) {
                double max = values[0];
                for (int i = 1; i < numArguments; i++) {
                    if (values[i] > max) {
                        max = values[i];
                    }
                }
                return max;
            }
        };
        double varX = Math.E;
        Expression e = new ExpressionBuilder("max(log(x),sin(x),x)")
                .variables("x")
                .function(custom1)
                .build()
                .setVariable("x", varX);
        assertThat(e.evaluate()).isEqualTo(varX);
    }

    @Test
    public void testFunction14() {
        Function custom1 = new Function("multiply", 2) {
            @Override
            public double apply(double... values) {
                return values[0] * values[1];
            }
        };
        double varX = 1;
        Expression e = new ExpressionBuilder("multiply(sin(x),x+1)")
                .variables("x")
                .function(custom1)
                .build()
                .setVariable("x", varX);
        double expected = Math.sin(varX) * (varX + 1);
        double actual = e.evaluate();
        assertThat(actual).isEqualTo(expected);
    }

    @Test
    public void testFunction15() {
        Function custom1 = new Function("timesPi") {
            @Override
            public double apply(double... values) {
                return values[0] * Math.PI;
            }
        };
        double varX = 1;
        Expression e = new ExpressionBuilder("timesPi(x^2)")
                .variables("x")
                .function(custom1)
                .build()
                .setVariable("x", varX);
        double expected = varX * Math.PI;
        double actual = e.evaluate();
        assertThat(actual).isEqualTo(expected);
    }

    @Test
    public void testFunction16() {
        Function custom1 = new Function("multiply", 3) {
            @Override
            public double apply(double... values) {
                return values[0] * values[1] * values[2];
            }
        };
        double varX = 1;
        Expression e = new ExpressionBuilder("multiply(sin(x),x+1^(-2),log(x))")
                .variables("x")
                .function(custom1)
                .build()
                .setVariable("x", varX);
        double expected = Math.sin(varX) * Math.pow((varX + 1), -2) * Math.log(varX);
        assertThat(e.evaluate()).isEqualTo(expected);
    }

    @Test
    public void testFunction17() {
        Function custom1 = new Function("timesPi") {
            @Override
            public double apply(double... values) {
                return values[0] * Math.PI;
            }
        };
        double varX = Math.E;
        Expression e = new ExpressionBuilder("timesPi(log(x^(2+1)))")
                .variables("x")
                .function(custom1)
                .build()
                .setVariable("x", varX);
        double expected = Math.log(Math.pow(varX, 3)) * Math.PI;
        assertThat(e.evaluate()).isEqualTo(expected);
    }

    @Test
    public void testFunction18() {
        Function minFunction = new Function("min", 2) {
            @Override
            public double apply(double[] values) {
                double currentMin = Double.POSITIVE_INFINITY;
                for (double value : values) {
                    currentMin = Math.min(currentMin, value);
                }
                return currentMin;
            }
        };
        ExpressionBuilder b = new ExpressionBuilder("-min(5, 0) + 10")
                .function(minFunction);
        double calculated = b.build().evaluate();
        assertThat(calculated).isEqualTo(10d);
    }

    @Test
    public void testFunction19() {
        Function minFunction = new Function("power", 2) {
            @Override
            public double apply(double[] values) {
                return Math.pow(values[0], values[1]);
            }
        };
        ExpressionBuilder b = new ExpressionBuilder("power(2,3)")
                .function(minFunction);
        double calculated = b.build().evaluate();
        assertThat(calculated).isEqualTo(Math.pow(2, 3));
    }

    @Test
    public void testFunction20() {
        Function maxFunction = new Function("max", 3) {
            @Override
            public double apply(double... values) {
                double max = values[0];
                for (int i = 1; i < numArguments; i++) {
                    if (values[i] > max) {
                        max = values[i];
                    }
                }
                return max;
            }
        };
        ExpressionBuilder b = new ExpressionBuilder("max(1,2,3)")
                .function(maxFunction);
        double calculated = b.build().evaluate();
        assertThat(maxFunction.getNumArguments()).isEqualTo(3);
        assertThat(calculated).isEqualTo(3d);
    }

    @Test
    public void testOperators1() {
        Operator factorial = new Operator("!", 1, true, Operator.PRECEDENCE_POWER + 1) {
            @Override
            public double apply(double... args) {
                final int arg = (int) args[0];
                if ((double) arg != args[0]) {
                    throw new IllegalArgumentException("Operand for factorial has to be an integer");
                }
                if (arg < 0) {
                    throw new IllegalArgumentException("The operand of the factorial can not be less than zero");
                }
                double result = 1;
                for (int i = 1; i <= arg; i++) {
                    result *= i;
                }
                return result;
            }
        };

        Expression e = new ExpressionBuilder("1!").operator(factorial).build();
        assertThat(e.evaluate()).isEqualTo(1d);

        e = new ExpressionBuilder("2!").operator(factorial).build();
        assertThat(e.evaluate()).isEqualTo(2d);

        e = new ExpressionBuilder("3!").operator(factorial).build();
        assertThat(e.evaluate()).isEqualTo(6d);

        e = new ExpressionBuilder("4!").operator(factorial).build();
        assertThat(e.evaluate()).isEqualTo(24d);

        e = new ExpressionBuilder("5!").operator(factorial).build();
        assertThat(e.evaluate()).isEqualTo(120d);

        e = new ExpressionBuilder("11!").operator(factorial).build();
        assertThat(e.evaluate()).isEqualTo(39916800d);
    }

    @Test
    public void testOperators2() {
        Operator factorial = new Operator("!", 1, true, Operator.PRECEDENCE_POWER + 1) {
            @Override
            public double apply(double... args) {
                final int arg = (int) args[0];
                if ((double) arg != args[0]) {
                    throw new IllegalArgumentException("Operand for factorial has to be an integer");
                }
                if (arg < 0) {
                    throw new IllegalArgumentException("The operand of the factorial can not be less than zero");
                }
                double result = 1;
                for (int i = 1; i <= arg; i++) {
                    result *= i;
                }
                return result;
            }
        };
        Expression e = new ExpressionBuilder("2^3!").operator(factorial).build();
        assertThat(e.evaluate()).isEqualTo(64d);

        e = new ExpressionBuilder("3!^2").operator(factorial).build();
        assertThat(e.evaluate()).isEqualTo(36d);

        e = new ExpressionBuilder("-(3!)^-1").operator(factorial).build();
        assertThat(e.evaluate()).isEqualTo(Math.pow(-6d, -1));
    }

    @Test
    public void testOperators3() {
        Operator gteq = new Operator(">=", 2, true, Operator.PRECEDENCE_ADDITION - 1) {
            @Override
            public double apply(double[] values) {
                if (values[0] >= values[1]) {
                    return 1d;
                } else {
                    return 0d;
                }
            }
        };
        Expression e = new ExpressionBuilder("1>=2").operator(gteq).build();
        assertThat(e.evaluate()).isEqualTo(0d);

        e = new ExpressionBuilder("2>=1").operator(gteq).build();
        assertThat(e.evaluate()).isEqualTo(1d);

        e = new ExpressionBuilder("-2>=1").operator(gteq).build();
        assertThat(e.evaluate()).isEqualTo(0d);

        e = new ExpressionBuilder("-2>=-1").operator(gteq).build();
        assertThat(e.evaluate()).isEqualTo(0d);
    }

    @Test
    public void testModulo1() {
        double result = new ExpressionBuilder("33%(20/2)%2")
                .build().evaluate();
        assertThat(result).isEqualTo(1d);
    }

    @Test
    public void testOperators4() {
        Operator greaterEq = new Operator(">=", 2, true, 4) {
            @Override
            public double apply(double[] values) {
                if (values[0] >= values[1]) {
                    return 1d;
                } else {
                    return 0d;
                }
            }
        };
        Operator greater = new Operator(">", 2, true, 4) {
            @Override
            public double apply(double[] values) {
                if (values[0] > values[1]) {
                    return 1d;
                } else {
                    return 0d;
                }
            }
        };
        Operator newPlus = new Operator(">=>", 2, true, 4) {
            @Override
            public double apply(double[] values) {
                return values[0] + values[1];
            }
        };
        Expression e = new ExpressionBuilder("1>2").operator(greater)
                .build();
        assertThat(e.evaluate()).isEqualTo(0d);

        e = new ExpressionBuilder("2>=2").operator(greaterEq)
                .build();
        assertThat(e.evaluate()).isEqualTo(1d);

        e = new ExpressionBuilder("1>=>2").operator(newPlus)
                .build();
        assertThat(e.evaluate()).isEqualTo(3d);

        e = new ExpressionBuilder("1>=>2>2").operator(greater).operator(newPlus)
                .build();
        assertThat(e.evaluate()).isEqualTo(1d);

        e = new ExpressionBuilder("1>=>2>2>=1").operator(greater).operator(newPlus)
                .operator(greaterEq)
                .build();
        assertThat(e.evaluate()).isEqualTo(1d);

        e = new ExpressionBuilder("1 >=> 2 > 2 >= 1").operator(greater).operator(newPlus)
                .operator(greaterEq)
                .build();
        assertThat(e.evaluate()).isEqualTo(1d);

        e = new ExpressionBuilder("1 >=> 2 >= 2 > 1").operator(greater).operator(newPlus)
                .operator(greaterEq)
                .build();
        assertThat(e.evaluate()).isEqualTo(0d);

        e = new ExpressionBuilder("1 >=> 2 >= 2 > 0").operator(greater).operator(newPlus)
                .operator(greaterEq)
                .build();
        assertThat(e.evaluate()).isEqualTo(1d);

        e = new ExpressionBuilder("1 >=> 2 >= 2 >= 1").operator(greater).operator(newPlus)
                .operator(greaterEq)
                .build();
        assertThat(e.evaluate()).isEqualTo(1d);
    }

    @Test
    public void testInvalidOperator1() {
        Operator fail = new Operator("2", 2, true, 1) {
            @Override
            public double apply(double[] values) {
                return 0;
            }
        };
        assertThatThrownBy(() -> new ExpressionBuilder("1").operator(fail).build())
                .isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    public void testInvalidFunction1() {
        assertThatThrownBy(() -> new Function("1gd") {
            @Override
            public double apply(double... args) {
                return 0;
            }
        }).isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    public void testInvalidFunction2() {
        assertThatThrownBy(() -> new Function("+1gd") {
            @Override
            public double apply(double... args) {
                return 0;
            }
        }).isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    public void testExpressionBuilder01() {
        Expression e = new ExpressionBuilder("7*x + 3*y")
                .variables("x", "y")
                .build()
                .setVariable("x", 1)
                .setVariable("y", 2);
        double result = e.evaluate();
        assertThat(result).isEqualTo(13d);
    }

    @Test
    public void testExpressionBuilder02() {
        Expression e = new ExpressionBuilder("7*x + 3*y")
                .variables("x", "y")
                .build()
                .setVariable("x", 1)
                .setVariable("y", 2);
        double result = e.evaluate();
        assertThat(result).isEqualTo(13d);
    }

    @Test
    public void testExpressionBuilder03() {
        double varX = 1.3d;
        double varY = 4.22d;
        Expression e = new ExpressionBuilder("7*x + 3*y - log(y/x*12)^y")
                .variables("x", "y")
                .build()
                .setVariable("x", varX)
                .setVariable("y", varY);
        double result = e.evaluate();
        assertThat(result).isEqualTo(7 * varX + 3 * varY - pow(log(varY / varX * 12), varY));
    }

    @Test
    public void testExpressionBuilder04() {
        double varX = 1.3d;
        double varY = 4.22d;
        Expression e =
                new ExpressionBuilder("7*x + 3*y - log(y/x*12)^y")
                        .variables("x", "y")
                        .build()
                        .setVariable("x", varX)
                        .setVariable("y", varY);
        double result = e.evaluate();
        assertThat(result).isEqualTo(7 * varX + 3 * varY - pow(log(varY / varX * 12), varY));

        varX = 1.79854d;
        varY = 9281.123d;
        e.setVariable("x", varX);
        e.setVariable("y", varY);
        result = e.evaluate();
        assertThat(result).isEqualTo(7 * varX + 3 * varY - pow(log(varY / varX * 12), varY));
    }

    @Test
    public void testExpressionBuilder05() {
        double varX = 1.3d;
        double varY = 4.22d;
        Expression e = new ExpressionBuilder("3*y")
                .variables("y")
                .build()
                .setVariable("x", varX)
                .setVariable("y", varY);
        double result = e.evaluate();
        assertThat(result).isEqualTo(3 * varY);
    }

    @Test
    public void testExpressionBuilder06() {
        double varX = 1.3d;
        double varY = 4.22d;
        double varZ = 4.22d;
        Expression e = new ExpressionBuilder("x * y * z")
                .variables("x", "y", "z")
                .build();
        e.setVariable("x", varX);
        e.setVariable("y", varY);
        e.setVariable("z", varZ);
        double result = e.evaluate();
        assertThat(result).isEqualTo(varX * varY * varZ);
    }

    @Test
    public void testExpressionBuilder07() {
        double varX = 1.3d;
        Expression e = new ExpressionBuilder("log(sin(x))")
                .variables("x")
                .build()
                .setVariable("x", varX);
        double result = e.evaluate();
        assertThat(result).isEqualTo(log(sin(varX)));
    }

    @Test
    public void testExpressionBuilder08() {
        double varX = 1.3d;
        Expression e = new ExpressionBuilder("log(sin(x))")
                .variables("x")
                .build()
                .setVariable("x", varX);
        double result = e.evaluate();
        assertThat(result).isEqualTo(log(sin(varX)));
    }

    @Test
    public void testSameName() {
        Function custom = new Function("bar") {
            @Override
            public double apply(double... values) {
                return values[0] / 2;
            }
        };
        double varBar = 1.3d;

        // This is expected to fail during build or validation due to name conflict
        assertThatThrownBy(() -> {
            Expression e = new ExpressionBuilder("bar(bar)")
                    .variables("bar")
                    .function(custom)
                    .build()
                    .setVariable("bar", varBar);
            ValidationResult res = e.validate();
            if (!res.valid()) {
                throw new IllegalArgumentException(res.errors().toString());
            }
        }).isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    public void testInvalidFunction() {
        double varY = 4.22d;
        assertThatThrownBy(() -> {
            Expression e = new ExpressionBuilder("3*invalid_function(y)")
                    .variables("<")
                    .build()
                    .setVariable("y", varY);
            e.evaluate();
        }).isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    public void testMissingVar() {
        double varY = 4.22d;
        assertThatThrownBy(() -> {
            Expression e = new ExpressionBuilder("3*y*z")
                    .variables("y", "z")
                    .build()
                    .setVariable("y", varY);
            e.evaluate();
        }).isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    public void testUnaryMinusPowerPrecedence() {
        Expression e = new ExpressionBuilder("-1^2")
                .build();
        assertThat(e.evaluate()).isEqualTo(-1d);
    }

    @Test
    public void testUnaryMinus() {
        Expression e = new ExpressionBuilder("-1")
                .build();
        assertThat(e.evaluate()).isEqualTo(-1d);
    }

    @Test
    public void testExpression1() {
        String expr = "2 + 4";
        double expected = 6d;
        Expression e = new ExpressionBuilder(expr).build();
        assertThat(e.evaluate()).isEqualTo(expected);
    }

    @Test
    public void testExpression10() {
        String expr = "1 * 1.5 + 1";
        double expected = 1 * 1.5 + 1;
        Expression e = new ExpressionBuilder(expr).build();
        assertThat(e.evaluate()).isEqualTo(expected);
    }

    @Test
    public void testExpression11() {
        double x = 1d;
        double y = 2d;
        String expr = "log(x) ^ sin(y)";
        double expected = Math.pow(Math.log(x), Math.sin(y));
        Expression e = new ExpressionBuilder(expr)
                .variables("x", "y")
                .build()
                .setVariable("x", x)
                .setVariable("y", y);
        assertThat(e.evaluate()).isEqualTo(expected);
    }

    @Test
    public void testExpression12() {
        String expr = "log(2.5333333333)^(0-1)";
        double expected = Math.pow(Math.log(2.5333333333d), -1);
        Expression e = new ExpressionBuilder(expr).build();
        assertThat(e.evaluate()).isEqualTo(expected);
    }

    @Test
    public void testExpression13() {
        String expr = "2.5333333333^(0-1)";
        double expected = Math.pow(2.5333333333d, -1);
        Expression e = new ExpressionBuilder(expr).build();
        assertThat(e.evaluate()).isEqualTo(expected);
    }

    @Test
    public void testExpression14() {
        String expr = "2 * 17.41 + (12*2)^(0-1)";
        double expected = 2 * 17.41d + Math.pow((12 * 2), -1);
        Expression e = new ExpressionBuilder(expr).build();
        assertThat(e.evaluate()).isEqualTo(expected);
    }

    @Test
    public void testExpression15() {
        String expr = "2.5333333333 * 17.41 + (12*2)^log(2.764)";
        double expected = 2.5333333333d * 17.41d + Math.pow((12 * 2), Math.log(2.764d));
        Expression e = new ExpressionBuilder(expr).build();
        assertThat(e.evaluate()).isEqualTo(expected);
    }

    @Test
    public void testExpression16() {
        String expr = "2.5333333333/2 * 17.41 + (12*2)^(log(2.764) - sin(5.6664))";
        double expected = 2.5333333333d / 2 * 17.41d + Math.pow((12 * 2), Math.log(2.764d) - Math.sin(5.6664d));
        Expression e = new ExpressionBuilder(expr).build();
        assertThat(e.evaluate()).isEqualTo(expected);
    }

    @Test
    public void testExpression17() {
        String expr = "x^2 - 2 * y";
        double x = Math.E;
        double y = Math.PI;
        double expected = x * x - 2 * y;
        Expression e = new ExpressionBuilder(expr)
                .variables("x", "y")
                .build()
                .setVariable("x", x)
                .setVariable("y", y);
        assertThat(e.evaluate()).isEqualTo(expected);
    }

    @Test
    public void testExpression18() {
        String expr = "-3";
        double expected = -3;
        Expression e = new ExpressionBuilder(expr).build();
        assertThat(e.evaluate()).isEqualTo(expected);
    }

    @Test
    public void testExpression19() {
        String expr = "-3 * -24.23";
        double expected = -3 * -24.23d;
        Expression e = new ExpressionBuilder(expr).build();
        assertThat(e.evaluate()).isEqualTo(expected);
    }

    @Test
    public void testExpression2() {
        String expr = "2+3*4-12";
        double expected = 2 + 3 * 4 - 12;
        Expression e = new ExpressionBuilder(expr).build();
        assertThat(e.evaluate()).isEqualTo(expected);
    }

    @Test
    public void testExpression20() {
        String expr = "-2 * 24/log(2) -2";
        double expected = -2 * 24 / Math.log(2) - 2;
        Expression e = new ExpressionBuilder(expr).build();
        assertThat(e.evaluate()).isEqualTo(expected);
    }

    @Test
    public void testExpression21() {
        String expr = "-2 *33.34/log(x)^-2 + 14 *6";
        double x = 1.334d;
        double expected = -2 * 33.34 / Math.pow(Math.log(x), -2) + 14 * 6;
        Expression e = new ExpressionBuilder(expr)
                .variables("x")
                .build()
                .setVariable("x", x);
        assertThat(e.evaluate()).isEqualTo(expected);
    }

    @Test
    public void testExpressionPower() {
        String expr = "2^-2";
        double expected = Math.pow(2, -2);
        Expression e = new ExpressionBuilder(expr).build();
        assertThat(e.evaluate()).isEqualTo(expected);
    }

    @Test
    public void testExpressionMultiplication() {
        String expr = "2*-2";
        double expected = -4d;
        Expression e = new ExpressionBuilder(expr).build();
        assertThat(e.evaluate()).isEqualTo(expected);
    }

    @Test
    public void testExpression22() {
        String expr = "-2 *33.34/log(x)^-2 + 14 *6";
        double x = 1.334d;
        double expected = -2 * 33.34 / Math.pow(Math.log(x), -2) + 14 * 6;
        Expression e = new ExpressionBuilder(expr)
                .variables("x")
                .build()
                .setVariable("x", x);
        assertThat(e.evaluate()).isEqualTo(expected);
    }

    @Test
    public void testExpression23() {
        String expr = "-2 *33.34/(log(foo)^-2 + 14 *6) - sin(foo)";
        double x = 1.334d;
        double expected = -2 * 33.34 / (Math.pow(Math.log(x), -2) + 14 * 6) - Math.sin(x);
        Expression e = new ExpressionBuilder(expr)
                .variables("foo")
                .build()
                .setVariable("foo", x);
        assertThat(e.evaluate()).isEqualTo(expected);
    }

    @Test
    public void testExpression24() {
        String expr = "3+4-log(23.2)^(2-1) * -1";
        double expected = 3 + 4 - Math.pow(Math.log(23.2), (2 - 1)) * -1;
        Expression e = new ExpressionBuilder(expr).build();
        assertThat(e.evaluate()).isEqualTo(expected);
    }

    @Test
    public void testExpression25() {
        String expr = "+3+4-+log(23.2)^(2-1) * + 1";
        double expected = 3 + 4 - Math.log(23.2d);
        Expression e = new ExpressionBuilder(expr).build();
        assertThat(e.evaluate()).isEqualTo(expected);
    }

    @Test
    public void testExpression26() {
        String expr = "14 + -(1 / 2.22^3)";
        double expected = 14 + -(1d / Math.pow(2.22d, 3d));
        Expression e = new ExpressionBuilder(expr).build();
        assertThat(e.evaluate()).isEqualTo(expected);
    }

    @Test
    public void testExpression27() {
        String expr = "12^-+-+-+-+-+-+---2";
        double expected = Math.pow(12, -2);
        Expression e = new ExpressionBuilder(expr).build();
        assertThat(e.evaluate()).isEqualTo(expected);
    }

    @Test
    public void testExpression28() {
        String expr = "12^-+-+-+-+-+-+---2 * (-14) / 2 ^ -log(2.22323) ";
        double expected = Math.pow(12, -2) * -14 / Math.pow(2, -Math.log(2.22323));
        Expression e = new ExpressionBuilder(expr).build();
        assertThat(e.evaluate()).isEqualTo(expected);
    }

    @Test
    public void testExpression29() {
        String expr = "24.3343 % 3";
        double expected = 24.3343 % 3;
        Expression e = new ExpressionBuilder(expr).build();
        assertThat(e.evaluate()).isEqualTo(expected);
    }

    @Test
    public void testVarName1() {
        String expr = "12.23 * foo.bar";
        Expression e = new ExpressionBuilder(expr)
                .variables("foo.bar")
                .build()
                .setVariable("foo.bar", 1d);
        assertThat(e.evaluate()).isEqualTo(12.23);
    }

    @Test
    public void testMisplacedSeparator() {
        String expr = "12.23 * ,foo";
        assertThatThrownBy(() -> new ExpressionBuilder(expr)
                .build()
                .setVariable(",foo", 1d))
                .isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    public void testInvalidVarName() {
        String expr = "12.23 * @foo";
        assertThatThrownBy(() -> new ExpressionBuilder(expr)
                .build()
                .setVariable("@foo", 1d))
                .isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    public void testVarMap() {
        String expr = "12.23 * foo - bar";
        Map<String, Double> variables = new HashMap<>();
        variables.put("foo", 2d);
        variables.put("bar", 3.3d);
        Expression e = new ExpressionBuilder(expr)
                .variables(variables.keySet())
                .build()
                .setVariables(variables);
        assertThat(e.evaluate()).isEqualTo(12.23d * 2d - 3.3d);
    }

    @Test
    public void testInvalidNumberOfArguments1() {
        String expr = "log(2,2)";
        assertThatThrownBy(() -> new ExpressionBuilder(expr).build().evaluate())
                .isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    public void testInvalidNumberOfArguments2() {
        Function avg = new Function("avg", 4) {
            @Override
            public double apply(double... args) {
                double sum = 0;
                for (double arg : args) {
                    sum += arg;
                }
                return sum / args.length;
            }
        };
        String expr = "avg(2,2)";
        assertThatThrownBy(() -> new ExpressionBuilder(expr).build().evaluate())
                .isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    public void testExpression3() {
        String expr = "2+4*5";
        double expected = 2 + 4 * 5;
        Expression e = new ExpressionBuilder(expr).build();
        assertThat(e.evaluate()).isEqualTo(expected);
    }

    @Test
    public void testExpression30() {
        String expr = "24.3343 % 3 * 20 ^ -(2.334 % log(2 / 14))";
        double expected = 24.3343d % 3 * Math.pow(20, -(2.334 % Math.log(2d / 14d)));
        Expression e = new ExpressionBuilder(expr).build();
        assertThat(e.evaluate()).isEqualTo(expected);
    }

    @Test
    public void testExpression31() {
        String expr = "-2 *33.34/log(y_x)^-2 + 14 *6";
        double x = 1.334d;
        double expected = -2 * 33.34 / Math.pow(Math.log(x), -2) + 14 * 6;
        Expression e = new ExpressionBuilder(expr)
                .variables("y_x")
                .build()
                .setVariable("y_x", x);
        assertThat(e.evaluate()).isEqualTo(expected);
    }

    @Test
    public void testExpression32() {
        String expr = "-2 *33.34/log(y_2x)^-2 + 14 *6";
        double x = 1.334d;
        double expected = -2 * 33.34 / Math.pow(Math.log(x), -2) + 14 * 6;
        Expression e = new ExpressionBuilder(expr)
                .variables("y_2x")
                .build()
                .setVariable("y_2x", x);
        assertThat(e.evaluate()).isEqualTo(expected);
    }

    @Test
    public void testExpression33() {
        String expr = "-2 *33.34/log(_y)^-2 + 14 *6";
        double x = 1.334d;
        double expected = -2 * 33.34 / Math.pow(Math.log(x), -2) + 14 * 6;
        Expression e = new ExpressionBuilder(expr)
                .variables("_y")
                .build()
                .setVariable("_y", x);
        assertThat(e.evaluate()).isEqualTo(expected);
    }

    @Test
    public void testExpression34() {
        String expr = "-2 + + (+4) +(4)";
        double expected = -2 + 4 + 4;
        Expression e = new ExpressionBuilder(expr).build();
        assertThat(e.evaluate()).isEqualTo(expected);
    }

    @Test
    public void testExpression40() {
        String expr = "1e1";
        double expected = 10d;
        Expression e = new ExpressionBuilder(expr).build();
        assertThat(e.evaluate()).isEqualTo(expected);
    }

    @Test
    public void testExpression41() {
        String expr = "1e-1";
        double expected = 0.1d;
        Expression e = new ExpressionBuilder(expr).build();
        assertThat(e.evaluate()).isEqualTo(expected);
    }

    @Test
    public void testExpression42() {
        String expr = "7.2973525698e-3";
        double expected = 7.2973525698e-3d;
        Expression e = new ExpressionBuilder(expr).build();
        assertThat(e.evaluate()).isEqualTo(expected);
    }

    @Test
    public void testExpression43() {
        String expr = "6.02214E23";
        double expected = 6.02214e23d;
        Expression e = new ExpressionBuilder(expr).build();
        assertThat(e.evaluate()).isEqualTo(expected);
    }

    @Test
    public void testExpression44() {
        String expr = "6.02214E23";
        double expected = 6.02214e23d;
        Expression e = new ExpressionBuilder(expr).build();
        assertThat(e.evaluate()).isEqualTo(expected);
    }

    @Test
    public void testExpression45() {
        String expr = "6.02214E2E3";
        assertThatThrownBy(() -> new ExpressionBuilder(expr).build())
                .isInstanceOf(NumberFormatException.class);
    }

    @Test
    public void testExpression46() {
        String expr = "6.02214e2E3";
        assertThatThrownBy(() -> new ExpressionBuilder(expr).build())
                .isInstanceOf(NumberFormatException.class);
    }

    @Test
    public void testExpression48() {
        String expr = "(1*2";
        assertThatThrownBy(() -> new ExpressionBuilder(expr).build().evaluate())
                .isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    public void testExpression49() {
        String expr = "{1*2";
        assertThatThrownBy(() -> new ExpressionBuilder(expr).build().evaluate())
                .isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    public void testExpression50() {
        String expr = "[1*2";
        assertThatThrownBy(() -> new ExpressionBuilder(expr).build().evaluate())
                .isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    public void testExpression51() {
        String expr = "(1*{2+[3}";
        assertThatThrownBy(() -> new ExpressionBuilder(expr).build().evaluate())
                .isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    public void testExpression52() {
        String expr = "(1*(2+(3";
        assertThatThrownBy(() -> new ExpressionBuilder(expr).build().evaluate())
                .isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    public void testExpression53() {
        String expr = "14 * 2x";
        Expression exp = new ExpressionBuilder(expr)
                .variables("x")
                .build();
        exp.setVariable("x", 1.5d);
        assertThat(exp.validate().valid()).isTrue();
        assertThat(exp.evaluate()).isEqualTo(14d * 2d * 1.5d);
    }

    @Test
    public void testExpression54() {
        String expr = "2 ((-(x)))";
        Expression e = new ExpressionBuilder(expr)
                .variables("x")
                .build();
        e.setVariable("x", 1.5d);
        assertThat(e.evaluate()).isEqualTo(-3d);
    }

    @Test
    public void testExpression55() {
        String expr = "2 sin(x)";
        Expression e = new ExpressionBuilder(expr)
                .variables("x")
                .build();
        e.setVariable("x", 2d);
        assertThat(e.evaluate()).isEqualTo(sin(2d) * 2);
    }

    @Test
    public void testExpression56() {
        String expr = "2 sin(3x)";
        Expression e = new ExpressionBuilder(expr)
                .variables("x")
                .build();
        e.setVariable("x", 2d);
        assertThat(e.evaluate()).isEqualTo(sin(6d) * 2d);
    }

    @Test
    public void testDocumentationExample1() {
        Expression e = new ExpressionBuilder("3 * sin(y) - 2 / (x - 2)")
                .variables("x", "y")
                .build()
                .setVariable("x", 2.3)
                .setVariable("y", 3.14);
        double result = e.evaluate();
        double expected = 3 * Math.sin(3.14d) - 2d / (2.3d - 2d);
        assertThat(result).isEqualTo(expected);
    }

    @Test
    public void testDocumentationExample2() throws Exception {
        ExecutorService exec = Executors.newFixedThreadPool(1);
        try {
            Expression e = new ExpressionBuilder("3log(y)/(x+1)")
                    .variables("x", "y")
                    .build()
                    .setVariable("x", 2.3)
                    .setVariable("y", 3.14);
            Future<Double> result = e.evaluateAsync(exec);
            double expected = 3 * Math.log(3.14d) / (3.3);
            assertThat(result.get()).isEqualTo(expected);
        } finally {
            exec.shutdown();
        }
    }

    @Test
    public void testDocumentationExample3() {
        double result = new ExpressionBuilder("2cos(xy)")
                .variables("x", "y")
                .build()
                .setVariable("x", 0.5d)
                .setVariable("y", 0.25d)
                .evaluate();
        assertThat(result).isEqualTo(2d * Math.cos(0.5d * 0.25d));
    }

    @Test
    public void testDocumentationExample4() {
        String expr = "pi+π+e+φ";
        double expected = 2 * Math.PI + Math.E + 1.61803398874d;
        Expression e = new ExpressionBuilder(expr).build();
        assertThat(e.evaluate()).isEqualTo(expected);
    }

    @Test
    public void testDocumentationExample5() {
        String expr = "7.2973525698e-3";
        double expected = Double.parseDouble(expr);
        Expression e = new ExpressionBuilder(expr).build();
        assertThat(e.evaluate()).isEqualTo(expected);
    }

    @Test
    public void testDocumentationExample6() {
        Function logb = new Function("logb", 2) {
            @Override
            public double apply(double... args) {
                return Math.log(args[0]) / Math.log(args[1]);
            }
        };
        double result = new ExpressionBuilder("logb(8, 2)")
                .function(logb)
                .build()
                .evaluate();
        assertThat(result).isEqualTo(3d);
    }

    @Test
    public void testDocumentationExample7() {
        Function avg = new Function("avg", 4) {
            @Override
            public double apply(double... args) {
                double sum = 0;
                for (double arg : args) {
                    sum += arg;
                }
                return sum / args.length;
            }
        };
        double result = new ExpressionBuilder("avg(1,2,3,4)")
                .function(avg)
                .build()
                .evaluate();

        assertThat(result).isEqualTo(2.5d);
    }

    @Test
    public void testDocumentationExample8() {
        Operator factorial = new Operator("!", 1, true, Operator.PRECEDENCE_POWER + 1) {
            @Override
            public double apply(double... args) {
                final int arg = (int) args[0];
                if ((double) arg != args[0]) {
                    throw new IllegalArgumentException("Operand for factorial has to be an integer");
                }
                if (arg < 0) {
                    throw new IllegalArgumentException("The operand of the factorial can not be less than zero");
                }
                double result = 1;
                for (int i = 1; i <= arg; i++) {
                    result *= i;
                }
                return result;
            }
        };

        double result = new ExpressionBuilder("3!")
                .operator(factorial)
                .build()
                .evaluate();
        assertThat(result).isEqualTo(6d);
    }

    @Test
    public void testDocumentationExample9() {
        Operator gteq = new Operator(">=", 2, true, Operator.PRECEDENCE_ADDITION - 1) {
            @Override
            public double apply(double[] values) {
                if (values[0] >= values[1]) {
                    return 1d;
                } else {
                    return 0d;
                }
            }
        };

        Expression e = new ExpressionBuilder("1>=2").operator(gteq).build();
        assertThat(e.evaluate()).isEqualTo(0d);

        e = new ExpressionBuilder("2>=1").operator(gteq).build();
        assertThat(e.evaluate()).isEqualTo(1d);
    }

    @Test
    public void testDocumentationExample10() {
        Operator reciprocal = new Operator("$", 1, true, Operator.PRECEDENCE_DIVISION) {
            @Override
            public double apply(final double... args) {
                if (args[0] == 0d) {
                    throw new ArithmeticException("Division by zero!");
                }
                return 1d / args[0];
            }
        };
        assertThatThrownBy(() -> new ExpressionBuilder("0$").operator(reciprocal).build().evaluate())
                .isInstanceOf(ArithmeticException.class);
    }

    @Test
    public void testDocumentationExample11() {
        Expression e = new ExpressionBuilder("x")
                .variable("x")
                .build();

        ValidationResult res = e.validate();
        assertThat(res.valid()).isFalse();
        assertThat(res.errors()).hasSize(1);

        e.setVariable("x", 1d);
        res = e.validate();
        assertThat(res.valid()).isTrue();
    }

    @Test
    public void testDocumentationExample12() {
        Expression e = new ExpressionBuilder("x")
                .variable("x")
                .build();

        ValidationResult res = e.validate(false);
        assertThat(res.valid()).isTrue();
        assertThat(res.errors()).isNull();
    }

    @Test
    public void testExpression57() {
        String expr = "1 / 0";
        Expression e = new ExpressionBuilder(expr).build();
        assertThatThrownBy(e::evaluate).isInstanceOf(ArithmeticException.class);
    }

    @Test
    public void testExpression58() {
        String expr = "17 * sqrt(-1) * 12";
        Expression e = new ExpressionBuilder(expr).build();
        assertThat(e.evaluate()).isNaN();
    }

    @Test
    public void testExpression59() {
        assertThatThrownBy(() -> new ExpressionBuilder("").build())
                .isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    public void testExpression60() {
        assertThatThrownBy(() -> new ExpressionBuilder("   ").build().evaluate())
                .isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    public void testExpression61() {
        assertThatThrownBy(() -> new ExpressionBuilder("14 % 0").build().evaluate())
                .isInstanceOf(ArithmeticException.class);
    }

    @Test
    public void testExpression62() {
        Expression e = new ExpressionBuilder("x*1.0e5+5")
                .variables("x")
                .build()
                .setVariable("x", Math.E);
        assertThat(e.evaluate()).isEqualTo(E * 1.0 * pow(10, 5) + 5);
    }

    @Test
    public void testExpression63() {
        Expression e = new ExpressionBuilder("log10(5)").build();
        assertThat(e.evaluate()).isEqualTo(Math.log10(5));
    }

    @Test
    public void testExpression64() {
        Expression e = new ExpressionBuilder("log2(5)").build();
        assertThat(e.evaluate()).isEqualTo(Math.log(5) / Math.log(2));
    }

    @Test
    public void testExpression65() {
        Expression e = new ExpressionBuilder("2log(e)")
                .variables("e")
                .build()
                .setVariable("e", Math.E);
        assertThat(e.evaluate()).isEqualTo(2d);
    }

    @Test
    public void testExpression66() {
        Expression e = new ExpressionBuilder("log(e)2")
                .variables("e")
                .build()
                .setVariable("e", Math.E);
        assertThat(e.evaluate()).isEqualTo(2d);
    }

    @Test
    public void testExpression67() {
        Expression e = new ExpressionBuilder("2esin(pi/2)")
                .variables("e", "pi")
                .build()
                .setVariable("e", Math.E)
                .setVariable("pi", Math.PI);
        assertThat(e.evaluate()).isEqualTo(2 * Math.E * Math.sin(Math.PI / 2d));
    }

    @Test
    public void testExpression68() {
        Expression e = new ExpressionBuilder("2x")
                .variables("x")
                .build()
                .setVariable("x", Math.E);
        assertThat(e.evaluate()).isEqualTo(2 * Math.E);
    }

    @Test
    public void testExpression69() {
        Expression e = new ExpressionBuilder("2x2")
                .variables("x")
                .build()
                .setVariable("x", Math.E);
        assertThat(e.evaluate()).isEqualTo(4 * Math.E);
    }

    @Test
    public void testExpression70() {
        Expression e = new ExpressionBuilder("2xx")
                .variables("x")
                .build()
                .setVariable("x", Math.E);
        assertThat(e.evaluate()).isEqualTo(2 * Math.E * Math.E);
    }

    @Test
    public void testExpression71() {
        Expression e = new ExpressionBuilder("x2x")
                .variables("x")
                .build()
                .setVariable("x", Math.E);
        assertThat(e.evaluate()).isEqualTo(2 * Math.E * Math.E);
    }

    @Test
    public void testExpression72() {
        Expression e = new ExpressionBuilder("2cos(x)")
                .variables("x")
                .build()
                .setVariable("x", Math.E);
        assertThat(e.evaluate()).isEqualTo(2 * Math.cos(Math.E));
    }

    @Test
    public void testExpression73() {
        Expression e = new ExpressionBuilder("cos(x)2")
                .variables("x")
                .build()
                .setVariable("x", Math.E);
        assertThat(e.evaluate()).isEqualTo(2 * Math.cos(Math.E));
    }

    @Test
    public void testExpression74() {
        Expression e = new ExpressionBuilder("cos(x)(-2)")
                .variables("x")
                .build()
                .setVariable("x", Math.E);
        assertThat(e.evaluate()).isEqualTo(-2d * Math.cos(Math.E));
    }

    @Test
    public void testExpression75() {
        Expression e = new ExpressionBuilder("(-2)cos(x)")
                .variables("x")
                .build()
                .setVariable("x", Math.E);
        assertThat(e.evaluate()).isEqualTo(-2d * Math.cos(Math.E));
    }

    @Test
    public void testExpression76() {
        Expression e = new ExpressionBuilder("(-x)cos(x)")
                .variables("x")
                .build()
                .setVariable("x", Math.E);
        assertThat(e.evaluate()).isEqualTo(-E * Math.cos(Math.E));
    }

    @Test
    public void testExpression77() {
        Expression e = new ExpressionBuilder("(-xx)cos(x)")
                .variables("x")
                .build()
                .setVariable("x", Math.E);
        assertThat(e.evaluate()).isEqualTo(-E * E * Math.cos(Math.E));
    }

    @Test
    public void testExpression78() {
        Expression e = new ExpressionBuilder("(xx)cos(x)")
                .variables("x")
                .build()
                .setVariable("x", Math.E);
        assertThat(e.evaluate()).isEqualTo(E * E * Math.cos(Math.E));
    }

    @Test
    public void testExpression79() {
        Expression e = new ExpressionBuilder("cos(x)(xx)")
                .variables("x")
                .build()
                .setVariable("x", Math.E);
        assertThat(e.evaluate()).isEqualTo(E * E * Math.cos(Math.E));
    }

    @Test
    public void testExpression80() {
        Expression e = new ExpressionBuilder("cos(x)(xy)")
                .variables("x", "y")
                .build()
                .setVariable("x", Math.E)
                .setVariable("y", Math.sqrt(2));
        assertThat(e.evaluate()).isEqualTo(sqrt(2) * E * Math.cos(Math.E));
    }

    @Test
    public void testExpression81() {
        Expression e = new ExpressionBuilder("cos(xy)")
                .variables("x", "y")
                .build()
                .setVariable("x", Math.E)
                .setVariable("y", Math.sqrt(2));
        assertThat(e.evaluate()).isEqualTo(cos(sqrt(2) * E));
    }

    @Test
    public void testExpression82() {
        Expression e = new ExpressionBuilder("cos(2x)")
                .variables("x")
                .build()
                .setVariable("x", Math.E);
        assertThat(e.evaluate()).isEqualTo(cos(2 * E));
    }

    @Test
    public void testExpression83() {
        Expression e = new ExpressionBuilder("cos(xlog(xy))")
                .variables("x", "y")
                .build()
                .setVariable("x", Math.E)
                .setVariable("y", Math.sqrt(2));
        assertThat(e.evaluate()).isEqualTo(cos(E * log(E * sqrt(2))));
    }

    @Test
    public void testExpression84() {
        Expression e = new ExpressionBuilder("3x_1")
                .variables("x_1")
                .build()
                .setVariable("x_1", Math.E);
        assertThat(e.evaluate()).isEqualTo(3d * E);
    }

    @Test
    public void testExpression85() {
        Expression e = new ExpressionBuilder("1/2x")
                .variables("x")
                .build()
                .setVariable("x", 6);
        assertThat(e.evaluate()).isEqualTo(3d);
    }

    @Test
    public void testSpaceBetweenNumbers() {
        assertThatThrownBy(() -> new ExpressionBuilder("1 1").build())
                .isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    public void testUnaryMinusInParenthesisSpace() {
        ExpressionBuilder b = new ExpressionBuilder("( -1)^2");
        double calculated = b.build().evaluate();
        assertThat(calculated).isEqualTo(1d);
    }

    @Test
    public void testUnaryMinusSpace() {
        ExpressionBuilder b = new ExpressionBuilder(" -1 + 2");
        double calculated = b.build().evaluate();
        assertThat(calculated).isEqualTo(1d);
    }

    @Test
    public void testUnaryMinusSpaces() {
        ExpressionBuilder b = new ExpressionBuilder(" -1 + + 2 +   -   1");
        double calculated = b.build().evaluate();
        assertThat(calculated).isEqualTo(0d);
    }

    @Test
    public void testUnaryMinusSpace1() {
        ExpressionBuilder b = new ExpressionBuilder("-1");
        double calculated = b.build().evaluate();
        assertThat(calculated).isEqualTo(-1d);
    }

    @Test
    public void testExpression4() {
        String expr = "2+4 * 5";
        double expected = 2 + 4 * 5;
        Expression e = new ExpressionBuilder(expr).build();
        assertThat(e.evaluate()).isEqualTo(expected);
    }

    @Test
    public void testExpression5() {
        String expr = "(2+4)*5";
        double expected = (2 + 4) * 5;
        Expression e = new ExpressionBuilder(expr).build();
        assertThat(e.evaluate()).isEqualTo(expected);
    }

    @Test
    public void testExpression6() {
        String expr = "(2+4)*5 + 2.5*2";
        double expected = (2 + 4) * 5 + 2.5 * 2;
        Expression e = new ExpressionBuilder(expr).build();
        assertThat(e.evaluate()).isEqualTo(expected);
    }

    @Test
    public void testExpression7() {
        String expr = "(2+4)*5 + 10/2";
        double expected = (2 + 4) * 5 + 10 / 2;
        Expression e = new ExpressionBuilder(expr).build();
        assertThat(e.evaluate()).isEqualTo(expected);
    }

    @Test
    public void testExpression8() {
        String expr = "(2 * 3 +4)*5 + 10/2";
        double expected = (2 * 3 + 4) * 5 + 10 / 2;
        Expression e = new ExpressionBuilder(expr).build();
        assertThat(e.evaluate()).isEqualTo(expected);
    }

    @Test
    public void testExpression9() {
        String expr = "(2 * 3 +4)*5 +4 + 10/2";
        double expected = 59; //(2 * 3 + 4) * 5 + 4 + 10 / 2 = 59
        Expression e = new ExpressionBuilder(expr).build();
        assertThat(e.evaluate()).isEqualTo(expected);
    }

    @Test
    public void testFailUnknownFunction1() {
        assertThatThrownBy(() -> {
            String expr = "lig(1)";
            new ExpressionBuilder(expr).build().evaluate();
        }).isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    public void testFailUnknownFunction2() {
        assertThatThrownBy(() -> {
            String expr = "galength(1)";
            new ExpressionBuilder(expr).build().evaluate();
        }).isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    public void testFailUnknownFunction3() {
        assertThatThrownBy(() -> {
            String expr = "tcos(1)";
            new ExpressionBuilder(expr).build().evaluate();
        }).isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    public void testFunction22() {
        String expr = "cos(cos_1)";
        Expression e = new ExpressionBuilder(expr)
                .variables("cos_1")
                .build()
                .setVariable("cos_1", 1d);
        assertThat(e.evaluate()).isEqualTo(cos(1d));
    }

    @Test
    public void testFunction23() {
        String expr = "log1p(1)";
        Expression e = new ExpressionBuilder(expr).build();
        assertThat(e.evaluate()).isEqualTo(log1p(1d));
    }

    @Test
    public void testFunction24() {
        String expr = "pow(3,3)";
        Expression e = new ExpressionBuilder(expr).build();
        assertThat(e.evaluate()).isEqualTo(27d);
    }

    @Test
    public void testPostfix1() {
        String expr = "2.2232^0.1";
        double expected = Math.pow(2.2232d, 0.1d);
        double actual = new ExpressionBuilder(expr).build().evaluate();
        assertThat(actual).isEqualTo(expected);
    }

    @Test
    public void testPostfixEverything() {
        String expr = "(sin(12) + log(34)) * 3.42 - cos(2.234-log(2))";
        double expected = (Math.sin(12) + Math.log(34)) * 3.42 - Math.cos(2.234 - Math.log(2));
        Expression e = new ExpressionBuilder(expr).build();
        assertThat(e.evaluate()).isEqualTo(expected);
    }

    @Test
    public void testPostfixExponentiation1() {
        String expr = "2^3";
        double expected = Math.pow(2, 3);
        Expression e = new ExpressionBuilder(expr).build();
        assertThat(e.evaluate()).isEqualTo(expected);
    }

    @Test
    public void testPostfixExponentiation2() {
        String expr = "24 + 4 * 2^3";
        double expected = 24 + 4 * Math.pow(2, 3);
        Expression e = new ExpressionBuilder(expr).build();
        assertThat(e.evaluate()).isEqualTo(expected);
    }

    @Test
    public void testPostfixExponentiation3() {
        double x = 4.334d;
        String expr = "24 + 4 * 2^x";
        double expected = 24 + 4 * Math.pow(2, x);
        Expression e = new ExpressionBuilder(expr)
                .variables("x")
                .build()
                .setVariable("x", x);
        assertThat(e.evaluate()).isEqualTo(expected);
    }

    @Test
    public void testPostfixExponentiation4() {
        double x = 4.334d;
        String expr = "(24 + 4) * 2^log(x)";
        double expected = (24 + 4) * Math.pow(2, Math.log(x));
        Expression e = new ExpressionBuilder(expr)
                .variables("x")
                .build()
                .setVariable("x", x);
        assertThat(e.evaluate()).isEqualTo(expected);
    }

    @Test
    public void testPostfixFunction1() {
        String expr = "log(1) * sin(0)";
        double expected = Math.log(1) * Math.sin(0);
        Expression e = new ExpressionBuilder(expr).build();
        assertThat(e.evaluate()).isEqualTo(expected);
    }

    @Test
    public void testPostfixFunction10() {
        String expr = "cbrt(x)";
        Expression e = new ExpressionBuilder(expr)
                .variables("x")
                .build();
        for (double x = -10; x < 10; x = x + 0.5d) {
            double expected = Math.cbrt(x);
            assertThat(e.setVariable("x", x).evaluate()).isEqualTo(expected);
        }
    }

    @Test
    public void testPostfixFunction11() {
        String expr = "cos(x) - (1/cbrt(x))";
        Expression e = new ExpressionBuilder(expr)
                .variables("x")
                .build();
        for (double x = -10; x < 10; x = x + 0.5d) {
            if (x == 0d) continue;
            double expected = Math.cos(x) - (1 / Math.cbrt(x));
            assertThat(e.setVariable("x", x).evaluate()).isEqualTo(expected);
        }
    }

    @Test
    public void testPostfixFunction12() {
        String expr = "acos(x) * expm1(asin(x)) - exp(atan(x)) + floor(x) + cosh(x) - sinh(cbrt(x))";
        Expression e = new ExpressionBuilder(expr)
                .variables("x")
                .build();
        for (double x = -10; x < 10; x = x + 0.5d) {
            double expected = Math.acos(x) * Math.expm1(Math.asin(x)) - Math.exp(Math.atan(x)) + Math.floor(x) + Math.cosh(x) - Math.sinh(Math.cbrt(x));
            if (Double.isNaN(expected)) {
                assertThat(e.setVariable("x", x).evaluate()).isNaN();
            } else {
                assertThat(e.setVariable("x", x).evaluate()).isEqualTo(expected);
            }
        }
    }

    @Test
    public void testPostfixFunction13() {
        String expr = "acos(x)";
        Expression e = new ExpressionBuilder(expr)
                .variables("x")
                .build();
        for (double x = -10; x < 10; x = x + 0.5d) {
            double expected = Math.acos(x);
            if (Double.isNaN(expected)) {
                assertThat(e.setVariable("x", x).evaluate()).isNaN();
            } else {
                assertThat(e.setVariable("x", x).evaluate()).isEqualTo(expected);
            }
        }
    }

    @Test
    public void testPostfixFunction14() {
        String expr = " expm1(x)";
        Expression e = new ExpressionBuilder(expr)
                .variables("x")
                .build();
        for (double x = -10; x < 10; x = x + 0.5d) {
            double expected = Math.expm1(x);
            if (Double.isNaN(expected)) {
                assertThat(e.setVariable("x", x).evaluate()).isNaN();
            } else {
                assertThat(e.setVariable("x", x).evaluate()).isEqualTo(expected);
            }
        }
    }

    @Test
    public void testPostfixFunction15() {
        String expr = "asin(x)";
        Expression e = new ExpressionBuilder(expr)
                .variables("x")
                .build();
        for (double x = -10; x < 10; x = x + 0.5d) {
            double expected = Math.asin(x);
            if (Double.isNaN(expected)) {
                assertThat(e.setVariable("x", x).evaluate()).isNaN();
            } else {
                assertThat(e.setVariable("x", x).evaluate()).isEqualTo(expected);
            }
        }
    }

    @Test
    public void testPostfixFunction16() {
        String expr = " exp(x)";
        Expression e = new ExpressionBuilder(expr)
                .variables("x")
                .build();
        for (double x = -10; x < 10; x = x + 0.5d) {
            double expected = Math.exp(x);
            assertThat(e.setVariable("x", x).evaluate()).isEqualTo(expected);
        }
    }

    @Test
    public void testPostfixFunction17() {
        String expr = "floor(x)";
        Expression e = new ExpressionBuilder(expr)
                .variables("x")
                .build();
        for (double x = -10; x < 10; x = x + 0.5d) {
            double expected = Math.floor(x);
            assertThat(e.setVariable("x", x).evaluate()).isEqualTo(expected);
        }
    }

    @Test
    public void testPostfixFunction18() {
        String expr = " cosh(x)";
        Expression e = new ExpressionBuilder(expr)
                .variables("x")
                .build();
        for (double x = -10; x < 10; x = x + 0.5d) {
            double expected = Math.cosh(x);
            assertThat(e.setVariable("x", x).evaluate()).isEqualTo(expected);
        }
    }

    @Test
    public void testPostfixFunction19() {
        String expr = "sinh(x)";
        Expression e = new ExpressionBuilder(expr)
                .variables("x")
                .build();
        for (double x = -10; x < 10; x = x + 0.5d) {
            double expected = Math.sinh(x);
            assertThat(e.setVariable("x", x).evaluate()).isEqualTo(expected);
        }
    }

    @Test
    public void testPostfixFunction20() {
        String expr = "cbrt(x)";
        Expression e = new ExpressionBuilder(expr)
                .variables("x")
                .build();
        for (double x = -10; x < 10; x = x + 0.5d) {
            double expected = Math.cbrt(x);
            assertThat(e.setVariable("x", x).evaluate()).isEqualTo(expected);
        }
    }

    @Test
    public void testPostfixFunction21() {
        String expr = "tanh(x)";
        Expression e = new ExpressionBuilder(expr)
                .variables("x")
                .build();
        for (double x = -10; x < 10; x = x + 0.5d) {
            double expected = Math.tanh(x);
            assertThat(e.setVariable("x", x).evaluate()).isEqualTo(expected);
        }
    }

    @Test
    public void testPostfixFunction2() {
        String expr = "log(1)";
        double expected = 0d;
        Expression e = new ExpressionBuilder(expr).build();
        assertThat(e.evaluate()).isEqualTo(expected);
    }

    @Test
    public void testPostfixFunction3() {
        String expr = "sin(0)";
        double expected = 0d;
        Expression e = new ExpressionBuilder(expr).build();
        assertThat(e.evaluate()).isEqualTo(expected);
    }

    @Test
    public void testPostfixFunction5() {
        String expr = "ceil(2.3) +1";
        double expected = Math.ceil(2.3) + 1;
        Expression e = new ExpressionBuilder(expr).build();
        assertThat(e.evaluate()).isEqualTo(expected);
    }

    @Test
    public void testPostfixFunction6() {
        double x = 1.565d;
        double y = 2.1323d;
        String expr = "ceil(x) + 1 / y * abs(1.4)";
        double expected = Math.ceil(x) + 1 / y * Math.abs(1.4);
        Expression e = new ExpressionBuilder(expr)
                .variables("x", "y")
                .build();
        assertThat(e.setVariable("x", x).setVariable("y", y).evaluate()).isEqualTo(expected);
    }

    @Test
    public void testPostfixFunction7() {
        double x = Math.E;
        String expr = "tan(x)";
        double expected = Math.tan(x);
        Expression e = new ExpressionBuilder(expr)
                .variables("x")
                .build();
        assertThat(e.setVariable("x", x).evaluate()).isEqualTo(expected);
    }

    @Test
    public void testPostfixFunction8() {
        String expr = "2^3.4223232 + tan(e)";
        double expected = Math.pow(2, 3.4223232d) + Math.tan(Math.E);
        Expression e = new ExpressionBuilder(expr)
                .variables("e")
                .build();
        assertThat(e.setVariable("e", E).evaluate()).isEqualTo(expected);
    }

    @Test
    public void testPostfixFunction9() {
        double x = Math.E;
        String expr = "cbrt(x)";
        double expected = Math.cbrt(x);
        Expression e = new ExpressionBuilder(expr)
                .variables("x")
                .build();
        assertThat(e.setVariable("x", x).evaluate()).isEqualTo(expected);
    }

    @Test
    public void testPostfixInvalidVariableName() {
        double x = 4.5334332d;
        double log = Math.PI;
        String expr = "x * pi";
        assertThatThrownBy(() -> new ExpressionBuilder(expr)
                .variables("x", "pi")
                .build()
                .setVariable("x", x)
                .setVariable("log", log).evaluate())
                .isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    public void testPostfixParenthesis() {
        String expr = "(3 + 3 * 14) * (2 * (24-17) - 14)/((34) -2)";
        double expected = 0; //(3 + 3 * 14) * (2 * (24-17) - 14)/((34) -2) = 0
        Expression e = new ExpressionBuilder(expr).build();
        assertThat(e.evaluate()).isEqualTo(expected);
    }

    @Test
    public void testPostfixVariables() {
        double x = 4.5334332d;
        double pi = Math.PI;
        String expr = "x * pi";
        double expected = x * pi;
        Expression e = new ExpressionBuilder(expr)
                .variables("x", "pi")
                .build();
        assertThat(e.setVariable("x", x).setVariable("pi", pi).evaluate()).isEqualTo(expected);
    }

    @Test
    public void testUnicodeVariable1() {
        Expression e = new ExpressionBuilder("λ")
                .variable("λ")
                .build()
                .setVariable("λ", E);
        assertThat(e.evaluate()).isEqualTo(E);
    }

    @Test
    public void testUnicodeVariable2() {
        Expression e = new ExpressionBuilder("log(3ε+1)")
                .variable("ε")
                .build()
                .setVariable("ε", E);
        assertThat(e.evaluate()).isEqualTo(log(3 * E + 1));
    }

    @Test
    public void testUnicodeVariable3() {
        Function log = new Function("λωγ", 1) {
            @Override
            public double apply(double... args) {
                return log(args[0]);
            }
        };

        Expression e = new ExpressionBuilder("λωγ(π)")
                .variable("π")
                .function(log)
                .build()
                .setVariable("π", PI);
        assertThat(e.evaluate()).isEqualTo(log(PI));
    }

    @Test
    public void testUnicodeVariable4() {
        Function log = new Function("λ_ωγ", 1) {
            @Override
            public double apply(double... args) {
                return log(args[0]);
            }
        };

        Expression e = new ExpressionBuilder("3λ_ωγ(πε6)")
                .variables("π", "ε")
                .function(log)
                .build()
                .setVariable("π", PI)
                .setVariable("ε", E);
        assertThat(e.evaluate()).isEqualTo(3 * log(PI * E * 6));
    }

    @Test
    public void testImplicitMultiplicationOffNumber() {
        assertThatThrownBy(() -> new ExpressionBuilder("var_12")
                .variable("var_1")
                .implicitMultiplication(false)
                .build()
                .evaluate()).isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    public void testImplicitMultiplicationOffVariable() {
        assertThatThrownBy(() -> new ExpressionBuilder("var_1var_1")
                .variable("var_1")
                .implicitMultiplication(false)
                .build()
                .evaluate()).isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    public void testImplicitMultiplicationOffParentheses() {
        assertThatThrownBy(() -> new ExpressionBuilder("var_1(2)")
                .variable("var_1")
                .implicitMultiplication(false)
                .build()
                .evaluate()).isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    public void testImplicitMultiplicationOffFunction() {
        assertThatThrownBy(() -> new ExpressionBuilder("var_1log(2)")
                .variable("var_1")
                .implicitMultiplication(false)
                .build()
                .setVariable("var_1", 2)
                .evaluate()).isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    public void testImplicitMultiplicationOnNumber() {
        Expression e = new ExpressionBuilder("var_12")
                .variable("var_1")
                .build()
                .setVariable("var_1", 2);
        assertThat(e.evaluate()).isEqualTo(4d);
    }

    @Test
    public void testImplicitMultiplicationOnVariable() {
        Expression e = new ExpressionBuilder("var_1var_1")
                .variable("var_1")
                .build()
                .setVariable("var_1", 2);
        assertThat(e.evaluate()).isEqualTo(4d);
    }

    @Test
    public void testImplicitMultiplicationOnParentheses() {
        Expression e = new ExpressionBuilder("var_1(2)")
                .variable("var_1")
                .build()
                .setVariable("var_1", 2);
        assertThat(e.evaluate()).isEqualTo(4d);
    }

    @Test
    public void testImplicitMultiplicationOnFunction() {
        Expression e = new ExpressionBuilder("var_1log(2)")
                .variable("var_1")
                .build()
                .setVariable("var_1", 2);
        assertThat(e.evaluate()).isEqualTo(2 * log(2));
    }

    @Test
    public void testSecondArgumentNegative() {
        Function round = new Function("MULTIPLY", 2) {
            @Override
            public double apply(double... args) {
                return Math.round(args[0] * args[1]);
            }
        };
        double result = new ExpressionBuilder("MULTIPLY(2,-1)")
                .function(round)
                .build()
                .evaluate();
        assertThat(result).isEqualTo(-2d);
    }

    @Test
    public void testVariableWithDot() {
        double result = new ExpressionBuilder("2*SALARY.Basic")
                .variable("SALARY.Basic")
                .build()
                .setVariable("SALARY.Basic", 1.5d)
                .evaluate();
        assertThat(result).isEqualTo(3d);
    }

    @Test
    public void testTwoAdjacentOperators() {
        final Operator factorial = new Operator("!", 1, true, Operator.PRECEDENCE_POWER + 1) {
            @Override
            public double apply(double... args) {
                final int arg = (int) args[0];
                if ((double) arg != args[0]) {
                    throw new IllegalArgumentException("Operand for factorial has to be an integer");
                }
                if (arg < 0) {
                    throw new IllegalArgumentException("The operand of the factorial can not be less than zero");
                }
                double result = 1;
                for (int i = 1; i <= arg; i++) {
                    result *= i;
                }
                return result;
            }
        };

        double result = new ExpressionBuilder("3!+2")
                .operator(factorial)
                .build()
                .evaluate();

        assertThat(result).isEqualTo(8d);
    }

    @Test
    public void testGetVariableNames1() {
        Expression e = new ExpressionBuilder("b*a-9.24c")
                .variables("b", "a", "c")
                .build();
        Set<String> variableNames = e.getVariableNames();
        assertThat(variableNames).contains("a", "b", "c");
    }

    @Test
    public void testGetVariableNames2() {
        Expression e = new ExpressionBuilder("log(bar)-FOO.s/9.24c")
                .variables("bar", "FOO.s", "c")
                .build();
        Set<String> variableNames = e.getVariableNames();
        assertThat(variableNames).contains("bar", "FOO.s", "c");
    }

    @Test
    public void testSameVariableAndBuiltinFunctionName() {
        assertThatThrownBy(() -> new ExpressionBuilder("log10(log10)")
                .variables("log10")
                .build())
                .isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    public void testSameVariableAndUserFunctionName() {
        assertThatThrownBy(() -> new ExpressionBuilder("2*tr+tr(2)")
                .variables("tr")
                .function(new Function("tr") {
                    @Override
                    public double apply(double... args) {
                        return 0;
                    }
                })
                .build())
                .isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    public void testSignum() {
        Expression e = new ExpressionBuilder("signum(1)").build();
        assertThat(e.evaluate()).isEqualTo(1d);

        e = new ExpressionBuilder("signum(-1)").build();
        assertThat(e.evaluate()).isEqualTo(-1d);

        e = new ExpressionBuilder("signum(--1)").build();
        assertThat(e.evaluate()).isEqualTo(1d);

        e = new ExpressionBuilder("signum(+-1)").build();
        assertThat(e.evaluate()).isEqualTo(-1d);

        e = new ExpressionBuilder("-+1").build();
        assertThat(e.evaluate()).isEqualTo(-1d);

        e = new ExpressionBuilder("signum(-+1)").build();
        assertThat(e.evaluate()).isEqualTo(-1d);
    }

    @Test
    public void testCustomPercent() {
        Function percentage = new Function("percentage", 2) {
            @Override
            public double apply(double... args) {
                double val = args[0];
                double percent = args[1];
                if (percent < 0) {
                    return val - val * Math.abs(percent) / 100d;
                } else {
                    return val - val * percent / 100d;
                }
            }
        };

        Expression e = new ExpressionBuilder("percentage(1000,-10)")
                .function(percentage)
                .build();
        assertThat(e.evaluate()).isEqualTo(900d);

        e = new ExpressionBuilder("percentage(1000,12)")
                .function(percentage)
                .build();
        assertThat(e.evaluate()).isEqualTo(1000d * 0.12d);
    }
}
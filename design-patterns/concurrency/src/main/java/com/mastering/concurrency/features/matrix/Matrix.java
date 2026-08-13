package com.mastering.concurrency.features.matrix;

import java.util.ArrayList;
import java.util.function.Supplier;

public class Matrix {
    private final double[][] values;

    public Matrix(double[][] values) {
        this.values = values;
    }

    public double sum() {
        var result = 0.0;
        for (double[] value : values) {
            result += addRow(value);
        }
        return result;
    }

    public double sumParallel() {
        var threads = new ArrayList<ThreadValue<Double>>();
        for (double[] value : values) {
            ThreadValue<Double> t = new ThreadValue<>(() -> addRow(value));
            t.start();
            threads.add(t);
        }
        // ---- Aca calcula
        for (var thread : threads) {
            try {
                thread.join();
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
        double result = 0.0;
        for (var t : threads)
            result += t.getValue();

        return result;

    }

    public Matrix addSerial(Matrix other) {
        double[][] result = new double[values.length][];
        for (int i = 0; i < values.length; i++) {
            int cols = values[i].length;
            var row = new double[cols];
            for (int j = 0; j < cols; j++) {
                row[j] = values[i][j] + other.values[i][j];
            }
            result[i] = row;
        }
        return new Matrix(result);
    }

    public Matrix addParallel(Matrix other) {
        throw new RuntimeException("Implement me!");
    }

    private double addRow(double[] value) {
        var result = 0.0;
        for (double v : value) {
            result += v;
        }
        return result;
    }

    static class ThreadValue<T> extends Thread {
        private final Supplier<T> expression;
        private T result;

        public ThreadValue(Supplier<T> expression) {
            this.expression = expression;
        }

        public void run() {
            result = expression.get();
        }

        public T getValue() {
            return result;
        }
    }


}
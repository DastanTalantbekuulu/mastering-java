package org.jqurantree.analysis;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

import org.jqurantree.core.error.Errors;
import org.jqurantree.core.error.QuranException;

public class AnalysisTable implements Iterable<Object[]> {

    private final String[] columnNames;
    private final List<Object[]> rows = new ArrayList<Object[]>();

    public AnalysisTable(String... columnNames) {
//        String[] col = new String[columnNames.length + 1];
//        col[0] = "Index";
//        for (int i = 1; i < columnNames.length; i++) {
//            col[i] = columnNames[i];
//        }
//        this.columnNames = col;
        this.columnNames = columnNames;
    }

    public void add(Object... values) {
        rows.add(values);
    }

    public int getRowCount() {
        return rows.size();
    }

    public int getColumnCount() {
        return columnNames.length;
    }

    public String getColumnName(int columnIndex) {
        return columnNames[columnIndex];
    }

    public int getColumnIndex(String columnName) {
        int columnIndex = -1;
        int size = columnNames.length;
        for (int i = 0; i < size; i++) {
            if (columnNames[i] == columnName) {
                columnIndex = i;
                break;
            }
        }
        return columnIndex;
    }

    public Object getValue(int rowIndex, int columnIndex) {
        return rows.get(rowIndex)[columnIndex];
    }

    public Object getValue(int rowIndex, String columnName) {
        return getValue(rowIndex, getValidColumnIndex(columnName));
    }

    public int getInteger(int rowIndex, int columnIndex) {
        return Integer.parseInt(getString(rowIndex, columnIndex));
    }

    public int getInteger(int rowIndex, String columnName) {
        return getInteger(rowIndex, getValidColumnIndex(columnName));
    }

    public String getString(int rowIndex, int columnIndex) {
        return getValue(rowIndex, columnIndex).toString();
    }

    public String getString(int rowIndex, String columnName) {
        return getString(rowIndex, getValidColumnIndex(columnName));
    }

    @Override
    public String toString() {
        return toString(rows.size());
    }

    public String toString(int rowCount) {
        return new TableWriter().write(this, 0, rowCount);
    }

    public void writeFile(String filename) {
        new TableExport().write(this, 0, rows.size(), filename);
    }

    public void writeFile(String filename, char delimiter) {
        writeFile(filename, delimiter, rows.size());
    }

    public void writeFile(String filename, char delimiter, int rowCount) {
        new TableExport().write(this, 0, rowCount, filename, delimiter);
    }

    public Iterator<Object[]> iterator() {
        return rows.iterator();
    }

    public void sort(String columnName) {
        sort(columnName, SortOrder.Ascending);
    }

    public void sort(String columnName, SortOrder direction) {
        Collections.sort(rows, new TableSort(getValidColumnIndex(columnName), direction));
    }

    public AnalysisTable group(String... columnNames) {

        int size = columnNames.length;
        int[] columnIndexes = new int[size];
        for (int i = 0; i < size; i++) {
            columnIndexes[i] = getValidColumnIndex(columnNames[i]);
        }
        return new TableGroup().group(this, columnIndexes);
    }

    private int getValidColumnIndex(String columnName) {
        int columnIndex = getColumnIndex(columnName);
        if (columnIndex < 0) {
            throw new QuranException(Errors.INVALID_COLUMN_NAME);
        }
        return columnIndex;
    }
}

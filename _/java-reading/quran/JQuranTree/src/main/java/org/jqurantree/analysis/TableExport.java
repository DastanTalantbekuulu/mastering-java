package org.jqurantree.analysis;

import org.jqurantree.core.io.FileWriter;

class TableExport {

    private FileWriter writer;
    private AnalysisTable table;
    private static final char DEFAULT_DELIMETER = '\t';
    private char delimiter;

    public void write(AnalysisTable table, int startRowIndex, int rowCount, String filename) {
        write(table, startRowIndex, rowCount, filename, DEFAULT_DELIMETER);
    }

    public void write(AnalysisTable table, int startRowIndex, int rowCount, String filename, char delimiter) {
        this.table = table;
        this.delimiter = delimiter;
        writer = new FileWriter(filename);

        writeColumnNames();
        for (int i = 0; i < rowCount; i++) {
            writeRow(startRowIndex + i);
        }
        writer.close();
    }

    private void writeColumnNames() {
        int columnCount = table.getColumnCount();
        for (int i = 0; i < columnCount; i++) {
            if (i > 0) {
                writer.write(delimiter);
            }
            writer.write(table.getColumnName(i));
        }
        writer.writeLine();
    }

    private void writeRow(int rowIndex) {
        int columnCount = table.getColumnCount();
        for (int i = 0; i < columnCount; i++) {
            if (i > 0) {
                writer.write(delimiter);
            }
            writer.write(table.getString(rowIndex, i));
        }
        writer.writeLine();
    }
}

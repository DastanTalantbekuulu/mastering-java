package org.jqurantree.analysis;

class TableWriter {

    private AnalysisTable table;
    private int startRowIndex;
    private int rowCount;
    private int[] columnPositions;
    private StringBuilder text;
    private static final int COLUMN_PADDING = 1;
    private static final char HEADER_SEPERATOR = '-';
    private int textPosition;

    public String write(AnalysisTable table, int startRowIndex, int rowCount) {

        this.table = table;
        this.startRowIndex = startRowIndex;
        this.rowCount = rowCount;

        columnPositions = getColumnPositions();

        return writeTable();
    }

    private int[] getColumnPositions() {
        int size = table.getColumnCount();
        int[] columnPositions = new int[size];
        int columnPosition = 0;
        for (int i = 0; i < size - 1; i++) {
            columnPosition += getColumnWidth(i) + COLUMN_PADDING;
            columnPositions[i + 1] = columnPosition;
        }
        return columnPositions;
    }

    private int getColumnWidth(int columnIndex) {
        int columnWidth = table.getColumnName(columnIndex).length();
        for (int i = 0; i < rowCount; i++) {
            int valueWidth = table.getString(startRowIndex + i, columnIndex)
                    .length();
            if (valueWidth > columnWidth) {
                columnWidth = valueWidth;
            }
        }
        return columnWidth;
    }

    private String writeTable() {
        text = new StringBuilder();
        writeColumnNames();
        writeHeaderSeperator();
        for (int i = 0; i < rowCount; i++) {
            writeRow(startRowIndex + i);
        }
        return text.toString();
    }

    private void writeColumnNames() {
        int size = table.getColumnCount();
        for (int i = 0; i < size; i++) {
            writeValue(table.getColumnName(i), i);
        }
        writeLine();
    }

    private void writeHeaderSeperator() {
        int size = table.getColumnCount();
        for (int i = 0; i < size; i++) {
            writeValue(getHeaderSeperator(table.getColumnName(i).length()), i);
        }
        writeLine();
    }

    private String getHeaderSeperator(int size) {
        char[] ch = new char[size];
        for (int i = 0; i < size; i++) {
            ch[i] = HEADER_SEPERATOR;
        }
        return new String(ch);
    }

    private void writeRow(int rowIndex) {
        int size = table.getColumnCount();
        for (int i = 0; i < size; i++) {
            writeValue(table.getString(rowIndex, i), i);
        }
        writeLine();
    }

    private void writeValue(String value, int columnIndex) {

        int columnPosition = columnPositions[columnIndex];
        int padCount = columnPosition - textPosition;
        for (int i = 0; i < padCount; i++) {
            text.append(' ');
        }

        text.append(value);
        textPosition = columnPosition + value.length();
    }

    private void writeLine() {
        text.append("\r\n");
        textPosition = 0;
    }
}

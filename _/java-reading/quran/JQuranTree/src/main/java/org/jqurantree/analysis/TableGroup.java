package org.jqurantree.analysis;

import java.util.HashMap;
import java.util.Map;

class TableGroup {

    private AnalysisTable table;
    private int[] columnIndexes;
    private final Map<String, Group> groupMap = new HashMap<String, Group>();
    private static final char GROUP_KEY_SEPERATOR = '|';
    private static final String COUNT_COLUMN_NAME = "Count";

    public AnalysisTable group(AnalysisTable table, int[] columnIndexes) {

        this.table = table;
        this.columnIndexes = columnIndexes;

        int size = table.getRowCount();
        for (int i = 0; i < size; i++) {
            groupRow(i);
        }
        return createGroupTable();
    }

    private void groupRow(int rowIndex) {

        String key = getGroupKey(rowIndex);

        Group group = groupMap.get(key);
        if (group == null) {
            group = new Group(rowIndex);
            groupMap.put(key, group);
        }
        group.setRowCount(group.getRowCount() + 1);
    }

    private AnalysisTable createGroupTable() {
        int size = columnIndexes.length + 1;
        String[] columnNames = new String[size];
        for (int i = 0; i < size - 1; i++) {
            columnNames[i] = table.getColumnName(columnIndexes[i]);
        }
        columnNames[size - 1] = COUNT_COLUMN_NAME;
        AnalysisTable groupTable = new AnalysisTable(columnNames);

        for (Group group : groupMap.values()) {
            int rowIndex = group.getRowIndex();
            Object[] values = new Object[size];
            for (int i = 0; i < size - 1; i++) {
                values[i] = table.getValue(rowIndex, columnIndexes[i]);
            }
            values[size - 1] = group.getRowCount();
            groupTable.add(values);
        }
        return groupTable;
    }

    private String getGroupKey(int rowIndex) {
        StringBuilder text = new StringBuilder();
        int size = columnIndexes.length;
        for (int i = 0; i < size; i++) {
            if (i > 0) {
                text.append(GROUP_KEY_SEPERATOR);
            }
            text.append(table.getValue(rowIndex, columnIndexes[i]));
        }
        return text.toString();
    }
}

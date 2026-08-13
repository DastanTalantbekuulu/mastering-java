package org.jqurantree.analysis;

import java.util.Comparator;

class TableSort implements Comparator<Object[]> {

	private final int columnIndex;
	private final SortOrder direction;

	public TableSort(int columnIndex, SortOrder direction) {
		this.columnIndex = columnIndex;
		this.direction = direction;
	}

	public int compare(Object[] row1, Object[] row2) {
		Comparable value1 = (Comparable) row1[columnIndex];
		Comparable value2 = (Comparable) row2[columnIndex];
		return direction == SortOrder.Ascending ? value1.compareTo(value2)
				: value2.compareTo(value1);
	}
}

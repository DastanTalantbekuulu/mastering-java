package org.jqurantree.analysis;

class Group {

	private final int rowIndex;
	private int rowCount;

	public Group(int rowIndex) {
		this.rowIndex = rowIndex;
	}

	public int getRowIndex() {
		return rowIndex;
	}

	public void setRowCount(int rowCount) {
		this.rowCount = rowCount;
	}

	public int getRowCount() {
		return rowCount;
	}
}

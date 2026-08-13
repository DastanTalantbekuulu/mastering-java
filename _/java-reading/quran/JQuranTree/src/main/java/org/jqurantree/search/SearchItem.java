package org.jqurantree.search;

class SearchItem {

	private SearchType type;
	private String text;
	private SearchOptions options;

	public SearchItem(SearchType type, String text, SearchOptions options) {
		this.type = type;
		this.text = text;
		this.options = options;
	}

	public SearchType getType() {
		return type;
	}

	public String getText() {
		return text;
	}

	public SearchOptions getOptions() {
		return options;
	}
}

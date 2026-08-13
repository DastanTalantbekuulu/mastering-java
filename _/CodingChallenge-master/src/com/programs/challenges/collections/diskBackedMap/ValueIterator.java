
package com.programs.challenges.collections.diskBackedMap;

import java.io.Serializable;
import java.util.Iterator;

class ValueIterator<K extends Serializable, V extends Serializable> implements Iterator<V> {
	private DiskBackedMap<K, V> map;
	private Iterator<K> iterator;
	private K current;

	public ValueIterator(DiskBackedMap<K, V> map) {
		this.map = map;

		iterator = new KeyIterator<K, V>(map);
		current = null;
	}

	public boolean hasNext() {
		return iterator.hasNext();
	}

	public V next() {
		current = iterator.next();

		return map.get(current);
	}

	public void remove() {
		if (current != null)
			map.remove(current);
	}
}

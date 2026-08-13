
package com.programs.challenges.collections.diskBackedMap;

import java.io.Serializable;
import java.util.AbstractSet;
import java.util.Collection;
import java.util.Iterator;

class ValueSet<K extends Serializable, V extends Serializable> extends AbstractSet<V> {

	private DiskBackedMap<K, V> map;

	public ValueSet(DiskBackedMap<K, V> map) {
		this.map = map;
	}

	@Override
	public boolean contains(Object o) {
		return map.containsValue(o);
	}

	@Override
	@SuppressWarnings("unchecked")
	public boolean containsAll(Collection<?> c) {
		Collection<V> ec = (Collection<V>) c;
		for (V value : ec)
			if (!contains(value))
				return false;

		return true;
	}

	@Override
	public boolean isEmpty() {
		return map.isEmpty();
	}

	@Override
	public Iterator<V> iterator() {
		return new ValueIterator<K, V>(map);
	}

	@Override
	public int size() {
		return map.size();
	}
}

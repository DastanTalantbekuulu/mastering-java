package com.programs.challenges.collections.diskBackedMap;

import java.io.Serializable;
import java.util.AbstractSet;
import java.util.Collection;
import java.util.Iterator;

class KeySet<K extends Serializable, V extends Serializable> extends AbstractSet<K> {

	private DiskBackedMap<K, V> map;

	public KeySet(DiskBackedMap<K, V> map) {
		this.map = map;
	}

	@Override
	public boolean contains(Object o) {
		return map.containsKey(o);
	}

	@Override
	@SuppressWarnings("unchecked")
	public boolean containsAll(Collection<?> c) {
		Collection<K> ec = (Collection<K>) c;
		for (K key : ec)
			if (!contains(key))
				return false;

		return true;
	}

	@Override
	public boolean isEmpty() {
		return map.isEmpty();
	}

	@Override
	public Iterator<K> iterator() {
		return new KeyIterator<K, V>(map);
	}

	@Override
	public int size() {
		return map.size();
	}
}

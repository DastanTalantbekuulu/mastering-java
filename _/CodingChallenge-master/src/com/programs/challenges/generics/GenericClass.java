package com.programs.challenges.generics;

import java.util.HashMap;
import java.util.Map;

public class GenericClass<T> {

	private T t;
	private Map<?, ?> map = new HashMap<>();
	
	public GenericClass(T t1,Map<?, ?> map) {
		this.t = t1;
		this.map = map;
	}

	public T getT() {
		return t;
	}

	public void setT(T t) {
		this.t = t;
	}
	
	public Map<?, ?> getMap(){
		return map;
	}
	
}

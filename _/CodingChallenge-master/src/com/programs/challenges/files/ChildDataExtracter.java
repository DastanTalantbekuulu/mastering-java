package com.programs.challenges.files;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public interface ChildDataExtracter {

	/**
	 * Generates list of child ordered by height
	 * @param childDataList
	 * @param order
	 * @return {@link List}
	 */
	public List<ChildData> getHeightList(List<ChildData> childDataList, String order);
	
	public Map<Integer, String> getTallestByAge(List<ChildData> childlist);
	
	public Map<Integer, HashMap<String, Float>> getChildrenGroup();
	
	public default void getMethodDetails(){
		System.out.println("getHeightList(List<ChildData> childDataList, String order)");
		System.out.println("getTallestByAge(List<ChildData> childlist)");
		System.out.println("getChildrenGroup()");
	}
}

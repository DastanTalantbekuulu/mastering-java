package com.programs.challenges.files;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

public class ChildDataExtractorImpl implements ChildDataExtracter {
	
	private Comparator<Float> heightComparator = Collections.reverseOrder();
		
	@Override
	public List<ChildData> getHeightList(List<ChildData> childDataList,String order) {
		try{
			Collections.sort(childDataList, new Comparator<ChildData>() {

				@Override
				public int compare(ChildData o1, ChildData o2) {
					if(o1.getHeight() == o2.getHeight()){
						return 0;
					}
					if(o1.getHeight() > o2.getHeight()){
						return 1;
					}else{
						return -1;
					}
					
				}
				
			});
		}catch(Exception e){
			System.err.println("Error Occured : "+e);
		}
		if("DESC".equalsIgnoreCase(order)){
			Collections.reverse(childDataList);
		}
		return childDataList;
	}

	@Override
	public Map<Integer, HashMap<String, Float>> getChildrenGroup() {
		
		return null;
	}

	@Override
	public Map<Integer, String> getTallestByAge(List<ChildData> childlist) {
		Map<Integer, String> ageGroup = new HashMap<>();
		Map<Integer, HashMap<String, Float>> groupMap = getAgeGroupMap(childlist);
		for(Entry<Integer, HashMap<String, Float>> entry : groupMap.entrySet()){
			HashMap<String, Float> heightMap = entry.getValue();
			Collections.sort(new ArrayList<>(heightMap.values()), heightComparator);
			
		}
		
		
		return null;
	}
	
	public Map<Integer, HashMap<String, Float>> getAgeGroupMap(List<ChildData> childlist){
		Map<Integer, HashMap<String, Float>> grpMap = new HashMap<>();
		for(ChildData child : childlist){
			if(grpMap.containsKey(child.getAge())){
				if(!grpMap.get(child.getAge()).containsKey(child.getName())){
					grpMap.get(child.getAge()).put(child.getName(), child.getHeight());
				}
			}else{
				HashMap<String, Float> heightMap = new HashMap<>();
				heightMap.put(child.getName(), child.getHeight());
				grpMap.put(child.getAge(), heightMap);
			}
		}
		
		return grpMap;
	}

}

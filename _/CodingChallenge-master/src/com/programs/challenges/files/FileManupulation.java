package com.programs.challenges.files;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class FileManupulation {

	private File file;

	private ChildDataExtracter extracter = new ChildDataExtractorImpl();
	
	public FileManupulation(String filePath) {
		this.file = new File(filePath);
	}

	public static void main(String[] args) {
		new FileManupulation("/home/pranshu/Documents/sample.txt").processFile();

	}

	public void processFile() {
		List<ChildData> childList = new ArrayList<>();
		try (BufferedReader br = new BufferedReader(new FileReader(file))) {
			String line;
			
			while ((line = br.readLine()) != null) {
				String[] childDataStr = line.split("\\s");
				childList.add(new ChildData(childDataStr[0], Integer.parseInt(childDataStr[1]),
						Float.parseFloat(childDataStr[2])));
			}
		} catch (FileNotFoundException e) {
			System.err.println("File not fount at the specified path : " + e);
		} catch (IOException e) {
			System.err.println("Error loading file : " + e);
		}
		
		showChildList(childList,"ASC");
		showChildList(childList,"DESC");
		showTallestByAgeGroup(childList);
		
		
	}

	private void showTallestByAgeGroup(List<ChildData> childList) {
		if(childList.isEmpty()) return;
		Map<Integer, String> ageGroupMap = extracter.getTallestByAge(childList);
		extracter.getMethodDetails();
		
	}

	private void showChildList(List<ChildData> childList, String order) {
		List<ChildData> sortedList = extracter.getHeightList(childList, order);
		System.out.println("LIST OF CHILDREN ACCORDING TO HEIGHT - "+order);
		for(ChildData child : sortedList){
			System.out.println(child.getName()+" - "+child.getHeight());
		}
	}
	
	

}

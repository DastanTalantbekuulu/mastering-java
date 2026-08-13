
package com.programs.challenges.collections.diskBackedMap.serializers;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;

public class DataSerializer implements ObjectSerializer {

	public Object readObject(File f) throws IOException {
		try(ObjectInputStream ois = new ObjectInputStream(new FileInputStream(f));) {
			
			Object o = ois.readObject();
	
			return o;
		}
		catch (ClassNotFoundException e) {
			return null;
		}
	}

	public void writeObject(File f, Serializable o, boolean force) throws IOException {
		//System.out.println(f.getAbsolutePath()+" "+f.getName());
		
		try(FileOutputStream fos = new FileOutputStream(f);){
			fos.getChannel().force(force);
			
			try(ObjectOutputStream oos = new ObjectOutputStream(fos);){
				oos.writeObject(o);
				//System.out.println("Writing to file");
			}	
		}catch(FileNotFoundException e){
			return;
		}
	}
}


package com.programs.challenges.collections.diskBackedMap.serializers;

import java.io.File;
import java.io.IOException;
import java.io.Serializable;

public interface ObjectSerializer {
	public void writeObject(File f, Serializable o, boolean force) throws IOException;
	public Object readObject(File f) throws IOException;
}

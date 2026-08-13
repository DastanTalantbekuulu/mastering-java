package com.mastering.reflections;

import org.junit.jupiter.api.Test;
import com.mastering.reflections.scanners.Scanners;
import com.mastering.reflections.serializers.JsonSerializer;
import com.mastering.reflections.util.ConfigurationBuilder;
import com.mastering.reflections.util.FilterBuilder;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static com.mastering.reflections.ReflectionsTest.getUserDir;

public class ReflectionsCollectTest {

	@Test
	public void testCollect() {
		Reflections reflections = new Reflections(
			new ConfigurationBuilder()
				.forPackage("com.mastering.reflections")
				.filterInputsBy(new FilterBuilder()
					.includePattern("com\\.mastering\\.reflections\\.TestModel\\$.*")
					.includePattern(".*\\.xml"))
				.addScanners(Scanners.values()));

		String targetDir = getUserDir() + "/target/test-classes";

		// xml
		reflections.save(targetDir + "/META-INF/reflections/saved-testModel-reflections.xml");
		assertEquals(
			Reflections.collect("/META-INF/reflections/testModel-reflections.xml", a -> true).getStore(),
			Reflections.collect("/META-INF/reflections/saved-testModel-reflections.xml", a -> true).getStore());

		// json
		reflections.save(targetDir + "/META-INF/reflections/saved-testModel-reflections.json", new JsonSerializer());
		assertEquals(
			Reflections.collect("/META-INF/reflections/testModel-reflections.json", a -> true).getStore(),
			Reflections.collect("/META-INF/reflections/saved-testModel-reflections.json", a -> true).getStore());
	}
}

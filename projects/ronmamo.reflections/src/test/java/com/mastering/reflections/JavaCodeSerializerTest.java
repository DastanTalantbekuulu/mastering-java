package com.mastering.reflections;

import org.junit.jupiter.api.Test;
import com.mastering.reflections.scanners.TypeElementsScanner;
import com.mastering.reflections.serializers.JavaCodeSerializer;
import com.mastering.reflections.util.FilterBuilder;
import com.mastering.reflections.util.NameHelper;

public class JavaCodeSerializerTest implements NameHelper {

	public JavaCodeSerializerTest() {
		FilterBuilder filterBuilder = new FilterBuilder().includePattern("com\\.mastering\\.reflections\\.TestModel\\$.*");
		Reflections reflections = new Reflections(
			TestModel.class,
			new TypeElementsScanner().filterResultsBy(filterBuilder),
			filterBuilder);

		String filename = ReflectionsTest.getUserDir() + "/src/test/java/com.mastering.reflections.MyTestModelStore";
		reflections.save(filename, new JavaCodeSerializer());
	}

	@Test
	public void check() {
		// MyTestModelStore contains TestModel type elements
		Class<?> c1 = MyTestModelStore.com.mastering.reflections.TestModel$C1.class;
		Class<?> ac1 = MyTestModelStore.com.mastering.reflections.TestModel$C1.annotations.com_mastering_reflections_TestModel$AC1.class;
		Class<?> f1 = MyTestModelStore.com.mastering.reflections.TestModel$C4.fields.f1.class;
		Class<?> m1 = MyTestModelStore.com.mastering.reflections.TestModel$C4.methods.m1.class;
	}
}

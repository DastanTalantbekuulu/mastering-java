package com.mastering.reflections;

import org.junit.jupiter.api.Test;
import com.mastering.reflections.scanners.Scanners;
import com.mastering.reflections.util.ClasspathHelper;
import com.mastering.reflections.util.ConfigurationBuilder;
import com.mastering.reflections.util.FilterBuilder;

import java.net.URL;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashSet;
import java.util.function.Predicate;

import static org.junit.jupiter.api.Assertions.*;

public class ConfigurationBuilderTest {

	@Test
	public void buildForConfig() {
		assertConfig(ConfigurationBuilder.build("com.mastering.reflections"),
			ClasspathHelper.forPackage("com.mastering.reflections"),
			new FilterBuilder().includePackage("com.mastering.reflections"));

		assertConfig(ConfigurationBuilder.build("com"),
			ClasspathHelper.forPackage("com"),
			new FilterBuilder().includePackage("com"));
	}

	@Test
	public void buildFor() {
		assertThrows(ReflectionsException.class, () -> ConfigurationBuilder.build(""));

		assertConfig(ConfigurationBuilder.build(),
			ClasspathHelper.forClassLoader(),
			new FilterBuilder());

		assertConfig(ConfigurationBuilder.build("not.exist"),
			ClasspathHelper.forClassLoader(),
			new FilterBuilder().includePackage("not.exist"));
	}

	private void assertConfig(ConfigurationBuilder config, Collection<URL> urls, Predicate<String> inputsFilter) {
		assertEquals(config.getUrls(), new HashSet<>(urls));
		assertEquals(config.getInputsFilter(), inputsFilter);
		assertEquals(config.getScanners(), new HashSet<>(Arrays.asList(Scanners.SubTypes, Scanners.TypesAnnotated)));
	}
}
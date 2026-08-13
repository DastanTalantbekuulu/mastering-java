package com.mastering.reflections;

import org.junit.jupiter.api.Test;
import com.mastering.reflections.util.ConfigurationBuilder;
import com.mastering.reflections.util.FilterBuilder;

import java.lang.annotation.Inherited;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

import static org.hamcrest.MatcherAssert.assertThat;
import static com.mastering.reflections.ReflectionsExpandSupertypesTest.ExpandTestModel.NotScanned;
import static com.mastering.reflections.ReflectionsExpandSupertypesTest.ExpandTestModel.Scanned;
import static com.mastering.reflections.ReflectionsQueryTest.equalTo;
import static com.mastering.reflections.scanners.Scanners.SubTypes;

public class ReflectionsExpandSupertypesTest {

    private final FilterBuilder inputsFilter = new FilterBuilder()
        .includePattern("com\\.mastering\\.reflections\\.ReflectionsExpandSupertypesTest\\$ExpandTestModel\\$Scanned\\$.*");

    @SuppressWarnings("unused")
    public interface ExpandTestModel {
        interface NotScanned {
            @Retention(RetentionPolicy.RUNTIME)
            @interface MetaAnnotation { } // outside of scanned scope

            @Retention(RetentionPolicy.RUNTIME)
            @Inherited
            @MetaAnnotation
            @interface TestAnnotation { } // outside of scanned scope, but immediate annotation

            interface BaseInterface { } // outside of scanned scope

            @TestAnnotation
            class BaseClass implements BaseInterface { } // outside of scanned scope, but immediate supertype
        }

        interface Scanned {
            class ChildrenClass extends NotScanned.BaseClass { }
        }
    }

    @Test
    public void testExpandSupertypes() {
        ConfigurationBuilder configuration = new ConfigurationBuilder()
            .forPackage("com.mastering.reflections")
            .filterInputsBy(inputsFilter);

        Reflections reflections = new Reflections(configuration);
        assertThat(reflections.get(SubTypes.of(NotScanned.BaseInterface.class).asClass()),
            equalTo(
                NotScanned.BaseClass.class,
                Scanned.ChildrenClass.class));

        Reflections refNoExpand = new Reflections(configuration.setExpandSuperTypes(false));
        assertThat(refNoExpand.get(SubTypes.of(NotScanned.BaseInterface.class).asClass()),
            equalTo());
    }

    @Test
    void testDetectInheritedAnnotations() {
        ConfigurationBuilder configuration = new ConfigurationBuilder()
            .forPackage("com.mastering.reflections")
            .filterInputsBy(inputsFilter);

        Reflections reflections = new Reflections(configuration);
        assertThat(reflections.getTypesAnnotatedWith(NotScanned.TestAnnotation.class),
            equalTo(
                NotScanned.BaseClass.class,
                Scanned.ChildrenClass.class));

        Reflections refNoExpand = new Reflections(configuration.setExpandSuperTypes(false));
        assertThat(refNoExpand.getTypesAnnotatedWith(NotScanned.TestAnnotation.class),
            equalTo());
    }

    @Test
    void testExpandMetaAnnotations() {
        ConfigurationBuilder configuration = new ConfigurationBuilder()
            .forPackage("com.mastering.reflections")
            .filterInputsBy(inputsFilter);

        Reflections reflections = new Reflections(configuration);
        assertThat(reflections.getTypesAnnotatedWith(NotScanned.MetaAnnotation.class),
            equalTo());
//         todo fix, support expansion of meta annotations outside of scanned scope
//            equalTo(
//                NotScanned.TestAnnotation.class,
//                NotScanned.BaseClass.class,
//                Scanned.ChildrenClass.class));

        Reflections refNoExpand = new Reflections(configuration.setExpandSuperTypes(false));
        assertThat(refNoExpand.getTypesAnnotatedWith(NotScanned.MetaAnnotation.class),
            equalTo());
    }
}

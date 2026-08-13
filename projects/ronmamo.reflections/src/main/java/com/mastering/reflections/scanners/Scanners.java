package com.mastering.reflections.scanners;

import javassist.bytecode.ClassFile;
import com.mastering.reflections.Store;
import com.mastering.reflections.util.FilterBuilder;
import com.mastering.reflections.util.NameHelper;
import com.mastering.reflections.util.QueryBuilder;
import com.mastering.reflections.util.QueryFunction;
import com.mastering.reflections.vfs.Vfs;

import java.lang.annotation.Inherited;
import java.lang.reflect.AnnotatedElement;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.function.Predicate;
import java.util.stream.Collectors;

import static com.mastering.reflections.util.JavassistHelper.*;

/**
 * base Reflections {@link Scanner}s such as:
 * <ul>
 *   <li>{@link #SubTypes}</li>
 *   <li>{@link #TypesAnnotated}</li>
 *   <li>{@link #MethodsAnnotated}</li>
 *   <li>{@link #ConstructorsAnnotated}</li>
 *   <li>{@link #FieldsAnnotated}</li>
 *   <li>{@link #Resources}</li>
 *   <li>{@link #MethodsParameter}</li>
 *   <li>{@link #ConstructorsParameter}</li>
 *   <li>{@link #MethodsSignature}</li>
 *   <li>{@link #ConstructorsSignature}</li>
 *   <li>{@link #MethodsReturn}</li>
 * </ul>
 * <i>note that scanners must be configured in {@link com.mastering.reflections.Configuration} in order to be queried</i>
 * */
public enum Scanners implements Scanner, QueryBuilder, NameHelper {

    /** scan type superclasses and interfaces
     * <p></p>
     * <i>Note that {@code Object} class is excluded by default, in order to reduce store size.
     * <br>Use {@link #filterResultsBy(Predicate)} to change, for example {@code SubTypes.filterResultsBy(c -> true)}</i>
     * */
    SubTypes {
        /* Object class is excluded by default from subtypes indexing */
        { filterResultsBy(new FilterBuilder().excludePattern("java\\.lang\\.Object")); }

        public void scan(ClassFile classFile, List<Map.Entry<String, String>> entries) {
            entries.add(entry(classFile.getSuperclass(), classFile.getName()));
            entries.addAll(entries(Arrays.asList(classFile.getInterfaces()), classFile.getName()));
        }
    },

    /** scan type annotations */
    TypesAnnotated {
        public boolean acceptResult(String annotation) {
            return super.acceptResult(annotation) || annotation.equals(Inherited.class.getName());
        }

        public void scan(ClassFile classFile, List<Map.Entry<String, String>> entries) {
            entries.addAll(entries(getAnnotations(classFile::getAttribute), classFile.getName()));
        }
    },

    /** scan method annotations */
    MethodsAnnotated {
        public void scan(ClassFile classFile, List<Map.Entry<String, String>> entries) {
            getMethods(classFile).forEach(method ->
                entries.addAll(entries(getAnnotations(method::getAttribute), methodName(classFile, method))));
        }
    },

    /** scan constructor annotations */
    ConstructorsAnnotated {
        public void scan(ClassFile classFile, List<Map.Entry<String, String>> entries) {
            getConstructors(classFile).forEach(constructor ->
                entries.addAll(entries(getAnnotations(constructor::getAttribute), methodName(classFile, constructor))));
        }
    },

    /** scan field annotations */
    FieldsAnnotated {
        public void scan(ClassFile classFile, List<Map.Entry<String, String>> entries) {
            classFile.getFields().forEach(field ->
                entries.addAll(entries(getAnnotations(field::getAttribute), fieldName(classFile, field))));
        }
    },

    /** scan non .class files such as xml or properties files */
    Resources {
        public boolean acceptsInput(String file) {
            return !file.endsWith(".class");
        }

        public List<Map.Entry<String, String>> scan(Vfs.File file) {
            return Collections.singletonList(entry(file.getName(), file.getRelativePath()));
        }

        public void scan(ClassFile classFile, List<Map.Entry<String, String>> entries) {
            throw new IllegalStateException();
        }

        public QueryFunction<Store, String> with(String pattern) {
            return store -> store.getOrDefault(index(), Collections.emptyMap())
                .entrySet().stream().filter(entry -> entry.getKey().matches(pattern))
                .flatMap(entry -> entry.getValue().stream()).collect(Collectors.toCollection(LinkedHashSet::new));
        }
    },

    /** scan method parameters types and annotations */
    MethodsParameter {
        public void scan(ClassFile classFile, List<Map.Entry<String, String>> entries) {
            getMethods(classFile).forEach(method -> {
                String value = methodName(classFile, method);
                entries.addAll(entries(getParameters(method), value));
                getParametersAnnotations(method).forEach(annotations -> entries.addAll(entries(annotations, value)));
            });
        }
    },

    /** scan constructor parameters types and annotations */
    ConstructorsParameter {
        public void scan(ClassFile classFile, List<Map.Entry<String, String>> entries) {
            getConstructors(classFile).forEach(constructor -> {
                String value = methodName(classFile, constructor);
                entries.addAll(entries(getParameters(constructor), value));
                getParametersAnnotations(constructor).forEach(annotations -> entries.addAll(entries(annotations, value)));
            });
        }
    },

    /** scan methods signature */
    MethodsSignature {
        public void scan(ClassFile classFile, List<Map.Entry<String, String>> entries) {
            getMethods(classFile).forEach(method ->
                entries.add(entry(getParameters(method).toString(), methodName(classFile, method))));
        }

        public QueryFunction<Store, String> with(AnnotatedElement... keys) {
            return QueryFunction.single(toNames(keys).toString()).getAll(this::get);
        }
    },

    /** scan constructors signature */
    ConstructorsSignature {
        public void scan(ClassFile classFile, List<Map.Entry<String, String>> entries) {
            getConstructors(classFile).forEach(constructor ->
                entries.add(entry(getParameters(constructor).toString(), methodName(classFile, constructor))));
        }

        public QueryFunction<Store, String> with(AnnotatedElement... keys) {
            return QueryFunction.single(toNames(keys).toString()).getAll(this::get);
        }
    },

    /** scan method return type */
    MethodsReturn {
        public void scan(ClassFile classFile, List<Map.Entry<String, String>> entries) {
            getMethods(classFile).forEach(method ->
                entries.add(entry(getReturnType(method), methodName(classFile, method))));
        }
    };

    private Predicate<String> resultFilter = s -> true; //accept all by default

    @Override
    public String index() {
        return name();
    }

    public Scanners filterResultsBy(Predicate<String> filter) {
        this.resultFilter = filter;
        return this;
    }

    @Override
    public final List<Map.Entry<String, String>> scan(ClassFile classFile) {
        List<Map.Entry<String, String>> entries = new ArrayList<>();
        scan(classFile, entries);
        return entries.stream().filter(a -> acceptResult(a.getKey())).collect(Collectors.toList());
    }

    public abstract void scan(ClassFile classFile, List<Map.Entry<String, String>> entries);

    protected boolean acceptResult(String fqn) {
        return fqn != null && resultFilter.test(fqn);
    }
}

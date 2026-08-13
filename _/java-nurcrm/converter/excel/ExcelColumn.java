package kg.nurtelecom.util.converter.excel;

import kg.nurtelecom.util.parser.Parser;
import kg.nurtelecom.util.parser.DefaultParser;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
public @interface ExcelColumn {
    String value();

    Class<? extends Parser<?>> parser() default DefaultParser.class;
}

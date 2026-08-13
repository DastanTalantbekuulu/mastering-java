package com.mastering;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.Arrays;
import java.util.Map;
import java.util.stream.Collectors;

@Getter
@RequiredArgsConstructor
public enum Type {
    ALL("*"),
    PDF("pdf"),
    DJVU("djvu"),
    EPUB("epub"),
    MOBI("mobi"),
    FB2("fb2"),
    HTML("html"),
    TXT("txt"),
    RTF("rtf"),
    DOC("doc"),
    DOCX("docx"),
    XLS("xls"),
    XLSX("xlsx"),
    PPT("ppt"),
    PPTX("pptx"),
    ODT("odt"),
    ;
    private final String value;

    private static final Map<String, Type> MAP = Arrays.stream(values())
            .collect(Collectors.toMap(t -> t.value.toLowerCase(), t -> t));

    public static boolean isValid(String value) {
        return MAP.containsKey(value.toLowerCase());
    }

    @JsonCreator
    public static Type fromValue(String value) {
        if (value == null || value.isBlank()) return null;
        return MAP.get(value.toLowerCase());
    }

    @Override
    @JsonValue
    public String toString() {
        return value;
    }
}

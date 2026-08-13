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
public enum Language {
    KG("ky-KG"),
    RU("ru-RU"),
    EN("en-US"),
    ;
    private final String value;

    private static final Map<String, Language> MAP = Arrays.stream(values())
            .collect(Collectors.toMap(t -> t.value.toLowerCase(), t -> t));

    @JsonCreator
    public static Language fromValue(String value) {
        if (value == null || value.isBlank()) return null;
        return MAP.get(value.toLowerCase());
    }
    @Override
    @JsonValue
    public String toString() {
        return value;
    }

}

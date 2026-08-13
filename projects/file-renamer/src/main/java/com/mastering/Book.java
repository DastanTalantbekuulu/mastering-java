package com.mastering;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.time.Year;
import java.util.Objects;

@Getter
@Setter
@ToString
@Builder
@NoArgsConstructor
@AllArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class Book {
    @JsonProperty("path")
    private String path;
    @JsonProperty("fileName")
    private String fileName;
    @JsonProperty("type")
    private Type type;

    @JsonProperty("title")
    private String title;
    @JsonProperty("author")
    private String author;
    @JsonProperty("pages")
    private Integer pages;
    @JsonProperty("year")
    private Year year;
    @JsonProperty("language")
    private Language language;
    @JsonProperty("isbn")
    private String isbn;
    @JsonProperty("publisher")
    private String publisher;
    @JsonProperty("edition")
    private Integer edition;

    @JsonProperty("volume")
    private Integer volume;
    @JsonProperty("volume-name")
    private String volumeName;
    @JsonProperty("series")
    private String series;

    @JsonProperty("description")
    private String description;
    @JsonProperty("keywords")
    private String keywords;
    @JsonProperty("category")
    private String category;

    @JsonProperty("annotate")
    private String annotate;

    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        Book book = (Book) o;
        return Objects.equals(path, book.path);
    }

    public int hashCode() {
        return Objects.hashCode(path);
    }
}

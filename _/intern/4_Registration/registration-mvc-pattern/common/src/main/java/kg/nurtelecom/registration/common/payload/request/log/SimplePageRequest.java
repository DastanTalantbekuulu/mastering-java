package kg.nurtelecom.registration.common.payload.request.log;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import java.util.Map;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

public class SimplePageRequest {
    @Min(value = 0, message = "Номер страницы не может быть отрицательным")
    private Integer page;

    @Min(value = 5, message = "Минимальный размер страницы 5")
    @Max(value = 20, message = "Максимальный размер страницы 20")
    private Integer size;

    @Pattern(regexp = "^(identifier|datetime|user|event|record_name|record_identifier|record_version|ip|agent|code)$",
            message = "sortBy должен быть одним из: identifier, datetime, user, event, record_name, record_identifier, record_version, ip, agent, code")
    private String sortBy;

    private Boolean asc;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @Pattern(regexp = "^\\d{4}-\\d{2}-\\d{2} \\d{2}:\\d{2}:\\d{2}$", message = "Фильтр from не соответствует шаблону yyyy-MM-dd HH:mm:ss")
    private String from;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @Pattern(regexp = "^\\d{4}-\\d{2}-\\d{2} \\d{2}:\\d{2}:\\d{2}$", message = "Фильтр to не соответствует шаблону yyyy-MM-dd HH:mm:ss")
    private String to;

    private Map<@Pattern(regexp = "^(identifier|datetime|user|event|record_name|record_identifier|record_version|ip|agent|code)$",
                    message = "Ключ фильтра должен быть одним из: identifier, datetime, user, event, record_name, record_identifier, record_version, ip, agent, code")
                    String,
            @Size(max = 255, message = "Длина строки не должен превышать 255 символов") String> filters;

    @Size(max = 255, message = "Длина строки не должен превышать 255 символов")
    @Pattern(regexp = "^(login|version)$", message = "Поле type должно быть либо 'login', либо 'version'")
    private String type;

    public SimplePageRequest() {
        page = 0;
        size = 5;
        sortBy = "identifier";
    }

    public boolean hasWhere() {
        return sortBy != null || from != null || to != null || hasFilters() || hasType();
    }

    public Pageable getPageable() {
        Sort sort = asc ? Sort.by(sortBy).ascending() : Sort.by(sortBy).descending();
        return PageRequest.of(page, size, sort);
    }

    public boolean hasBetween() {
        return from != null && to != null;
    }

    public boolean hasFilters() {
        return filters != null && !filters.isEmpty();
    }

    public boolean hasType() {
        return type != null;
    }

    public int getPage() {
        return page;
    }

    public void setPage(int page) {
        this.page = page;
    }

    public int getSize() {
        return size;
    }

    public void setSize(int size) {
        this.size = size;
    }

    public String getSortBy() {
        return sortBy;
    }

    public void setSortBy(String sortBy) {
        this.sortBy = sortBy;
    }

    public boolean isAsc() {
        return asc;
    }

    public void setAsc(boolean asc) {
        this.asc = asc;
    }

    public String getFrom() {
        return from;
    }

    public void setFrom(String from) {
        this.from = from;
    }

    public String getTo() {
        return to;
    }

    public void setTo(String to) {
        this.to = to;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Map<String, String> getFilters() {
        return filters;
    }

    public void setFilters(Map<String, String> filters) {
        this.filters = filters;
    }

    @Override
    public String toString() {
        return "SimplePageRequest{" +
               "page=" + page +
               ", size=" + size +
               ", sortBy='" + sortBy + '\'' +
               ", asc=" + asc +
               ", from='" + from + '\'' +
               ", to='" + to + '\'' +
               ", filters=" + filters +
               ", type='" + type + '\'' +
               '}';
    }
}

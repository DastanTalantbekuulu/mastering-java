package kg.nurtelecom.util.comparator.entity.field;

import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Builder
@Getter
@RequiredArgsConstructor
public class Difference {
    private final String fieldName;
    private final Object oldValue;
    private final Object newValue;

    @Override
    public String toString() {
        return String.format("Field '%s' changed from '%s' to '%s'", fieldName, oldValue, newValue);
    }
}
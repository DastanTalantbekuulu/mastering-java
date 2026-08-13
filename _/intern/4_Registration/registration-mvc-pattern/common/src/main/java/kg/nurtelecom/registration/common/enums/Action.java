package kg.nurtelecom.registration.common.enums;

public enum Action {
    INSERT("INSERT"),
    UPDATE("UPDATE"),
    DELETE("DELETE"),
    LOGIN("LOGIN"),
    LOGOUT("LOGOUT"),
    UNKNOWN("UNKNOWN"),
    ;
    private final String value;

    Action(String value) {
        this.value = value;
    }
    public String getValue() {
        return value;
    }
}

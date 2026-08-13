package kg.nurtelecom.registration.common.enums;

public enum SQLAction {
    INSERT("INSERT"),
    UPDATE("UPDATE"),
    DELETE("DELETE"),
    UNKNOWN("UNKNOWN");

    private final String value;

    SQLAction(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }

    public static SQLAction fromQuery(String sql) {
        if (sql == null || sql.trim().isEmpty()) {
            return UNKNOWN;
        }

        String trimmedSql = sql.trim().toUpperCase();
        if (trimmedSql.startsWith("INSERT")) {
            return INSERT;
        } else if (trimmedSql.startsWith("UPDATE")) {
            return UPDATE;
        } else if (trimmedSql.startsWith("DELETE")) {
            return DELETE;
        } else {
            return UNKNOWN;
        }
    }
}


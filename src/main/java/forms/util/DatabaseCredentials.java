package forms.util;

public enum DatabaseCredentials {
    URL("jdbc:mysql://localhost/restaurant_app"),
    USERNAME(System.getenv("RESTAURANT_APP_USER")),
    PASSWORD(System.getenv("RESTAURANT_APP_PASSWORD"))
    ;

    private final String value;

    public String getValue() {
        return value;
    }

    DatabaseCredentials(String s) {
        value = s;
    }
}

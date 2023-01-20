package util;

public enum RestaurantDatabaseCredentials {
    URL("jdbc:mysql://localhost/restaurant_app"),
    USERNAME(System.getenv("RESTAURANT_APP_USER")),
    PASSWORD(System.getenv("RESTAURANT_APP_PASSWORD"))
    ;

    private final String value;

    public String getValue() {
        return value;
    }

    RestaurantDatabaseCredentials(String s) {
        value = s;
    }
}

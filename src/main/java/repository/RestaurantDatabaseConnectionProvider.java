package repository;

import util.RestaurantDatabaseCredentials;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class RestaurantDatabaseConnectionProvider {
    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(RestaurantDatabaseCredentials.URL.getValue(),
                RestaurantDatabaseCredentials.USERNAME.getValue(), RestaurantDatabaseCredentials.PASSWORD.getValue());
    }
}

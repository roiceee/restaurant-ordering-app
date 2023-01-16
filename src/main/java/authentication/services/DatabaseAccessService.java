package authentication.services;

import authentication.util.DatabaseCredentials;
import model.RestaurantMainInfo;
import util.JOptionPaneLogger;

import java.sql.*;

public class DatabaseAccessService {

    public DatabaseAccessService() {
    }

    public static boolean register(String username, String password, String restaurantName, String country,
                                   String region, String city) {
        Connection conn;

        try {
            conn = DriverManager.getConnection(DatabaseCredentials.URL.getValue(),
                    DatabaseCredentials.USERNAME.getValue(), DatabaseCredentials.PASSWORD.getValue());


            PreparedStatement accountsInsertStatement = conn.prepareStatement("INSERT INTO restaurant_accounts " +
                    "(username, password, name, country, region, city) " + "VALUES (?, ?, ?, ?, ?, ?);");

            accountsInsertStatement.setString(1, username);
            accountsInsertStatement.setString(2, PasswordSecurityService.hashPassword(password));
            accountsInsertStatement.setString(3, restaurantName);
            accountsInsertStatement.setString(4, country);
            accountsInsertStatement.setString(5, region);
            accountsInsertStatement.setString(6, city);

            accountsInsertStatement.executeUpdate();


            JOptionPaneLogger.showInformationDialog("Success", "Restaurant registered successfully.");

        } catch (SQLException e) {
            JOptionPaneLogger.showErrorDialog("Database Error", "We are having trouble working with the" + " database"
                    + ".");
            return false;
        }

        return true;
    }

    public static RestaurantMainInfo login(String username, String password) {
        Connection conn;
        RestaurantMainInfo restaurantMainInfo = new RestaurantMainInfo();
        try {
            conn = DriverManager.getConnection(DatabaseCredentials.URL.getValue(),
                    DatabaseCredentials.USERNAME.getValue(), DatabaseCredentials.PASSWORD.getValue());
            PreparedStatement statement = conn.prepareStatement("SELECT * FROM restaurant_accounts " + "WHERE " +
                    "username = ?;");
            statement.setString(1, username);

            ResultSet resultSet = statement.executeQuery();

            if (!resultSet.next()) {
                JOptionPaneLogger.showErrorDialog("Validation Error", "No such username found.");
                return null;
            }

            if (!isValidPassword(password, resultSet.getString("password"))) {
                JOptionPaneLogger.showErrorDialog("Validation Error", "Wrong password.");
                return null;
            }

            restaurantMainInfo.setRestaurantID(resultSet.getString("id"));
            restaurantMainInfo.setName(resultSet.getString("name"));
            restaurantMainInfo.setCountry(resultSet.getString("country"));
            restaurantMainInfo.setRegion(resultSet.getString("region"));
            restaurantMainInfo.setCity(resultSet.getString("city"));

            JOptionPaneLogger.showInformationDialog("Success", "Welcome, " + restaurantMainInfo.getName() + ".");
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return restaurantMainInfo;
    }

    public static boolean isValidPassword(String password, String hashedPassword) {
        return PasswordSecurityService.hashPassword(password).equals(hashedPassword);
    }
}

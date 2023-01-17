package forms.repository;

import forms.util.DatabaseCredentials;
import util.JOptionPaneLogger;

import java.sql.*;

public class AdminRepository {

    public boolean addMenuItem(int restaurantID, String name, String description, int price, int pax) {
        try {

            Connection con = DriverManager.getConnection(DatabaseCredentials.URL.getValue(),
                    DatabaseCredentials.USERNAME.getValue(), DatabaseCredentials.PASSWORD.getValue());

            PreparedStatement statement = con.prepareStatement("INSERT INTO items (restaurant_id, name, " +
                    "description, price, pax) VALUES (?, ?, ?, ?, ?);");

            statement.setInt(1, restaurantID);
            statement.setString(2, name);
            statement.setString(3, description);
            statement.setInt(4, price);
            statement.setInt(5, pax);
            statement.executeUpdate();
            return true;
        } catch (SQLException e) {
            return false;
        }
    }

    public ResultSet returnMenuItemsResultSet(int restaurantID) {
        try {
            Connection con = DriverManager.getConnection(DatabaseCredentials.URL.getValue(),
                    DatabaseCredentials.USERNAME.getValue(), DatabaseCredentials.PASSWORD.getValue());

            PreparedStatement statement = con.prepareStatement("SELECT name, description, price, pax FROM " +
                            "items WHERE restaurant_id = ?", ResultSet.TYPE_SCROLL_SENSITIVE,
                    ResultSet.CONCUR_UPDATABLE);

            statement.setInt(1, restaurantID);

            return statement.executeQuery();
        } catch (SQLException e) {
            JOptionPaneLogger.showErrorDialog("Database Error", "Something wrong occurred.");
            throw new RuntimeException(e);
        }
    }

}

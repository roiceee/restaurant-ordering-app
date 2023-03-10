package repository;

import util.DatabaseTableDataUtil;
import util.PasswordHasher;
import model.TableDataObject;
import util.JOptionPaneLogger;

import java.sql.*;

public class MenuRepository {


    public boolean addMenuItem(int restaurantID, String name, String description, int price, int pax) {
        try {

            Connection con =  RestaurantDatabaseConnectionProvider.getConnection();

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

    public boolean editMenuItem(int itemID, String name, String description, int price, int pax) {
        try {
            Connection con = RestaurantDatabaseConnectionProvider.getConnection();
            PreparedStatement statement = con.prepareStatement("UPDATE items SET name = ?, description = ?, price" +
                    " = ?, pax = ? WHERE item_id = ?;");
            statement.setString(1, name);
            statement.setString(2, description);
            statement.setInt(3, price);
            statement.setInt(4, pax);
            statement.setInt(5, itemID);
            statement.executeUpdate();
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            showDatabaseError();
            return false;
        }
    }

    public boolean deleteMenuItem(int itemID) {
        try {
            Connection con = RestaurantDatabaseConnectionProvider.getConnection();
            PreparedStatement statement = con.prepareStatement("DELETE FROM items WHERE item_id = ?;");
            statement.setInt(1, itemID);
            statement.executeUpdate();
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            showDatabaseError();
            return false;
        }
    }

    public boolean clearMenu(String password, int restaurantID) {
        try {
            Connection con = RestaurantDatabaseConnectionProvider.getConnection();

            PreparedStatement statement = con.prepareStatement("SELECT password FROM restaurant_accounts WHERE " +
                    "restaurant_id = ?;");
            statement.setInt(1, restaurantID);

            ResultSet resultSet = statement.executeQuery();

            String hashedPassword = "";
            if (resultSet.next()) {
                hashedPassword = resultSet.getString("password");
            }

            if (!PasswordHasher.hashPassword(password).equals(hashedPassword)) {
                JOptionPaneLogger.showErrorDialog("Validation Error", "Wrong password.");
                return false;
            }

            PreparedStatement deleteStatement = con.prepareStatement("DELETE FROM items WHERE restaurant_id = ?");
            deleteStatement.setInt(1, restaurantID);
            deleteStatement.executeUpdate();

            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            showDatabaseError();
            return false;
        }
    }
    

    public TableDataObject getMenuDataObject(int restaurantID) {
        try {
            Connection con = RestaurantDatabaseConnectionProvider.getConnection();

            PreparedStatement statement = con.prepareStatement("SELECT item_id, name, description, price, pax " +
                            "FROM items WHERE restaurant_id = ?", ResultSet.TYPE_SCROLL_SENSITIVE,
                    ResultSet.CONCUR_UPDATABLE);

            statement.setInt(1, restaurantID);

            ResultSet resultSet = statement.executeQuery();

            return new TableDataObject(DatabaseTableDataUtil.getTableRows(resultSet),
                    DatabaseTableDataUtil.getTableColumns(resultSet));
        } catch (SQLException e) {
            showDatabaseError();
            throw new RuntimeException(e);
        }
    }

    private void showDatabaseError() {
        JOptionPaneLogger.showErrorDialog("Database error", "Error connecting to database.");
    }

}

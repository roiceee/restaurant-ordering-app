package repository;

import model.Order;
import model.OrderItem;
import model.TableDataObject;
import util.DatabaseTableDataUtil;
import util.JOptionPaneLogger;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class OrderRepository {
    public int addOrder(Order order) {

        try {
            Connection connection = RestaurantDatabaseConnectionProvider.getConnection();


            String query = "INSERT INTO orders (restaurant_id, " +
                    "item_id, customer_name, quantity) VALUES (?, ?, ?, ?)";

            PreparedStatement insertStatement1 = connection.prepareStatement(query);

            OrderItem orderItem = order.getOrderItemList().remove(0);
            insertStatement1.setInt(1, order.getRestaurantID());
            insertStatement1.setInt(2, orderItem.getMenuItem().getId());
            insertStatement1.setString(3, order.getCustomerName());
            insertStatement1.setInt(4, orderItem.getQuantity());
            insertStatement1.executeUpdate();

            if (order.getOrderItemList().size() > 1) {
                StringBuilder sb = new StringBuilder();

                for (int i = 0; i < order.getOrderItemList().size(); i++) {
                    sb.append("(LAST_INSERT_ID(), ?, ?, ?, ?)");
                    if (i + 1 == order.getOrderItemList().size()) {
                        break;
                    }
                    sb.append(", ");
                }

                sb.append(";");

                String batchQuery = "INSERT INTO orders (order_id, item_id, " +
                        "restaurant_id, customer_name, quantity) VALUES " + sb;

                PreparedStatement statement = connection.prepareStatement(batchQuery);

                int parameterIndex = 1;
                for (OrderItem oi : order.getOrderItemList()) {
                    statement.setInt(parameterIndex++, oi.getMenuItem().getId());
                    statement.setInt(parameterIndex++, order.getRestaurantID());
                    statement.setString(parameterIndex++, order.getCustomerName());
                    statement.setInt(parameterIndex++, oi.getQuantity());
                }

                statement.executeUpdate();
            }

            ResultSet set = connection.prepareStatement("SELECT LAST_INSERT_ID();").executeQuery();
            int orderNumber = 0;
            if (set.next()) {
                orderNumber = set.getInt(1);
            }
            return orderNumber;
        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPaneLogger.showErrorDialog("Database error", "Something wrong happened.");
            return -1;
        }
    }

    public TableDataObject getPendingOrders(int restaurantID) {
        try {
            Connection connection = RestaurantDatabaseConnectionProvider.getConnection();
            PreparedStatement statement = connection.prepareStatement(
                    "SELECT DISTINCT order_id, customer_name, timestamp " +
                            "FROM orders " +
                            "WHERE restaurant_id = ? AND is_accepted = false " +
                            "ORDER BY timestamp;"
                    , ResultSet.TYPE_SCROLL_SENSITIVE,
                    ResultSet.CONCUR_UPDATABLE);
            statement.setInt(1, restaurantID);
            ResultSet set = statement.executeQuery();
            return new TableDataObject(DatabaseTableDataUtil.getTableRows(set),
                    DatabaseTableDataUtil.getTableColumns(set));
        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPaneLogger.showErrorDialog("Database error", "Error connection with the database.");
            return new TableDataObject();
        }
    }

    public String getOrderPreview(int orderID, int restaurantID) {
        try {
            Connection connection = RestaurantDatabaseConnectionProvider.getConnection();
            PreparedStatement statement = connection.prepareStatement(
                    "SELECT po.order_id, i.item_id, i.name, po.customer_name, po.quantity, po.timestamp " +
                            "FROM orders po " +
                            "JOIN items i on po.item_id = i.item_id " +
                            "WHERE po.order_id = ? AND po.restaurant_id = ?;");

            statement.setInt(1, orderID);
            statement.setInt(2, restaurantID);

            ResultSet set = statement.executeQuery();

            StringBuilder sb = new StringBuilder();

            if (set.next()) {
                sb.append("Order ID: ").append(set.getInt(1)).append("\n\n");
                sb.append("Customer Name: ").append(set.getString(4)).append("\n\n");
                sb.append("Timestamp: ").append(set.getTimestamp(6)).append("\n\n");
                sb.append("Items: \n")
                        .append(set.getInt(5)).append("    ").append(set.getString(3)).append("\n");
                while (set.next()) {
                    sb.append(set.getInt(5)).append("    ").append(set.getString(3)).append("\n");
                }
            }

            return sb.toString();
        } catch (SQLException e) {
            e.printStackTrace();
            return "";
        }

    }

    public boolean updateOrderAsAccepted(int orderID) {
        try {
            Connection connection = RestaurantDatabaseConnectionProvider.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(
                    "UPDATE orders " +
                            "SET is_accepted = true " +
                            "WHERE order_id = ?;"
            );

            preparedStatement.setInt(1, orderID);
            preparedStatement.executeUpdate();
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPaneLogger.showErrorDialog("Database error", "Problem connecting to database.");
            return false;
        }
    }

}

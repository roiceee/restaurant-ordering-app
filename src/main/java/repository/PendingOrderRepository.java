package repository;

import model.Order;
import model.OrderItem;
import util.JOptionPaneLogger;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class PendingOrderRepository {
    public int addOrder(Order order) {

        try {
            Connection connection = RestaurantDatabaseConnectionProvider.getConnection();


            String query = "INSERT INTO pending_orders (restaurant_id, " +
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

                String batchQuery = "INSERT INTO pending_orders (order_id, item_id, " +
                        "restaurant_id, customer_name, quantity) VALUES " + sb;

                PreparedStatement statement = connection.prepareStatement(batchQuery);

                int parameterIndex = 1;
                for (OrderItem oi: order.getOrderItemList()) {
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
}

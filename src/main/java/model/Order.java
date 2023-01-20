package model;

import java.security.Timestamp;
import java.util.ArrayList;
import java.util.List;

public class Order {
    List<OrderItem> orderItemList;

    String customerName;
    int restaurantID;
    Timestamp timestamp;

    public Order(int restaurantID) {
        this.restaurantID = restaurantID;
        this.orderItemList = new ArrayList<>();
    }
    public Order(int restaurantID, Timestamp timestamp) {
        this(restaurantID);
        this.timestamp = timestamp;
    }


    public List<OrderItem> getOrderItemList() {
        return orderItemList;
    }

    public void addOrder(OrderItem orderItem) {
        orderItemList.add(orderItem);
    }

    public void addOrderItemQuantity(OrderItem orderItem, int quantity) {
        int index = orderItemList.indexOf(orderItem);
        OrderItem tempOrderItem = orderItemList.get(index);
        tempOrderItem.addQuantity(quantity);
        orderItemList.set(index, tempOrderItem);
    }

    public void clearOrder() {
        orderItemList = new ArrayList<>();
    }

    public void deleteOrder(OrderItem orderItem) {
        orderItemList.remove(orderItem);
    }
    
    public void setRestaurantID(int restaurantID) {
        this.restaurantID = restaurantID;
    }
    public int getRestaurantID() {
        return restaurantID;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }
    public String getCustomerName() {
        return customerName;
    }

    public Timestamp getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Timestamp timestamp) {
        this.timestamp = timestamp;
    }
    

    @Override
    public String toString() {
        return "Order{" +
                "orderItemList=" + orderItemList +
                ", restaurantID=" + restaurantID +
                ", timestamp=" + timestamp +
                '}';
    }
}

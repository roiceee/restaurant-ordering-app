package forms.users.client;

import javax.swing.*;

public class OrderNumberPanelFrame {
    JFrame frame;

    String restaurantName;

    String customerName;

    int orderID;

    public OrderNumberPanelFrame(String restaurantName, String customerName, int orderID) {
        this.frame = new JFrame("Order Information");
        this.restaurantName = restaurantName;
        this.customerName = customerName;
        this.orderID = orderID;
    }

    public void run() {
        frame.setContentPane(new OrderNumberPanel(frame, restaurantName, customerName, orderID).getMainPanel());
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.pack();
        frame.setResizable(false);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }
}

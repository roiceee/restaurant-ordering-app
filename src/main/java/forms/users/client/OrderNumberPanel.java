package forms.users.client;

import model.RestaurantMainInfo;

import javax.swing.*;

public class OrderNumberPanel {
    private JPanel mainPanel;
    private JLabel orderNumberLabel;
    private JLabel restaurantNameLabel;
    private JButton OKButton;
    private JLabel customerNameLabel;

    private JFrame parentFrame;
    private String restaurantName;

    private String customerName;

    private int orderID;

    public OrderNumberPanel(JFrame parentFrame, String restaurantName, String customerName, int orderID) {
        this.parentFrame = parentFrame;
        this.restaurantName = restaurantName;
        this.customerName = customerName;
        this.orderID = orderID;
        setValues();
        addActionListeners();
    }

    private void addActionListeners() {
        OKButton.addActionListener(e -> closeFrame());
    }

    private void closeFrame() {
        parentFrame.dispose();
    }

    private void setValues() {
        restaurantNameLabel.setText(restaurantName);
        orderNumberLabel.setText(String.valueOf(orderID));
        customerNameLabel.setText(customerName);
    }


    public JPanel getMainPanel() {
        return mainPanel;
    }
}

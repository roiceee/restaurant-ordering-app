package authentication.login;

import model.RestaurantMainInfo;

import javax.swing.*;

public class ChooseUserTypePanel {
    private JPanel mainPanel;
    private JPanel restaurantNamePanel;
    private JLabel usertypeLabel;
    private JButton btnClient;
    private JButton btnStaff;
    private JPanel buttonspanel;
    private JPanel staffButtonPanel;
    private JPanel clientButtonPanel;
    private JPanel adminButtonPanel;
    private JButton btnAdmin;
    private JLabel restaurantNameLabel;
    private JFrame parentFrame;

    private RestaurantMainInfo info;

    public ChooseUserTypePanel(JFrame parentFrame, RestaurantMainInfo info) {
        this.parentFrame = parentFrame;
        this.info = info;
        setRestaurantName(info.getName());
    }

    private void setRestaurantName(String name) {
        this.restaurantNameLabel.setText(name);
    }

    public JPanel getMainPanel() {
        return mainPanel;
    }

    public JPanel getRestaurantNamePanel() {
        return restaurantNamePanel;
    }

    public JLabel getUsertypeLabel() {
        return usertypeLabel;
    }

    public JButton getBtnClient() {
        return btnClient;
    }

    public JButton getBtnStaff() {
        return btnStaff;
    }

    public JPanel getButtonspanel() {
        return buttonspanel;
    }

    public JPanel getStaffButtonPanel() {
        return staffButtonPanel;
    }

    public JPanel getClientButtonPanel() {
        return clientButtonPanel;
    }

    public JPanel getAdminButtonPanel() {
        return adminButtonPanel;
    }

    public JButton getBtnAdmin() {
        return btnAdmin;
    }

    public JFrame getParentFrame() {
        return parentFrame;
    }
}

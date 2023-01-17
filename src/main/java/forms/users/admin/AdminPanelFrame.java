package forms.users.admin;

import forms.authentication.login.LoginPanel;
import model.RestaurantMainInfo;

import javax.swing.*;

public class AdminPanelFrame {
    JFrame frame;

    RestaurantMainInfo info;
    public AdminPanelFrame(RestaurantMainInfo info) {
        frame = new JFrame(info.getName() + " (Admin)");
        this.info = info;
    }

    public void run() {
        frame.setContentPane(new AdminPanel(frame, info).getMainPanel());
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }
}

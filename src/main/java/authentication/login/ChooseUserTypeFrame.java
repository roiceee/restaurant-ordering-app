package authentication.login;

import model.RestaurantMainInfo;

import javax.swing.*;

public class ChooseUserTypeFrame {
    JFrame frame;
    RestaurantMainInfo info;

    public ChooseUserTypeFrame(RestaurantMainInfo info) {
        frame = new JFrame("Choose User");
        this.info = info;
    }

    public void run() {
        frame.setContentPane(new ChooseUserTypePanel(frame, info).getMainPanel());
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }
}

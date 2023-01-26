package forms.authentication.login;

import javax.swing.*;

public class LoginFrame {
    JFrame frame;

    public LoginFrame() {
        frame = new JFrame("Login");
    }

    public void run() {
        frame.setContentPane(new LoginPanel(frame).getRestaurantLoginMainPanel());
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setResizable(false);
        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }
}

package authentication.registration;

import javax.swing.*;

public class RegistrationFrame {
    JFrame frame;

    public RegistrationFrame() {
        frame = new JFrame("Registration");
    }

    public void run() {
        frame.setContentPane(new RegistrationPanel(frame).getMainPanel());
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }
}

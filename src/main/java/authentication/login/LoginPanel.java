package authentication.login;

import authentication.registration.RegistrationFrame;

import javax.swing.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class LoginPanel {
    private JTextField textField1;
    private JPasswordField passwordField1;
    private JPanel restaurantLoginMainPanel;
    private JPanel titlepanel;
    private JPanel loginBodyPanel;
    private JPanel usernameFieldPanel;
    private JPanel passwordFieldPanel;
    private JPanel registerLinkPanel;
    private JPanel loginFieldPanel;
    private JLabel registerLabel;
    private JButton loginButton;

    private JFrame parentFrame;
    public JPanel getRestaurantLoginMainPanel() {
        return restaurantLoginMainPanel;
    }

    public LoginPanel(JFrame parentFrame) {
        addActionListeners();
        this.parentFrame = parentFrame;
    }


    public void addActionListeners() {
        loginButton.addActionListener(e -> {
            //login functionality
        });
        registerLabel.addMouseListener(new MouseAdapter() {
                                           @Override
                                           public void mouseClicked(MouseEvent e) {
                                                //launch registration panel
                                               RegistrationFrame frame = new RegistrationFrame();
                                               frame.run();
                                               parentFrame.dispose();
                                           }
                                       }
        );
    }

}

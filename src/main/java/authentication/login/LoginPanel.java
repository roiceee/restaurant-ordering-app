package authentication.login;

import authentication.registration.RegistrationFrame;
import authentication.services.DatabaseAccessService;
import model.RestaurantMainInfo;

import javax.swing.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class LoginPanel {
    private JTextField usernameField;
    private JPasswordField passwordField;
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
                                               parentFrame.dispose();
                                               runRegistrationFrame();
                                           }
                                       }
        );
        loginButton.addActionListener(e -> login());
    }

    public void login() {
        String username = getUsername();
        String password = getPassword();

        if (username.isEmpty() || password.isEmpty()) {
            launchErrorDialog("Validation Error", "Please fill out all the fields.");
            return;
        }

       RestaurantMainInfo restaurantMainInfo = DatabaseAccessService.login(username, password);

        if (restaurantMainInfo == null) {
            return;
        }

    }

    public String getUsername() {
        return usernameField.getText().trim();
    }

    public String getPassword() {
        return String.valueOf(passwordField.getPassword());
    }

    public void runRegistrationFrame() {
        RegistrationFrame frame = new RegistrationFrame();
        frame.run();
    }

    public void launchErrorDialog(String title, String message) {
        JOptionPane.showMessageDialog(null, title, message, JOptionPane.ERROR_MESSAGE);
    }

    public void launchTextDialog(String title, String message) {
        JOptionPane.showMessageDialog(null, title, message, JOptionPane.INFORMATION_MESSAGE);
    }

}

package authentication.registration;

import authentication.login.LoginFrame;
import authentication.services.DatabaseAccessService;
import authentication.utility.DatabaseCredentials;
import authentication.services.PasswordSecurityService;

import javax.swing.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.*;


public class RegistrationPanel {
    private JPanel mainPanel;
    private JPanel nameFieldPanel;
    private JPanel addressFieldPanel;
    private JPanel usernameFieldPanel;
    private JPanel passwordFieldPanel;
    private JPanel confirmPasswordFieldPanel;
    private JTextField nameField;
    private JPanel countryFieldPanel;
    private JTextField countryField;
    private JTextField regionField;
    private JPanel regionFieldPanel;
    private JPanel cityFieldPanel;
    private JTextField cityField;
    private JPanel loginCredentialsPanel;
    private JTextField usernameField;
    private JPasswordField passwordField;
    private JPasswordField confirmPasswordField;
    private JButton registerButton;
    private JPanel controlPanel;
    private JLabel loginLink;
    private JPanel leftPanel;
    private JPanel rightPanel;
    private JFrame parentFrame;



    public RegistrationPanel(JFrame parentFrame) {
        addActionListeners();
        this.parentFrame = parentFrame;

    }

    public void addActionListeners() {
        loginLink.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                //launch login frame
                parentFrame.dispose();
                runLoginFrame();

            }
        });
        registerButton.addActionListener(e -> register());
    }

    public void register() {
        String restaurantName = getNameField();
        String country = getCountryField();
        String region = getRegionField();
        String city = getCityField();
        String username = getUsernameField();
        String password = getPasswordField();

        if (!checkAllFieldsAreFilled()) {
            showJOptionPaneError("Validation Error", "Please fill out all fields.");
            return;
        }

        if (!validateConfirmPassword()) {
            showJOptionPaneError("Validation Error", "PASSWORD and CONFIRM PASSWORD don't match.");
            return;
        }
        if (!DatabaseAccessService.register(username, password, restaurantName, country, region, city)) {
            return;
        };

        parentFrame.dispose();
        runLoginFrame();

    }

    public boolean validateConfirmPassword() {
        String password = String.valueOf(passwordField.getPassword());
        String confirmPassword = String.valueOf(confirmPasswordField.getPassword());
        return password.equals(confirmPassword);
    }

    public boolean checkAllFieldsAreFilled() {
        return !getNameField().isEmpty() && !getCountryField().isEmpty() &&
                !getRegionField().isEmpty() && !getCityField().isEmpty() &&
                !getUsernameField().isEmpty() &&
                !getPasswordField().isEmpty();
    }

    public String getNameField() {
        return nameField.getText().trim();
    }

    public String getCountryField() {
        return countryField.getText().trim();
    }

    public String getRegionField() {
        return regionField.getText().trim();
    }

    public String getCityField() {
        return cityField.getText().trim();
    }

    public String getUsernameField() {
        return usernameField.getText().trim();
    }

    public String getPasswordField() {
        return String.valueOf(passwordField.getPassword());
    }

    public void showJOptionPaneError(String title, String message) {
        JOptionPane.showMessageDialog(null, message,
                title, JOptionPane.ERROR_MESSAGE);
    }

    public void showJOptionPaneText(String title, String message) {
        JOptionPane.showMessageDialog(null, message,
                title, JOptionPane.INFORMATION_MESSAGE);
    }

    public void runLoginFrame() {
        LoginFrame loginFrame = new LoginFrame();
        loginFrame.run();
    }

    public JPanel getMainPanel() {
        return mainPanel;
    }
}

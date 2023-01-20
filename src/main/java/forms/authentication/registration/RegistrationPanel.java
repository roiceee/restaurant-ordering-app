package forms.authentication.registration;

import forms.authentication.login.LoginFrame;
import repository.RegistrationRepository;
import util.JOptionPaneLogger;

import javax.swing.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;


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

    private RegistrationRepository repository;

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

        if (isInputInvalid(restaurantName) || isInputInvalid(country) || isInputInvalid(region) || isInputInvalid(city) ||
                isInputInvalid(username) || isInputInvalid(password)) {
            return;
        }

        if (!validateConfirmPassword()) {
            JOptionPaneLogger.showErrorDialog("Validation Error", "PASSWORD and CONFIRM PASSWORD don't match.");
            return;
        }


        if (!repository.register(username, password, restaurantName, country, region, city)) {
            return;
        }


        parentFrame.dispose();
        runLoginFrame();

    }


    private boolean validateConfirmPassword() {
        String password = String.valueOf(passwordField.getPassword());
        String confirmPassword = String.valueOf(confirmPasswordField.getPassword());
        return password.equals(confirmPassword);
    }


    private void runLoginFrame() {
        LoginFrame loginFrame = new LoginFrame();
        loginFrame.run();
    }

    private boolean isInputInvalid(String str) {

        return isInputLengthInvalid(str) || isInputEmpty(str);
    }

    private boolean isInputLengthInvalid(String str) {
        int maxInputLength = 60;
        if (str.length() > maxInputLength) {
            JOptionPaneLogger.showErrorDialog("Input Error", "Input length must not exceed " + maxInputLength +
                    " characters.");
            return true;
        }
        return false;
    }

    private boolean isInputEmpty(String str) {
        if (!str.isEmpty()) {
            return false;
        }
        JOptionPaneLogger.showErrorDialog("Input Error", "All fields should be filled.");
        return true;
    }


    private String getNameField() {
        return nameField.getText().trim();
    }

    private String getCountryField() {
        return countryField.getText().trim();
    }

    private String getRegionField() {
        return regionField.getText().trim();
    }

    private String getCityField() {
        return cityField.getText().trim();
    }

    private String getUsernameField() {
        return usernameField.getText().trim();
    }

    private String getPasswordField() {
        return String.valueOf(passwordField.getPassword());
    }

    public JPanel getMainPanel() {
        return mainPanel;
    }
}

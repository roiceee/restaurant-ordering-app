package authentication.registration;

import authentication.login.LoginFrame;

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
    private JTextField passwordField;
    private JTextField confirmPasswordField;
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
                //launch registration panel
                LoginFrame frame = new LoginFrame();
                frame.run();
                parentFrame.dispose();
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

        Connection conn;

        try {

            conn = DriverManager.getConnection("jdbc:mysql://localhost/restaurant_app", System.getenv("RESTAURANT_APP_USER"), System.getenv("RESTAURANT_APP_PASSWORD"));
            PreparedStatement selectStatement = conn.prepareStatement("SELECT * FROM restaurant_accounts WHERE username = ?;");
            selectStatement.setString(1, username);

            ResultSet rows = selectStatement.executeQuery();

            //if username exists, show error message and prevent from registration.
            if (rows.next()) {
                showJOptionPaneError("Validation Error", "Username already exists.");
                return;
            }

            PreparedStatement accountsInsertStatement =
                    conn.prepareStatement("INSERT INTO restaurant_accounts (username, password) VALUES (?, ?);");

            accountsInsertStatement.setString(1, username);
            accountsInsertStatement.setString(2, password);

            PreparedStatement detailsInsertStatement =
                    conn.prepareStatement("INSERT INTO restaurant_details (id, name, country, region, city) " +
                            "VALUES (LAST_INSERT_ID(), ?, ?, ?, ?);");
            detailsInsertStatement.setString(1, restaurantName);
            detailsInsertStatement.setString(2, country);
            detailsInsertStatement.setString(3, region);
            detailsInsertStatement.setString(4, city);

            accountsInsertStatement.executeUpdate();
            detailsInsertStatement.executeUpdate();

            showJOptionPaneText("Success", "Restaurant registered successfully.");
        } catch (SQLException e) {
            showJOptionPaneError("Database Error", "We are having trouble working with the database.");
            throw new RuntimeException(e);
        }

    }

    public boolean validateConfirmPassword() {
        String password = passwordField.getText().trim();
        String confirmPassword = confirmPasswordField.getText().trim();
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
        return passwordField.getText().trim();
    }

    public void showJOptionPaneError(String title, String message) {
        JOptionPane.showMessageDialog(null, message,
                title, JOptionPane.ERROR_MESSAGE);
    }

    public void showJOptionPaneText(String title, String message) {
        JOptionPane.showMessageDialog(null, message,
                title, JOptionPane.INFORMATION_MESSAGE);
    }

    public JPanel getMainPanel() {
        return mainPanel;
    }
}

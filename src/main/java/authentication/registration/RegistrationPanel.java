package authentication.registration;

import authentication.login.LoginFrame;

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
    private JTextField barangayField;
    private JTextField streetField;
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
    }

    public void register() {
        String restaurantName = nameField.getText().trim();
        String restaurantAddress = countryField.getText().trim() + ", " + regionField.getText().trim() + ", "
                + cityField.getText().trim() + ", " + barangayField.getText().trim() + ", "
                + streetField.getText().trim();
        String username = usernameField.getText().trim();
        String password = passwordField.getText().trim();

        if (!validateConfirmPassword()) {
            return;
        }

        //check if username exists on the database


    }

    public boolean validateConfirmPassword() {
        String password = passwordField.getText().trim();
        String confirmPassword = confirmPasswordField.getText().trim();
        return password.equals(confirmPassword);
    }

    public JPanel getMainPanel() {
        return mainPanel;
    }
}

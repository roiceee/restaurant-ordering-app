package authentication.registration;

import authentication.login.LoginFrame;

import javax.swing.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class RegistrationPanel {
    private JPanel mainPanel;
    private JPanel nameFieldPanel;
    private JPanel addressFieldPanel;
    private JPanel businessNumberFieldPanel;
    private JPanel usernameFieldPanel;
    private JPanel passwordFieldPanel;
    private JPanel confirmPasswordFieldPanel;
    private JTextField nameField;
    private JPanel countryFieldPanel;
    private JTextField countryField;
    private JTextField regionField;
    private JPanel regionFieldPanel;
    private JPanel cityFieldPanel;
    private JTextField textField1;
    private JPanel barangayFieldPanel;
    private JTextField textField2;
    private JPanel streetFieldPanel;
    private JTextField textField3;
    private JTextField textField4;
    private JPanel loginCredentialsPanel;
    private JTextField textField5;
    private JTextField textField6;
    private JTextField textField7;
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
    public JPanel getMainPanel() {
        return mainPanel;
    }
}

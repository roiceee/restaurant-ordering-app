package forms.authentication.login;

import forms.authentication.registration.RegistrationFrame;
import repository.RegistrationRepository;
import model.RestaurantMainInfo;
import util.JOptionPaneLogger;

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

    private RegistrationRepository repository;

    private JFrame parentFrame;
    public JPanel getRestaurantLoginMainPanel() {
        return restaurantLoginMainPanel;
    }

    public LoginPanel(JFrame parentFrame) {
        addActionListeners();
        this.parentFrame = parentFrame;
        repository = new RegistrationRepository();
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
//        String username = getUsername();
//        String password = getPassword();

        //TODO: disable on production
        String username = "hellnah";
        String password = "mcdo4ever";

        if (username.isEmpty() || password.isEmpty()) {
            JOptionPaneLogger.showErrorDialog("Validation Error", "Please fill out all the fields.");
            return;
        }

       RestaurantMainInfo restaurantMainInfo = repository.login(username, password);

        if (restaurantMainInfo == null) {
            return;
        }

        runChooserUserTypeFrame(restaurantMainInfo);
        parentFrame.dispose();

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

    public void runChooserUserTypeFrame(RestaurantMainInfo info) {
        ChooseUserTypeFrame frame = new ChooseUserTypeFrame(info);
        frame.run();
    }

}

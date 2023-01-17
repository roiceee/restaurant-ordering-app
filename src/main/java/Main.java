import forms.authentication.login.LoginFrame;
import util.DesignManager;

import javax.swing.*;
import javax.swing.UIManager.*;

public class Main {
    public static void main(String[] args) {

        DesignManager.setLookAndFeelToNimbus();
        LoginFrame loginFrame = new LoginFrame();
        loginFrame.run();

    }
}

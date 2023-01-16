package util;

import javax.swing.*;

public class JOptionPaneLogger {

    public static void showErrorDialog(String title, String message) {
        JOptionPane.showMessageDialog(null, message,
                title, JOptionPane.ERROR_MESSAGE);
    }

    public static void showInformationDialog(String title, String message) {
        JOptionPane.showMessageDialog(null, message,
                title, JOptionPane.INFORMATION_MESSAGE);
    }
}

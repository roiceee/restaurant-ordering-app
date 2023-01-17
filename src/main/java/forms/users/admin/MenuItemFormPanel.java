package forms.users.admin;

import forms.repository.AdminRepository;
import util.JOptionPaneLogger;

import javax.swing.*;

public class MenuItemFormPanel {
    private JPanel menuControlPanel;
    private JPanel mainPanel;
    private JTextField nameField;
    private JSpinner paxSpinner;
    private JSpinner priceSpinner;
    private JTextArea descriptionTextArea;
    private JButton cancelButton;
    private JButton confirmButton;

    private JFrame frame;

    private int restaurantID;

    AdminRepository repository;

    public MenuItemFormPanel(JFrame frame, int restaurantID) {
        this.frame = frame;
        addActionListeners();
        repository = new AdminRepository();
        this.restaurantID = restaurantID;
    }

    private void addActionListeners() {
        cancelButton.addActionListener(e -> frame.dispose());
        confirmButton.addActionListener(e -> addMenuItem());
    }


    private void addMenuItem() {
        String name = getNameFieldData();
        String description = getDescriptionTextAreaData();
        int price = getPriceSpinnerData();
        int pax = getPaxSpinnerData();

        if (!checkIfAllFieldsAreFilled(name, description, price, pax)) {
            JOptionPaneLogger.showErrorDialog("Validation Error", "Fill out all the fields appropriately. Price " +
                    "and pax value should not be below zero.");
            return;
        }

        if (!repository.addMenuItem(restaurantID, name, description, price, pax)) {
            JOptionPaneLogger.showErrorDialog("Database error", "Error occurred in the database.");
            return;
        }
        frame.dispose();
    }

    private boolean checkIfAllFieldsAreFilled(String name, String description, int price, int pax) {
        return !name.isEmpty() && !description.isEmpty() && price >= 0 && pax >= 0;
    }

    public JPanel getMenuControlPanel() {
        return menuControlPanel;
    }

    public JPanel getMainPanel() {
        return mainPanel;
    }

    public String getNameFieldData() {
        return nameField.getText().trim();
    }

    public int getPaxSpinnerData() {
        return (int) paxSpinner.getValue();
    }

    public int getPriceSpinnerData() {
        return (int) priceSpinner.getValue();
    }

    public String getDescriptionTextAreaData() {
        return descriptionTextArea.getText().trim();
    }

}

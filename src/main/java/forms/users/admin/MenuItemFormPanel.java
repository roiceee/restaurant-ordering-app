package forms.users.admin;

import forms.repository.AdminRepository;
import model.MenuItem;
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

    AdminRepository repository;

    AdminPanel adminPanel;

    MenuItem menuItem;

    MenuItemActions action;

    public MenuItemFormPanel(JFrame frame, MenuItem menuItem, AdminPanel adminPanel, MenuItemActions action) {
        this.frame = frame;
        this.adminPanel = adminPanel;
        repository = new AdminRepository();
        this.menuItem = menuItem;
        this.action = action;
        addActionListeners();
        setFieldValues();
    }

    private void addActionListeners() {
        cancelButton.addActionListener(e -> frame.dispose());
        confirmButton.addActionListener(e -> actionDispatcher());
    }


    private void actionDispatcher() {
        String name = getNameFieldData();
        String description = getDescriptionTextAreaData();
        int price = getPriceSpinnerData();
        int pax = getPaxSpinnerData();

        if (!checkIfAllFieldsAreFilled(name, description, price, pax)) {
            JOptionPaneLogger.showErrorDialog("Validation Error", "Fill out all the fields appropriately. Price " +
                    "and pax value should not be below zero.");
            return;
        }
        boolean success = switch (action) {
            case ADD -> addMenuItem(name, description, price, pax);
            case EDIT -> editMenuItem(name, description, price, pax);
        };
        if (!success) {
            return;
        }
        frame.dispose();
        adminPanel.refreshMenuTable();
    }
    private boolean addMenuItem(String name, String description, int price, int pax) {
        return repository.addMenuItem(menuItem.getRestaurantID(), name, description, price, pax);
    }

    private boolean editMenuItem(String name, String description, int price, int pax) {
        return repository.editMenuItem(menuItem.getId(), name, description, price, pax);
    }

    private boolean checkIfAllFieldsAreFilled(String name, String description, int price, int pax) {
        return !name.isEmpty() && !description.isEmpty() && price >= 0 && pax >= 0;
    }

    private void setFieldValues() {
        nameField.setText(menuItem.getName());
        descriptionTextArea.setText(menuItem.getDescription());
        priceSpinner.setValue(menuItem.getPrice());
        paxSpinner.setValue(menuItem.getPax());
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

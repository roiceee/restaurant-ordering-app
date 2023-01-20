package forms.users.admin;

import forms.repository.MenuRepository;
import model.MenuDataObject;
import model.MenuItem;
import model.RestaurantMainInfo;
import util.CustomStringFormatter;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class AdminPanel {
    private final JFrame parentFrame;
    private final RestaurantMainInfo restaurantMainInfo;
    private final MenuRepository adminRepository;
    MenuItem selectedItem;
    private JPanel mainPanel;
    private JLabel restaurantName;
    private JPanel upperPanel;
    private JPanel restaurantNamePanel;
    private JPanel analyticsLabelPanel;
    private JPanel analyticsPanel;
    private JPanel analyticsPanelBody;
    private JComboBox dateSelectorComboBox;
    private JPanel analyticsPanelRight;
    private JPanel analyticsPanelLeft;
    private JPanel labelPanel;

    private JPanel valuesPanel;

    private JButton addItemButton;
    private JLabel countryLabel;
    private JLabel regionLabel;
    private JLabel cityLabel;
    private JTable menuTable;
    private JButton editItemButton;
    private JButton deleteItemButton;
    private JButton clearMenuButton;
    private JButton refreshButton;
    private JTextArea previewTextArea;
    private JTextArea ordersTextArea;
    private JTextArea grossRevenueTextArea;


    public AdminPanel(JFrame parentFrame, RestaurantMainInfo info) {
        this.parentFrame = parentFrame;
        this.restaurantMainInfo = info;
        adminRepository = new MenuRepository();
        setRestaurantValues();
        addActionListeners();
        refreshMenuTable();
    }

    private void setRestaurantValues() {
        restaurantName.setText(formatInfoString(restaurantMainInfo.getName()));
        countryLabel.setText(formatInfoString(restaurantMainInfo.getCountry()) + ",");
        regionLabel.setText(formatInfoString(restaurantMainInfo.getRegion()) + ",");
        cityLabel.setText(formatInfoString(restaurantMainInfo.getCity()) + ",");
    }

    private void addActionListeners() {
        addItemButton.addActionListener(e -> addMenuItem());
        refreshButton.addActionListener(e -> refreshMenuTable());
        editItemButton.addActionListener(e -> editSelectedMenuItem());
        deleteItemButton.addActionListener(e -> deleteSelectedMenuItem());
        clearMenuButton.addActionListener(e -> clearMenu());
        menuTable.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                DefaultTableModel model = (DefaultTableModel) menuTable.getModel();
                int selectedRow = menuTable.getSelectedRow();

                int id = (int) model.getValueAt(selectedRow, 0);
                String name = (String) model.getValueAt(selectedRow, 1);
                String description = (String) model.getValueAt(selectedRow, 2);
                int price = (int) model.getValueAt(selectedRow, 3);
                int pax = (int) model.getValueAt(selectedRow, 4);
                MenuItem menuItem = new MenuItem(id, restaurantMainInfo.getRestaurantID(), name, description,
                        price, pax);
                setSelectedItem(menuItem);
            }
        });
    }

    private void addMenuItem() {
        MenuItem menuItem = new MenuItem(restaurantMainInfo.getRestaurantID());
        MenuItemFrame frame = new MenuItemFrame(MenuItemActions.ADD, menuItem, this);
        frame.run();
    }

    private void editSelectedMenuItem() {
        if (selectedItem == null) {
            return;
        }
        MenuItemFrame frame = new MenuItemFrame(MenuItemActions.EDIT, selectedItem, this);
        frame.run();
    }

    private void deleteSelectedMenuItem() {
        if (selectedItem == null) {
            return;
        }
        Object res = JOptionPane.showConfirmDialog(null,
                "Do you want to delete menu item " + selectedItem.getName() + "?", "Delete Confirmation",
                JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
        if (res.equals(JOptionPane.NO_OPTION)) {
            return;
        }
        if (!adminRepository.deleteMenuItem(selectedItem.getId())) {
            return;
        }
        refreshMenuTable();
    }

    private void clearMenu() {
        String password = JOptionPane.showInputDialog("Enter password");
        if (password.isBlank()) {
            return;
        }
        if (!adminRepository.clearMenu(password, restaurantMainInfo.getRestaurantID())) {
            return;
        }
        refreshMenuTable();
    }


    public void refreshMenuTable() {

        MenuDataObject menuDataObject = adminRepository.getMenuDataObject(restaurantMainInfo.getRestaurantID());

        menuTable.setModel(new DefaultTableModel(menuDataObject.getRows(), menuDataObject.getColumns()));
        //prevent user from directly editing the table
        menuTable.setDefaultEditor(Object.class, null);

        setSelectedItem(null);
    }

    private void setSelectedItem(MenuItem menuItem) {
        selectedItem = menuItem;
        setPreviewTextArea();
        toggleEditAndDeleteButtonEnabled();
    }

    private void setPreviewTextArea() {
        if (selectedItem == null) {
            previewTextArea.setText("");
            return;
        }
        previewTextArea.setText(selectedItem.toPreviewString());
    }

    private String formatInfoString(String str) {
        return CustomStringFormatter.capitalize(CustomStringFormatter.truncate(str, 60));
    }

    private void toggleEditAndDeleteButtonEnabled() {
        if (selectedItem == null) {
            editItemButton.setEnabled(false);
            deleteItemButton.setEnabled(false);
            return;
        }
        editItemButton.setEnabled(true);
        deleteItemButton.setEnabled(true);
    }

    public JPanel getMainPanel() {
        return mainPanel;
    }

}

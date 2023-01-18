package forms.users.admin;

import forms.repository.AdminRepository;
import model.MenuItem;
import model.RestaurantMainInfo;
import util.CustomStringFormatter;
import util.JOptionPaneLogger;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;

public class AdminPanel {
    private final JFrame parentFrame;
    private final RestaurantMainInfo restaurantMainInfo;
    private final AdminRepository adminRepository;
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
        adminRepository = new AdminRepository();
        setRestaurantValues();
        addActionListeners();
        refreshMenuTable();
    }

    private void setRestaurantValues() {
        restaurantName.setText(CustomStringFormatter.truncate(restaurantMainInfo.getName(), 40));
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
        menuTable.addMouseListener(new MouseListener() {
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

            @Override
            public void mousePressed(MouseEvent e) {

            }

            @Override
            public void mouseReleased(MouseEvent e) {

            }

            @Override
            public void mouseEntered(MouseEvent e) {

            }

            @Override
            public void mouseExited(MouseEvent e) {

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
                "Do you want to delete menu item " + selectedItem.getName() + "?",
                "Delete Confirmation", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
        if (res.equals(JOptionPane.NO_OPTION)) {
            return;
        }
        if (!adminRepository.deleteMenuItem(selectedItem.getId())){
            return;
        }
        refreshMenuTable();
    }

    private void clearMenu() {
        String password = JOptionPane.showInputDialog("Enter password");
        if (password.isBlank()) {
            return;
        }
        if (!adminRepository.clearMenu(password, restaurantMainInfo.getRestaurantID())){
            return;
        }
        refreshMenuTable();
    }


    private Object[][] convertResultSetToRows(ResultSet rs) throws SQLException {
        ResultSetMetaData metaData = rs.getMetaData();
        int numberOfColumns = metaData.getColumnCount();
        int numberOfRows = 0;
        while (rs.next()) {
            numberOfRows++;
        }
        rs.beforeFirst();
        Object[][] data = new Object[numberOfRows][numberOfColumns];
        int row = 0;
        while (rs.next()) {
            for (int column = 0; column < numberOfColumns; column++) {
                data[row][column] = rs.getObject(column + 1);
            }
            row++;
        }
        return data;
    }

    private String[] convertResultSetToColumns(ResultSet rs) throws SQLException {
        ResultSetMetaData metaData = rs.getMetaData();
        int numberOfColumns = metaData.getColumnCount();
        String[] objArray = new String[numberOfColumns];

        for (int i = 1; i <= numberOfColumns; i++) {
            objArray[i - 1] = metaData.getColumnName(i);
        }
        return objArray;
    }

    public void refreshMenuTable() {
        try {
            ResultSet set = adminRepository.returnMenuItemsResultSet(restaurantMainInfo.getRestaurantID());

            menuTable.setModel(new DefaultTableModel(convertResultSetToRows(set), convertResultSetToColumns(set)));
            //prevent user from directly editing the table
            menuTable.setDefaultEditor(Object.class, null);
            setSelectedItem(null);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

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
        previewTextArea.setText("\nName: " + selectedItem.getName() + "\n\nDescription: " + selectedItem.getDescription() + "\n\nPrice" + ": " + selectedItem.getDescription() + "\n\nPax: " + selectedItem.getPax());
    }

    private String formatInfoString(String str) {
        return CustomStringFormatter.capitalize(CustomStringFormatter.truncate(str, 53));
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

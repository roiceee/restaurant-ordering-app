package forms.users.admin;

import forms.repository.AdminRepository;
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
import java.util.HashMap;

public class AdminPanel {
    private final JFrame parentFrame;
    private final RestaurantMainInfo restaurantMainInfo;
    private final AdminRepository adminRepository;
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
    private JTextArea orderCountTextArea;
    private JTextArea grossRevenueTextArea;
    private JPanel menuControlPanel;
    private JTextArea nameTextArea;
    private JTextArea descriptionTextArea;
    private JTextArea urlTextArea;
    private JSpinner paxSpinner;
    private JSpinner priceSpinner;
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


    public AdminPanel(JFrame parentFrame, RestaurantMainInfo info) {
        this.parentFrame = parentFrame;
        this.restaurantMainInfo = info;
        adminRepository = new AdminRepository();
        setRestaurantValues();
        addActionListeners();
        setMenuTable();
    }

    private void setRestaurantValues() {
        restaurantName.setText(CustomStringFormatter.truncate(restaurantMainInfo.getName(), 40));
        countryLabel.setText(formatInfoString(restaurantMainInfo.getCountry()) + ",");
        regionLabel.setText(formatInfoString(restaurantMainInfo.getRegion()) + ",");
        cityLabel.setText(formatInfoString(restaurantMainInfo.getCity()) + ",");
    }

    private void addActionListeners() {
        addItemButton.addActionListener(e ->addMenuItem());
        refreshButton.addActionListener(e -> setMenuTable());
        menuTable.addMouseListener(new MouseListener() {
            @Override
            public void mouseClicked(MouseEvent e) {
                DefaultTableModel model = (DefaultTableModel) menuTable.getModel();
                int selectedRow = menuTable.getSelectedRow();

                String name = (String) model.getValueAt(selectedRow, 1);
                String description = (String) model.getValueAt(selectedRow, 2);
                int price = (int) model.getValueAt(selectedRow, 3);
                int pax = (int) model.getValueAt(selectedRow, 4);
                setPreviewTextArea(name, description, price, pax);
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


        }); {

        }
    }

    private void addMenuItem() {
        MenuItemFrame frame = new MenuItemFrame(MenuItemActions.ADD, restaurantMainInfo.getRestaurantID(), this);
        frame.run();
    }

    public void setMenuTable() {
        try {
            ResultSet set = adminRepository.returnMenuItemsResultSet(restaurantMainInfo.getRestaurantID());

            menuTable.setModel(new DefaultTableModel(convertResultSetToRows(set), convertResultSetToColumns(set)
            ));
            //prevent user from directly editing the table
            menuTable.setDefaultEditor(Object.class, null);

        } catch (SQLException e) {
            JOptionPaneLogger.showErrorDialog("Database error", "Something wrong occurred.");
            throw new RuntimeException(e);
        }

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
            Integer id = rs.getInt("item_id");
            String name = rs.getString("name");
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


    private void setPreviewTextArea(String name, String description, int price, int pax) {
            previewTextArea.setText("\nName: " + name + "\n\nDescription: " + description + "\n\nPrice: " + price +
                    "\n\nPax: " + pax);
    }
    private String formatInfoString(String str) {
        return CustomStringFormatter.capitalize(CustomStringFormatter.truncate(str, 53));
    }


    public JPanel getMainPanel() {
        return mainPanel;
    }

}

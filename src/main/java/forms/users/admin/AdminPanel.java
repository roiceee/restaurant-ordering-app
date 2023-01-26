package forms.users.admin;

import model.AnalyticsModel;
import repository.MenuRepository;
import model.TableDataObject;
import model.MenuItem;
import model.RestaurantMainInfo;
import repository.OrderRepository;
import util.CustomStringFormatter;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class AdminPanel {
    private final JFrame parentFrame;
    private final RestaurantMainInfo restaurantMainInfo;
    private final MenuRepository adminRepository;

    private final OrderRepository orderRepository;
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
        orderRepository = new OrderRepository();
        setRestaurantValues();
        setAnalyticsValue();
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
        dateSelectorComboBox.addActionListener(e -> {
           setAnalyticsValue();
        });
    }

    private void setAnalyticsValue() {
        String selectedItem = (String) getDateSelectorComboBoxSelectedItem();

        AnalyticsModel model = switch (selectedItem) {
            case "Yesterday" -> getAnalyticsDataYesterday();
            case "Last Week" -> getAnalyticsDataLastWeek();
            case "Last Month" -> getAnalyticsDataLastMonth();
            default -> getAnalyticsDataAllTime();
        };
        ordersTextArea.setText(String.valueOf(model.getOrders()));
        grossRevenueTextArea.setText(String.valueOf(model.getGrossRevenue()));
    }

    private AnalyticsModel getAnalyticsDataYesterday() {
      return orderRepository.getAnalyticsDataYesterday(restaurantMainInfo.getRestaurantID());
    }

    private AnalyticsModel getAnalyticsDataLastWeek() {
        return orderRepository.getAnalyticsDataLastWeek(restaurantMainInfo.getRestaurantID());
    }

    private AnalyticsModel getAnalyticsDataLastMonth() {
        return orderRepository.getAnalyticsDataLastMonth(restaurantMainInfo.getRestaurantID());
    }

    private AnalyticsModel getAnalyticsDataAllTime() {
        return orderRepository.getAnalyticsDataAllTime(restaurantMainInfo.getRestaurantID());
    }
    private Object getDateSelectorComboBoxSelectedItem() {
        return dateSelectorComboBox.getSelectedItem();
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
        if (password == null) {
            return;
        }
        if (password.isBlank()) {
            return;
        }
        if (!adminRepository.clearMenu(password, restaurantMainInfo.getRestaurantID())) {
            return;
        }
        refreshMenuTable();
    }


    public void refreshMenuTable() {

        TableDataObject tableDataObject = adminRepository.getMenuDataObject(restaurantMainInfo.getRestaurantID());

        menuTable.setModel(new DefaultTableModel(tableDataObject.getRows(), tableDataObject.getColumns()));
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

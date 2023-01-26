package forms.users.client;

import model.MenuItem;
import model.Order;
import model.OrderItem;
import model.RestaurantMainInfo;
import repository.MenuRepository;
import repository.OrderRepository;
import util.CurrentTimeProvider;
import util.JOptionPaneLogger;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

public class ClientPanel {
    private JLabel timeLabel;
    private JList<Object> menuItemJList;
    private JPanel mainPanel;
    private JTextArea previewTextArea;
    private JButton addButton;
    private JTable orderTable;
    private JSpinner quantitySpinner;
    private JButton deleteButton;
    private JButton clearOrderButton;
    private JButton checkoutButton;
    private JTextField nameField;
    private JLabel restaurantNameLabel;

    private RestaurantMainInfo restaurantMainInfo;

    private MenuRepository menuRepository;

    private List<MenuItem> menuList;

    private Order order;
    private MenuItem selectedMenuItem;

    private OrderItem selectedOrderItem;

    private OrderRepository orderRepository;


    public ClientPanel(RestaurantMainInfo restaurantMainInfo) {
        this.restaurantMainInfo = restaurantMainInfo;
        this.menuRepository = new MenuRepository();
        this.menuList = new ArrayList<>();
        order = new Order(restaurantMainInfo.getRestaurantID());
        orderRepository = new OrderRepository();
        setInitialState();
        addActionListeners();
    }

    private void setInitialState() {
        //prevent user from directly editing the table
        orderTable.setDefaultEditor(Object.class, null);
        restaurantNameLabel.setText(restaurantMainInfo.getName());
        updateTime();
        resetQuantitySpinner();
        getMenuList();
        populateMenuJList();
        updateCartTable();
    }

    private void addActionListeners() {
        menuItemJList.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                int selectedItemIndex = menuItemJList.getSelectedIndex();
                selectedMenuItem = menuList.get(selectedItemIndex);
                setPreviewTextArea();
                togglePreviewButtons();
            }
        });
        orderTable.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                int selectedRow = orderTable.getSelectedRow();
                selectedOrderItem = order.getOrderItemList().get(selectedRow);
                deleteButton.setEnabled(true);
            }
        });
        addButton.addActionListener(e -> {
            if (!addSelectedMenuItemToCart()) {
                return;
            }
            ;
            updateCartTable();
            clearAddArea();
        });
        deleteButton.addActionListener(e -> {
            deleteSelectedOrderItem();
            clearDeleteArea();
        });
        clearOrderButton.addActionListener(e -> clearCartWithDialog());
        checkoutButton.addActionListener(e -> {
            if (!checkOut()) {
                return;
            }
            clearCart();
            clearAddArea();
            clearNameField();
            clearDeleteArea();
            resetQuantitySpinner();
        });
    }

    private boolean checkOut() {
        String customerName = getName();
        if (customerName.isEmpty()) {
            JOptionPaneLogger.showErrorDialog("Input error", "Name should not be empty.");
            return false;
        }
        if (customerName.length() > 50) {
            JOptionPaneLogger.showErrorDialog("Input error", "Name length should not be more than 50.");
            return false;
        }
        if (order.getOrderItemList().isEmpty()) {
            JOptionPaneLogger.showErrorDialog("Input error", "Order should not be empty.");
            return false;
        }

        order.setCustomerName(customerName);
        //submit to database
        int orderID = orderRepository.addOrder(order);
        if (orderID == -1) {
            return false;
        }
        runOrderNumberPanelFrame(orderID);
        return true;
    }

    private void getMenuList() {
        Object[][] objects = menuRepository.getMenuDataObject(restaurantMainInfo.getRestaurantID()).getRows();
        for (Object[] objectArray : objects) {
            int itemID = (int) objectArray[0];
            String name = (String) objectArray[1];
            String description = (String) objectArray[2];
            int price = (int) objectArray[3];
            int pax = (int) objectArray[4];
            MenuItem item = new MenuItem(itemID, restaurantMainInfo.getRestaurantID(), name, description, price,
                    pax);
            menuList.add(item);
        }
    }

    private Object[] getMenuItemNames() {
        Object[] itemsArray = new Object[menuList.size()];
        for (int i = 0; i < itemsArray.length; i++) {
            itemsArray[i] = menuList.get(i).getName();
        }
        return itemsArray;
    }

    private boolean addSelectedMenuItemToCart() {
        int quantity = (int) quantitySpinner.getValue();
        if (quantity <= 0) {
            JOptionPaneLogger.showErrorDialog("Input Error", "Quantity must be greater than 0.");
            return false;
        }
        if (selectedMenuItem == null) {
            return false;
        }
        OrderItem orderItem = new OrderItem(selectedMenuItem, quantity);
        if (order.getOrderItemList().contains(orderItem)) {
            order.addOrderItemQuantity(orderItem, quantity);
        } else {
            order.addOrder(orderItem);
        }

        return true;
    }

    private void updateCartTable() {
        if (order.getOrderItemList().isEmpty()) {
            orderTable.setModel(new DefaultTableModel(new Object[]{"Name", "Quantity"}, 0));
            return;
        }
        Object[][] rows = new Object[order.getOrderItemList().size()][2];
        for (int i = 0; i < rows.length; i++) {
            rows[i][0] = order.getOrderItemList().get(i).getMenuItem().getName();
            rows[i][1] = order.getOrderItemList().get(i).getQuantity();
        }
        orderTable.setModel(new DefaultTableModel(rows, new Object[]{"Name", "Quantity"}));
    }


    private void clearAddArea() {
        menuItemJList.clearSelection();
        selectedMenuItem = null;
        resetQuantitySpinner();
        addButton.setEnabled(false);
        setPreviewTextArea();
    }

    private void clearCartWithDialog() {
        Object response = JOptionPane.showConfirmDialog(null, "Do you want to clear your order?",
                "Clear Order", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
        if (response.equals(JOptionPane.NO_OPTION)) {
            return;
        }
        clearCart();
    }

    private void clearCart() {
        order.clearOrder();
        updateCartTable();
    }

    private void runOrderNumberPanelFrame(int orderID) {
        OrderNumberPanelFrame frame = new OrderNumberPanelFrame(restaurantMainInfo.getName(),
                order.getCustomerName(), orderID);
        frame.run();
    }

    public JPanel getMainPanel() {
        return mainPanel;
    }

    private void deleteSelectedOrderItem() {
        order.deleteOrder(selectedOrderItem);
        updateCartTable();
    }

    private void populateMenuJList() {
        menuItemJList.setListData(getMenuItemNames());
    }

    private void setPreviewTextArea() {
        if (selectedMenuItem == null) {
            previewTextArea.setText("");
            return;
        }
        previewTextArea.setText(selectedMenuItem.toPreviewString());
    }

    private void togglePreviewButtons() {
        if (selectedMenuItem == null) {
            quantitySpinner.setEnabled(false);
            addButton.setEnabled(false);
            return;
        }
        quantitySpinner.setEnabled(true);
        addButton.setEnabled(true);
    }

    private String getName() {
        return nameField.getText().trim();
    }

    private void clearNameField() {
        nameField.setText("");
    }

    private void clearDeleteArea() {
        deleteButton.setEnabled(false);
    }

    private void resetQuantitySpinner() {
        quantitySpinner.setValue(1);
        quantitySpinner.setEnabled(false);
    }

    private void updateTime() {
        new Timer(1000, e -> {
            timeLabel.setText(CurrentTimeProvider.getCurrentTime());
        }).start();

    }
}


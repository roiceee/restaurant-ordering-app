package forms.users.staff;

import model.RestaurantMainInfo;
import model.TableDataObject;
import repository.OrderRepository;
import util.CurrentTimeProvider;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.util.LinkedList;
import java.util.Queue;

public class StaffPanel {
    private final RestaurantMainInfo restaurantMainInfo;
    private final JFrame parentFrame;
    private final OrderRepository orderRepository;
    private JPanel mainPanel;
    private JTextArea orderPreviewTextArea;
    private JTable ordersTable;
    private JButton acceptButton;
    private JButton signOutButton;
    private JButton refreshButton;
    private JLabel restaurantNameLabel;
    private JLabel timeLabel;
    private Queue<Integer> orderIDQueue;


    public StaffPanel(RestaurantMainInfo restaurantMainInfo, JFrame parentFrame) {
        this.restaurantMainInfo = restaurantMainInfo;
        this.parentFrame = parentFrame;
        orderRepository = new OrderRepository();
        addActionListeners();
        setInitialValues();
    }

    private void addActionListeners() {
        acceptButton.addActionListener(e -> acceptOrder());
        signOutButton.addActionListener(e -> parentFrame.dispose());
        refreshButton.addActionListener(e -> setInitialValues());
    }


    private void setInitialValues() {
        setRestaurantNameLabel();
        updateTime();
        TableDataObject tableDataObject = retrieveOrderIDData();
        setOrderTableData(tableDataObject);
        setOrderIDQueueValues(tableDataObject);
        if (tableDataObject.getRows().length == 0) {
            return;
        }
        selectFirstTableRow();
    }

    private void setOrderIDQueueValues(TableDataObject tableDataObject) {
        //order ID is in the index 0
        orderIDQueue = new LinkedList<>();
        for (Object[] orderID : tableDataObject.getRows()) {
            orderIDQueue.add((int) (orderID[0]));
        }
    }

    private void acceptOrder() {
        if (orderIDQueue.isEmpty()) {
            return;
        }
        Integer orderID = orderIDQueue.remove();
        if (!orderRepository.updateOrderAsAccepted(orderID)) {
            return;
        }
        ((DefaultTableModel) ordersTable.getModel()).removeRow(0);
        if (orderIDQueue.isEmpty()) {
            setOrderPreviewTextArea("");
            return;
        }
        System.out.println(orderIDQueue);
        selectFirstTableRow();
    }

    private TableDataObject retrieveOrderIDData() {
        return orderRepository.getPendingOrders(restaurantMainInfo.getRestaurantID());
    }

    private void setOrderTableData(TableDataObject tableDataObject) {
        ordersTable.setModel(new DefaultTableModel(tableDataObject.getRows(), tableDataObject.getColumns()));
    }

    private void selectFirstTableRow() {
        ordersTable.setRowSelectionInterval(0, 0);
        int selectedRow = ordersTable.getSelectedRow();
        int orderID = (int) ordersTable.getModel().getValueAt(selectedRow, 0);
        setOrderPreviewTextArea(orderRepository.getOrderPreview(orderID,
                restaurantMainInfo.getRestaurantID()));
    }

    private void setOrderPreviewTextArea(String str) {
        orderPreviewTextArea.setText(str);
    }

    private void setRestaurantNameLabel() {
        restaurantNameLabel.setText(restaurantMainInfo.getName());
    }

    private void updateTime() {
        new Timer(1000, e -> {
            timeLabel.setText(CurrentTimeProvider.getCurrentTime());
        }).start();
    }

    public JPanel getMainPanel() {
        return mainPanel;
    }
}

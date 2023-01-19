package forms.users.client;

import forms.repository.MenuRepository;
import model.MenuItem;
import model.RestaurantMainInfo;

import javax.swing.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.List;

public class ClientPanel {
    private JLabel timeLabel;
    private JList<Object> menuItemJList;
    private JPanel mainPanel;
    private JTextArea previewTextArea;
    private JButton addButton;
    private JTable table1;
    private JScrollPane cartTable;
    private JSpinner quantitySpinner;
    private JButton deleteButton;
    private JButton clearCartButton;
    private JButton checkoutButton;
    private JTextField nameField;

    private RestaurantMainInfo restaurantMainInfo;

    private MenuRepository menuRepository;

    private List<MenuItem> menuList;

    private MenuItem selectedItem;

    public ClientPanel(RestaurantMainInfo restaurantMainInfo) {
        this.restaurantMainInfo = restaurantMainInfo;
        this.menuRepository = new MenuRepository();
        this.menuList = new ArrayList<>();
        setInitialState();
        addActionListeners();
    }

    private void setInitialState() {
        getMenuList();
        populateMenuJList();
    }

    private void addActionListeners() {
        menuItemJList.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                int selectedItemIndex = menuItemJList.getSelectedIndex();
                selectedItem = menuList.get(selectedItemIndex);
                setPreviewTextArea();
                togglePreviewButtons();
            }
        });
    }
    private void getMenuList() {
        Object[][] objects = menuRepository.returnMenuRows(restaurantMainInfo.getRestaurantID());
        for (Object[] objectArray : objects) {
            int itemID = (int) objectArray[0];
            String name = (String) objectArray[1];
            String description = (String) objectArray[2];
            int price = (int) objectArray[3];
            int pax = (int) objectArray[4];
            MenuItem item = new MenuItem(itemID, restaurantMainInfo.getRestaurantID(), name, description, price, pax);
            System.out.println(item);
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


    public JPanel getMainPanel() {
        return mainPanel;
    }

    private void populateMenuJList() {
        menuItemJList.setListData(getMenuItemNames());
    }

    private void setPreviewTextArea() {
        previewTextArea.setText(selectedItem.toPreviewString());
    }

    private void togglePreviewButtons() {
        if (selectedItem == null) {
            quantitySpinner.setEnabled(false);
            addButton.setEnabled(false);
            return;
        }
        quantitySpinner.setEnabled(true);
        addButton.setEnabled(true);
    }
}

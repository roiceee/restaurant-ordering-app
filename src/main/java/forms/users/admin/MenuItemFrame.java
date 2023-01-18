package forms.users.admin;

import javax.swing.*;

public class MenuItemFrame {
    private final JFrame frame;
    private final int restaurantID;

    private AdminPanel adminPanel;
    public MenuItemFrame(MenuItemActions action, int restaurantID, AdminPanel adminPanel) {
        frame = new JFrame(getTitle(action));
        this.restaurantID = restaurantID;
        this.adminPanel = adminPanel;
    }

    private String getTitle(MenuItemActions action) {
        return switch (action) {
            case ADD -> "Add Item";
            case EDIT -> "Edit Item";
        };
    }

    public void run() {
        frame.setContentPane(new MenuItemFormPanel(frame, restaurantID, adminPanel).getMainPanel());
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }
}

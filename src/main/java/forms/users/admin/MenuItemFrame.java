package forms.users.admin;

import javax.swing.*;

public class MenuItemFrame {
    private final JFrame frame;
    private final int restaurantID;
    public MenuItemFrame(MenuItemActions action, int restaurantID) {
        frame = new JFrame(getTitle(action));
        this.restaurantID = restaurantID;
    }

    private String getTitle(MenuItemActions action) {
        return switch (action) {
            case ADD -> "Add Item";
            case EDIT -> "Edit Item";
        };
    }

    public void run() {
        frame.setContentPane(new MenuItemFormPanel(frame, restaurantID).getMainPanel());
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }
}

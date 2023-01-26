package forms.users.admin;

import model.MenuItem;

import javax.swing.*;

public class MenuItemFrame {
    private final JFrame frame;
    private final MenuItem menuItem;

    private final AdminPanel adminPanel;

    private MenuItemActions action;
    public MenuItemFrame(MenuItemActions action, MenuItem menuItem, AdminPanel adminPanel) {
        frame = new JFrame(getTitle(action));
        this.action = action;
        this.menuItem = menuItem;
        this.adminPanel = adminPanel;
    }

    private String getTitle(MenuItemActions action) {
        return switch (action) {
            case ADD -> "Add Item";
            case EDIT -> "Edit Item";
        };
    }

    public void run() {
        frame.setContentPane(new MenuItemFormPanel(frame, menuItem, adminPanel, action).getMainPanel());
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.setResizable(false);
        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }
}

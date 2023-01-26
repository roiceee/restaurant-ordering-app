package forms.users.staff;

import model.RestaurantMainInfo;

import javax.swing.*;

public class StaffPanelFrame {
    private final JFrame frame;
    private final RestaurantMainInfo restaurantMainInfo;

    public StaffPanelFrame(RestaurantMainInfo restaurantMainInfo) {
        frame = new JFrame(restaurantMainInfo.getName() + " (Staff)");
        this.restaurantMainInfo = restaurantMainInfo;
    }

    public void run() {
        frame.setContentPane(new StaffPanel(restaurantMainInfo, frame).getMainPanel());
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setResizable(false);
        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }
}

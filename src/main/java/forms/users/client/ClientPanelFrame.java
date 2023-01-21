package forms.users.client;

import model.RestaurantMainInfo;

import javax.swing.*;

public class ClientPanelFrame {
    RestaurantMainInfo info;
    JFrame frame;


    public ClientPanelFrame(RestaurantMainInfo info) {
        frame = new JFrame(info.getName() + " (Client)");
        this.info = info;
    }
    public void run() {
        frame.setContentPane(new ClientPanel(info).getMainPanel());
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }
}

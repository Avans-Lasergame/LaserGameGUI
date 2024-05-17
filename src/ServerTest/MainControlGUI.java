package ServerTest;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class MainControlGUI extends JFrame {

    private JButton newConnectionButton;
    private int connectionCount = 0;

    public MainControlGUI() {
        setTitle("Main Control GUI");
        setSize(300, 100);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        newConnectionButton = new JButton("New Connection");

        newConnectionButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                connectionCount++;
                SwingUtilities.invokeLater(() -> {
                    TestClientGUI clientGUI = new TestClientGUI("Client " + connectionCount);
                    clientGUI.setVisible(true);
                });
            }
        });

        setLayout(new FlowLayout());
        add(newConnectionButton);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            MainControlGUI mainControlGUI = new MainControlGUI();
            mainControlGUI.setVisible(true);
        });
    }
}

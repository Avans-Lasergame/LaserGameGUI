package ServerTest;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class TestClientGUI extends JFrame {
    private Socket socket;
    private PrintWriter out;
    private BufferedReader in;

    private JTextArea displayArea;
    private JTextField inputField;
    private JButton sendButton;
    private JButton connectButton;

    public TestClientGUI(String title) {
        // Set up the GUI
        setTitle(title);
        setSize(400, 300);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE); // Close only the current window

        displayArea = new JTextArea();
        displayArea.setEditable(false);
        JScrollPane scrollPane = new JScrollPane(displayArea);

        inputField = new JTextField(20);
        sendButton = new JButton("Send");
        connectButton = new JButton("Connect");

        JPanel panel = new JPanel();
        panel.add(inputField);
        panel.add(sendButton);
        panel.add(connectButton);

        add(scrollPane, BorderLayout.CENTER);
        add(panel, BorderLayout.SOUTH);

        // Add action listeners
        sendButton.addActionListener(new SendButtonListener());
        connectButton.addActionListener(new ConnectButtonListener());
    }

    private class ConnectButtonListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            try {
                socket = new Socket("localhost", 8888); // Adjust the IP and port as needed
                out = new PrintWriter(socket.getOutputStream(), true);
                in = new BufferedReader(new InputStreamReader(socket.getInputStream()));

                // Start a new thread to listen for server messages
                new Thread(new ServerListener()).start();

                displayArea.append("Connected to the server.\n");
            } catch (IOException ex) {
                displayArea.append("Error connecting to the server: " + ex.getMessage() + "\n");
            }
        }
    }

    private class SendButtonListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            String message = inputField.getText();
            if (message != null && !message.isEmpty()) {
                out.println(message);
                inputField.setText("");
            }
        }
    }

    private class ServerListener implements Runnable {
        @Override
        public void run() {
            try {
                String serverMessage;
                while ((serverMessage = in.readLine()) != null) {
                    displayArea.append("Server: " + serverMessage + "\n");
                }
            } catch (IOException ex) {
                displayArea.append("Error reading from server: " + ex.getMessage() + "\n");
            }
        }
    }
}

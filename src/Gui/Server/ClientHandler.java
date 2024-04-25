package Gui.Server;

import Gui.GUI;
import Objects.Gun;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

class ClientHandler implements Runnable {
    private Socket clientSocket;

    public ClientHandler(Socket clientSocket) {
        this.clientSocket = clientSocket;
    }

    public void run() {
        ServerGUI.log("New Client thread");
        try {
            BufferedReader in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
            PrintWriter out = new PrintWriter(clientSocket.getOutputStream(), true);

            String inputLine;
            while ((inputLine = in.readLine()) != null) {
                ServerGUI.log(clientSocket.getInetAddress().getHostName() + ": " + inputLine);
                int id = 0;
                if (inputLine.contains(":")) {
                    if (inputLine.split(":", 2)[0].equalsIgnoreCase("ID")) {
                        id = Integer.parseInt(inputLine.split(":", 2)[1]);
                        Gun gun = new Gun(id);
                        GUI.getGuns().add(gun);
                    } else if (inputLine.split(":", 2)[0].equalsIgnoreCase("hit")) {

                    }
                }
//                out.println(inputLine);
            }

            out.close();
            in.close();
            clientSocket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
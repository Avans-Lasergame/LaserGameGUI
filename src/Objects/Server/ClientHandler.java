package Objects.Server;

import Gui.GUI;
import Gui.ServerGUI;
import Objects.Callbacks.GunCallback;
import Objects.Gun;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

class ClientHandler implements Runnable, GunCallback {
    private Socket clientSocket;
    private Gun gun = null;
    private BufferedReader in;
    private PrintWriter out;

    public ClientHandler(Socket clientSocket) {
        this.clientSocket = clientSocket;
    }

    public void run() {
        ServerGUI.log("New Client thread");
        try {
            in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
            out = new PrintWriter(clientSocket.getOutputStream(), true);

            String inputLine;
//            while ((inputLine = in.readLine()) != null) {
            while (clientSocket.isConnected()) {
                if ((inputLine = in.readLine()) != null) {

                    ServerGUI.log(clientSocket.getInetAddress().getHostName() + ": " + inputLine);
                    int id = 0;
                    if (inputLine.contains(":")) {
                        if (inputLine.split(":", 2)[0].equalsIgnoreCase("ID")) {
                            id = Integer.parseInt(inputLine.split(":", 2)[1]);
                            gun = new Gun(id);
                            GUI.getGuns().add(gun);
                            gun.setCallback(this);
                        } else if (inputLine.split(":", 2)[0].equalsIgnoreCase("hit")) {
                            if(gun != null) {
                                gun.isHit();
                            }
                        }
                    }
                }
//                out.println(inputLine);
            }
            System.out.println("stop");

            out.close();
            in.close();
            clientSocket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void changeLED(int r, int g, int b) {
        if (clientSocket.isConnected()) {
            out.println("led,"+","+r+","+g+","+b+".");
        }

    }
}
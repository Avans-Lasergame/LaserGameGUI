package Server;

import Gui.GUI;
import Gui.ServerGUI;
import Objects.Interfaces.GunCallback;
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
        // on run log the new thread
        ServerGUI.log("New Client thread");
        try {
            in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
            out = new PrintWriter(clientSocket.getOutputStream(), true);

            String inputLine;
//            while ((inputLine = in.readLine()) != null) {
            while (clientSocket.isConnected()) { // only run this if we are connected
                if ((inputLine = in.readLine()) != null) {
                    ServerGUI.log(clientSocket.getInetAddress().getHostName() + ": " + inputLine);// log the connection data
                    int id = 0;
                    if (inputLine.contains(":")) {
                        if (inputLine.split(":", 2)[0].equalsIgnoreCase("ID")) {// extract the correct data form the client
                            id = Integer.parseInt(inputLine.split(":", 2)[1]);
                            gun = new Gun(id); // create a new gun with the received id
//                            System.out.println("new gun:" + id);
                            if (!GUI.getGuns().isEmpty()) { // check if there are already guns in the GUI
                                boolean found = false;
                                for (Gun gun : GUI.getGuns()) {
                                    if (gun.getID() == this.gun.getID()) {// check if there is already a gun with the same id
                                        this.gun = gun; // make the gun object in this thread the same as the already existing object
                                        found = true;
//                                        System.out.println("found");
                                        break;
                                    }
                                }
                                if (!found) {
//                                    System.out.println("not found");
                                    GUI.getGuns().add(gun); // if no gun is found with the same id then add one
                                }
                            }else {
//                                System.out.println("empty");
                                GUI.getGuns().add(gun); // if the list is empty add this gun
                            }
                            ServerGUI.updateGunsContainer();
                            gun.setCallback(this);
                        } else if (inputLine.split(":", 2)[0].equalsIgnoreCase("hitby")) { // extract the hit data
                            if (gun != null) {
                                gun.isHit();// when hit call the function ishit for this gun
                            }
                        }
                    }
                }
//                out.println(inputLine);
            }
//            System.out.println("stop");

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
            out.println("led," + r + "," + g + "," + b); // send the command to change the led color
        }
    }

    @Override
    public void blink(double i, int r, int g, int b) { // send the command to blink the led
        if (clientSocket.isConnected()) {
            out.println("ledblink," + i + "," + r + "," + g + "," + b);
        }
    }

    @Override
    public void rawCommand(String msg) { // send raw command
        if(clientSocket.isConnected()){
            out.println(msg);
        }
    }

    @Override
    public void stop() { // stop the thread and log this
//        System.out.println("stop");
        ServerGUI.log("Stopped client for gun"+this.gun.getID());
        try {
            out.close();
            in.close();
            clientSocket.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
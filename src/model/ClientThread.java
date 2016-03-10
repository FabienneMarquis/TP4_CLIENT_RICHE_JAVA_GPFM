package model;

import javafx.scene.control.Alert;

import java.io.*;
import java.net.Socket;
import java.util.List;

/**
 * Created by mathildemarquis on 16-03-09.
 */

public class ClientThread extends Thread {
    private Socket socket;
    private BufferedReader br;
    private BufferedWriter bw;

    public ClientThread(Socket socket) {
        this.socket = socket;
        try {
            bw = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
            br = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            System.out.println("hello?");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void run() {
        while (true) {
            String line = null;
            try {
                line = br.readLine();
                if (line != null) {
                    System.out.println(line);
                    String[] split = line.split("@");
                    String controller = split[0];
                    String actionAndArg = split[1];
                    split = actionAndArg.split("\\?");
                    String action = split[0];
                    String arg = split[1];
                    switch (controller) {
                        case "client": {
                            switch (action) {
                                case "select": {
                                    //client@select te retourne client@select?adresse du client 1;adresse du client 2;...
                                    for (String args : arg.split(";")) {
                                        System.out.println(args.replace("�", "e"));
                                       String a = args.replace("�", "e");
                                        Context.getInstance().addClient(a);
                                    }
                                    break;
                                }
                            }
                            break;
                        }
                        case "route": {
                            switch (action) {
                                case "delete": {
                                    System.out.println("la route optimal a été effacé");
                                    break;
                                }
                                case "update": {
                                    System.out.println("update de la base de donnée");
                                    break;
                                }
                            }
                            break;
                        }
                    }
                }

            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public void send(String msg) {
        try {
            System.out.println(msg);
            bw.write(msg + "\n");
            bw.flush();
        } catch (IOException e) {
            e.printStackTrace();
            close();
        }
    }

    public void close() {

        try {
            socket.close();
        } catch (IOException e1) {
            // e1.printStackTrace();
        }
        try {
            br.close();
        } catch (IOException e1) {
            // e1.printStackTrace();
        }
        try {
            bw.close();
        } catch (IOException e1) {
            // e1.printStackTrace();
        }
    }

}

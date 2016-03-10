package model;

import javafx.scene.control.Alert;

import java.io.*;
import java.net.Socket;
import java.util.List;

/**
 * Created by mathildemarquis on 16-03-09.
 */
public class Connection {
    class ClientThread extends Thread {
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
                    if(line!=null){
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
                                       for (String args : arg.split(";")){
                                           Context.getInstance().addClient(args);
                                       }
                                        break;
                                    }
                                }
                                break;
                            }
                            case "route": {
                                switch (action) {
                                    case "delete": {
                                        Alert alert = new Alert(Alert.AlertType.INFORMATION);
                                        alert.setTitle("Supression");
                                        alert.setContentText("Les routes ont été supprimer de la base de données");
                                        alert.show();
                                        break;
                                    }
                                    case "update":{
                                        Alert alert = new Alert(Alert.AlertType.INFORMATION);
                                        alert.setTitle("Update");
                                        alert.setContentText("Les routes ont été mise à jour de la base de données");
                                        alert.show();
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

        private void close() {

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
}

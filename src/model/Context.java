package model;

import javafx.collections.FXCollections;
import javafx.collections.ObservableArray;
import javafx.collections.ObservableList;

import javax.net.ssl.HttpsURLConnection;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.Socket;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Observable;


/**
 * Created by 1494778 on 2016-03-07.
 */
public class Context extends Observable {

    private String ip;
    private ClientThread connection;
    private static Context context;
    private Client client;
    private ObservableList<Client> clients;
    private Client routeClient[];
    private ArrayList<Client> routeOptimal;
    private Client origin;
    private String ordre;


    private Context() {
        clients = FXCollections.observableArrayList(new ArrayList<>());
        routeOptimal = new ArrayList<>();
    }

    public static Context getInstance() {
        if (context == null) {
            context = new Context();
        }
        return context;
    }

    public ClientThread getConnection() {
        return connection;
    }

    public Client getOrigin() {
        return origin;
    }

    public void setOrigin(Client origin) {
        this.origin = origin;
    }

    public ArrayList<Client> getRouteOptimal() {
        return routeOptimal;
    }

    public void setRouteOptimal(ArrayList<Client> routeOptimal) {
        this.routeOptimal = routeOptimal;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public ObservableList<Client> getClients() {
        return clients;
    }

    public Client[] getRouteClient() {
        return routeClient;
    }

    public void setRouteClient(Client[] routeClient) {
        this.routeClient = routeClient;
    }

    public void setClients(ObservableList<Client> clients) {
        this.clients = clients;
    }

    public Client getClient() {
        return client;
    }

    public void setClient(Client client) {
        this.client = client;
    }

    public void addClient(String string) {
        clients.add(new Client(string));
    }

    public void envoiRoute(String adress) {
        if (routeClient != null) {
            System.out.println("route@update?" + origin + ";" + adress+"test test patate");
            connection.send("route@update?" + origin + ";" + adress);

        }
    }

    private String renvoitString(){
        String route="";
        for (Client adress : routeOptimal ){
            route+=adress+";";
        }
        return route;
    }

    public void deleteRoute() {
        connection.send("route@delete");
    }

    public void clientsSelect() {
        connection.send("client@select");
    }

    public void connectionServeur(Socket socket) {
        connection = new ClientThread(socket);
        connection.start();
    }

    public void envoiGoogleRouteOptimal() {
        routeOptimal.clear();
//https://maps.googleapis.com/maps/api/directions/json?origin=Adelaide,SA&destination=Adelaide,SA&waypoints=optimize:true|Barossa+Valley,SA|Clare,SA|Connawarra,SA|McLaren+Vale,SA&key=YOUR_API_KEY
        String waypoint = "";
        for (Client adress : routeClient) {
            waypoint += "|" + adress.getAddress().replace(" ", "+") + ",qc";
        }
        System.out.println(waypoint);
        try {
            URL url = new URL("https://maps.googleapis.com/maps/api/directions/json?origin=" + origin.getAddress().replace(" ", "+")
                    + "&destination=" + origin.getAddress().replace(" ", "+")
                    + "&waypoints=optimize:true" + waypoint);
            System.out.println(url.toString());
            HttpsURLConnection google = (HttpsURLConnection) url.openConnection();
            google.connect();
            BufferedReader br = new BufferedReader(new InputStreamReader(google.getInputStream()));
            while (true) {
                String line = br.readLine();
                if (line != null) {
                    System.out.println(line);
                    String search[] = line.split(":");
                    switch (search[0]) {
                        case "         \"waypoint_order\" ": {
                            ordre = search[1].replace("[", "").replace("]", "").replace(" ", "");
                            String a[] = ordre.split(",");
                            for (String b : a) {
                                routeOptimal.add(routeClient[Integer.parseInt(b)]);
                            }
                            setChanged();
                            notifyObservers();
                            envoiRoute(renvoitString());
                            break;
                        }
                    }
                } else {
                    break;
                }
            }
            System.out.println(ordre);
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

}

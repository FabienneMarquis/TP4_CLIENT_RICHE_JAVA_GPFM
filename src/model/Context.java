package model;

import javafx.collections.FXCollections;
import javafx.collections.ObservableArray;
import javafx.collections.ObservableList;

import java.net.Socket;
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
    private List<Client> routeClient;



    private Context() {
        clients = FXCollections.observableArrayList(new ArrayList<>());
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

    public void setIp(String ip) {
        this.ip = ip;
    }

    public ObservableList<Client> getClients() {
        return clients;
    }

    public List<Client> getRouteClient() {
        return routeClient;
    }

    public void setRouteClient(List<Client> routeClient) {
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

    public void addClient(String string){
        clients.add(new Client(string));
    }

 public void envoiRoute(String adress){
     if(routeClient!=null) {
         String msg= "";
         for(Client client : routeClient){
             msg+= client.getAddress()+";";
         }
         connection.send("route@update?"+adress+";"+msg);
     }
 }

    public void deleteRoute(){
        connection.send("route@delete");
    }

    public void clientsSelect(){
        connection.send("client@select");
    }

    public void connectionServeur(Socket socket){
         connection= new ClientThread(socket);
        connection.start();
    }

}

package controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.SelectionMode;
import javafx.scene.control.TextField;
import model.Client;
import model.Context;

import java.net.URL;
import java.util.List;
import java.util.Observable;
import java.util.Observer;
import java.util.ResourceBundle;

public class Controller implements Initializable, Observer {

    @FXML
    private ListView<Client> listClient;

    @FXML
    private TextField noCivique;

    @FXML
    private TextField rue;

    @FXML
    private TextField ville;

    @FXML
    private TextField codePostal;

    @FXML
    private Button viderBD;

    @FXML
    private Button routeOptimal;

    @FXML
    private Button genererClient;

    @FXML
    private Button quit;

    List<Client> selected;

    @FXML
    void cleanBD(ActionEvent event) {

    }

    @FXML
    void getClients(ActionEvent event) {

    }

    @FXML
    void getRoute(ActionEvent event) {
        String origin = noCivique.getText() + " " + rue.getText() + " " + ville.getText() + " " + codePostal.getText();
        Context.getInstance().envoiRoute(origin);
    }

    @FXML
    void quit(ActionEvent event) {


    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        listClient.setItems(Context.getInstance().getClients());
        listClient.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
        listClient.getSelectionModel().selectedItemProperty().addListener(
                (observable, oldValue, newValue) -> {
                    selected.add(listClient.getSelectionModel().getSelectedItem());
                    Context.getInstance().setRouteClient(selected);
                }
        );

    }

    @Override
    public void update(Observable o, Object arg) {
        listClient.setItems(Context.getInstance().getClients());
    }
}


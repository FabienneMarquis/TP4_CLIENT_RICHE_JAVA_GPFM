package controller;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.text.Text;
import model.Client;
import model.Context;

import java.io.IOException;
import java.net.Socket;
import java.net.URL;
import java.util.*;

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
    private Text routeOp;

    @FXML
    private Button quit;

    Client selected[];

    @FXML
    void cleanBD(ActionEvent event) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Suppression");
        alert.setHeaderText("Suppression des routes");
        alert.setContentText("Voulez-vous supprimer les routes enregistrées dans la base de données?");
        Optional<ButtonType> result = alert.showAndWait();

        if (result.get() == ButtonType.OK) {
            Context.getInstance().deleteRoute();
        }
    }

    @FXML
    void getClients(ActionEvent event) {
        Context.getInstance().clientsSelect();
        genererClient.setDisable(true);
    }

    @FXML
    void getRoute(ActionEvent event) {

        if(noCivique.getText().isEmpty() || rue.getText().isEmpty() || ville.getText().isEmpty() || codePostal.getText().isEmpty()){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Erreur");
            alert.setContentText("Des sections de l'origine n'ont pas été complétées ");
            alert.show();
        }else {
            //listClient.getSelectionModel().getSelectedItems().toArray(selected);
            //selected= (Client[])listClient.getSelectionModel().getSelectedItems().toArray();
            selected = new Client[listClient.getSelectionModel().getSelectedItems().size()];
            for(int i = 0; i<listClient.getSelectionModel().getSelectedItems().size();i++){
                selected[i] = listClient.getSelectionModel().getSelectedItems().get(i);
            }
            Context.getInstance().setRouteClient(selected);
            Context.getInstance().setOrigin(new Client(noCivique.getText() + " " + rue.getText() + " " + ville.getText() + " " + codePostal.getText()));
            Context.getInstance().envoiGoogleRouteOptimal();
       }
    }

    @FXML
    void quit(ActionEvent event) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Avertissement");
        alert.setHeaderText("Quitter?");
        alert.setContentText("Voulez-vous quitter le programme ? ");
        Optional<ButtonType> result = alert.showAndWait();

        if (result.get() == ButtonType.OK) {
          //  Context.getInstance().getConnection().close();
            Platform.exit();
            System.exit(0);
        } else event.consume();

    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        Context.getInstance().addObserver(this);
        String ip="";
        TextInputDialog dialog =new TextInputDialog(ip);
        dialog.setTitle("Configuration IP serveur");
        dialog.setHeaderText("Aucune IP serveur configurer pour l'application");
        dialog.setContentText("Veuillez entrer l'IP:");
        Optional<String> result = dialog.showAndWait();
        if (result.isPresent()) {
            ip = result.get();
            System.out.println("IP: " + ip);
            Context.getInstance().setIp(ip);
            Socket socket = null;
            try {
                socket = new Socket(ip , 8888);
                Context.getInstance().connectionServeur(socket);
            } catch (IOException e) {
                e.printStackTrace();
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Erreur");
                alert.setContentText("L'adresse ip est incorrect");
                alert.show();
            }

        }
        listClient.setItems(Context.getInstance().getClients());
        listClient.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);


    }

    @Override
    public void update(Observable o, Object arg) {
        listClient.setItems(Context.getInstance().getClients());
        routeOp.setText(Context.getInstance().getRouteOptimal().toString());
    }
}


package controller;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import model.Client;
import model.Context;

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
    private Button quit;

    List<Client> selected;

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
    }

    @FXML
    void getRoute(ActionEvent event) {
        if(noCivique.getText().isEmpty() || rue.getText().isEmpty() || ville.getText().isEmpty() || codePostal.getText().isEmpty()){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Erreur");
            alert.setContentText("Des sections de l'origine n'ont pas été complétées ");
        }else Context.getInstance().envoiRoute(noCivique.getText() + " " + rue.getText() + " " + ville.getText() + " " + codePostal.getText());


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
            //arrrrrgggggg connecter au serveur blublublbu
        }
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


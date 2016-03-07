package controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;

import java.net.URL;
import java.util.ResourceBundle;

public class Controller implements Initializable{

    @FXML
    private ListView<?> listClient;

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

    @FXML
    void cleanBD(ActionEvent event) {

    }

    @FXML
    void getClients(ActionEvent event) {

    }

    @FXML
    void getRoute(ActionEvent event) {

    }

    @FXML
    void quit(ActionEvent event) {

    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }
}


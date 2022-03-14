package com.unah.planners.controller;

import com.unah.planners.model.RoundRobinModel;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;

import java.net.URL;
import java.util.ResourceBundle;

public class RoundRobinController implements Initializable {
    @FXML
    private TableView<RoundRobinModel> RoundRobinTable;

    @FXML
    private TableColumn<RoundRobinModel, Integer> turnaroundTime;

    @FXML
    private TableColumn<RoundRobinModel, Integer> burstTime;

    @FXML
    private ComboBox<Integer> cbxQuantum;

    @FXML
    private ListView<Integer> lsvProcessBurstTime;

    @FXML
    private ListView<Integer> lsvProcess;

    @FXML
    private TableColumn<RoundRobinModel, Integer> processID;

    @FXML
    private TextField txfAverageTurnaroundTime;

    @FXML
    private TextField txfAverageWaitingTime;

    @FXML
    private TextField txfProcess;

    @FXML
    private TextField txfProcessBurst;

    @FXML
    private TableColumn<RoundRobinModel, Integer> waitingTime;

    ObservableList<RoundRobinModel> list = FXCollections.observableArrayList(
            new RoundRobinModel(1, 1, 1, 1)
    );

    Alert alert = new Alert(Alert.AlertType.NONE);

    @FXML
    void onAddProcess(ActionEvent event) {
        try {
            lsvProcess.getItems().add(Integer.parseInt(txfProcess.getText()));
            lsvProcessBurstTime.getItems().add(Integer.parseInt(txfProcessBurst.getText()));
        } catch (Exception e) {
            alert.setAlertType(Alert.AlertType.WARNING);
            alert.setContentText("Enter a number");
            alert.show();
        }
        txfProcess.setText("");
        txfProcessBurst.setText("");
    }

    @FXML
    void onComputeAlgorithm(ActionEvent event) {

        for (int index = 0; index <= lsvProcessBurstTime.getItems().size() - 1; index++){
            lsvProcessBurstTime.getItems().get(index);
        }

        list.add(new RoundRobinModel(1,1,1,1));



        RoundRobinTable.setItems(list);

    }

    @FXML
    void onClear(ActionEvent event) {
        try {
            for (int index = 0; index <= lsvProcessBurstTime.getItems().size(); index++) {
                lsvProcess.getItems().remove(index);
                lsvProcessBurstTime.getItems().remove(index);
            }

            RoundRobinTable.getItems().clear();
        } catch (Exception e){
            alert.setAlertType(Alert.AlertType.INFORMATION);
            alert.setContentText("Enter values to clean");
            alert.show();
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        cbxQuantum.getItems().addAll(1, 2, 5, 10);

        processID.setCellValueFactory(new PropertyValueFactory<>("processID"));
        burstTime.setCellValueFactory(new PropertyValueFactory<>("burstTime"));
        turnaroundTime.setCellValueFactory(new PropertyValueFactory<>("turnaroundTime"));
        waitingTime.setCellValueFactory(new PropertyValueFactory<>("waitingTime"));
    }

}

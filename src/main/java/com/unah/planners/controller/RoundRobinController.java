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
import java.util.ArrayList;

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

    ObservableList<RoundRobinModel> list = FXCollections.observableArrayList();

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
        ArrayList<Integer> processes = new ArrayList<>();
        ArrayList<Integer> burstTime = new ArrayList<>();

        for (int index = 0; index <= lsvProcessBurstTime.getItems().size() - 1; index++) {
            processes.add(lsvProcess.getItems().get(index));
            burstTime.add(lsvProcessBurstTime.getItems().get(index));
        }

        findAverageTime(processes, burstTime, cbxQuantum.getValue());
        RoundRobinTable.setItems(list);
    }

    private void findAverageTime(ArrayList<Integer> processes, ArrayList<Integer> burstTime, Integer quantum) {
        int averageWaitingTime = 0, averageTurnaroundTime = 0;

        ArrayList<Integer> waitingTime = findWaitingTime(processes, burstTime, quantum);
        ArrayList<Integer> turnAroundTime = findTurnAroundTime(processes, waitingTime);

        for (int index = 0; index < processes.size(); index++) {
            list.add(new RoundRobinModel(processes.get(index), burstTime.get(index), waitingTime.get(index), turnAroundTime.get(index)));
            averageWaitingTime += waitingTime.get(index);
            averageTurnaroundTime += turnAroundTime.get(index);
        }

        txfAverageWaitingTime.setText(String.valueOf((float) averageWaitingTime / (float) processes.size()));
        txfAverageTurnaroundTime.setText(String.valueOf((float) averageTurnaroundTime / (float) processes.size()));
    }

    private ArrayList<Integer> findTurnAroundTime(ArrayList<Integer> processes, ArrayList<Integer> waitingTime) {
        ArrayList<Integer> turnAroundTime = new ArrayList<>();
        for (int index = 0; index < processes.size(); index++) {
            turnAroundTime.add(processes.get(index) + waitingTime.get(index));
        }
        return turnAroundTime;
    }

    private ArrayList<Integer> findWaitingTime(ArrayList<Integer> processes, ArrayList<Integer> burstTime, Integer quantum) {
        //we assume that the arrival times are 0, so the lap and finish times are the same.
        int time = 0;
        ArrayList<Integer> waitingTime = new ArrayList<>();

        ArrayList<Integer> burstTimeTemp = new ArrayList<>(burstTime);

        while (true) {
            boolean pendingProcess = true;

            for (int index = 0; index < processes.size(); index++) {

                if (burstTimeTemp.get(index) > 0) {
                    pendingProcess = false;

                    if (burstTimeTemp.get(index) > quantum) {
                        time += quantum;

                        burstTimeTemp.add(index, burstTimeTemp.get(index) - quantum);
                    }

                    if (burstTimeTemp.get(index) <= quantum) {
                        time = time + burstTimeTemp.get(index);

                        waitingTime.add(time - burstTimeTemp.get(index));

                        burstTimeTemp.add(index, 0);
                    }
                }
            }

            if (pendingProcess)
                return waitingTime;
        }
    }

    @FXML
    void onClear(ActionEvent event) {
        try {
            lsvProcess.getItems().clear();
            lsvProcessBurstTime.getItems().clear();
            RoundRobinTable.getItems().clear();
            txfProcess.setText("");
            txfProcessBurst.setText("");
            txfAverageWaitingTime.setText("");
            txfAverageTurnaroundTime.setText("");
        } catch (Exception e) {
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

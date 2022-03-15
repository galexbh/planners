package com.unah.planners.controller;

import com.unah.planners.process.SRTProcess;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

import java.net.URL;
import java.util.ResourceBundle;

public class HRRNController implements Initializable {
    int quantum = 20;
    @FXML
    private Pane proccessTable;
    @FXML
    private TextField processIdentifier;
    @FXML
    private TextField arrivalTime;
    @FXML
    private TextField serviceTime;
    @FXML
    private Pane namesPane;
    @FXML
    private Pane columnNumbers;
    @FXML
    private Button applyPlannerButton;

    private ObservableList<SRTProcess> processes = FXCollections.observableArrayList();
    private ObservableList<SRTProcess> waitingProcesses = FXCollections.observableArrayList();

    private SRTProcess runningProcess;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        applyPlannerButton.setDisable(true);
        double tempX = 0.0;
        for (int i = 0; i < quantum; i++) {
            Label numColumn = new Label(String.valueOf(i + 1));
            numColumn.setAlignment(Pos.CENTER);
            numColumn.setPrefWidth(proccessTable.getPrefWidth() / 20);
            numColumn.setPrefHeight(columnNumbers.getPrefHeight());
            tempX = i * proccessTable.getPrefWidth() / 20;
            numColumn.setLayoutX(tempX);
            columnNumbers.getChildren().add(numColumn);
        }
        columnNumbers.setVisible(false);
    }

    @FXML
    private void addProcess() {
        proccessTable.getChildren().add(createProcess(processIdentifier.getText()));
        SRTProcess tempProcess = new SRTProcess();
        tempProcess.setProcessIdentifier(processIdentifier.getText());
        tempProcess.setArrivalTime(Integer.parseInt(arrivalTime.getText()) - 1);
        tempProcess.setServiceTime(Integer.parseInt(serviceTime.getText()));
        tempProcess.setPosition(processes.size());
        processes.add(tempProcess);


        if (!proccessTable.getChildren().isEmpty()) {
            columnNumbers.setVisible(true);
            applyPlannerButton.setDisable(false);

        }
        processIdentifier.setText("");
        arrivalTime.setText("");
        serviceTime.setText("");
    }


    private Pane createProcess(String name) {
        Pane tempProcessPane = new Pane();
        tempProcessPane.setPrefHeight(50);
        tempProcessPane.setPrefWidth(proccessTable.getPrefWidth());
        if (proccessTable.getChildren().isEmpty()) {
            tempProcessPane.setLayoutY(0);
        } else {
            tempProcessPane.setLayoutY(proccessTable.getChildren().get(proccessTable.getChildren().size() - 1).getLayoutY() + 50);

        }
        double aux = 0.0;
        for (int i = 0; i < quantum; i++) {
            Rectangle tempRectangle = new Rectangle();
            tempRectangle.setWidth(proccessTable.getPrefWidth() / 20);
            tempRectangle.setHeight(tempProcessPane.getPrefHeight());
            tempRectangle.setFill(Color.AZURE);
            tempRectangle.setStroke(Color.LIGHTGREY);
            aux = i * proccessTable.getPrefWidth() / 20;
            tempRectangle.setX(aux);
            tempProcessPane.getChildren().add(tempRectangle);


        }
        addNameProcess(name, tempProcessPane);
        return tempProcessPane;

    }

    private void addNameProcess(String name, Pane processPane) {
        Label nameProcess = new Label("Proceso " + name + " =>");
        nameProcess.setAlignment(Pos.CENTER);
        nameProcess.setPrefHeight(processPane.getPrefHeight());
        nameProcess.setPrefWidth(namesPane.getPrefWidth());
        nameProcess.setLayoutY(processPane.getLayoutY() + processPane.getPrefHeight() - nameProcess.getPrefHeight());

        namesPane.getChildren().add(nameProcess);
    }

    @FXML
    private void deleteLastProcess() {
        if (!proccessTable.getChildren().isEmpty()) {
            proccessTable.getChildren().remove(proccessTable.getChildren().size() - 1);
            namesPane.getChildren().remove(namesPane.getChildren().size() - 1);
            processes.remove(processes.size() - 1);
        }
        if (proccessTable.getChildren().isEmpty()) {
            columnNumbers.setVisible(false);
            applyPlannerButton.setDisable(true);
        }

    }

    @FXML
    private void deleteAllProcess() {
        proccessTable.getChildren().remove(0, proccessTable.getChildren().size());
        namesPane.getChildren().remove(0, namesPane.getChildren().size());
        columnNumbers.setVisible(false);
        applyPlannerButton.setDisable(true);
        processes = FXCollections.observableArrayList();
        processIdentifier.setText("");
        arrivalTime.setText("");
        serviceTime.setText("");
    }

    @FXML
    private void applyPlanner() {

        runningProcess = getFirtProcess();
        processes.remove(runningProcess);
        for (int i = runningProcess.getArrivalTime(); i < quantum; i++) {
            if (!processes.isEmpty()) {
                SRTProcess tempProcess = checkProcessStart(i);
                if (tempProcess != null) {
                    if (runningProcess.getServiceTime() == 0) {
                        runningProcess = tempProcess;
                    } else {
                        waitingProcesses.add(tempProcess);
                    }
                    processes.remove(tempProcess);
                }
            }

            Pane tempPane;
            if (runningProcess.getServiceTime() != 0) {
                tempPane = (Pane) proccessTable.getChildren().get(runningProcess.getPosition());
                Rectangle tempTriangle = (Rectangle) tempPane.getChildren().get(i);
                tempTriangle.setFill(Color.LIGHTGREEN);
                tempTriangle.setStroke(Color.WHITE);
                runningProcess.setServiceTime(runningProcess.getServiceTime() - 1);
            } else {
                if (!waitingProcesses.isEmpty()) {
                    if (waitingProcesses.size() == 1) {
                        runningProcess = waitingProcesses.get(0);
                        waitingProcesses.remove(runningProcess);
                    } else {
                        runningProcess = getLowerResponseRate(i);
                    }

                    tempPane = (Pane) proccessTable.getChildren().get(runningProcess.getPosition());
                    waitingProcesses.remove(runningProcess);
                    Rectangle tempTriangle = (Rectangle) tempPane.getChildren().get(i);
                    tempTriangle.setFill(Color.LIGHTGREEN);
                    tempTriangle.setStroke(Color.WHITE);
                    runningProcess.setServiceTime(runningProcess.getServiceTime() - 1);
                }
            }
        }
    }

    private SRTProcess getFirtProcess() {
        SRTProcess firtProcessList;
        int smallerNumber = processes.get(0).getArrivalTime();
        int posSmallerNumber = 0;
        for (int i = 1; i < processes.size(); i++) {

            if (processes.get(i).getArrivalTime() < smallerNumber) {
                smallerNumber = processes.get(i).getArrivalTime();
                posSmallerNumber = i;
            }
        }
        firtProcessList = processes.get(posSmallerNumber);
        return firtProcessList;

    }

    private SRTProcess checkProcessStart(int position) {
        SRTProcess tempProcess = null;
        for (SRTProcess process : processes) {
            if (process.getArrivalTime() == position) {
                tempProcess = process;
            }
        }
        return tempProcess;
    }

    private SRTProcess getLowerResponseRate(int position) {
        SRTProcess bestProcess;
        double smallerNumber = (((waitingProcesses.get(0).getArrivalTime() - (double) position) + waitingProcesses.get(0).getServiceTime()) / waitingProcesses.get(0).getServiceTime());
        int posSmallerNumber = 0;
        for (int i = 1; i < waitingProcesses.size(); i++) {

            if ((((waitingProcesses.get(i).getArrivalTime() - (double) position) + waitingProcesses.get(i).getServiceTime()) / waitingProcesses.get(i).getServiceTime()) < smallerNumber) {
                smallerNumber = (((waitingProcesses.get(i).getArrivalTime() - (double) position) + waitingProcesses.get(i).getServiceTime()) / waitingProcesses.get(i).getServiceTime());
                posSmallerNumber = i;
            }
        }
        bestProcess = waitingProcesses.get(posSmallerNumber);
        return bestProcess;
    }

}

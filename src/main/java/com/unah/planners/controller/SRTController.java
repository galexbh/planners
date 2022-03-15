package com.unah.planners.controller;

import com.unah.planners.process.ProcessSRT;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

import java.net.URL;
import java.util.ResourceBundle;

public class SRTController implements Initializable {
    int quantum = 20;
    @FXML
    private Pane proccessTable;
    @FXML
    private TextField processIdentifierObtained;
    @FXML
    private TextField arrivalTimeObtained;
    @FXML
    private TextField serviceTimeObtained;
    @FXML
    private Pane namesPane;
    @FXML
    private Pane columnNumbers;
    @FXML
    private Button applyPlannerButton;

    private ObservableList<ProcessSRT> processes = FXCollections.observableArrayList();
    private ObservableList<ProcessSRT> waitingProcesses = FXCollections.observableArrayList();
    private ProcessSRT runningProcess;

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
        proccessTable.getChildren().add(createProcess(processIdentifierObtained.getText()));
        ProcessSRT tempProcess = new ProcessSRT();
        tempProcess.setProcessIdentifier(processIdentifierObtained.getText());
        tempProcess.setArrivalTime(Integer.parseInt(arrivalTimeObtained.getText()) - 1);
        tempProcess.setServiceTime(Integer.parseInt(serviceTimeObtained.getText()));
        tempProcess.setPosition(processes.size());
        processes.add(tempProcess);


        if (!proccessTable.getChildren().isEmpty()) {
            columnNumbers.setVisible(true);
            applyPlannerButton.setDisable(false);

        }
        processIdentifierObtained.setText("");
        arrivalTimeObtained.setText("");
        serviceTimeObtained.setText("");
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
        processIdentifierObtained.setText("");
        arrivalTimeObtained.setText("");
        serviceTimeObtained.setText("");
    }

    // Color = Color.LIGHTGREEN
    @FXML
    private void applyPlanner() {

        ObservableList<ProcessSRT> firtProcessList = getFirtProcess();
        if (firtProcessList.size() == 1) {
            runningProcess = firtProcessList.get(0);
        } else {
            runningProcess = compareServiceTime(firtProcessList);

        }
        processes.remove(runningProcess);
        for (int i = runningProcess.getArrivalTime(); i < quantum; i++) {
            if (!processes.isEmpty()) {
                ProcessSRT tempProcess = checkProcessStart(i);
                if (tempProcess != null) {
                    if (runningProcess.getServiceTime() == 0) {
                        runningProcess = tempProcess;
                    } else if (tempProcess.getServiceTime() < runningProcess.getServiceTime()) {
                        waitingProcesses.add(runningProcess);
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
                    runningProcess = waitingProcesses.get(0);
                    tempPane = (Pane) proccessTable.getChildren().get(runningProcess.getPosition());
                    waitingProcesses.remove(runningProcess);
                    Rectangle tempTriangle = (Rectangle) tempPane.getChildren().get(i);
                    tempTriangle.setFill(Color.LIGHTGREEN);
                    tempTriangle.setStroke(Color.WHITE);
                    runningProcess.setServiceTime(runningProcess.getServiceTime() - 1);
                }
            }
            orderProcesses();
        }

    }

    private ObservableList<ProcessSRT> getFirtProcess() {
        ObservableList<ProcessSRT> firtProcessList = FXCollections.observableArrayList();
        int smallerNumber = processes.get(0).getArrivalTime();
        int posSmallerNumber = 0;
        for (int i = 1; i < processes.size(); i++) {

            if (processes.get(i).getArrivalTime() < smallerNumber) {
                smallerNumber = processes.get(i).getArrivalTime();
                posSmallerNumber = i;
            }
        }
        firtProcessList.add(processes.get(posSmallerNumber));
        for (int i = 0; i < processes.size(); i++) {
            if (i == posSmallerNumber) {
                continue;
            }
            if (processes.get(posSmallerNumber).getArrivalTime() == processes.get(i).getArrivalTime()) {
                firtProcessList.add(processes.get(i));
            }

        }
        return firtProcessList;

    }

    private ProcessSRT compareServiceTime(ObservableList<ProcessSRT> processToCompare) {

        ProcessSRT shorterProcess = processes.get(processToCompare.get(0).getPosition());
        for (int i = 1; i < processToCompare.size(); i++) {
            if (processes.get(processToCompare.get(i).getPosition()).getServiceTime() < shorterProcess.getServiceTime()) {
                shorterProcess = processes.get(processToCompare.get(i).getPosition());
            }
        }
        return shorterProcess;

    }

    private ProcessSRT checkProcessStart(int position) {
        ProcessSRT tempProcess = null;
        for (ProcessSRT process : processes) {
            if (process.getArrivalTime() == position) {
                tempProcess = process;
            }
        }
        return tempProcess;
    }

    private void orderProcesses() {

        if (waitingProcesses.size() > 1) {
            for (int i = 0; i < waitingProcesses.size(); i++) {
                for (int j = 0; j < waitingProcesses.size(); j++) {
                    if (waitingProcesses.get(i).getServiceTime() < waitingProcesses.get(j).getServiceTime()) {
                        ProcessSRT tmpProcess = waitingProcesses.get(i);
                        waitingProcesses.set(i, waitingProcesses.get(j));
                        waitingProcesses.set(j, tmpProcess);

                    }
                }
            }
        }
    }


}



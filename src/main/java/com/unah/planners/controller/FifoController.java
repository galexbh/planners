package com.unah.planners.controller;

import java.net.URL;
import java.util.ResourceBundle;

import com.unah.planners.process.FifoProcess;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Accordion;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.ListView;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;

public class FifoController implements Initializable {

    @FXML
    private TableColumn<FifoProcess, String> name;

    @FXML
    private TableColumn<FifoProcess, Number> timeArrival;

    @FXML
    private TableColumn<FifoProcess, Number> timeService;

    @FXML
    private TableColumn<FifoProcess, Number> timeStay;

    @FXML
    private TableColumn<FifoProcess, Number> timeWaiting;

    @FXML
    private TableColumn<FifoProcess, Number> timeStart;

    @FXML
    private TableColumn<FifoProcess, Number> timeFinalize;

    @FXML
    private TableColumn<FifoProcess, Float> timeNormalized;

    @FXML
    private TextField txtName;

    @FXML
    private TextField txtQuantun;

    @FXML
    private TextField txtTimeService;

    @FXML
    private Button btnAlgorithm;

    @FXML
    private Button btnAddProcess;

    @FXML
    private Button btnNewProcesses;

    @FXML
    private ComboBox<Integer> cboTimeArrival;

    @FXML
    private TableView<FifoProcess> tbTable;

    @FXML
    private ListView<String> lstProcesses;


    private ObservableList<FifoProcess> contentTable;
    private ObservableList<Integer> contentComboBox;
    private ObservableList<String> processes;

    @Override
    public void initialize(URL arg0, ResourceBundle arg1) {

        instantiateObservableList();
        chargeComboBoxNumerico(contentComboBox, 3);
        lstProcesses.setItems(processes);
        cboTimeArrival.setItems(contentComboBox);
    }

    public void instantiateObservableList() {
        contentComboBox = FXCollections.observableArrayList();
        contentTable = FXCollections.observableArrayList();
        processes = FXCollections.observableArrayList();
    }

    @FXML
    public void addProcess() {
        contentTable.add(new FifoProcess(txtName.getText(),
                Integer.parseInt(txtQuantun.getText()),
                cboTimeArrival.getSelectionModel().getSelectedItem(),
                Integer.parseInt(txtTimeService.getText()
                ))
        );
        times();
        processes.add(txtName.getText());
        cleanRegistry();
        btnAlgorithm.setDisable(false);
    }

    public void cleanRegistry() {
        txtName.clear();
        txtQuantun.clear();
        cboTimeArrival.getSelectionModel().select(null);
        txtTimeService.clear();
    }

    public void times() {
        for (int i = 0; i < contentTable.size(); i++) {
            contentTable.get(i).timeStartFF(contentTable);
            contentTable.get(i).timeWaitingFF();
            contentTable.get(i).timeFinalizeFF();
            contentTable.get(i).timeStayFF();
            contentTable.get(i).timeNormalizedFF();
        }
    }

    @FXML
    public void applyAlgorithm() {
        times();
        btnAddProcess.setDisable(true);
        btnAlgorithm.setDisable(true);
        btnNewProcesses.setDisable(false);
        tbTable.setItems(contentTable);
        bindColumns();
    }

    public void bindColumns() {
        bindStringColumn(name, "name");
        bindNumberColumn(timeArrival, "timeArrival");
        bindNumberColumn(timeService, "timeService");
        bindNumberColumn(timeStay, "timeStay");
        bindNumberColumn(timeWaiting, "timeWaiting");
        bindNumberColumn(timeStart, "timeStart");
        bindNumberColumn(timeFinalize, "timeFinalize");
        bindFloatColumn(timeNormalized, "timeNormalized");
    }

    @FXML
    public void newProcesses() {
        ObservableList<FifoProcess> vacio = FXCollections.observableArrayList();
        tbTable.getItems().clear();
        tbTable.setItems(vacio);
        processes.clear();
        cleanRegistry();
        btnAddProcess.setDisable(false);
        btnNewProcesses.setDisable(true);
    }

    public static void chargeComboBoxNumerico(ObservableList<Integer> obs, int j) {
        int i = 0;
        while (i < j) {
            obs.add(i);
            i++;
        }
    }

    public static void bindStringColumn(TableColumn<FifoProcess, String> instanciaClm, String identificador) {
        instanciaClm.setCellValueFactory(new PropertyValueFactory<>(identificador));
    }

    public static void bindNumberColumn(TableColumn<FifoProcess, Number> instanciaClm, String identificador) {
        instanciaClm.setCellValueFactory(new PropertyValueFactory<>(identificador));
    }

    public static void bindFloatColumn(TableColumn<FifoProcess, Float> instanciaClm, String identificador) {
        instanciaClm.setCellValueFactory(new PropertyValueFactory<>(identificador));
    }

}

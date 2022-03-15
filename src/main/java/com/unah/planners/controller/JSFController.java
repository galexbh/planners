package com.unah.planners.controller;

import java.net.URL;
import java.util.ResourceBundle;

import com.unah.planners.process.JSFProcess;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
//import javafx.scene.control.Accordion;
import javafx.scene.control.Button;
//import javafx.scene.control.ComboBox;
import javafx.scene.control.ListView;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;

public class JSFController implements Initializable {

    @FXML
    private TableColumn<JSFProcess, String> name;
    @FXML
    private TableColumn<JSFProcess, Number> timeArrival;
    @FXML
    private TableColumn<JSFProcess, Number> timeService;
    @FXML
    private TableColumn<JSFProcess, Number> timeStay;
    @FXML
    private TableColumn<JSFProcess, Number> timeWaiting;
    @FXML
    private TableColumn<JSFProcess, Number> timeStart;
    @FXML
    private TableColumn<JSFProcess, Number> timeFinalize;
    @FXML
    private TableColumn<JSFProcess, Float> timeNormalized;
    @FXML
    private TextField txtTimeArrival;
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
    private TableView<JSFProcess> tbTable;
    @FXML
    private ListView<String> lstProcesses;

    private ObservableList<JSFProcess> contentTable;

    private ObservableList<String> processes;

    @Override
    public void initialize(URL arg0, ResourceBundle arg1) {
        instantiateObservableList();

        lstProcesses.setItems(processes);

    }

    public void instantiateObservableList() {

        contentTable = FXCollections.observableArrayList();
        processes = FXCollections.observableArrayList();
    }

    @FXML
    public void addProcess() {
        contentTable.add(new JSFProcess(txtName.getText(),
                Integer.parseInt(txtQuantun.getText()),
                Integer.parseInt(txtTimeArrival.getText()),
                Integer.parseInt(txtTimeService.getText()
                ))
        );
        processes.add(txtName.getText());
        times();
        cleanRegistry();
        btnAlgorithm.setDisable(false);
    }

    public void cleanRegistry() {
        txtName.clear();
        txtQuantun.clear();
        txtTimeArrival.clear();
        txtTimeService.clear();
    }

    public void times() {
        contentTable.get(0).timeStartJSF(contentTable);
        for (int i = 0; i < contentTable.size(); i++) {
            contentTable.get(i).timeWaitingFF();
            contentTable.get(i).timeFinalizeFF();
            contentTable.get(i).timeStayFF();
            contentTable.get(i).timeNormalizedFF();
        }
    }

    @FXML
    public void applyAlgorithm() {
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
        ObservableList<JSFProcess> vacio = FXCollections.observableArrayList();
        tbTable.getItems().clear();
        tbTable.setItems(vacio);
        processes.clear();
        cleanRegistry();
        btnAddProcess.setDisable(false);
        btnNewProcesses.setDisable(true);
    }

    public static void bindStringColumn(TableColumn<JSFProcess, String> instanciaClm, String identificador) {
        instanciaClm.setCellValueFactory(new PropertyValueFactory<>(identificador));
    }

    public static void bindNumberColumn(TableColumn<JSFProcess, Number> instanciaClm, String identificador) {
        instanciaClm.setCellValueFactory(new PropertyValueFactory<>(identificador));
    }

    public static void bindFloatColumn(TableColumn<JSFProcess, Float> instanciaClm, String identificador) {
        instanciaClm.setCellValueFactory(new PropertyValueFactory<>(identificador));
    }

}

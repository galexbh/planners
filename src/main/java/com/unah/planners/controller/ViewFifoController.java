package com.unah.planners.controller;

import java.net.URL;
import java.util.ResourceBundle;

import com.unah.planners.classes.Process ;
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

public class ViewFifoController implements Initializable {
	@FXML private Accordion accordion;

	@FXML private TableColumn<Process, String> name;	
	@FXML private TableColumn<Process, Number> timeArrival;	
	@FXML private TableColumn<Process, Number> timeService;
	@FXML private TableColumn<Process, Number> timeStay;	
	@FXML private TableColumn<Process, Number> timeWaiting;	
    @FXML private TableColumn<Process, Number> timeStart;
    @FXML private TableColumn<Process, Number> timeFinalize;
    @FXML private TableColumn<Process, Float> timeNormalized;
    @FXML private TextField txtName;
    @FXML private TextField txtQuantun;
    @FXML private TextField txtTimeService;
    @FXML private Button btnAlgorithm;
    @FXML private Button btnAddProcess;
    @FXML private Button btnNewProcesses;
    @FXML private ComboBox<Integer> cboTimeArrival;
    @FXML private TableView<Process> tbTable;
    @FXML private ListView<String> lstProcesses;

    private ObservableList<Process> contentTable;
    private ObservableList<Integer> contentComboBox;
    private ObservableList<String> processes;

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {

		instantiateObservableList();
		chargeComboBoxNumerico(contentComboBox,3);
//		enlazarLista();
//		MostrarResultado();
//		enlazarComponentes();
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
		contentTable.add(new Process(txtName.getText(),
										Integer.parseInt(txtQuantun.getText()),
										(int)cboTimeArrival.getSelectionModel().getSelectedItem(),
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
		for(int i = 0; i<contentTable.size(); i++) {
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
		 bindStringColumn(name,"name");
		 bindNumberColumn(timeArrival,"timeArrival");
		 bindNumberColumn(timeService,"timeService");
		 bindNumberColumn(timeStay,"timeStay");
		 bindNumberColumn(timeWaiting,"timeWaiting");
		 bindNumberColumn(timeStart,"timeStart");
		 bindNumberColumn(timeFinalize,"timeFinalize");
		 bindFloatColumn(timeNormalized,"timeNormalized");
	}
	
	@FXML
	public void newProcesses() {
		ObservableList<Process> vacio = FXCollections.observableArrayList();
		tbTable.getItems().clear();
		tbTable.setItems(vacio);
		processes.clear();
		cleanRegistry();
		btnAddProcess.setDisable(false);
		btnNewProcesses.setDisable(true);
	}
	
	public void deleteProcess(ObservableList<Process> obs,int index) {obs.remove(index);}
	
//	public void cargarTiempos(ObservableList<Proceso> obs,int index) {
//		contentTable.get(5).timeStart(contentTable);
//		
//	}
	
	
	
	

	public static void chargeComboBoxNumerico(ObservableList<Integer> obs,int j) {
		int i=0;
		while(i<j) {
			obs.add(Integer.valueOf(i));
			i++;
		}
	}
	public static void deleteOptionComboBox(ObservableList<Integer> obs, int index) {obs.remove(index);}


	public static void bindStringColumn(TableColumn<Process, String> instanciaClm, String identificador) {
		instanciaClm.setCellValueFactory(new PropertyValueFactory<Process, String>(identificador));
	}
	public static void bindNumberColumn(TableColumn<Process, Number> instanciaClm, String identificador) {
		instanciaClm.setCellValueFactory(new PropertyValueFactory<Process, Number>(identificador));
	}
	public static void bindFloatColumn(TableColumn<Process, Float> instanciaClm, String identificador) {
		instanciaClm.setCellValueFactory(new PropertyValueFactory<Process, Float>(identificador));
	}
	
//	//	Manera Generica de enlazar las columnas sin importar el tipo de dato referenciado
//	public static void enlazarAColumna(TableColumn<Object, String> instanciaClm, String identificador) {
//		instanciaClm.setCellValueFactory(new PropertyValueFactory<Object, String>(identificador));
//	}
	
	
	
}

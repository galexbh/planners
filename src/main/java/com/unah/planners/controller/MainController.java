package com.unah.planners.controller;

import javafx.event.ActionEvent;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;

import java.io.IOException;

public class MainController {

    public void onOpenForm(ActionEvent actionEvent) throws IOException {
        Button button = (Button) actionEvent.getSource();
        int value = Integer.parseInt(button.getText());

        FXMLLoader fxmlLoader = null;

        switch (value) {
            case 1 -> fxmlLoader = loadForm("/com/unah/planners/fifo-view.fxml");
            case 2 -> fxmlLoader = loadForm("/com/unah/planners/round-robin-view.fxml");
            case 3 -> fxmlLoader = loadForm("/com/unah/planners/JSF-view.fxml");
            case 4 -> fxmlLoader = loadForm("/com/unah/planners/SRT-view.fxml");
            case 5 -> fxmlLoader = loadForm("/com/unah/planners/HRRN-view.fxml");
        }

        Parent root = fxmlLoader.load();
        Stage stage = new Stage();
        stage.setTitle("Planners");
        stage.setScene(new Scene(root));
        stage.show();
    }

    private FXMLLoader loadForm(String url) {
        return new FXMLLoader(getClass().getResource(url));
    }
}


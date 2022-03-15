module com.unah.planners {
    requires javafx.controls;
    requires javafx.fxml;

    requires org.controlsfx.controls;
    requires com.dlsc.formsfx;
    requires org.kordamp.bootstrapfx.core;


    opens com.unah.planners to javafx.fxml, javafx.base;
    opens com.unah.planners.process to javafx.base;
    exports com.unah.planners;
    exports com.unah.planners.controller;
    opens com.unah.planners.controller to javafx.base, javafx.fxml;
    opens com.unah.planners.model;
}
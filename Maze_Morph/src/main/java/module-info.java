module com.example.oops_project {
    requires javafx.controls;
    requires javafx.fxml;

    requires org.controlsfx.controls;
    requires com.almasb.fxgl.all;
    requires javafx.media;

    opens com.example.oops_project to javafx.fxml;
    exports com.example.oops_project;
}
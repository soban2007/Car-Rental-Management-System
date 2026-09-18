module hahaha {

    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.graphics;
    requires javafx.base;

    opens app to javafx.graphics, javafx.fxml;
    opens controller to javafx.fxml;
    opens model to javafx.base;

    exports app;
}
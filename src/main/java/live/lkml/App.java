package live.lkml;


import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.net.URL;

public class App extends Application {

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage stage) throws Exception {
        URL resource = getClass().getResource("/live/lkml/view/MainForm.fxml");
        Parent root = FXMLLoader.load(resource);
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }
}

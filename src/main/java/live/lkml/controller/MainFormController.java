package live.lkml.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;

public class MainFormController {

    public AnchorPane context;


    public void openLoginForm(ActionEvent event) throws IOException {
        setUi("LoginForm");
    }


    public void openRegisterForm(ActionEvent event) throws IOException {
        setUi("RegisterForm");
    }

    private void setUi(String ui) throws IOException {
        Stage stage = (Stage) context.getScene().getWindow();
        URL resource = getClass().getResource("/live/lkml/view/"+ui+".fxml");
        Parent root = FXMLLoader.load(resource);
        Scene scene = new Scene(root);
        stage.setScene(scene);
    }
}

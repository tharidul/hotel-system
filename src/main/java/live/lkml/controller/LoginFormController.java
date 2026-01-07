package live.lkml.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import live.lkml.util.PasswordManager;

import java.io.IOException;
import java.sql.*;

public class LoginFormController {

    @FXML
    private AnchorPane context;
    @FXML
    private TextField txtEmail;
    @FXML
    private TextField txtPassword;

    @FXML
    public void loginOnAction(ActionEvent event) {
        String email = txtEmail.getText();
        String password = txtPassword.getText();

        if (email == null || email.isBlank() || password == null || password.isBlank()) {
            new Alert(Alert.AlertType.WARNING, "Please enter email and password.").show();
            return;
        }

        String sql = "SELECT password FROM user WHERE email = ?";

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            new Alert(Alert.AlertType.ERROR, "JDBC Driver not found: " + e.getMessage()).show();
            return;
        }

        try (Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/hotel_api", "root", "1234");
             PreparedStatement stm = connection.prepareStatement(sql)) {

            stm.setString(1, email);
            ResultSet rs = stm.executeQuery();

            if (rs.next()) {
                String hashedPassword = rs.getString("password");
                if (PasswordManager.check(password, hashedPassword)) {
                    new Alert(Alert.AlertType.INFORMATION, "Login successful!").show();
                    // TODO: Navigate to dashboard/main screen
                } else {
                    new Alert(Alert.AlertType.ERROR, "Invalid password.").show();
                }
            } else {
                new Alert(Alert.AlertType.ERROR, "User not found.").show();
            }

        } catch (SQLException ex) {
            new Alert(Alert.AlertType.ERROR, "Database error: " + ex.getMessage()).show();
            ex.printStackTrace();
        }
    }

    @FXML
    public void backToHomeOnAction(ActionEvent event) throws IOException {
        Stage stage = (Stage) context.getScene().getWindow();
        Parent root = FXMLLoader.load(getClass().getResource("/live/lkml/view/MainForm.fxml"));
        stage.setScene(new Scene(root));
    }
}

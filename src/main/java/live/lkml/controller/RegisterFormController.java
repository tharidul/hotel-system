package live.lkml.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import live.lkml.util.PasswordManager;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.UUID;

public class RegisterFormController {

    @FXML
    private TextField txtName;
    @FXML
    private TextField txtContact;
    @FXML
    private TextField txtEmail;
    @FXML
    private TextField txtPassword;

    public void registerOnAction(ActionEvent event) {
        String name = txtName.getText();
        String contact = txtContact.getText();
        String email = txtEmail.getText();
        String password = txtPassword.getText();

        if (name == null || name.isBlank() || email == null || email.isBlank() || password == null || password.isBlank()) {
            new Alert(Alert.AlertType.WARNING, "Please fill Name, Email and Password fields.").show();
            return;
        }

        String sql = "INSERT INTO user (id, full_name, contact, email, password) VALUES (?,?,?,?,?)";

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            new Alert(Alert.AlertType.ERROR, "JDBC Driver not found: " + e.getMessage()).show();
            return;
        }

        try (Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/hotel_api", "root", "1234");
             PreparedStatement stm = connection.prepareStatement(sql)) {

            stm.setObject(1, UUID.randomUUID().toString());
            stm.setObject(2, name);
            stm.setObject(3, contact);
            stm.setObject(4, email);
            stm.setObject(5, PasswordManager.hash(password));

            boolean isSaved = stm.executeUpdate() > 0;
            if (isSaved) {
                new Alert(Alert.AlertType.INFORMATION, "Saved.").show();
                txtName.clear();
                txtContact.clear();
                txtEmail.clear();
                txtPassword.clear();
            } else {
                new Alert(Alert.AlertType.ERROR, "Could not save user. No rows affected.").show();
            }

        } catch (SQLException ex) {
            new Alert(Alert.AlertType.ERROR, "Database error: " + ex.getMessage()).show();
            ex.printStackTrace();
        }
    }
}

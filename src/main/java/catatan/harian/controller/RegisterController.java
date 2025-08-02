package catatan.harian.controller;

import catatan.harian.dao.UserDAO;
import catatan.harian.model.User;
import java.io.IOException;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;

public class RegisterController {

    @FXML private TextField namaLengkapField;
    @FXML private TextField emailField;
    @FXML private TextField usernameField;
    @FXML private PasswordField passwordField;
    @FXML private Label errorLabel;
    @FXML private Button registerButton;
    
    private final UserDAO userDAO = new UserDAO();

    @FXML
    private void handleRegisterButton(ActionEvent event) {
        String namaLengkap = namaLengkapField.getText();
        String email = emailField.getText();
        String username = usernameField.getText();
        String password = passwordField.getText();

        if (namaLengkap.isEmpty() || username.isEmpty() || password.isEmpty()) {
            errorLabel.setText("Semua field wajib diisi!");
            return;
        }

        User newUser = new User();
        newUser.setNamaLengkap(namaLengkap);
        newUser.setEmail(email);
        newUser.setUsername(username);
        newUser.setPassword(password); // Dalam aplikasi nyata, password harus di-hash

        if (userDAO.register(newUser)) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Sukses");
            alert.setHeaderText(null);
            alert.setContentText("Registrasi berhasil! Silakan login.");
            alert.showAndWait();
            handleLoginLink(event);
        } else {
            errorLabel.setText("Registrasi gagal. Username mungkin sudah ada.");
        }
    }

    @FXML
    private void handleLoginLink(ActionEvent event) {
        try {
            Stage currentStage = (Stage) registerButton.getScene().getWindow();
            currentStage.close();

            Parent root = FXMLLoader.load(getClass().getResource("/catatan/harian/view/Login.fxml"));
            Stage loginStage = new Stage();
            loginStage.setTitle("Aplikasi Catatan Keuangan - Login");
            loginStage.setScene(new Scene(root));
            loginStage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
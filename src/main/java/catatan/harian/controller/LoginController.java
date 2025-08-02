package catatan.harian.controller;

import catatan.harian.dao.UserDAO;
import catatan.harian.model.User;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class LoginController implements Initializable {

    @FXML
    private TextField usernameField;
    @FXML
    private PasswordField passwordField;
    @FXML
    private Button loginButton;
    @FXML
    private Label errorLabel;
    @FXML
    private Hyperlink registerLink;

    private final UserDAO userDAO = new UserDAO();

    @FXML
    private void handleLoginButtonAction(ActionEvent event) {
        String username = usernameField.getText();
        String password = passwordField.getText();

        // 1. Validasi input
        if (username.isEmpty() || password.isEmpty()) {
            errorLabel.setText("Username dan password tidak boleh kosong.");
            return;
        }

        // 2. Memanggil metode login dari DAO
        User user = userDAO.login(username, password);

        // 3. Memeriksa hasil login
        if (user != null) {
            errorLabel.setText("Login Berhasil!");
            System.out.println("Login sukses sebagai: " + user.getNamaLengkap());
            
            try {
                openDashboard(user);
                Stage currentStage = (Stage) loginButton.getScene().getWindow();
                currentStage.close();
            } catch (IOException e) {
                e.printStackTrace();
                errorLabel.setText("Gagal membuka halaman utama.");
            }
            
        } else {
            // Jika user null, berarti login gagal
            errorLabel.setText("Username atau password salah.");
        }
    }

    private void openDashboard(User user) throws IOException {
        // Memuat file FXML untuk dashboard
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/catatan/harian/view/Dashboard.fxml"));
        Parent root = loader.load();

        // (Opsional) Mengirim data user ke DashboardController
        DashboardController dashboardController = loader.getController();
        dashboardController.initData(user);

        // Menampilkan jendela dashboard
        Stage dashboardStage = new Stage();
        dashboardStage.setTitle("Dashboard - " + user.getNamaLengkap());
        dashboardStage.setScene(new Scene(root));
        dashboardStage.show();
    }
    
    
    @FXML
    private void handleRegisterLink(ActionEvent event) {
        try {
            Stage currentStage = (Stage) registerLink.getScene().getWindow();
            currentStage.close();

            Parent root = FXMLLoader.load(getClass().getResource("/catatan/harian/view/Register.fxml"));
            Stage registerStage = new Stage();
            registerStage.setTitle("Buat Akun Baru");
            registerStage.setScene(new Scene(root));
            registerStage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // Mengosongkan label error saat aplikasi dimulai
        errorLabel.setText("");
    }    
}
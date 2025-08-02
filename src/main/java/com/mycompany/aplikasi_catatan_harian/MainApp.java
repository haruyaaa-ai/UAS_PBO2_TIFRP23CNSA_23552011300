package com.mycompany.aplikasi_catatan_harian;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
import static javafx.application.Application.launch;

/**
 * JavaFX App
 */
public class MainApp extends Application {

    @Override
    public void start(Stage stage) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("/catatan/harian/view/Login.fxml"));
        
        Scene scene = new Scene(root);
        
        stage.setTitle("Aplikasi Catatan Keuangan");
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}
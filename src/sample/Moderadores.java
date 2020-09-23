package sample;


import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class Moderadores {
    public void btnDenuncias() throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("Denuncias.fxml"));
        Stage primaryStage = new Stage();
        primaryStage.setTitle("Criar Infográfico");
        primaryStage.setScene(new Scene(root, 790.0, 517.0));
        primaryStage.show();
    }
    public void btnValidacoes() throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("Validacoes.fxml"));
        Stage primaryStage = new Stage();
        primaryStage.setTitle("Criar Infográfico");
        primaryStage.setScene(new Scene(root, 790.0, 517.0));
        primaryStage.show();
    }
}

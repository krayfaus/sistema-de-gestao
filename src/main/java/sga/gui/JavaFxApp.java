package sga.gui;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

import java.io.IOException;

import static sga.gui.JavaFxUtils.getResourcePath;
import static sga.gui.JavaFxUtils.loadFxml;

public class JavaFxApp extends Application {
    private static Scene scene;

    @Override
    public void start(Stage stage) throws IOException {
        scene = new Scene(loadFxml("Home"));
        stage.setScene(scene);
        stage.setTitle("Sistema de Gestão de Atividades");
        stage.getIcons().add(new Image(getResourcePath("img/icons8-study-100.png")));
        stage.setResizable(false);
        stage.show();
    }

    static void setRoot(String fxml) throws IOException {
        scene.setRoot(loadFxml(fxml));
    }

    public static void main(String[] args) { launch(); }
}
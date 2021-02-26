package sga.gui;

import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;

import java.io.IOException;

public class JavaFxUtils {
    public static Parent loadFxml(String filename) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(JavaFxApp.class.getResource("fxml/" + filename + ".fxml"));
        return fxmlLoader.load();
    }

    public static Parent loadFxml(String filename, Initializable controller) throws IOException {
        FXMLLoader loader = new FXMLLoader(JavaFxApp.class.getResource("fxml/" + filename + ".fxml"));
        loader.setController(controller);
        return loader.load();
    }

    public static String getResourcePath(String filename) {
        return JavaFxApp.class.getResource("fxml/" + filename).toExternalForm();
    }
}

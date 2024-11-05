package org.uns.notepadmini;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;


public class MainApp extends Application {
    public static final String TITLE = "Notepad Mini";
    public static final String FXML = "primary";
    private static Stage stage;

    @Override
    public void start(@SuppressWarnings("exports") Stage s) throws IOException {
        stage = s;
        FXMLLoader fxmlLoader = new FXMLLoader(MainApp.class.getResource("/fxml/" + FXML + ".fxml"));
        setRoot(fxmlLoader, s);
    }

    static void setRoot(FXMLLoader fxmlLoader, Stage s) throws IOException {
        fxmlLoader.setControllerFactory(param -> new FXMLController(s));
        Parent root = fxmlLoader.load();
        Scene scene = new Scene(root);
        stage.setTitle(TITLE);
        stage.setScene(scene);
        stage.show();
    }


    public static void main(String[] args) {
        launch(args);
    }

}

package org.uns.notepadmini.components;

import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class ErrorPopup {

    private final Stage popupStage;

    public ErrorPopup(Stage ownerStage, String message) {
        popupStage = new Stage();

        popupStage.initOwner(ownerStage);
        popupStage.initModality(Modality.APPLICATION_MODAL);

        Label messageLabel = new Label(message);
        Button closeButton = new Button("Close");
        closeButton.setOnAction(e -> popupStage.close());

        VBox layout = new VBox(10);
        layout.setAlignment(Pos.CENTER);
        layout.getChildren().addAll(messageLabel, closeButton);

        Scene scene = new Scene(layout, 300, 150);
        popupStage.setScene(scene);
    }

    public void show() {
        popupStage.showAndWait(); 
    }
}

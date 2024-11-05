package org.uns.notepadmini.components;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class UnsavedConfirmationPopup {

    private final Stage popupStage;
    private boolean confirmed;

    public UnsavedConfirmationPopup(Stage ownerStage, String message) {
        popupStage = new Stage();
        popupStage.setTitle("Unsaved Warning");
        confirmed = false;

        popupStage.initOwner(ownerStage);
        popupStage.initModality(Modality.APPLICATION_MODAL);

        Label messageLabel = new Label(message);
        messageLabel.setWrapText(true);
        messageLabel.setAlignment(Pos.CENTER);

        Button yesButton = new Button("Yes");
        yesButton.setOnAction(e -> {
            confirmed = true;
            popupStage.close();
        });

        Button noButton = new Button("No");
        noButton.setOnAction(e -> popupStage.close());

        HBox buttonLayout = new HBox(10, yesButton, noButton);
        buttonLayout.setAlignment(Pos.CENTER);

        VBox layout = new VBox(15, messageLabel, buttonLayout);
        layout.setAlignment(Pos.CENTER);
        layout.setPadding(new Insets(10, 20, 10, 20));

        Scene scene = new Scene(layout, 300, 150);
        popupStage.setScene(scene);
    }

    public boolean showAndWait() {
        popupStage.showAndWait();
        return confirmed;
    }
}

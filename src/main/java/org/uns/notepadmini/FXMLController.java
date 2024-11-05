package org.uns.notepadmini;
/*
Put header here


 */

import java.net.URL;
import java.util.ResourceBundle;

import org.uns.notepadmini.common.FileHelper;
import org.uns.notepadmini.components.ErrorPopup;
import org.uns.notepadmini.components.UnsavedConfirmationPopup;
import org.uns.notepadmini.exception.UnknownException;
import org.uns.notepadmini.models.FileObject;
import org.uns.notepadmini.models.FileStatus;
import org.uns.notepadmini.services.FileProcessor;
import org.uns.notepadmini.services.JavaFXFileProcessorImpl;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.stage.Stage;


public class FXMLController implements Initializable {

    //unchanged dependencies
    private final FileProcessor fileProcessor;
    private final Stage stage;

    //changeable dependencies for managing state
    private FileObject currentFile;
    private FileStatus fileStatus;
    
    //fxml component
    @FXML
    private TextArea note;
    @FXML
    private Label filePathLabel;
    @FXML
    private Label fileStatusLabel;

    public FXMLController(Stage stage) {
        this.stage = stage;
    
        /*
        dapat mengganti komponen sesuai yang di inginkan terdapat dua opsi: swing file chooser atau file chooser dari javaFx

        implementasi JavaFX file chooser:
         this.fileProcessor = new JavaFXFileProcessorImpl(stage); 
        implementasi Swing file chooser:
         this.fileProcessor = new SwingFileProcessorImpl(stage); 
          
         */
        
        this.fileProcessor = new JavaFXFileProcessorImpl(stage);
        this.currentFile = new FileObject();
        this.stage.setOnCloseRequest(event -> {
            handleAppClose();
            event.consume(); 
        });
    }

    @FXML
    private void handleOpenButton(){
        try {
            FileObject openedFile = fileProcessor.openFile();

            if (openedFile == null) {
                return;
            }
            showUnsavedConfirmationPopup("Apakah Kamu Ingin membuka note ini?", () -> {
                this.currentFile = openedFile;
                note.setText(currentFile.getText());
                setFilePathLabel(currentFile.getPath());
                checkFileStatus();             
            });
   

        } catch (UnknownException e) {
            showErrorPopup(e.getMsg());
        } 
    }

    @FXML
    private void handleSaveButton() {
        if (currentFile.getPath() == null) {
            handleSaveAsButton();
            return;
        }

        try {
            fileProcessor.saveExistingFile(currentFile.getPath(), note.getText());
            currentFile.setText(note.getText());
            checkFileStatus();

        } catch (UnknownException e) {
            showErrorPopup(e.getMsg());
        }
    }

    @FXML
    private void handleSaveAsButton() {
        try {
            FileObject savedFile = fileProcessor.saveFile(note.getText());

            if (savedFile == null) {
                return;
            }

            this.currentFile = savedFile;
            note.setText(currentFile.getText());
            setFilePathLabel(currentFile.getPath());
            checkFileStatus();

        } catch (UnknownException e) {
            showErrorPopup(e.getMsg());
        }
    }
    @FXML
    public void handleNewNoteButton() {
        showUnsavedConfirmationPopup("Apakah Kamu Ingin membuat note baru?", () -> {
            this.currentFile = new FileObject();
            note.clear();
            setFilePathLabel("None");
            checkFileStatus();
        });
    }

    private void showErrorPopup(String message) {
        ErrorPopup errorPopup = new ErrorPopup(this.stage, message);
        errorPopup.show();
    }

    private void showUnsavedConfirmationPopup(String message, Runnable onConfirmOrSavedAction) {

        if (this.fileStatus == FileStatus.UNSAVED_CHANGE || (!note.getText().isEmpty() && this.fileStatus == FileStatus.UNSAVED_FILE)) {
            UnsavedConfirmationPopup confirmation = new UnsavedConfirmationPopup(this.stage, "note sekarang belum tersimpan," + message);
            boolean confirmed = confirmation.showAndWait();
    
            if (!confirmed) {
                return;
            }
            onConfirmOrSavedAction.run(); 
            return;
        }
        onConfirmOrSavedAction.run();
    }

    private void checkFileStatus() {
        try {
            this.fileStatus = FileHelper.checkFileState(currentFile, note.getText());    

            switch (this.fileStatus) {
                case SAVED:
                    setFileStatusLabel("File Saved");
                    break;
                case UNSAVED_CHANGE:
                    setFileStatusLabel("File Unsaved");
                    break;
                case UNSAVED_FILE:
                    setFileStatusLabel("File Unsaved on Computer");
                    break;
                default:
                    setFileStatusLabel("Unknown");
            } 

        } catch (UnknownException e) {
            showErrorPopup(e.getMsg());
        }
    }

    private void setFilePathLabel(String pathFile) {
        filePathLabel.setText("Path : " + pathFile);
    }

    private void setFileStatusLabel(String status) {
        fileStatusLabel.setText("Status : " + status);
    }

    private void handleAppClose() {
        showUnsavedConfirmationPopup("Apakah Kamu Ingin Menutup Aplikasi??", () -> {
            stage.close();
        });
    }


    @Override
    public void initialize(URL url, ResourceBundle rb) {
        note.textProperty().addListener((observable, oldValue, newValue) -> {
            checkFileStatus();
        });
    }

}

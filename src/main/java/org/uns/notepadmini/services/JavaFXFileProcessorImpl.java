package org.uns.notepadmini.services;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

import org.uns.notepadmini.common.FileHelper;
import org.uns.notepadmini.exception.UnknownException;
import org.uns.notepadmini.models.FileObject;

import javafx.stage.FileChooser;
import javafx.stage.Stage;

public class JavaFXFileProcessorImpl implements FileProcessor {

    private final FileChooser fileChooser;
    private final Stage stage;

    public JavaFXFileProcessorImpl(Stage stage) {
        this.fileChooser = new FileChooser();
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("Text Files", "*.txt"));
        this.stage = stage;
    }

    @Override
    public FileObject openFile() throws UnknownException {

        File openedFile = fileChooser.showOpenDialog(stage);

         if (openedFile == null) {
            return null; 
         }
        return FileHelper.fileObjectMapper(openedFile);
    }


    @Override
    public FileObject saveFile(String content) throws UnknownException {

        File savedFile = fileChooser.showSaveDialog(stage);

        if (savedFile == null) {
            return null;
        }

        try (BufferedWriter writer = new BufferedWriter(new FileWriter(savedFile))) {
            writer.write(content);
        } catch (IOException e) {
            throw new UnknownException(e);
        }

        return FileHelper.fileObjectMapper(savedFile);
    }


    @Override
    public void saveExistingFile(String filePath, String content) throws UnknownException {

        if (filePath == null) {
            throw new IllegalArgumentException("file not found");
        }

        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filePath))) {
            writer.write(content);
        } catch (IOException e) {
           throw new UnknownException(e);
        }
    }
}

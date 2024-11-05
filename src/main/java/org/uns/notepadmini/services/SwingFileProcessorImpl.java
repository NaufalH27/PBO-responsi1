package org.uns.notepadmini.services;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

import javax.swing.JDialog;
import javax.swing.JFileChooser;
import javax.swing.filechooser.FileNameExtensionFilter;

import org.uns.notepadmini.common.FileHelper;
import org.uns.notepadmini.exception.UnknownException;
import org.uns.notepadmini.models.FileObject;

import javafx.stage.Stage;

public class SwingFileProcessorImpl implements FileProcessor {

    private final JFileChooser fileChooser;
    private final Stage primaryStage;

    public SwingFileProcessorImpl(Stage primaryStage) {
        this.fileChooser = new JFileChooser();
        this.fileChooser.setFileFilter(new FileNameExtensionFilter("Text Files", "txt"));
        this.primaryStage = primaryStage;
    }

    @Override
    public FileObject openFile() throws UnknownException {
        File openedFile = showFileChooser(JFileChooser.OPEN_DIALOG);
        return openedFile != null ? FileHelper.fileObjectMapper(openedFile) : null;
    }

    @Override
    public FileObject saveFile(String content) throws UnknownException {
        File savedFile = showFileChooser(JFileChooser.SAVE_DIALOG);

        if (savedFile == null) {
            return null;
        }

        if (!savedFile.getName().toLowerCase().endsWith(".txt")) {
            savedFile = new File(savedFile.getAbsolutePath() + ".txt");
        }

        try (BufferedWriter writer = new BufferedWriter(new FileWriter(savedFile))) {
            writer.write(content);
        } catch (IOException e) {
            throw new UnknownException(e);
        }

        return FileHelper.fileObjectMapper(savedFile);
    }

    private File showFileChooser(int dialogType) {
        File selectedFile = null;

        JDialog dialog = new JDialog();
        dialog.setAlwaysOnTop(true);
        dialog.setModal(true);
        dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);

        int result;
        if (dialogType == JFileChooser.OPEN_DIALOG) {
            result = fileChooser.showOpenDialog(dialog);
        } else {
            result = fileChooser.showSaveDialog(dialog);
        }

        if (result == JFileChooser.APPROVE_OPTION) {
            selectedFile = fileChooser.getSelectedFile();
        }

        dialog.dispose();

        return selectedFile;
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

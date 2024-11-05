package org.uns.notepadmini.services;

import org.uns.notepadmini.exception.UnknownException;
import org.uns.notepadmini.models.FileObject;

public interface FileProcessor {
    public FileObject openFile() throws UnknownException;
    public FileObject saveFile(String content) throws UnknownException;
    public void saveExistingFile(String filePath, String content) throws UnknownException;
}

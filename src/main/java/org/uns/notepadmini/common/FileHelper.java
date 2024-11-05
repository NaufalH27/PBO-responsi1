package org.uns.notepadmini.common;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;

import org.uns.notepadmini.exception.UnknownException;
import org.uns.notepadmini.models.FileObject;
import org.uns.notepadmini.models.FileStatus;

public class FileHelper {
    public static FileStatus checkFileState(FileObject fileObject, String content) throws UnknownException {

        if (fileObject.getPath() == null) {
            return FileStatus.UNSAVED_FILE;
        }

        if (HashHelper.checkHash(fileObject.getHashedTextCache(), content)) {
            return FileStatus.SAVED;
        } else {
            return FileStatus.UNSAVED_CHANGE;
        }
        
    }

    public static FileObject fileObjectMapper(File openedFile) throws UnknownException {
        try {
            FileObject file = new FileObject();
            String content = Files.readString(openedFile.toPath());
            file.setText(content);
            file.setPath(openedFile.getPath());
            return file;
        } catch (IOException e) {
            throw new UnknownException(e);
        }
    }


}

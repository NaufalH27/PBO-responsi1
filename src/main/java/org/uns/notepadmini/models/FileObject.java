package org.uns.notepadmini.models;

import org.uns.notepadmini.common.HashHelper;
import org.uns.notepadmini.exception.UnknownException;

public class FileObject {
    private String text;
    private String path;
    private byte[] hashedTextCache;


    public String getText() {
        return this.text;
    }

    public void setText(String text) throws UnknownException {
        this.hashedTextCache = HashHelper.hash(text);
        this.text = text;
    }

    public String getPath() {
        return this.path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public byte[] getHashedTextCache() {
        return this.hashedTextCache;
    }  

}

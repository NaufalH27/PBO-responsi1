module org.uns.notepadmini {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.media;
    requires java.desktop;
    opens org.uns.notepadmini to javafx.fxml;
    exports org.uns.notepadmini;
}
package vidmot;

/**
 * Represents different views of the application.
 */
public enum View {
    GAME("slanga-view.fxml");

    private final String filename;

    View(String filename) {
        this.filename = filename;
    }

    /**
     * @return filename
     */
    public String getFileName() {
        return filename;
    }
}

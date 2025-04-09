package vidmot;

/**
 * Nafn: Hjörleifur Örn Sveinsson
 * Gmail: hjorleifursveins@gmail.com
 * Lýsing:
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

package BasicGUI;

public enum FontSize {
    SMALL(25), MEDIUM(36), Large(60);
    private int fontSize;

    FontSize(int fontSize) {
        this.fontSize = fontSize;
    }

    public int getFontSize() {
        return fontSize;
    }
}

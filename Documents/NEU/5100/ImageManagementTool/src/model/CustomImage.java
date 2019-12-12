package model;

public class CustomImage {
    //encapsulation
    private String sourcePath;//image source path
    private int width;//width to change to
    private int height;//height to change to

    //construction
    public CustomImage(String sourcePath, int width, int height) {
        this.sourcePath = sourcePath;
        this.width = width;
        this.height = height;
    }

    //getters and setters
    public String getSourcePath() {
        return sourcePath;
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    public void setSourcePath(String sourcePath) {
        this.sourcePath = sourcePath;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public void setHeight(int height) {
        this.height = height;
    }
}

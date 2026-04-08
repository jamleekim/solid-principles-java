package solid.lsp.after;

/**
 * 직사각형: 가로와 세로를 독립적으로 가짐
 * 불변 객체로 만들어 setter 함정을 원천 차단
 */
public class Rectangle implements Shape {
    private final int width;
    private final int height;

    public Rectangle(int width, int height) {
        this.width = width;
        this.height = height;
    }

    public int getWidth() { return width; }
    public int getHeight() { return height; }

    @Override
    public int getArea() {
        return width * height;
    }
}

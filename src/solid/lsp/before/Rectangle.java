package solid.lsp.before;

/**
 * LSP 위반 예시: 정사각형(Square)이 직사각형(Rectangle)을 상속
 *
 * 수학적으로 정사각형 is-a 직사각형이지만,
 * 객체지향에서는 행위의 호환성이 깨지면 LSP 위반이다.
 */
public class Rectangle {
    protected int width;
    protected int height;

    public void setWidth(int width) {
        this.width = width;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public int getWidth() { return width; }
    public int getHeight() { return height; }

    public int getArea() {
        return width * height;
    }
}

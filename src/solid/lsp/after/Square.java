package solid.lsp.after;

/**
 * 정사각형: 변의 길이 하나만 가짐
 * Rectangle과 독립적 — 서로의 계약을 깨뜨리지 않음
 * 둘 다 Shape의 getArea() 계약을 올바르게 이행
 */
public class Square implements Shape {
    private final int side;

    public Square(int side) {
        this.side = side;
    }

    public int getSide() { return side; }

    @Override
    public int getArea() {
        return side * side;
    }
}

package solid.lsp.after;

/**
 * 어떤 Shape이 와도 getArea()는 올바르게 동작
 * → LSP 준수: 하위 타입이 상위 타입을 완전히 대체 가능
 */
public class AreaCalculator {

    public static void printArea(Shape shape) {
        System.out.println("면적: " + shape.getArea());
    }

    public static void main(String[] args) {
        printArea(new Rectangle(5, 3)); // 면적: 15
        printArea(new Square(5));       // 면적: 25
    }
}

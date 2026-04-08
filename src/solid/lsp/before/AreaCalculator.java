package solid.lsp.before;

/**
 * 이 코드는 Rectangle의 계약을 신뢰하고 작성됨
 * Square를 넣으면 assert가 실패한다 → LSP 위반의 실질적 피해
 */
public class AreaCalculator {

    public static void printArea(Rectangle rect) {
        rect.setWidth(5);
        rect.setHeight(3);
        int expected = 15; // 5 * 3
        int actual = rect.getArea();

        System.out.println("기대 면적: " + expected);
        System.out.println("실제 면적: " + actual);
        System.out.println("정상 동작? " + (expected == actual));
    }

    public static void main(String[] args) {
        System.out.println("--- Rectangle ---");
        printArea(new Rectangle()); // 정상: 15

        System.out.println("\n--- Square (LSP 위반!) ---");
        printArea(new Square());    // 비정상: 9 (height=3으로 덮어씀)
    }
}

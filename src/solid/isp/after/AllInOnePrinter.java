package solid.isp.after;

/**
 * 복합기: 필요한 인터페이스를 조합하여 구현
 * Java의 다중 인터페이스 구현이 여기서 빛을 발함
 */
public class AllInOnePrinter implements Printer, Scanner, Fax {

    @Override
    public void print(String document) {
        System.out.println("인쇄: " + document);
    }

    @Override
    public void scan(String document) {
        System.out.println("스캔: " + document);
    }

    @Override
    public void fax(String document) {
        System.out.println("팩스 전송: " + document);
    }

    // 사용 예시
    public static void main(String[] args) {
        // 프린트만 필요한 곳에서는 Printer 타입으로 주입
        Printer printer = new SimplePrinter();
        printer.print("보고서.pdf");

        // 복합기가 필요한 곳에서는 여러 인터페이스 활용
        AllInOnePrinter allInOne = new AllInOnePrinter();
        allInOne.print("계약서.pdf");
        allInOne.scan("영수증.jpg");
        allInOne.fax("견적서.pdf");
    }
}

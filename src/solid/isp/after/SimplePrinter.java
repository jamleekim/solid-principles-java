package solid.isp.after;

/**
 * ISP 적용: Printer 인터페이스만 구현
 * 스캔, 팩스 같은 불필요한 의존이 사라짐
 */
public class SimplePrinter implements Printer {

    @Override
    public void print(String document) {
        System.out.println("인쇄: " + document);
    }
}

package solid.isp.before;

/**
 * ISP 위반의 결과: 사용하지 않는 메서드를 억지로 구현
 *
 * 문제점:
 * - scan(), fax(), staple()은 이 프린터에 존재하지 않는 기능
 * - UnsupportedOperationException은 컴파일 타임에 잡히지 않음
 * - 이 인터페이스에 copyDouble() 같은 메서드가 추가되면?
 *   → SimplePrinter도 수정해야 함 (사용도 안 하는데!)
 */
public class SimplePrinter implements MultiFunctionDevice {

    @Override
    public void print(String document) {
        System.out.println("인쇄: " + document);
    }

    @Override
    public void scan(String document) {
        throw new UnsupportedOperationException("스캔 기능이 없습니다");
    }

    @Override
    public void fax(String document) {
        throw new UnsupportedOperationException("팩스 기능이 없습니다");
    }

    @Override
    public void staple(String document) {
        throw new UnsupportedOperationException("스테이플 기능이 없습니다");
    }
}

package solid.isp.before;

/**
 * ISP 위반 예시: 너무 많은 기능을 하나의 인터페이스에 묶음
 * 단순 프린터도 이 인터페이스를 구현하면 scan(), fax()를 억지로 구현해야 함
 */
public interface MultiFunctionDevice {
    void print(String document);
    void scan(String document);
    void fax(String document);
    void staple(String document);
}

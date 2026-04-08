package solid.isp.after;

/**
 * ISP 적용: 역할별로 인터페이스를 분리
 * 각 클라이언트는 자신이 필요한 인터페이스에만 의존
 */
public interface Printer {
    void print(String document);
}

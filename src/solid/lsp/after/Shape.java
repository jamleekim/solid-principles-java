package solid.lsp.after;

/**
 * LSP 적용: 상속 대신 공통 인터페이스로 추상화
 * 각 도형은 자신의 계약을 독립적으로 정의
 */
public interface Shape {
    int getArea();
}

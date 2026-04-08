package solid.lsp.before;

/**
 * LSP 위반: Square가 Rectangle의 setWidth/setHeight 계약을 깨뜨림
 *
 * Rectangle을 사용하는 코드:
 *   rect.setWidth(5);
 *   rect.setHeight(3);
 *   assert rect.getArea() == 15;  // 직사각형이라면 당연히 15
 *
 * 그런데 Square를 넣으면:
 *   square.setWidth(5);  → width=5, height=5
 *   square.setHeight(3); → width=3, height=3
 *   square.getArea() == 9  // 15가 아님! → 기대가 깨짐
 */
public class Square extends Rectangle {

    @Override
    public void setWidth(int width) {
        // 정사각형이므로 가로를 바꾸면 세로도 같이 바꿈
        this.width = width;
        this.height = width;
    }

    @Override
    public void setHeight(int height) {
        // 정사각형이므로 세로를 바꾸면 가로도 같이 바꿈
        this.width = height;
        this.height = height;
    }
}

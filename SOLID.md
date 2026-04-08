# SOLID 원칙 — 객체지향 설계의 5가지 약속

> SOLID는 Robert C. Martin(Uncle Bob)이 정리한 객체지향 설계 원칙 5가지의 머리글자이다.
> 이 원칙들을 지키면 **변경에 강하고, 이해하기 쉽고, 재사용 가능한** 코드를 만들 수 있다.

---

## 왜 SOLID가 필요한가?

코드는 한 번 작성하고 끝이 아니다. 기능이 추가되고, 요구사항이 바뀌고, 버그를 고쳐야 한다.
이때 설계가 나쁘면 **하나를 고치면 다른 곳이 깨지는** 악순환에 빠진다.

SOLID는 이런 질문에 답한다:
- 이 클래스는 너무 많은 일을 하고 있지 않은가?
- 새 기능을 추가할 때 기존 코드를 건드려야 하는가?
- 부모 클래스를 자식 클래스로 바꿔도 안전한가?
- 사용하지도 않는 메서드를 억지로 구현하고 있지 않은가?
- 구체적인 구현에 직접 의존하고 있지 않은가?

---

## 목차

| 약자 | 원칙 | 한 줄 요약 |
|:---:|------|-----------|
| **S** | [Single Responsibility](#1-srp--single-responsibility-principle-단일-책임-원칙) | 클래스는 하나의 이유로만 변경되어야 한다 |
| **O** | [Open/Closed](#2-ocp--openclosed-principle-개방-폐쇄-원칙) | 기존 코드를 수정하지 않고 기능을 확장할 수 있어야 한다 |
| **L** | [Liskov Substitution](#3-lsp--liskov-substitution-principle-리스코프-치환-원칙) | 자식 클래스는 부모 클래스를 대체할 수 있어야 한다 |
| **I** | [Interface Segregation](#4-isp--interface-segregation-principle-인터페이스-분리-원칙) | 사용하지 않는 메서드에 의존하지 않아야 한다 |
| **D** | [Dependency Inversion](#5-dip--dependency-inversion-principle-의존성-역전-원칙) | 구체 클래스가 아닌 추상화에 의존해야 한다 |

---

## 1. SRP — Single Responsibility Principle (단일 책임 원칙)

### 원칙

> **"클래스는 변경의 이유가 단 하나여야 한다."**

여기서 "책임"이란 **변경의 이유(reason to change)**를 의미한다.
쉽게 말해, "이 클래스를 수정해야 하는 사람이 몇 명인가?"를 생각하면 된다.

### 비유

식당에서 한 사람이 요리도 하고, 서빙도 하고, 회계도 한다면?
요리법이 바뀌거나, 서빙 방식이 바뀌거나, 세금 정책이 바뀔 때마다 그 사람을 찾아야 한다.
각각 전문 담당자를 두면 변경이 서로에게 영향을 주지 않는다.

### 위반 예시

```java
// Employee 하나가 3가지 일을 한다
public class Employee {
    private String name;
    private double baseSalary;
    private int overtimeHours;

    // 책임 1: 급여 계산 (비즈니스 로직) — 급여 정책팀이 변경
    public double calculatePay() {
        double overtimePay = overtimeHours * (baseSalary / 160) * 1.5;
        return baseSalary + overtimePay;
    }

    // 책임 2: DB 저장 (영속성) — DBA가 변경
    public void saveToDatabase() {
        // INSERT INTO employees ...
    }

    // 책임 3: 리포트 생성 (프레젠테이션) — 보고서팀이 변경
    public String generateReport() {
        return "이름: " + name + ", 급여: " + calculatePay();
    }
}
```

**문제:** 급여 계산 공식이 바뀌어도, DB 스키마가 바뀌어도, 리포트 형식이 바뀌어도 — 전부 이 클래스를 수정해야 한다. 한 곳의 변경이 다른 기능에 버그를 일으킬 수 있다.

### 리팩토링

```java
// 모델: 데이터만 보유
public class Employee {
    private String name;
    private double baseSalary;
    private int overtimeHours;
    // getter만 제공
}

// 책임 1: 급여 계산만 담당
public class PayCalculator {
    public double calculatePay(Employee employee) {
        double hourlyRate = employee.getBaseSalary() / 160;
        double overtimePay = employee.getOvertimeHours() * hourlyRate * 1.5;
        return employee.getBaseSalary() + overtimePay;
    }
}

// 책임 2: DB 저장만 담당
public class EmployeeRepository {
    public void save(Employee employee, double salary) {
        // INSERT INTO employees ...
    }
}

// 책임 3: 리포트 생성만 담당
public class PayReportGenerator {
    public String generate(Employee employee) {
        // 리포트 포맷팅
    }
}
```

**효과:**
- 급여 정책이 바뀌면 `PayCalculator`만 수정
- DB 스키마가 바뀌면 `EmployeeRepository`만 수정
- 리포트 형식이 바뀌면 `PayReportGenerator`만 수정
- 각 변경이 서로에게 영향을 주지 않는다

### 판별법

> "이 클래스를 바꿔야 하는 이유를 세 가지 이상 댈 수 있으면 SRP 위반이다."

---

## 2. OCP — Open/Closed Principle (개방-폐쇄 원칙)

### 원칙

> **"확장에는 열려 있고, 수정에는 닫혀 있어야 한다."**

새로운 기능을 추가할 때 **기존 코드를 건드리지 않고**, 새 코드를 **추가**하는 것만으로 대응할 수 있어야 한다.

### 비유

콘센트에 플러그를 꽂아 가전제품을 사용한다.
새 가전을 쓸 때마다 집의 전기 배선을 뜯어고치지 않는다.
콘센트라는 **표준 인터페이스** 덕분에, 어떤 제품이든 꽂기만 하면 된다.

### 위반 예시

```java
public class DiscountCalculator {
    public double calculate(String discountType, double price) {
        if ("NORMAL".equals(discountType)) {
            return price;
        } else if ("VIP".equals(discountType)) {
            return price * 0.8;
        } else if ("SUPER_VIP".equals(discountType)) {
            return price * 0.7;
        }
        // 시즌 할인을 추가하려면? → 여기에 else-if를 또 추가해야 함
        throw new IllegalArgumentException("Unknown type");
    }
}
```

**문제:** 할인 타입이 추가될 때마다 이 메서드를 수정해야 한다. 기존 분기에 실수로 영향을 줄 수 있다.

### 리팩토링

```java
// 추상화: 할인 정책 인터페이스
public interface DiscountPolicy {
    double applyDiscount(double price);
}

// 구현체들: 각자 자기 할인 로직만 가짐
public class NormalDiscount implements DiscountPolicy {
    public double applyDiscount(double price) { return price; }
}

public class VipDiscount implements DiscountPolicy {
    public double applyDiscount(double price) { return price * 0.8; }
}

// ★ 새 할인 추가 — 기존 코드 수정 없이 클래스만 추가!
public class SeasonalDiscount implements DiscountPolicy {
    public double applyDiscount(double price) { return price * 0.85; }
}

// 사용하는 쪽: 인터페이스에만 의존
public class OrderService {
    private final DiscountPolicy discountPolicy;

    public OrderService(DiscountPolicy discountPolicy) {
        this.discountPolicy = discountPolicy;
    }

    public double calculateFinalPrice(double price) {
        return discountPolicy.applyDiscount(price);
    }
}
```

**효과:**
- 시즌 할인, 쿠폰 할인, 복합 할인 — 뭘 추가해도 `OrderService`는 수정하지 않는다
- 각 할인 정책은 독립적으로 테스트할 수 있다

### 판별법

> "새 기능을 추가할 때 기존 파일을 열어서 if-else를 추가하고 있으면 OCP 위반이다."

---

## 3. LSP — Liskov Substitution Principle (리스코프 치환 원칙)

### 원칙

> **"부모 타입 자리에 자식 타입을 넣어도 프로그램이 올바르게 동작해야 한다."**

상속은 "코드를 재사용하려고" 하는 게 아니다.
자식이 부모의 **행위 계약(behavior contract)**을 지켜야 비로소 올바른 상속이다.

### 비유

자동차 면허를 따면 어떤 승용차든 운전할 수 있다.
그런데 어떤 차가 "브레이크를 밟으면 가속한다"면?
겉모습은 자동차지만 운전자의 기대(브레이크 = 감속)를 배신한다. 이게 LSP 위반이다.

### 위반 예시: 정사각형-직사각형 문제

```java
public class Rectangle {
    protected int width;
    protected int height;

    public void setWidth(int width)   { this.width = width; }
    public void setHeight(int height) { this.height = height; }
    public int getArea() { return width * height; }
}

// 수학적으로 정사각형 is-a 직사각형이니까 상속... 맞을까?
public class Square extends Rectangle {
    @Override
    public void setWidth(int width) {
        this.width = width;
        this.height = width;  // 가로 바꾸면 세로도 바뀜
    }

    @Override
    public void setHeight(int height) {
        this.width = height;  // 세로 바꾸면 가로도 바뀜
        this.height = height;
    }
}
```

```java
// Rectangle을 기대하고 작성한 코드
public static void resize(Rectangle rect) {
    rect.setWidth(5);
    rect.setHeight(3);
    // 직사각형이면 당연히 5 × 3 = 15
    assert rect.getArea() == 15;  // Square를 넣으면 실패! (3 × 3 = 9)
}
```

**문제:** `Square`가 `Rectangle`의 행위 계약을 깨뜨린다. "가로와 세로를 독립적으로 설정할 수 있다"는 약속이 사라졌다.

### 리팩토링

```java
// 공통 인터페이스만 공유, 상속하지 않음
public interface Shape {
    int getArea();
}

// 직사각형: 불변 객체로 만들어 setter 함정 원천 차단
public class Rectangle implements Shape {
    private final int width;
    private final int height;

    public Rectangle(int width, int height) {
        this.width = width;
        this.height = height;
    }

    public int getArea() { return width * height; }
}

// 정사각형: 독립적으로 정의
public class Square implements Shape {
    private final int side;

    public Square(int side) { this.side = side; }
    public int getArea() { return side * side; }
}
```

**효과:**
- 어떤 `Shape`이 와도 `getArea()`는 올바르게 동작한다
- 서로의 행위 계약을 깨뜨릴 수 없다

### 판별법

> "자식 클래스를 부모 자리에 넣었을 때 `instanceof` 체크나 특별 분기가 필요하면 LSP 위반이다."

---

## 4. ISP — Interface Segregation Principle (인터페이스 분리 원칙)

### 원칙

> **"클라이언트가 사용하지 않는 메서드에 의존하지 않아야 한다."**

하나의 거대한 인터페이스보다 역할별로 작게 쪼갠 인터페이스가 낫다.

### 비유

스마트폰 충전기로 노트북을 충전할 수 없고, 노트북 충전기로 스마트폰을 충전하기 불편하다.
USB-C라는 작은 표준 규격이 등장하면서 둘 다 하나로 해결되었다.
"모든 전자기기 충전기" 같은 거대 규격 대신, 필요한 역할에 딱 맞는 작은 규격이 좋다.

### 위반 예시

```java
// 하나의 거대한 인터페이스
public interface MultiFunctionDevice {
    void print(String document);
    void scan(String document);
    void fax(String document);
    void staple(String document);
}

// 단순 프린터가 이걸 구현하면?
public class SimplePrinter implements MultiFunctionDevice {

    public void print(String document) {
        System.out.println("인쇄: " + document);
    }

    // 아래 3개는 사용할 수 없는데 억지로 구현해야 함
    public void scan(String document) {
        throw new UnsupportedOperationException("스캔 기능 없음");
    }
    public void fax(String document) {
        throw new UnsupportedOperationException("팩스 기능 없음");
    }
    public void staple(String document) {
        throw new UnsupportedOperationException("스테이플 기능 없음");
    }
}
```

**문제:**
- `SimplePrinter`는 `print()`만 필요한데 나머지 3개를 빈 껍데기로 구현해야 한다
- `MultiFunctionDevice`에 `copyDouble()` 메서드가 추가되면 `SimplePrinter`도 수정해야 한다
- `UnsupportedOperationException`은 컴파일 타임에 잡히지 않아 런타임 오류로 이어질 수 있다

### 리팩토링

```java
// 역할별로 작게 분리
public interface Printer {
    void print(String document);
}

public interface Scanner {
    void scan(String document);
}

public interface Fax {
    void fax(String document);
}

// 단순 프린터: 필요한 것만 구현
public class SimplePrinter implements Printer {
    public void print(String document) {
        System.out.println("인쇄: " + document);
    }
}

// 복합기: 필요한 인터페이스를 조합
public class AllInOnePrinter implements Printer, Scanner, Fax {
    public void print(String document) { /* 인쇄 */ }
    public void scan(String document)  { /* 스캔 */ }
    public void fax(String document)   { /* 팩스 */ }
}
```

**효과:**
- `SimplePrinter`는 `Printer`만 구현 — 불필요한 의존 없음
- `Scanner` 인터페이스에 메서드가 추가되어도 `SimplePrinter`는 영향 없음
- Java의 다중 인터페이스 구현이 자연스럽게 조합을 가능하게 함

### 판별법

> "인터페이스를 구현할 때 `throw new UnsupportedOperationException()`을 쓰고 있으면 ISP 위반이다."

---

## 5. DIP — Dependency Inversion Principle (의존성 역전 원칙)

### 원칙

> **"고수준 모듈이 저수준 모듈에 의존하면 안 된다. 둘 다 추상화에 의존해야 한다."**

- **고수준 모듈:** 비즈니스 로직 (주문 처리, 결제 등)
- **저수준 모듈:** 기술적 구현 (MySQL, 이메일 발송 등)
- **추상화:** 인터페이스

### 비유

벽에 직접 전구를 납땜하면, 전구를 바꿀 때마다 벽을 뜯어야 한다.
소켓(인터페이스)을 달아두면 어떤 전구든 끼울 수 있다.
고수준(벽)이 저수준(전구)에 직접 연결되지 않고, 추상화(소켓)를 통해 연결되는 것이다.

### 위반 예시

```java
public class OrderService {
    // 구체 클래스에 직접 의존 — new로 직접 생성!
    private MySqlOrderRepository repository = new MySqlOrderRepository();
    private EmailSender emailSender = new EmailSender();

    public void placeOrder(String orderId, String item) {
        repository.save(orderId, item);      // MySQL에 강하게 결합
        emailSender.send("주문 완료: " + item); // Email에 강하게 결합
    }
}
```

**문제:**
- MySQL → MongoDB로 교체하려면? → `OrderService` 코드 수정
- Email → 카카오톡으로 바꾸려면? → `OrderService` 코드 수정
- 테스트할 때 실제 DB와 이메일이 필요 → 단위 테스트 불가능

### 리팩토링

```java
// 추상화: 고수준 모듈(비즈니스) 쪽에서 인터페이스를 정의
public interface OrderRepository {
    void save(String orderId, String item);
}

public interface NotificationSender {
    void send(String message);
}

// 저수준 구현체들: 인터페이스를 구현
public class MySqlOrderRepository implements OrderRepository { /* ... */ }
public class MongoOrderRepository implements OrderRepository { /* ... */ }
public class EmailNotificationSender implements NotificationSender { /* ... */ }
public class KakaoNotificationSender implements NotificationSender { /* ... */ }

// 고수준 모듈: 추상화에만 의존
public class OrderService {
    private final OrderRepository repository;
    private final NotificationSender notificationSender;

    // 생성자 주입: 어떤 구현체를 쓸지는 외부에서 결정
    public OrderService(OrderRepository repository, NotificationSender sender) {
        this.repository = repository;
        this.notificationSender = sender;
    }

    public void placeOrder(String orderId, String item) {
        repository.save(orderId, item);
        notificationSender.send("주문 완료: " + item);
    }
}
```

**의존 방향이 역전되는 모습:**

```
[Before]  OrderService → MySqlOrderRepository   (고수준 → 저수준)

[After]   OrderService → OrderRepository(인터페이스) ← MySqlOrderRepository
                         (고수준 → 추상화 ← 저수준)
```

저수준 모듈이 추상화에 의존하도록 방향이 뒤집어진다. 이것이 "역전(Inversion)"의 의미이다.

**효과:**
- DB 교체: 생성자에 다른 구현체를 넘기면 끝
- 테스트: Mock 구현체를 넣으면 DB 없이 테스트 가능
- `OrderService`는 한 번도 수정하지 않는다

### DIP vs DI — 헷갈리기 쉬운 개념

| | DIP (Dependency Inversion Principle) | DI (Dependency Injection) |
|---|---|---|
| 분류 | **설계 원칙** | **구현 기법** |
| 의미 | 추상화에 의존하라 | 의존성을 외부에서 주입하라 |
| 관계 | 목표 | 목표를 달성하는 수단 |

Spring의 `@Autowired`는 DI이고, DI를 통해 DIP를 달성하는 것이다.

### 판별법

> "클래스 안에서 `new 구체클래스()`를 직접 호출하고 있으면 DIP 위반 가능성이 높다."

---

## SOLID 원칙 간의 관계

5가지 원칙은 독립적이지 않고 서로 보완한다:

```
    ISP를 지키면              DIP를 적용하면
 인터페이스가 작아지고  →  추상화에 의존하게 되고  →  OCP도 자연스럽게 달성
        ↑                                              ↑
       SRP를 지키면 클래스가 작아지고           LSP를 지키면 다형성이 안전해지고
```

- **SRP**를 지키면 → 클래스가 작아지고 → **ISP**도 자연스럽게 지켜진다
- **DIP**를 적용하면 → 인터페이스에 의존하게 되고 → **OCP**도 달성된다
- **LSP**를 지키면 → 다형성을 안전하게 사용할 수 있고 → **OCP**의 확장이 올바르게 작동한다

---

## 실전에서의 조언

1. **처음부터 완벽하게 적용하려 하지 말 것.** 코드가 변경될 때 원칙을 적용하면 된다. 변경이 없는 코드에 미리 추상화를 만드는 것은 오버엔지니어링이다.

2. **위반 신호를 감지하는 것이 중요하다.** 원칙 자체를 외우는 것보다, "if-else가 계속 늘어나네(OCP)", "이 메서드를 왜 빈 껍데기로 구현하지(ISP)" 같은 **냄새(code smell)**를 알아차리는 능력이 핵심이다.

3. **원칙은 트레이드오프다.** 모든 원칙을 극단적으로 적용하면 클래스와 인터페이스가 폭발적으로 늘어난다. "이 추상화가 실제로 변경에 도움이 되는가?"를 기준으로 판단하자.

---

## 예시 코드 안내

이 문서의 모든 예시에 대한 실행 가능한 Java 코드가 아래 경로에 있다:

```
src/solid/
├── srp/          # 단일 책임 원칙
│   ├── before/   #   위반 코드: Employee.java
│   └── after/    #   리팩토링: Employee, PayCalculator, EmployeeRepository, PayReportGenerator
├── ocp/          # 개방-폐쇄 원칙
│   ├── before/   #   위반 코드: DiscountCalculator.java
│   └── after/    #   리팩토링: DiscountPolicy + 구현체들, OrderService
├── lsp/          # 리스코프 치환 원칙
│   ├── before/   #   위반 코드: Rectangle, Square, AreaCalculator
│   └── after/    #   리팩토링: Shape 인터페이스 + Rectangle, Square
├── isp/          # 인터페이스 분리 원칙
│   ├── before/   #   위반 코드: MultiFunctionDevice, SimplePrinter
│   └── after/    #   리팩토링: Printer, Scanner, Fax + SimplePrinter, AllInOnePrinter
└── dip/          # 의존성 역전 원칙
    ├── before/   #   위반 코드: OrderService, MySqlOrderRepository, EmailSender
    └── after/    #   리팩토링: 인터페이스 + 구현체들, OrderService (생성자 주입)
```

각 `before/` → `after/`를 비교하면서 읽으면 원칙이 코드에 어떻게 적용되는지 확인할 수 있다.

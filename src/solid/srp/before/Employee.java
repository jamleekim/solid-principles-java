package solid.srp.before;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;

/**
 * SRP 위반 예시: Employee 클래스가 3가지 책임을 동시에 가짐
 * 1) 급여 계산 (비즈니스 로직)
 * 2) DB 저장 (영속성)
 * 3) 리포트 생성 (프레젠테이션)
 *
 * 문제점:
 * - 급여 계산 로직이 바뀌면 이 클래스를 수정해야 함
 * - DB 스키마가 바뀌어도 이 클래스를 수정해야 함
 * - 리포트 형식이 바뀌어도 이 클래스를 수정해야 함
 * → 변경의 이유가 3개 = SRP 위반
 */
public class Employee {
    private String name;
    private double baseSalary;
    private int overtimeHours;

    public Employee(String name, double baseSalary, int overtimeHours) {
        this.name = name;
        this.baseSalary = baseSalary;
        this.overtimeHours = overtimeHours;
    }

    // 책임 1: 급여 계산 (비즈니스 로직)
    public double calculatePay() {
        double overtimePay = overtimeHours * (baseSalary / 160) * 1.5;
        return baseSalary + overtimePay;
    }

    // 책임 2: DB 저장 (영속성 로직)
    public void saveToDatabase() {
        try {
            Connection conn = DriverManager.getConnection("jdbc:mysql://localhost/hr");
            PreparedStatement stmt = conn.prepareStatement(
                "INSERT INTO employees (name, salary) VALUES (?, ?)"
            );
            stmt.setString(1, name);
            stmt.setDouble(2, calculatePay());
            stmt.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // 책임 3: 리포트 생성 (프레젠테이션 로직)
    public String generateReport() {
        return String.format(
            "=== 급여 명세서 ===\n이름: %s\n기본급: %.0f원\n초과근무: %d시간\n총 급여: %.0f원",
            name, baseSalary, overtimeHours, calculatePay()
        );
    }

    // Getters
    public String getName() { return name; }
    public double getBaseSalary() { return baseSalary; }
    public int getOvertimeHours() { return overtimeHours; }
}

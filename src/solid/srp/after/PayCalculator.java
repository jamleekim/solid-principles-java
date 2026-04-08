package solid.srp.after;

/**
 * 책임 1 분리: 급여 계산만 담당
 * 변경 이유: 급여 정책이 바뀔 때만
 */
public class PayCalculator {

    public double calculatePay(Employee employee) {
        double hourlyRate = employee.getBaseSalary() / 160;
        double overtimePay = employee.getOvertimeHours() * hourlyRate * 1.5;
        return employee.getBaseSalary() + overtimePay;
    }
}

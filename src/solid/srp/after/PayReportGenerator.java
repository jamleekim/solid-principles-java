package solid.srp.after;

/**
 * 책임 3 분리: 리포트 생성만 담당
 * 변경 이유: 리포트 형식이 바뀔 때만
 */
public class PayReportGenerator {

    private final PayCalculator payCalculator;

    public PayReportGenerator(PayCalculator payCalculator) {
        this.payCalculator = payCalculator;
    }

    public String generate(Employee employee) {
        double totalPay = payCalculator.calculatePay(employee);
        return String.format(
            "=== 급여 명세서 ===\n이름: %s\n기본급: %.0f원\n초과근무: %d시간\n총 급여: %.0f원",
            employee.getName(),
            employee.getBaseSalary(),
            employee.getOvertimeHours(),
            totalPay
        );
    }
}

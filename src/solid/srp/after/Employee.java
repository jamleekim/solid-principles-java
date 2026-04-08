package solid.srp.after;

/**
 * SRP 적용: Employee는 순수한 데이터 모델 역할만 담당
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

    public String getName() { return name; }
    public double getBaseSalary() { return baseSalary; }
    public int getOvertimeHours() { return overtimeHours; }
}

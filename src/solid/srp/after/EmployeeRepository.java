package solid.srp.after;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;

/**
 * 책임 2 분리: DB 저장만 담당
 * 변경 이유: DB 스키마나 저장 방식이 바뀔 때만
 */
public class EmployeeRepository {

    public void save(Employee employee, double salary) {
        try {
            Connection conn = DriverManager.getConnection("jdbc:mysql://localhost/hr");
            PreparedStatement stmt = conn.prepareStatement(
                "INSERT INTO employees (name, salary) VALUES (?, ?)"
            );
            stmt.setString(1, employee.getName());
            stmt.setDouble(2, salary);
            stmt.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

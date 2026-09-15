package fu.de200319;

import fu.de200319.pojo.Department;
import fu.de200319.pojo.Employee;
import fu.de200319.pojo.Gender;
import fu.de200319.util.JPAUtil;

import java.math.BigDecimal;
import java.time.LocalDate;

public class Main {
    public static void main(String[] args) {
        // Test nhanh TODO 2.4 - Helper method đồng bộ 2 chiều
        Department dept = new Department("IT", "Ha Noi");
        Employee emp = new Employee("test@company.com", "Test Name", Gender.OTHER,
                new BigDecimal("1000"), LocalDate.now());

        // Gọi helper method
        dept.addEmployee(emp);

        // Kiểm tra kết quả in ra màn hình
        System.out.println(dept.getEmployees().contains(emp)); // Phải ra true
        System.out.println(emp.getDepartment() == dept);        // Phải ra true[cite: 1]

        JPAUtil.close();
    }
}
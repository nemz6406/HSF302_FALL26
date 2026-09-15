package fu.de200319;

import fu.de200319.dao.EmployeeDAO;
import fu.de200319.pojo.Employee;
import fu.de200319.pojo.Gender;

import java.math.BigDecimal;
import java.time.LocalDate;

public class Main {
    public static void main(String[] args) {
        EmployeeDAO dao = new EmployeeDAO();

        // Tạo một nhân viên mới để test
        Employee emp = new Employee("Nguyen Van A", "a@fpt.edu.vn",
                new BigDecimal("15000000"), Gender.MALE, LocalDate.of(2022, 3, 1));

        dao.save(emp);
        System.out.println(">>> Đã lưu thành công Employee ID: " + emp.getId());

        Employee found = dao.findById(emp.getId());
        System.out.println(">>> Tìm thấy trong DB: " + found);
    }
}
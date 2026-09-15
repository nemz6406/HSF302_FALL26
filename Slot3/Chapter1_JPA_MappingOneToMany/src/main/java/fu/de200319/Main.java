package fu.de200319;

import fu.de200319.dao.DepartmentDAO;
import fu.de200319.dao.EmployeeDAO;
import fu.de200319.pojo.Department;
import fu.de200319.pojo.Employee;
import fu.de200319.pojo.Gender;
import fu.de200319.util.JPAUtil;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        DepartmentDAO departmentDAO = new DepartmentDAO();
        EmployeeDAO employeeDAO = new EmployeeDAO(); // Khởi tạo EmployeeDAO
        String uniqueSuffix = String.valueOf(System.currentTimeMillis());

        // 1. Tạo phòng ban và nhân viên
        Department dept = new Department("IT Dept " + uniqueSuffix, "Da Nang");
        Employee emp = new Employee("dev." + uniqueSuffix + "@company.com", "Developer Pro", Gender.MALE,
                new BigDecimal("25000000"), LocalDate.now());

        dept.addEmployee(emp);
        departmentDAO.save(dept);
        System.out.println("Đã lưu phòng ban và nhân viên thành công.");

        // 2. Test EmployeeDAO bằng cách gọi findAll()
        List<Employee> employees = employeeDAO.findAll();
        System.out.println("--- DANH SÁCH TẤT CẢ NHÂN VIÊN TRONG DB ---");
        for (Employee e : employees) {
            System.out.println("ID: " + e.getId() + " | Name: " + e.getFullName() + " | Email: " + e.getEmail());
        }

        JPAUtil.close();
    }
}
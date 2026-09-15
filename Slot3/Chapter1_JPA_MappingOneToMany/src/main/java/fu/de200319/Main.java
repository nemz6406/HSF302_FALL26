package fu.de200319;

import fu.de200319.dao.DepartmentDAO;
import fu.de200319.pojo.Department;
import fu.de200319.pojo.Employee;
import fu.de200319.pojo.Gender;
import fu.de200319.util.JPAUtil;

import java.math.BigDecimal;
import java.time.LocalDate;

public class Main {
    public static void main(String[] args) {
        DepartmentDAO departmentDAO = new DepartmentDAO();
        String uniqueSuffix = String.valueOf(System.currentTimeMillis());

        // 1. Tạo phòng ban và nhân viên
        Department dept = new Department("Cascade Test " + uniqueSuffix, "Da Nang");
        Employee emp = new Employee("cascade." + uniqueSuffix + "@company.com", "Test Cascade", Gender.MALE,
                new BigDecimal("20000000"), LocalDate.now());

        // Dùng helper method addEmployee để đồng bộ 2 chiều
        dept.addEmployee(emp);

        // 2. CHỈ gọi save Department - KHÔNG gọi employeeDAO.save()
        // Nhờ cascade = ALL, Employee sẽ tự động được lưu theo Department (TODO 2.7)
        departmentDAO.save(dept);
        System.out.println("Đã lưu thành công Department với ID: " + dept.getId());

        // 3. Kiểm tra lại bằng cách query xem employee đã có trong DB chưa
        Department found = departmentDAO.findByIdWithEmployees(dept.getId());
        System.out.println("Số lượng nhân viên được cascade lưu tự động: " + found.getEmployees().size());

        if (!found.getEmployees().isEmpty()) {
            System.out.println("-> Kiểm tra CASCADE thành công! Nhân viên: " + found.getEmployees().get(0).getFullName());
        }

        JPAUtil.close();
    }
}
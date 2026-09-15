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

        // 1) Tạo Department + Employee và dùng helper method addEmployee
        Department it = new Department("IT Department", "Ha Noi");

        Employee e1 = new Employee("aa.nguyen@company.com", "Nguyen Van A", Gender.MALE,
                new BigDecimal("15000000"), LocalDate.of(2022, 1, 10));
        Employee e2 = new Employee("bb.tran@company.com", "Tran Thi B", Gender.FEMALE,
                new BigDecimal("18000000"), LocalDate.of(2021, 6, 1));

        it.addEmployee(e1);
        it.addEmployee(e2);

        // 2) Lưu Department xuống DB (Cascade = ALL sẽ tự lưu luôn Employee) (TODO 2.7)
        departmentDAO.save(it);
        System.out.println("Da luu Department thanh cong, id = " + it.getId());

        // 3) Tìm lại kèm employees bằng JOIN FETCH (TODO 2.6)
        Department found = departmentDAO.findByIdWithEmployees(it.getId());
        System.out.println("Phong ban vua tim thay: " + found.getName());
        for (Employee e : found.getEmployees()) {
            System.out.println("  - " + e.getFullName() + " (" + e.getEmail() + ")");
        }

        JPAUtil.close();
    }
}
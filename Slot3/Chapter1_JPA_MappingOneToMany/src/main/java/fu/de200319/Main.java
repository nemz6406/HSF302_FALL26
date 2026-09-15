package fu.de200319;

import fu.de200319.dao.DepartmentDAO;
import fu.de200319.pojo.Department;
import fu.de200319.pojo.Employee;
import fu.de200319.pojo.Gender;
import fu.de200319.util.JPAUtil;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;
import java.math.BigDecimal;
import java.time.LocalDate;

public class Main {
    public static void main(String[] args) {
        DepartmentDAO departmentDAO = new DepartmentDAO();
        String uniqueSuffix = String.valueOf(System.currentTimeMillis());

        // 1. Tạo phòng ban và 2 nhân viên, sau đó lưu xuống DB
        Department dept = new Department("Orphan Test " + uniqueSuffix, "Da Nang");
        Employee emp1 = new Employee("emp1." + uniqueSuffix + "@company.com", "Nhan Vien 1", Gender.MALE,
                new BigDecimal("12000000"), LocalDate.now());
        Employee emp2 = new Employee("emp2." + uniqueSuffix + "@company.com", "Nhan Vien 2", Gender.FEMALE,
                new BigDecimal("15000000"), LocalDate.now());

        dept.addEmployee(emp1);
        dept.addEmployee(emp2);
        departmentDAO.save(dept);
        System.out.println("Đã lưu phòng ban với ID: " + dept.getId() + " kèm 2 nhân viên.");

        // 2. Test Orphan Removal: Xóa 1 nhân viên khỏi danh sách của Department
        EntityManager em = JPAUtil.getEMF().createEntityManager();
        EntityTransaction tx = em.getTransaction();
        try {
            tx.begin();
            // Lấy lại department trong Transaction đang mở
            Department managedDept = em.find(Department.class, dept.getId());

            // Xóa nhân viên đầu tiên khỏi danh sách (Dùng helper method removeEmployee)
            if (!managedDept.getEmployees().isEmpty()) {
                Employee targetToRemove = managedDept.getEmployees().get(0);
                managedDept.removeEmployee(targetToRemove); // Gỡ khỏi list và set department = null
                System.out.println("Đã gỡ nhân viên ra khỏi danh sách phòng ban.");
            }

            tx.commit();
            System.out.println("-> Test ORPHAN REMOVAL thành công! Kiểm tra database xem nhân viên đã bị xóa chưa.");
        } catch (Exception e) {
            if (tx.isActive()) tx.rollback();
            e.printStackTrace();
        } finally {
            em.close();
        }

        JPAUtil.close();
    }
}
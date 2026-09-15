package fu.de200319;

import fu.de200319.dao.EmployeeDAO;
import fu.de200319.pojo.Employee;
import fu.de200319.pojo.Gender;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        EmployeeDAO dao = new EmployeeDAO();

        System.out.println("=== 1. TEST CREATE (Thêm mới nhân viên) ===");
        Employee emp = new Employee("Nguyen Van A", "a@fpt.edu.vn",
                new BigDecimal("15000000"), Gender.MALE, LocalDate.of(2022, 3, 1));
        try {
            dao.save(emp);
            System.out.println(">>> Đã lưu thành công Employee ID: " + emp.getId());
        } catch (Exception e) {
            System.out.println(">>> Lưu ý (Lỗi DB chung với Slot 3): " + e.getMessage());
        }

        System.out.println("\n=== 2. TEST FIND BY ID (Tìm kiếm theo ID) ===");
        if (emp.getId() != null) {
            Employee found = dao.findById(emp.getId());
            System.out.println(">>> Tìm thấy: " + found);
        }

        System.out.println("\n=== 3. TEST UPDATE (Cập nhật thông tin) ===");
        if (emp.getId() != null) {
            emp.setFullName("Nguyen Van A (Updated)");
            dao.update(emp);
            Employee updatedEmp = dao.findById(emp.getId());
            System.out.println(">>> Sau khi cập nhật: " + updatedEmp);
        }

        System.out.println("\n=== 4. TEST FIND ALL (Lấy danh sách toàn bộ) ===");
        List<Employee> list = dao.findAll();
        System.out.println(">>> Tổng số nhân viên trong DB: " + list.size());
        for (Employee e : list) {
            System.out.println(e);
        }

        System.out.println("\n=== 6. TEST FIND BY FULL NAME (Tìm kiếm theo tên) ===");
        List<Employee> searchByName = dao.findByFullName("Nguyen");
        System.out.println(">>> Số lượng tìm thấy theo từ khóa 'Nguyen': " + searchByName.size());
        for (Employee e : searchByName) {
            System.out.println(e);
        }

        System.out.println("\n=== 7. TEST FIND BY GENDER (Tìm kiếm theo giới tính) ===");
        List<Employee> searchByGender = dao.findByGender(Gender.MALE);
        System.out.println(">>> Số lượng nhân viên giới tính MALE: " + searchByGender.size());
        for (Employee e : searchByGender) {
            System.out.println(e);
        }

        System.out.println("\n=== 8. TEST PAGINATION (Phân trang: Trang 1, Kích thước 5) ===");
        List<Employee> pagedList = dao.findWithPagination(1, 5);
        System.out.println(">>> Số lượng nhân viên ở trang 1: " + pagedList.size());
        for (Employee e : pagedList) {
            System.out.println(e);
        }

        System.out.println("\n=== 5. TEST DELETE (Xóa nhân viên) ===");
        if (emp.getId() != null) {
            dao.delete(emp.getId());
            Employee deletedEmp = dao.findById(emp.getId());
            System.out.println(">>> Kiểm tra sau khi xóa (phải là null): " + deletedEmp);
        }

        // ==========================================
        // TODO 10: Đóng tài nguyên kết nối
        // ==========================================
        EmployeeDAO.closeFactory();
        System.out.println("\n=== ĐÃ HOÀN TẤT TOÀN BỘ CÁC TODO CỦA SLOT 2 ===");
    }
}
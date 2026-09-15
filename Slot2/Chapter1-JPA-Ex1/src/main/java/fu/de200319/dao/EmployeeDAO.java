package fu.de200319.dao;

import fu.de200319.pojo.Employee;
import fu.de200319.pojo.Gender;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;
import jakarta.persistence.TypedQuery;
import java.util.List;

public class EmployeeDAO {

    private static final EntityManagerFactory emf =
            Persistence.createEntityManagerFactory("hsf302FU");

    // ==========================================
    // TODO 1 & 2: Xây dựng các phương thức cơ bản (Create, Read theo ID)
    // ==========================================

    public void save(Employee employee) {
        EntityManager em = emf.createEntityManager();
        try {
            em.getTransaction().begin();
            em.persist(employee);
            em.getTransaction().commit();
        } catch (Exception e) {
            if (em.getTransaction().isActive()) {
                em.getTransaction().rollback();
            }
            throw e;
        } finally {
            em.close();
        }
    }

    public Employee findById(Long id) {
        EntityManager em = emf.createEntityManager();
        try {
            return em.find(Employee.class, id);
        } finally {
            em.close();
        }
    }

    // ==========================================
    // TODO 3: Bổ sung phương thức Update và Delete
    // ==========================================

    public void update(Employee employee) {
        EntityManager em = emf.createEntityManager();
        try {
            em.getTransaction().begin();
            em.merge(employee);
            em.getTransaction().commit();
        } catch (Exception e) {
            if (em.getTransaction().isActive()) {
                em.getTransaction().rollback();
            }
            throw e;
        } finally {
            em.close();
        }
    }

    public void delete(Long id) {
        EntityManager em = emf.createEntityManager();
        try {
            em.getTransaction().begin();
            Employee employee = em.find(Employee.class, id);
            if (employee != null) {
                em.remove(employee);
            }
            em.getTransaction().commit();
        } catch (Exception e) {
            if (em.getTransaction().isActive()) {
                em.getTransaction().rollback();
            }
            throw e;
        } finally {
            em.close();
        }
    }

    // ==========================================
    // TODO 4: Viết phương thức lấy danh sách toàn bộ nhân viên (findAll)
    // ==========================================

    public List<Employee> findAll() {
        EntityManager em = emf.createEntityManager();
        try {
            return em.createQuery("SELECT e FROM Employee e", Employee.class).getResultList();
        } finally {
            em.close();
        }
    }

    // ==========================================
    // TODO 6: Bổ sung phương thức tìm kiếm nâng cao (Theo tên hoặc Giới tính)
    // ==========================================

    public List<Employee> findByFullName(String nameKeyword) {
        EntityManager em = emf.createEntityManager();
        try {
            String jpql = "SELECT e FROM Employee e WHERE e.fullName LIKE :name";
            TypedQuery<Employee> query = em.createQuery(jpql, Employee.class);
            query.setParameter("name", "%" + nameKeyword + "%");
            return query.getResultList();
        } finally {
            em.close();
        }
    }

    public List<Employee> findByGender(Gender gender) {
        EntityManager em = emf.createEntityManager();
        try {
            String jpql = "SELECT e FROM Employee e WHERE e.gender = :gender";
            TypedQuery<Employee> query = em.createQuery(jpql, Employee.class);
            query.setParameter("gender", gender);
            return query.getResultList();
        } finally {
            em.close();
        }
    }

    // ==========================================
    // TODO 8: Bổ sung phương thức phân trang (Pagination)
    // ==========================================

    public List<Employee> findWithPagination(int pageNumber, int pageSize) {
        EntityManager em = emf.createEntityManager();
        try {
            String jpql = "SELECT e FROM Employee e";
            TypedQuery<Employee> query = em.createQuery(jpql, Employee.class);
            query.setFirstResult((pageNumber - 1) * pageSize); // Vị trí bắt đầu lấy
            query.setMaxResults(pageSize);                    // Số lượng bản ghi mỗi trang
            return query.getResultList();
        } finally {
            em.close();
        }
    }
}
package fu.de200319.dao;

import fu.de200319.pojo.Employee;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;

public class EmployeeDAO {

    private static final EntityManagerFactory emf =
            Persistence.createEntityManagerFactory("hsf302FU");

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
}
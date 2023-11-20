package com.igman.technicaltest.repository.repositoryImpl;

import com.igman.technicaltest.entity.Customer;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

@Repository
@Transactional
public class CustomerRepositoryImpl {

    @PersistenceContext
    private EntityManager entityManager;

    public Customer findById(String customerId) {
        return entityManager.find(Customer.class, customerId);
    }

    public Customer findByPhone(String phoneNumber) {
        return entityManager.createQuery("SELECT c FROM Customer c WHERE c.phoneNumber = :phoneNumber", Customer.class)
                .setParameter("phoneNumber", phoneNumber)
                .getResultList()
                .stream()
                .findFirst()
                .orElse(null);
    }

    public void create(Customer customer) {
        entityManager.persist(customer);
    }

    public void updateMethod(Customer customer) {
        entityManager.createQuery("UPDATE Customer c SET c.name = :name, c.address = :address WHERE c.id = :id")
                .setParameter("name", customer.getName())
                .setParameter("address", customer.getAddress())
                .setParameter("id", customer.getId())
                .executeUpdate();
    }

    public List<Customer> findAllCustomers() {
        return entityManager.createQuery("SELECT c FROM Customer c", Customer.class)
                .getResultList();
    }
}

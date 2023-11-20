package com.igman.technicaltest.repository.repositoryImpl;

import com.igman.technicaltest.dto.request.transaction.TransactionRequest;
import com.igman.technicaltest.dto.response.TransactionResponse;
import com.igman.technicaltest.entity.Customer;
import com.igman.technicaltest.entity.Transaction;
import com.igman.technicaltest.repository.TransactionRepository;
import com.igman.technicaltest.service.CustomerService;
import com.igman.technicaltest.util.ValidationUtil;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.time.LocalDateTime;
import java.util.List;

@Repository
@Transactional
@AllArgsConstructor
public class TransactionRepositoryImpl {


    @PersistenceContext
    private EntityManager entityManager;

    public List<Transaction> findAll() {
        return entityManager.createNativeQuery("SELECT * FROM transaction", Transaction.class)
                .getResultList();
    }

    public Transaction findById(String transactionId) {
        return entityManager.find(Transaction.class, transactionId);
    }


}

package com.igman.technicaltest.repository;

import com.igman.technicaltest.entity.Transaction;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Optional;

@Repository
public interface TransactionRepository extends JpaRepository<Transaction,String>, JpaSpecificationExecutor<Transaction> {
    @Modifying
    @Query(value = "INSERT INTO transaction (transaction_type, amount, timestamp, customer_id) " +
            "VALUES (:transactionType, :amount, :timestamp, :customerId) ", nativeQuery = true)
    Transaction createTransaction(
            @Param("transactionType") String transactionType,
            @Param("amount") BigDecimal amount,
            @Param("timestamp") LocalDateTime timestamp,
            @Param("customerId") String customerId
    );

    @Query(value = "SELECT * FROM transaction", nativeQuery = true)
    Page<Transaction> findAll(Pageable pageable);

    @Query(value = "SELECT * FROM transaction WHERE id = :transactionId", nativeQuery = true)
    Optional<Transaction> findById(@Param("transactionId") String transactionId);

}

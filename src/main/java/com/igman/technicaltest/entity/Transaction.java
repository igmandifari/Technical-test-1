package com.igman.technicaltest.entity;

import com.igman.technicaltest.constant.TransactionType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name="t_transaction")
public class Transaction {
    @Id
    @GenericGenerator(strategy = "uuid2", name = "system-uuid")
    @GeneratedValue(generator = "system-uuid")
    private String id;

    @ManyToOne
    @JoinColumn(name = "source_account_id")
    private Account sourceAccount;

    @Column(name = "transaction_type")
    private TransactionType transactionType;

    private BigDecimal amount;
    private LocalDateTime timestamp;

    @ManyToOne
    @JoinColumn(name = "customer_id")
    private Customer customer;



}
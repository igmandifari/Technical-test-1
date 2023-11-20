package com.igman.technicaltest.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.math.BigDecimal;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name="m_account")
public class Account {
    @Id
    @GenericGenerator(strategy = "uuid2", name = "system-uuid")
    @GeneratedValue(generator = "system-uuid")
    private String id;

    @OneToOne
    @JoinColumn(name = "customer_id")
    private Customer customerId;
    private BigDecimal balance;

//    @OneToMany(mappedBy = "sourceAccount")
//    private List<Transaction> outgoingTransactions;
//
//    @OneToMany(mappedBy = "targetAccount")
//    private List<Transaction> incomingTransactions;
}

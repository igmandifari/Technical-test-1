package com.igman.technicaltest.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name="m_customer")
public class Customer {
    @Id
    @GenericGenerator(name="uuid",strategy = "uuid")
    @GeneratedValue(generator = "uuid")
    private String id;
    private String name;
    private String pin;
    private String address;
    @Column(name = "phone_number",unique = true)
    private String phoneNumber;

    @OneToOne(mappedBy = "customerId", cascade = CascadeType.ALL)
    private Account account;

//    @OneToMany(mappedBy = "customer")
//    private List<Account> accounts;
//
//    @OneToMany(mappedBy = "customer")
//    private List<Transaction> transactions;

    @OneToOne
    @JoinColumn(name = "user_credential_id", unique = true)
    private UserCredential userCredential;
}

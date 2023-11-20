package com.igman.technicaltest.repository;

import com.igman.technicaltest.entity.Account;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

@Repository
public interface AcountRepository  extends JpaRepository<Account,String>, JpaSpecificationExecutor<Account> {
}

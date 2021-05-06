package com.transaction.transaction.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.transaction.transaction.entities.Transaction;

import java.util.List;

@Repository
public interface TransactionRepository extends JpaRepository<Transaction, Long> {

//    @Query("select tr from Transaction tr" +
//            "join ")
    List<Transaction> findAllByAccountId(Long id);
}

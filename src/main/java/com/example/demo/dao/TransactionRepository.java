package com.example.demo.dao;

import com.example.demo.model.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TransactionRepository extends JpaRepository<Transaction, Long> {

    boolean existsByIdempotencyKey(String key);


}

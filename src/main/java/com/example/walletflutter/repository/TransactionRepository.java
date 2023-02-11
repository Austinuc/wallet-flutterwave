package com.example.walletflutter.repository;

import com.example.walletflutter.entity.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface TransactionRepository extends JpaRepository<Transaction, String> {

    Optional<Transaction> findByTxRef(String tx_ref);
}

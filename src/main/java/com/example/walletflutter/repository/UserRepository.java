package com.example.walletflutter.repository;

import com.example.walletflutter.entity.Customer;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<Customer, Long> {

}

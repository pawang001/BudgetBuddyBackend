package com.budget_buddy.repo;

import com.budget_buddy.model.Transaction;
import com.budget_buddy.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TransactionRepo extends JpaRepository<Transaction, Long> {
    List<Transaction> findByUser(User user);

}

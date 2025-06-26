package com.budget_buddy.service;

import com.budget_buddy.dto.TransactionRequest;
import com.budget_buddy.dto.TransactionResponse;
import com.budget_buddy.model.Transaction;
import com.budget_buddy.model.User;
import com.budget_buddy.repo.TransactionRepo;
import com.budget_buddy.repo.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class TransactionService {

    @Autowired
    private TransactionRepo repo;
    @Autowired
    private UserRepo userRepo;

    public TransactionResponse createTransaction(TransactionRequest request) {
        String email = SecurityContextHolder.getContext().getAuthentication().getName();
        User user = userRepo.findByEmail(email).orElseThrow();

        Transaction transaction = new Transaction();
        transaction.setTitle(request.getTitle());
        transaction.setAmount(request.getAmount());
        transaction.setCategory(request.getCategory());
        transaction.setType(request.getType());
        transaction.setDateTime(LocalDateTime.now());
        transaction.setUser(user);

        Transaction saved = repo.save(transaction);

        return new TransactionResponse(saved.getId(), saved.getTitle(),
                saved.getAmount(), saved.getType(), saved.getCategory(), saved.getDateTime());
    }

    public List<TransactionResponse> getAllTransactions() {
        String email = SecurityContextHolder.getContext().getAuthentication().getName();
        User user = userRepo.findByEmail(email).orElseThrow();

        List<Transaction> transactions = repo.findByUser(user);
        return transactions.stream().map(t -> new TransactionResponse(t.getId(), t.getTitle(), t.getAmount(),
                        t.getType(), t.getCategory(), t.getDateTime()))
                .collect(Collectors.toList());
    }

    public String deleteTransaction(Long id) {
        String email = SecurityContextHolder.getContext().getAuthentication().getName();
        User user = userRepo.findByEmail(email).orElseThrow();
        Transaction transaction = repo.findById(id).orElseThrow(() ->
                new RuntimeException("Transaction not found."));

        if(!transaction.getUser().getId().equals(user.getId())) {
            throw new RuntimeException("Unauthorized to delete this transaction");
        }
        repo.delete(transaction);

        return "Transaction deleted successfully";
    }


    public Transaction updateTransaction(Long id, Transaction updatedTransaction) {
        String email = SecurityContextHolder.getContext().getAuthentication().getName();
        User user = userRepo.findByEmail(email).orElseThrow();
        Transaction existing = repo.findById(id).orElseThrow(() ->
                new RuntimeException("Transaction not found."));

        if(!existing.getUser().getId().equals(user.getId())) {
            throw new RuntimeException("Unauthorized to update this transaction");
        }

        existing.setAmount(updatedTransaction.getAmount());
        existing.setType(updatedTransaction.getType());
        existing.setCategory(updatedTransaction.getCategory());
        existing.setTitle(updatedTransaction.getTitle());
        existing.setDateTime(LocalDateTime.now());

        return repo.save(existing);
    }

}

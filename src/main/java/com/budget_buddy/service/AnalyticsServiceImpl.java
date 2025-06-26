package com.budget_buddy.service;

import com.budget_buddy.dto.StatsResponse;
import com.budget_buddy.enums.TransactionType;
import com.budget_buddy.model.Transaction;
import com.budget_buddy.model.User;
import com.budget_buddy.repo.TransactionRepo;
import com.budget_buddy.repo.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class AnalyticsServiceImpl implements AnalyticsService{

    @Autowired
    private TransactionRepo transactionRepo;

    @Autowired
    private UserRepo userRepo;

    @Override
    public StatsResponse getStats() {
        String email = SecurityContextHolder.getContext().getAuthentication().getName();
        User user = userRepo.findByEmail(email).orElseThrow();

        List<Transaction> transactions = transactionRepo.findByUser(user);

        double totalIncome = 0;
        double totalExpnese= 0;
        Map<String, Double> categoryBreakdown = new HashMap<>();

        for(Transaction tx : transactions) {
            if (tx.getType() == TransactionType.INCOME)
            {
                totalIncome += tx.getAmount();
            } else {
                totalExpnese += tx.getAmount();
                categoryBreakdown.merge(tx.getCategory(), tx.getAmount(), Double::sum);
            }
        }

        double balance = totalIncome - totalExpnese;

        return new StatsResponse(totalIncome, totalExpnese, balance, categoryBreakdown);

    }
}

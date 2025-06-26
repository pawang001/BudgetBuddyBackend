package com.budget_buddy.dto;

import com.budget_buddy.enums.TransactionType;
import lombok.Data;

@Data
public class TransactionRequest {
    private String title;
    private double amount;
    private TransactionType type;
    private String category;
}

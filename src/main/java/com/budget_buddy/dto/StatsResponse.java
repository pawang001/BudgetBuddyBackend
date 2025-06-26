package com.budget_buddy.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Map;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class StatsResponse {

    private double totalIncome;
    private double totalExpense;
    private double balance;
    private Map<String, Double> categoryBreakdown;
}

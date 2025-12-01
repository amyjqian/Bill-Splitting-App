package main.usecase;

import main.entities.Expense;

import java.util.List;

public interface SettlementCalculator {
    String suggestedPayment(List<Expense> expenses);
}

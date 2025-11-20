package main.usecase;

import main.entities.Group;

public interface SettlementCalculator {
    String suggestedPayment(Group group);
}

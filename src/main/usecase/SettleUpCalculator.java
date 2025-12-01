package main.usecase;

import main.entities.Expense;
import main.entities.User;

import java.util.*;

public class SettleUpCalculator implements SettlementCalculator{
    @Override
    public String suggestedPayment(List<Expense> expenses) {
        Map<User, Double> balance = new HashMap<>();
        for (Expense expense : expenses){
            if (!expense.getSettled()){
                User payer = expense.getPaidBy();
                double amount = expense.getAmount();
                balance.put(payer,
                        balance.getOrDefault(payer,0.0) + amount);
                List<User> participants = expense.getParticipants();
                double eachOwes = expense.calculateEqualShare();
                for (User participant : participants){
                    if (participant.getId() != null &&
                            payer.getId() != null &&
                            !participant.getId().equals(payer.getId())){
                        balance.put(participant,
                                balance.getOrDefault(participant, 0.0)-eachOwes);
                    }
                }
            }
        }
        List<Map.Entry<User, Double>> payers = new ArrayList<>();
        List<Map.Entry<User, Double>> receivers = new ArrayList<>();
        for (Map.Entry<User, Double> entry : balance.entrySet()){
            if (entry.getValue() > 0){
                receivers.add(entry);
            }
            else if (entry.getValue() < 0) {
                payers.add(entry);
            }
        }
        payers.sort((a, b) -> Double.compare(a.getValue(), b.getValue()));
        receivers.sort((a, b) -> Double.compare(b.getValue(), a.getValue()));
        StringBuilder messageBuilder = new StringBuilder();
        for (Map.Entry<User, Double> payer: payers) {
            for  (Map.Entry<User, Double> receiver : receivers) {
                if (receiver.getValue() != 0 && payer.getValue() != 0 ){
                    double payment;
                    payment = Math.min(receiver.getValue(), -payer.getValue());
                    messageBuilder.append(payer.getKey().getDisplayName());
                    messageBuilder.append(" owes ");
                    messageBuilder.append(receiver.getKey().getDisplayName());
                    messageBuilder.append(" $");
                    messageBuilder.append(String.format("%.2f", payment));
                    messageBuilder.append(".\n");
                    receiver.setValue(receiver.getValue() - payment);
                    payer.setValue(payer.getValue() + payment);
                }
            }
        }
        String message = messageBuilder.toString();
        if (message.length() == 0){
            return "No payments required";
        }
        return message;
    }
}

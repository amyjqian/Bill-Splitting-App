package usecase.SettleUp;

import entities.Expense;
import entities.User;

import java.util.*;

public class SettleUpCalculator implements SettlementCalculator {
    @Override
    public String suggestedPayment(List<Expense> expenses) {
        Map<String, Double> balance = new HashMap<>();
        for (Expense expense : expenses){
            if (!expense.getSettled()){
                User paidBy = expense.getPaidBy();
                double amount = expense.getAmount();
                List<User> participants = expense.getParticipants();
                double eachOwes = expense.calculateEqualShare();
                balance.put(paidBy.getLastName(),
                        balance.getOrDefault(paidBy.getLastName(),0.0) + amount - eachOwes);
                for (User participant : participants){
                    if (participant.getFirstName() != null &&
                            paidBy.getFirstName() != null &&
                            !participant.getFirstName().equals(paidBy.getFirstName())){
                        balance.put(participant.getLastName(),
                                balance.getOrDefault(participant.getLastName(), 0.0) - eachOwes);
                    }
                }
            }
        }
        List<Map.Entry<String, Double>> payers = new ArrayList<>();
        List<Map.Entry<String, Double>> receivers = new ArrayList<>();
        for (Map.Entry<String, Double> entry : balance.entrySet()){
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
        for (Map.Entry<String, Double> payer: payers) {
            for  (Map.Entry<String, Double> receiver : receivers) {
                if (receiver.getValue() != 0 && payer.getValue() != 0 ){
                    double payment;
                    payment = Math.min(receiver.getValue(), -payer.getValue());
                    messageBuilder.append(payer.getKey());
                    messageBuilder.append(" owes ");
                    messageBuilder.append(receiver.getKey());
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

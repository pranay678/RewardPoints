package com.pyramid.rewardPoints.service;

import com.pyramid.rewardPoints.model.Transaction;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


@Service
public class RewardCalculatorService {

    public Map<String, Map<String, Integer>> calculateRewardPoints(List<Transaction> transactions) {
        Map<String, Map<String, Integer>> rewardsPerCustomerPerMonth = new HashMap<>();

        for (Transaction transaction : transactions) {
            String customerId = transaction.getCustomerId();
            LocalDate transactionDate = transaction.getDate();
            int rewardPoints = calculateRewardPointsForTransaction(transaction.getAmount());

            String monthKey = getMonthKey(transactionDate);

            rewardsPerCustomerPerMonth.computeIfAbsent(customerId, k -> new HashMap<>())
                    .merge(monthKey, rewardPoints, Integer::sum);
        }

        return rewardsPerCustomerPerMonth;
    }

    public int calculateRewardPointsForTransaction(double amount) {
        int rewardPoints = 0;

        if (amount > 100) {
            rewardPoints += 2 * (int) (amount - 100);
            rewardPoints += 1 * (100 - 50);
        }

        if (amount > 50 && amount <= 100) {
            rewardPoints += (int) (amount - 50);
        }

        return rewardPoints;
    }

    private String getMonthKey(LocalDate date) {
        return date.getMonth().name() + " " + date.getYear();
    }

}


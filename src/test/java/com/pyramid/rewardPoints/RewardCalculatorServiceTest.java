package com.pyramid.rewardPoints;

import com.pyramid.rewardPoints.model.Transaction;
import com.pyramid.rewardPoints.service.RewardCalculatorService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class RewardCalculatorServiceTest {

    private final RewardCalculatorService rewardService = new RewardCalculatorService();

    @Test
    public void testCalculateRewardPointsForTransaction() {
        // Test case 1: Amount = $120
        int rewardPoints1 = rewardService.calculateRewardPointsForTransaction(120.0);
        Assertions.assertEquals(90, rewardPoints1);

        // Test case 2: Amount = $80
        int rewardPoints2 = rewardService.calculateRewardPointsForTransaction(80.0);
        Assertions.assertEquals(30, rewardPoints2);

        // Test case 3: Amount = $40
        int rewardPoints3 = rewardService.calculateRewardPointsForTransaction(40.0);
        Assertions.assertEquals(0, rewardPoints3);
    }

    @Test
    public void testCalculateRewardPoints() {
        // Test case 1: Single customer, multiple transactions
        List<Transaction> transactions1 = new ArrayList<>();
        transactions1.add(new Transaction("C001", 120.0, LocalDate.of(2023, 6, 1)));
        transactions1.add(new Transaction("C001", 80.0, LocalDate.of(2023, 6, 10)));
        Map<String, Map<String, Integer>> rewards1 = rewardService.calculateRewardPoints(transactions1);

        Map<String, Integer> customerRewards1 = rewards1.get("C001");
        Assertions.assertNotNull(customerRewards1);
        Assertions.assertEquals(120, customerRewards1.get("JUNE 2023"));

        // Test case 2: Multiple customers, multiple transactions
        List<Transaction> transactions2 = new ArrayList<>();
        transactions2.add(new Transaction("C001", 120.0, LocalDate.of(2023, 6, 1)));
        transactions2.add(new Transaction("C001", 80.0, LocalDate.of(2023, 6, 10)));
        transactions2.add(new Transaction("C002", 150.0, LocalDate.of(2023, 7, 5)));
        Map<String, Map<String, Integer>> rewards2 = rewardService.calculateRewardPoints(transactions2);

        Map<String, Integer> customerRewards2 = rewards2.get("C001");
        Assertions.assertNotNull(customerRewards2);
        Assertions.assertEquals(120, customerRewards2.get("JUNE 2023"));

        Map<String, Integer> customerRewards3 = rewards2.get("C002");
        Assertions.assertNotNull(customerRewards3);
        Assertions.assertEquals(150, customerRewards3.get("JULY 2023"));
    }
}

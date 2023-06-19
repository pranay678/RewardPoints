package com.pyramid.rewardPoints;

import com.pyramid.rewardPoints.controller.TransactionController;
import com.pyramid.rewardPoints.model.Transaction;
import com.pyramid.rewardPoints.service.RewardCalculatorService;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

public class TransactionControllerTest {

    @Mock
    private RewardCalculatorService rewardService;

    public TransactionControllerTest() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testCalculateRewards() {
        // Mock input transaction data
        List<Transaction> transactions = new ArrayList<>();
        transactions.add(new Transaction("C001", 120.0, LocalDate.now() ));
        transactions.add(new Transaction("C001", 80.0, LocalDate.now()));

        // Mock expected reward points
        Map<String, Map<String, Integer>> expectedRewards = new HashMap<>();
        Map<String, Integer> customerRewards = new HashMap<>();
        customerRewards.put("June 2023", 90);
        expectedRewards.put("C001", customerRewards);

        // Mock the rewardService.calculateRewardPoints() method
        when(rewardService.calculateRewardPoints(transactions)).thenReturn(expectedRewards);

        // Create the TransactionController instance
        TransactionController controller = new TransactionController(rewardService);

        // Make the request to calculate rewards
        ResponseEntity<Map<String, Map<String, Integer>>> response = controller.calculateRewards(transactions);

        // Verify the response status code
        assertEquals(HttpStatus.OK, response.getStatusCode());

        // Verify the response body
        Map<String, Map<String, Integer>> actualRewards = response.getBody();
        assertEquals(expectedRewards, actualRewards);
    }
}

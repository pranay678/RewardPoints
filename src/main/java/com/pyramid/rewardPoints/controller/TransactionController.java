package com.pyramid.rewardPoints.controller;

import com.pyramid.rewardPoints.model.Transaction;
import com.pyramid.rewardPoints.service.RewardCalculatorService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/transactions")
public class TransactionController {

    private final RewardCalculatorService rewardService;

    public TransactionController(RewardCalculatorService rewardService) {
        this.rewardService = rewardService;
    }

    @PostMapping("/rewards")
    public ResponseEntity<Map<String, Map<String, Integer>>> calculateRewards(@RequestBody List<Transaction> transactions) {
        Map<String, Map<String, Integer>> rewardsPerCustomerPerMonth = rewardService.calculateRewardPoints(transactions);
        return ResponseEntity.ok(rewardsPerCustomerPerMonth);
    }
}


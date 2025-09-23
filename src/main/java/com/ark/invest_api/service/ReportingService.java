package com.ark.invest_api.service;

import com.ark.invest_api.dto.*;
import com.ark.invest_api.exceptions.NotFoundException;
import com.ark.invest_api.repository.FundRepository;
import com.ark.invest_api.repository.InvestorRepository;
import com.ark.invest_api.repository.TransactionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class ReportingService {

    @Autowired
    private FundRepository fundRepo;

    @Autowired
    private TransactionRepository txRepo;

    @Autowired
    private InvestorRepository investorRepo;

    public FundSummary fundSummary(Long fundId) {
        Fund f = fundRepo.findById(fundId).orElseThrow();
        List<TransactionRequest> txs = txRepo.findByFundId(fundId);

        BigDecimal contrib = sumByType(txs, TransactionType.CONTRIBUTION);
        BigDecimal interest = sumByType(txs, TransactionType.INTEREST_INCOME);
        BigDecimal distr = sumByType(txs, TransactionType.DISTRIBUTION);
        BigDecimal expense = sumByType(txs, TransactionType.GENERAL_EXPENSE);
        BigDecimal mgmt = sumByType(txs, TransactionType.MANAGEMENT_FEE);

        BigDecimal net = contrib.add(interest)
                .subtract(distr)
                .subtract(expense)
                .subtract(mgmt);

        return new FundSummary(
                f.getId(), f.getName(), contrib, interest, distr, expense, mgmt, net, net
        );
    }

    private static BigDecimal sumByType(List<TransactionRequest> txs, TransactionType type) {
        return txs.stream()
                .filter(t -> t.getType() == type)
                .map(TransactionRequest::getAmount)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }

    public InvestorSummary investorSummary(Long investorId) {
        Investor inv = investorRepo.findById(investorId).orElseThrow();
        List<TransactionRequest> txs = txRepo.findByInvestorId(investorId);

        Map<Long, List<TransactionRequest>> byFund = txs.stream().collect(Collectors.groupingBy(t -> t.getFundId()));
        Map<Long, InvestorFundHolding> holdings = new LinkedHashMap<>();
        for (var entry : byFund.entrySet()) {
            Long fundId = entry.getKey();



            List<TransactionRequest> list = entry.getValue();


            String fundName = fundRepo.findNameById(fundId)
                    .orElseThrow(() -> new NotFoundException("Fund not found: " + fundId));

            BigDecimal contributed = list.stream()
                    .filter(t -> t.getType() == TransactionType.CONTRIBUTION || t.getType() == TransactionType.INTEREST_INCOME)
                    .map(TransactionService::signedAmount)
                    .reduce(BigDecimal.ZERO, BigDecimal::add);

            BigDecimal distributed = list.stream()
                    .filter(t -> t.getType() == TransactionType.DISTRIBUTION)
                    .map(TransactionRequest::getAmount)
                    .reduce(BigDecimal.ZERO, BigDecimal::add);

            BigDecimal expenses = list.stream()
                    .filter(t -> t.getType() == TransactionType.GENERAL_EXPENSE || t.getType() == TransactionType.MANAGEMENT_FEE)
                    .map(TransactionRequest::getAmount)
                    .reduce(BigDecimal.ZERO, BigDecimal::add);

            BigDecimal net = contributed.subtract(distributed).subtract(expenses);

            // For simplicity, currentBalanceShare == net (no unitization logic)
            holdings.put(fundId, new InvestorFundHolding(fundId, fundName, contributed, distributed, net, net));
        }

        String name = inv.getFirstName() + " " + inv.getLastName();
        return new InvestorSummary(inv.getId(), name, holdings);
    }
}

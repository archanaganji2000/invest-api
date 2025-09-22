package com.ark.invest_api.service;

import com.ark.invest_api.dto.*;
import com.ark.invest_api.repository.FundRepository;
import com.ark.invest_api.repository.InvestorRepository;
import com.ark.invest_api.repository.TransactionRepository;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestBody;

import java.math.BigDecimal;
import java.util.List;

@Service
@RequiredArgsConstructor
public class TransactionService {
    @Autowired
    private  TransactionRepository txRepo;

    @Autowired
    private  FundRepository fundRepo;

    @Autowired
    private  InvestorRepository investorRepo;

    public List<Transaction> all() { return txRepo.findAll(); }
    public Transaction get(Long id) { return txRepo.findById(id).orElseThrow(); }

    @Transactional
    public Transaction create(TransactionRequest req) {
        Fund fund = fundRepo.findById(req.getFundId()).orElseThrow(()->new NotFoundException("Fund not found"));
        Investor investor = investorRepo.findById(req.getInvestorId()).orElseThrow(()->new NotFoundException("Investor not found"));
        // ensure fund & investor exist and link
        Transaction t = new Transaction();
        t.setFund(fund);
        t.setInvestor(investor);
        t.setDate(req.getDate());
        t.setAmount(req.getAmount());
        t.setType(req.getType());

        fund.getInvestors().add(investor);
        investor.getFunds().add(fund);
        return txRepo.save(t);
    }

    @Transactional
    public Transaction update(Long id, Transaction patch) {
        Transaction t = get(id);
        t.setDate(patch.getDate());
        t.setAmount(patch.getAmount());
        t.setType(patch.getType());
        return t;
    }

    @Transactional
    public void delete(Long id) { txRepo.deleteById(id); }

    public static boolean isCredit(TransactionType type) {
        return switch (type) {
            case CONTRIBUTION, INTEREST_INCOME -> true;
            default -> false;
        };
    }

    public static boolean isDebit(TransactionType type) {
        return !isCredit(type);
    }

    public static BigDecimal signedAmount(Transaction tx) {
        return isCredit(tx.getType()) ? tx.getAmount() : tx.getAmount().negate();
    }
}

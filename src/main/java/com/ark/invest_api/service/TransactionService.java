package com.ark.invest_api.service;

import com.ark.invest_api.dto.*;
import com.ark.invest_api.exceptions.NotFoundException;
import com.ark.invest_api.repository.FundRepository;
import com.ark.invest_api.repository.InvestorRepository;
import com.ark.invest_api.repository.TransactionRepository;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;

@Service
public class TransactionService {
    private final TransactionRepository txRepo;

    private final FundRepository fundRepo;

    private final InvestorRepository investorRepo;

    public TransactionService(FundRepository fundRepo, InvestorRepository investorRepo, TransactionRepository txRepo) {
        this.fundRepo = fundRepo;
        this.investorRepo = investorRepo;
        this.txRepo = txRepo;
    }

    public List<TransactionRequest> all() { return txRepo.findAll(); }
    public TransactionRequest get(Long id) { return txRepo.findById(id).orElseThrow(() -> new NotFoundException("Transaction not found with id " + id)); }

    public TransactionRequest create(TransactionRequest req) {
        Fund fund = fundRepo.findById(req.getFundId()).orElseThrow(()->new NotFoundException("Fund not found"));
        Investor investor = investorRepo.findById(req.getInvestorId()).orElseThrow(()->new NotFoundException("Investor not found"));
        return txRepo.save(req);
    }


    public TransactionRequest update(Long id, UpdateRequest patch) {
        TransactionRequest t = get(id);
        t.setDate(patch.date());
        t.setAmount(patch.amount());
        t.setType(patch.type());

        return txRepo.save(t);
    }

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

    public static BigDecimal signedAmount(TransactionRequest tx) {
        return isCredit(tx.getType()) ? tx.getAmount() : tx.getAmount().negate();
    }
}

package com.ark.invest_api.repository;

import com.ark.invest_api.dto.Transaction;
import com.ark.invest_api.dto.TransactionRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.stereotype.Repository;

import java.util.List;

@EnableJpaRepositories
@Repository
public interface TransactionRepository extends JpaRepository<TransactionRequest, Long> {

    List<TransactionRequest> findByFundId(Long fundId);
    List<TransactionRequest> findByInvestorId(Long investorId);

    boolean existsByFundId(Long fundId);


    boolean existsByInvestorId(Long investorId);
}
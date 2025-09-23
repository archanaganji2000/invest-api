package com.ark.invest_api.repository;

import com.ark.invest_api.dto.Investor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@EnableJpaRepositories
@Repository
public interface InvestorRepository extends JpaRepository<Investor, Long> {
    Optional<Investor> findByEmail(String email);
    boolean existsByEmail(String email);
}
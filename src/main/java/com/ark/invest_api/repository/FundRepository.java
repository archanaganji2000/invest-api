package com.ark.invest_api.repository;

import com.ark.invest_api.dto.Fund;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@EnableJpaRepositories
@Repository
public interface FundRepository extends JpaRepository<Fund, Long>{
    Optional<Fund> findByName(String name);


}
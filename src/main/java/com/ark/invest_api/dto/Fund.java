package com.ark.invest_api.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Entity
public class Fund {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)  // DB auto-increment
    private Long id;

    @Column(nullable = false, unique = true)
    private String name;

    @Column(nullable = false)
    private String currency;


//    @ManyToMany
//@Transient
//    @JoinTable(
//            name = "fund_investor",
//            joinColumns = @JoinColumn(name = "fund_id"),
//            inverseJoinColumns = @JoinColumn(name = "investor_id")
//    )
//    private Set<Investor> investors = new HashSet<>();
//
//    @JsonProperty("investorIds")
//    public Set<Long> getInvestorIds() {
//        return investors.stream()
//                .map(Investor::getId)
//                .collect(Collectors.toSet());
//    }

//   @Transient
//
//    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
//    private List<Transaction> transactions= new ArrayList<>();
//
//    public Set<Investor> getInvestors() {
//        return investors;
//    }
//
//    public void setInvestors(Set<Investor> investors) {
//        this.investors = investors;
//    }

//    public List<Transaction> getTransactions() {
//        return transactions;
//    }
//
//    public void setTransactions(List<Transaction> transactions) {
//        this.transactions = transactions;
//    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @Override
    public String toString() {
        return "Fund{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", currency='" + currency + '\'' +
                '}';
    }
}

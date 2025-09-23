package com.ark.invest_api.dto;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.validator.constraints.Length;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity
@Table(name = "fund")
public class Fund {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)  // DB auto-increment
    private Long id;

    @Column(nullable = false, unique = true)
    @NotBlank
    private String name;

    @Column(nullable = false)
    @Length(min = 3, max = 3)
    private String currency;

    @Override
    public String toString() {
        return "Fund{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", currency='" + currency + '\'' +
                '}';
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

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
}

package com.example.nomo.repository;

import com.example.nomo.model.Debt;
import com.example.nomo.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DebtRepository extends JpaRepository<Debt, Long> {
    List<Debt> findByDebtor(User debtor);
    List<Debt> findByCreditor(User creditor);
}

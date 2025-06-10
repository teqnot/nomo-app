package com.example.nomo.service;

import com.example.nomo.dto.DebtDto;
import com.example.nomo.model.Debt;
import com.example.nomo.model.User;
import com.example.nomo.repository.DebtRepository;
import com.example.nomo.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class DebtService {
    private final DebtRepository debtRepository;
    private final UserRepository userRepository;

    public DebtService(DebtRepository debtRepository, UserRepository userRepository) {
        this.debtRepository = debtRepository;
        this.userRepository = userRepository;
    }

    public Debt createDebt(Long debtorId, Long creditorId, Double amount, String name, String description, Long roomId) {
        System.out.println("DebtorId: " + debtorId);
        User debtor = userRepository.findById(debtorId).orElseThrow();
        User creditor = userRepository.findById(creditorId).orElseThrow();

        Debt debt = new Debt();
        debt.setDebtor(debtor);
        debt.setCreditor(creditor);
        debt.setAmount(amount);
        debt.setName(name);
        debt.setDescription(description);
        debt.setIsPaid(false);
        debt.setCreatedAt(LocalDateTime.now());

        return debtRepository.save(debt);
    }

    public void payDebt(Long debtId, Double amountPaid) {
        Debt debt = debtRepository.findById(debtId).orElseThrow();
        if (debt.getIsPaid()) {
            throw new RuntimeException("Долг уже погашен");
        }

        Double remaining = debt.getAmount() - amountPaid;

        if (remaining <= 0) {
            debt.setIsPaid(true);
            debt.setAmount(0.0);
            debtRepository.delete(debt);
        } else {
            debt.setAmount(remaining);
            debtRepository.save(debt);
        }
    }

    public List<DebtDto> getDebtsByUser(Long userId) {
        User user = userRepository.findById(userId).orElseThrow();
        List<Debt> debtsFromMe = debtRepository.findByDebtor(user);
        List<Debt> debtsToMe = debtRepository.findByCreditor(user);
        List<DebtDto> debtDtos = new ArrayList<>();

        for (Debt debt : debtsFromMe) {
            DebtDto dtoFromMe = convertToDto(debt);
            debtDtos.add(dtoFromMe);
        }

        for (Debt debt : debtsToMe) {
            DebtDto dtoToMe = convertToDto(debt);
            debtDtos.add(dtoToMe);
        }

        return debtDtos;
    }

    private DebtDto convertToDto(Debt debt) {
        DebtDto dto = new DebtDto();
        dto.setId(debt.getId());
        dto.setCreditorId(debt.getCreditor().getId());
        dto.setCreditorUsername(debt.getCreditor().getUsername());
        dto.setDebtorId(debt.getDebtor().getId());
        dto.setDebtorUsername(debt.getDebtor().getUsername());
        dto.setAmount(debt.getAmount());
        dto.setName(debt.getName());
        dto.setDescription(debt.getDescription());
        dto.setIsPaid(debt.getIsPaid());
        dto.setCreatedAt(debt.getCreatedAt());
        return dto;
    }
}

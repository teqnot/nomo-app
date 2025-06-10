package com.example.nomo.controller;

import com.example.nomo.dto.DebtDto;
import com.example.nomo.dto.DebtRequest;
import com.example.nomo.dto.PayDebtRequest;
import com.example.nomo.model.Debt;
import com.example.nomo.service.DebtService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/debts")
public class DebtController {
    private final DebtService debtService;

    public DebtController(DebtService debtService) { this.debtService = debtService; }

    @PostMapping("/create")
    public ResponseEntity<?> createDebt(@RequestBody DebtRequest request) {
        System.out.println("Request body: " + request);
        Debt debt = debtService.createDebt(
                request.getDebtorId(),
                request.getCreditorId(),
                request.getAmount(),
                request.getName(),
                request.getDescription(),
                request.getRoomId()
        );

        DebtDto dto = new DebtDto();
        return ResponseEntity.ok(dto.responseForCreate(debt));
    }

    @PostMapping("/pay")
    public ResponseEntity<Void> payDebt(@RequestBody PayDebtRequest request) {
        debtService.payDebt(request.getDebtId(), request.getAmountPaid());
        return ResponseEntity.ok().build();
    }

    @PostMapping("/by-user")
    public ResponseEntity<List<DebtDto>> getDebtsByUser(@RequestBody Map<String, Long> body) {
        Long userId = body.get("userId");
        return ResponseEntity.ok(debtService.getDebtsByUser(userId));
    }
}

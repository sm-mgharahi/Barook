package org.example.barookwallet.infrastructure.api;

import lombok.AllArgsConstructor;
import org.example.barookwallet.application.services.WalletServices;
import org.example.barookwallet.infrastructure.api.dto.requests.UpdateBalanceRequest;
import org.example.barookwallet.infrastructure.api.dto.response.AddMoneyResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;

@RestController
@AllArgsConstructor
public class WalletResource {

    private final WalletServices walletServices;

    @GetMapping("get-balance")
    public ResponseEntity<BigDecimal> getBalance(Long userId) {
        return
                ResponseEntity.ok(
                        walletServices.getBalance(userId)
                );
    }

    @PostMapping("add-money")
    public ResponseEntity<AddMoneyResponse> addMoney(@RequestBody UpdateBalanceRequest updateBalanceRequest) {
        return
                ResponseEntity.ok(
                        new AddMoneyResponse(walletServices.updateBalance(updateBalanceRequest.userId(), BigDecimal.valueOf(updateBalanceRequest.amount())))
                );
    }
}
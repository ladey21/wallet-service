package com.example.demo.api;


import com.example.demo.model.Wallet;
import com.example.demo.service.WalletService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/wallets")
public class WalletController {

    private final WalletService walletService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Long createWallet(){
        return walletService.createWallet();
    }

    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public Wallet getWallet(@PathVariable(value = "id") Long id){
        return walletService.getWalletById(id);
    }

}

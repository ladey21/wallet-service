package com.example.demo.service;

import com.example.demo.dao.TransactionRepository;
import com.example.demo.dao.WalletRepository;
import com.example.demo.dto.Response;
import com.example.demo.dto.TransactionRequest;
import com.example.demo.exception.DuplicateRequestException;
import com.example.demo.exception.InsufficientFundException;
import com.example.demo.exception.WalletNotFoundException;
import com.example.demo.model.Transaction;
import com.example.demo.model.Wallet;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class WalletService {

    @Autowired
    private WalletRepository walletRepo;
    @Autowired
    private TransactionRepository txRepo;

    @Transactional
    public void transfer(TransactionRequest request) {
        if (txRepo.existsByIdempotencyKey(request.getIdempotencyKey())) {
            throw new DuplicateRequestException("duplicate transaction");
        };

        Wallet sender = walletRepo.findById(request.getSenderId())
                .orElseThrow(() -> new WalletNotFoundException("Sender not found"));

        if (sender.getBalance() < request.getAmount()) {
            throw new InsufficientFundException("Insufficient funds");
        }

        Wallet receiver = walletRepo.findById(request.getReceiverId())
                .orElseThrow(() -> new WalletNotFoundException("Receiver not found"));

        sender.setBalance(sender.getBalance() - request.getAmount());
        receiver.setBalance(receiver.getBalance() + request.getAmount());

        walletRepo.save(sender);
        walletRepo.save(receiver);
        txRepo.save(new Transaction(request));
    }


    public Long createWallet() {
        Wallet wallet = walletRepo.save(new Wallet());
        return wallet.getId();
    }

    public Wallet getWalletById(Long id){
        return walletRepo.findById(id)
                .orElseThrow(() -> new WalletNotFoundException("wallet not found"));
    }
}

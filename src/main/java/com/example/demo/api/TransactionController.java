package com.example.demo.api;

import com.example.demo.dto.Response;
import com.example.demo.dto.TransactionRequest;
import com.example.demo.service.WalletService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/transactions")
public class TransactionController {

    private final WalletService walletService;


    @PostMapping
    @ResponseStatus(HttpStatus.OK)
    public Response transact(@RequestBody TransactionRequest request){
        walletService.transfer(request);
        return new Response("200","credit successful");
    }
}

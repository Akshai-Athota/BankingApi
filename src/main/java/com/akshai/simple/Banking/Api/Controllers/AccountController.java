package com.akshai.simple.Banking.Api.Controllers;

import com.akshai.simple.Banking.Api.DTO.AccountDto;
import com.akshai.simple.Banking.Api.Exception.AccountNotFoundException;
import com.akshai.simple.Banking.Api.Exception.BalanceInsufficientException;
import com.akshai.simple.Banking.Api.Services.Impl.AccountService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/bank/account")
public class AccountController {
    private  AccountService accountService;

    public AccountController(AccountService accountService){
        this.accountService=accountService;
    }

    @PostMapping
    public ResponseEntity<AccountDto> createAccount(@RequestBody AccountDto accountDto){
        return ResponseEntity.ok(accountService.createAccount(accountDto));
    }

    @GetMapping("/{accountId}")
    public ResponseEntity<AccountDto> getAccount(@PathVariable Long accountId){
        try{
            return ResponseEntity.ok(accountService.getAccount(accountId));
        }catch (AccountNotFoundException anf){
            return ResponseEntity.badRequest().build();
        }
    }

    @PatchMapping("/{accountId}/deposit")
    public ResponseEntity<AccountDto> deposit(@PathVariable Long accountId,@RequestBody AccountDto accountDto){
        try{
            return ResponseEntity.ok(accountService.deposit(accountId,accountDto.getBalance()));
        }catch (AccountNotFoundException anf){
            return ResponseEntity.badRequest().build();
        }
    }

    @PatchMapping("/withdraw")
    public ResponseEntity<AccountDto> withdraw(@PathVariable Long accountId,@RequestBody AccountDto accountDto){
        try{
            return ResponseEntity.ok(accountService.withdraw(accountId,accountDto.getBalance()));
        }catch (AccountNotFoundException | BalanceInsufficientException anf){
            return ResponseEntity.badRequest().build();
        }
    }

    @GetMapping("/all")
    public ResponseEntity<List<AccountDto>> getAllAccount(){
        return ResponseEntity.ok(accountService.getAllAccounts());
    }
}

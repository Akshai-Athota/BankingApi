package com.akshai.simple.Banking.Api.Services.Impl;

import com.akshai.simple.Banking.Api.DTO.AccountDto;
import com.akshai.simple.Banking.Api.Exception.AccountNotFoundException;
import com.akshai.simple.Banking.Api.Exception.BalanceInsufficientException;
import com.akshai.simple.Banking.Api.Mappers.AccountMapper;
import com.akshai.simple.Banking.Api.Models.Account;
import com.akshai.simple.Banking.Api.Repositories.AccountRepository;
import com.akshai.simple.Banking.Api.Services.AccountServiceInterface;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class AccountService implements AccountServiceInterface {

    AccountRepository accountRepository;

    public AccountService(AccountRepository accountRepository){
        this.accountRepository=accountRepository;
    }
    @Override
    public AccountDto createAccount(AccountDto accountDto) {
        Account account = AccountMapper.mapAccountDtoToAccount(accountDto);
        return AccountMapper.mapAccountToAccountDTO(accountRepository.save(account));
    }

    @Override
    public AccountDto getAccount(Long id) throws AccountNotFoundException {
        Optional<Account> account = accountRepository.findById(id);
        if(account.isEmpty()){
            throw new AccountNotFoundException();
        }
        return AccountMapper.mapAccountToAccountDTO(account.get());
    }

    @Override
    public AccountDto deposit(Long id, double amount) throws AccountNotFoundException {
        Optional<Account> account = accountRepository.findById(id);
        if(account.isEmpty()){
            throw new AccountNotFoundException();
        }
        Account originalAccount =account.get();
        originalAccount.setBalance(originalAccount.getBalance()+amount);
        return AccountMapper.mapAccountToAccountDTO(accountRepository.save(originalAccount));
    }

    @Override
    public AccountDto withdraw(Long id, double amount) throws AccountNotFoundException, BalanceInsufficientException {
        Optional<Account> account = accountRepository.findById(id);
        if(account.isEmpty()){
            throw new AccountNotFoundException();
        }
        Account originalAccount =account.get();
        double curBalance = originalAccount.getBalance();
        if(curBalance<amount){
            throw  new BalanceInsufficientException();
        }
        double remainingBalance = curBalance-amount;
        originalAccount.setBalance(remainingBalance);
        return AccountMapper.mapAccountToAccountDTO(accountRepository.save(originalAccount));
    }

    @Override
    public List<AccountDto> getAllAccounts() {
        List<Account> accounts = accountRepository.findAll();
        List<AccountDto> accountDtos = accounts.stream().map(account -> AccountMapper.mapAccountToAccountDTO(account))
                                      .collect(Collectors.toList());
        return accountDtos;
    }
}

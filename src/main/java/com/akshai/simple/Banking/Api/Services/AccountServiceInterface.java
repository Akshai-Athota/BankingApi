package com.akshai.simple.Banking.Api.Services;

import com.akshai.simple.Banking.Api.DTO.AccountDto;
import com.akshai.simple.Banking.Api.Exception.AccountNotFoundException;
import com.akshai.simple.Banking.Api.Exception.BalanceInsufficientException;

import java.util.List;


public interface AccountServiceInterface  {

    AccountDto createAccount(AccountDto accountDto);
    AccountDto getAccount(Long id) throws AccountNotFoundException;
    AccountDto deposit(Long id,double amount) throws AccountNotFoundException;
    AccountDto withdraw(Long id,double amount) throws AccountNotFoundException, BalanceInsufficientException;
    List<AccountDto> getAllAccounts();

}

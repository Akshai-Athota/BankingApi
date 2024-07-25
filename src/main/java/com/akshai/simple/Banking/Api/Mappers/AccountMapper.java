package com.akshai.simple.Banking.Api.Mappers;

import com.akshai.simple.Banking.Api.DTO.AccountDto;
import com.akshai.simple.Banking.Api.Models.Account;

public class AccountMapper {

    public static AccountDto mapAccountToAccountDTO(Account account){
        return new AccountDto(account.getId(),account.getAccountHolderName(),account.getBalance());
    }

    public static Account mapAccountDtoToAccount(AccountDto accountDto){
        return new Account(accountDto.getId(),accountDto.getAccountHolderName(),accountDto.getBalance());
    }
}

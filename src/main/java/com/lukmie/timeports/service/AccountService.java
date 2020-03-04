package com.lukmie.timeports.service;

import com.lukmie.timeports.entity.Account;
import com.lukmie.timeports.repository.AccountRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@RequiredArgsConstructor
@Service
public class AccountService {
    private final AccountRepository accountRepository;

    public List<Account> getAll() {
        return accountRepository.findAll();
    }
}

package com.lukmie.timeports.controller;

import com.lukmie.timeports.dto.AccountDto;
import com.lukmie.timeports.dto.ReportEntryDto;
import com.lukmie.timeports.entity.Account;
import com.lukmie.timeports.service.AccountService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/jit-report")
public class JitReportController {

    private final AccountService accountService;

    @GetMapping("/account-worktime")
    public ResponseEntity<Page<ReportEntryDto>> getAllWorkTimeByAccount(@PageableDefault(size = 15) Pageable pageable) {
        return ResponseEntity.status(HttpStatus.OK).body(accountService.getAllAccountsWorkTime(pageable));
    }

    @GetMapping("/account-worktime/{id}")
    public ResponseEntity<ReportEntryDto> getWorkTimeByAccount(@PathVariable("id") Long id) {
        return ResponseEntity.status(HttpStatus.OK).body(accountService.getWorkTimeSumByAccount(id));
    }

    @GetMapping("/project-worktime")
    public ResponseEntity<Page<ReportEntryDto>> getAllWorkTimeByProject(@PageableDefault(size = 15) Pageable pageable) {
        return ResponseEntity.status(HttpStatus.OK).body(accountService.getAllProjectsWorkTime(pageable));
    }

    @GetMapping("/project-worktime/{id}")
    public ResponseEntity<ReportEntryDto> getWorkTimeByProject(@PathVariable("id") Long id) {
        return ResponseEntity.status(HttpStatus.OK).body(accountService.getWorkTimeSumByProject(id));
    }


    // TODO: 08.03.2020 below maybe not usefull, to delete

    @GetMapping
    public Page<AccountDto> getAll(@PageableDefault(size = 15) Pageable pageable,
                                   @RequestParam(defaultValue = "surname", required = false) String sortBy) {
        return accountService.getAll(pageable);
    }

    @GetMapping("/d")
    public Page<Account> getAllX(@PageableDefault(size = 15) Pageable pageable) {
        return accountService.getAllWorkTime(pageable);
    }

    @GetMapping("/dx")
    public Page<ReportEntryDto> getAllXX(@PageableDefault(size = 5) Pageable pageable) {
        return accountService.getAllAccountsWorkTime(pageable);
    }
}

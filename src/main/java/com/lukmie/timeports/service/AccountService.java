package com.lukmie.timeports.service;

import com.lukmie.timeports.dto.AccountDto;
import com.lukmie.timeports.dto.ReportEntryDto;
import com.lukmie.timeports.entity.Account;
import com.lukmie.timeports.entity.DailyTime;
import com.lukmie.timeports.mapper.FirstReportMapper;
import com.lukmie.timeports.repository.AccountRepository;
import com.lukmie.timeports.repository.DailyTimeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class AccountService {

    private final static double HOURS_CONVERTER = 3600*1000;

    private final AccountRepository accountRepository;
    private final DailyTimeRepository dailyTimeRepository;
    private final FirstReportMapper firstReportMapper;

    public Page<ReportEntryDto> doSmthAll(Pageable pageable) {
        Page<Account> accounts = accountRepository.findAll(pageable);

        dailyTimeRepository.findAllByAccountIdX();

        return accounts.map(d -> {
//            double sum = d.getTimeSheetReports().stream()
//                    .flatMap(r -> r.getDailyTimes().stream())
//                    .filter(dt -> Objects.nonNull(dt.getWorktime()))
//                    .map(DailyTime::getWorktime)
//                    .mapToDouble(workTime -> workTime.doubleValue() / HOURS_CONVERTER)
//                    .sum();
            Optional<Long> allByAccountId = dailyTimeRepository.findAllByAccountId(d.getId());


            ReportEntryDto finalReportEntryDto = firstReportMapper.toReportEntryDto(d);
            allByAccountId.ifPresent(l -> finalReportEntryDto.setValue(l / HOURS_CONVERTER));

//            if (Objects.nonNull(allByAccountId)) {
//                reportEntryDto = firstReportMapper.toReportEntryDto(d);
////            reportEntryDto.setValue(1255125 / HOURS_CONVERTER);
//                reportEntryDto.setValue((double)allByAccountId);
//            }

            return finalReportEntryDto;
        });
    }

    public ReportEntryDto getWorkTimeByAccount(Long id) {
        Account account = accountRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException(String.format("Account with id: %d was not found.", id)));

        Double workTimeSum = getWorkTimeForSingleAccount(account);

        return firstReportMapper.toReportEntryDto(account, workTimeSum);
    }

    private double getWorkTimeForSingleAccount(Account account) {
        return account.getTimeSheetReports().stream()
                .flatMap(r -> r.getDailyTimes().stream())
                .filter(dt -> Objects.nonNull(dt.getWorktime()))
                .map(DailyTime::getWorktime)
                .mapToDouble(workTime -> workTime.doubleValue() / HOURS_CONVERTER)
                .sum();
    }










//    public List<Account> getAll(Pageable pageable) {
//        return accountRepository.findAll();
//    }

    public Page<AccountDto> getAll(Pageable pageable) {
        List<AccountDto> list = accountRepository.findAll().stream()
                .map(AccountDto::toAccountDto)
                .collect(Collectors.toList());

        int start = (int) pageable.getOffset();
        int end = Math.min(start + pageable.getPageSize(), list.size());

        return new PageImpl<>(list.subList(start, end), pageable, list.size());
    }

    public Page<Account> getAllWorkTime(Pageable pageable) {
        return accountRepository.findAll(pageable);
    }




}
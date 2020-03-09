package com.lukmie.timeports.service;

import com.lukmie.timeports.dto.ReportEntryDto;
import com.lukmie.timeports.entity.Account;
import com.lukmie.timeports.entity.Client;
import com.lukmie.timeports.entity.DailyTime;
import com.lukmie.timeports.entity.Project;
import com.lukmie.timeports.mapper.FirstReportMapper;
import com.lukmie.timeports.repository.AccountRepository;
import com.lukmie.timeports.repository.ClientRepository;
import com.lukmie.timeports.repository.DailyTimeRepository;
import com.lukmie.timeports.repository.ProjectRepository;
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
public class JitReportService {

    private final static double HOURS_CONVERTER = 3600 * 1000;

    private final AccountRepository accountRepository;
    private final ProjectRepository projectRepository;
    private final ClientRepository clientRepository;
    private final DailyTimeRepository dailyTimeRepository;
    private final FirstReportMapper firstReportMapper;

    public Page<ReportEntryDto> getAllAccountsWorkTime(Pageable pageable) {
        Page<Account> accounts = accountRepository.findAll(pageable);

        return accounts.map(account -> {
            Optional<Long> workTimeSum = dailyTimeRepository.findSumWorkTimeForAccount(account.getId());
            return firstReportMapper.toAccountReportEntryDto(account,
                    workTimeSum.orElse(0L) / HOURS_CONVERTER);
        });
    }

    public ReportEntryDto getWorkTimeSumByAccount(Long id) {
        Account account = accountRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException(String.format("Account with id: %d was not found.", id)));

        Double workTimeSum = getWorkTimeForSingleAccount(account);

        return firstReportMapper.toAccountReportEntryDto(account, workTimeSum / HOURS_CONVERTER);
    }

    public Page<ReportEntryDto> getAllProjectsWorkTime(Pageable pageable) {
        Page<Project> projects = projectRepository.findAll(pageable);

        return projects.map(project -> {
            Optional<Long> workTimeSum = dailyTimeRepository.findSumWorkTimeForProject(project.getId());
            return firstReportMapper.toProjectReportEntryDto(project,
                    workTimeSum.orElse(0L) / HOURS_CONVERTER);
        });
    }

    public ReportEntryDto getWorkTimeSumByProject(Long id) {
        Project project = projectRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException(String.format("Project with id: %d was not found.", id)));

        Long workTimeSum = dailyTimeRepository.findSumWorkTimeForProject(id).orElse(0L);

        return firstReportMapper.toProjectReportEntryDto(project, workTimeSum / HOURS_CONVERTER);
    }

    public Page<ReportEntryDto> getWorkTimeSumByProjectForAccount(Long id, Pageable pageable) {

        List<ReportEntryDto> list = accountRepository.findAll().stream()
                .map(a -> {
                    Long workTimeSum = dailyTimeRepository.findSumWorkTimeForProjectByAccount(a.getId(), id).orElse(0L);
                    return firstReportMapper.toAccountReportEntryDto(a, workTimeSum / HOURS_CONVERTER);
                })
                .filter(r -> r.getValue() != 0)
                .collect(Collectors.toList());

        return pageImplReportEntry(pageable, list);
    }

    public Page<ReportEntryDto> getAllClientsWorkTime(Pageable pageable) {
        Page<Client> clients = clientRepository.findAll(pageable);

        return clients.map(client -> {
            Optional<Long> workTimeSum = dailyTimeRepository.findSumWorkTimeForClient(client.getId());
            return firstReportMapper.toClientReportEntryDto(client,
                    workTimeSum.orElse(0L) / HOURS_CONVERTER);
        });
    }

    public ReportEntryDto getWorkTimeSumByClient(Long id) {
        Client client = clientRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException(String.format("Client with id: %d was not found.", id)));

        Long workTimeSum = dailyTimeRepository.findSumWorkTimeForClient(id).orElse(0L);

        return firstReportMapper.toClientReportEntryDto(client, workTimeSum / HOURS_CONVERTER);
    }

    public Page<ReportEntryDto> getWorkTimeSumByClientForAccount(Long id, Pageable pageable) {

        List<ReportEntryDto> list = accountRepository.findAll().stream()
                .map(a -> {
                    Long workTimeSum = dailyTimeRepository.findSumWorkTimeForClientByAccount(a.getId(), id).orElse(0L);
                    return firstReportMapper.toAccountReportEntryDto(a, workTimeSum / HOURS_CONVERTER);
                })
                .filter(r -> r.getValue() != 0)
                .collect(Collectors.toList());

        return pageImplReportEntry(pageable, list);
    }

    private Page<ReportEntryDto> pageImplReportEntry(Pageable pageable, List<ReportEntryDto> list) {
        int start = (int) pageable.getOffset();
        int end = Math.min(start + pageable.getPageSize(), list.size());
        return new PageImpl<>(list.subList(start, end), pageable, list.size());
    }

    private double getWorkTimeForSingleAccount(Account account) {
        return account.getTimeSheetReports().stream()
                .flatMap(r -> r.getDailyTimes().stream())
                .filter(dt -> Objects.nonNull(dt.getWorktime()))
                .map(DailyTime::getWorktime)
                .mapToDouble(Long::doubleValue)
                .sum();
    }




    // TODO: 08.03.2020 below maybe not useful, to delete

//    public List<Account> getAll(Pageable pageable) {
//        return accountRepository.findAll();
//    }

//    public Page<AccountDto> getAll(Pageable pageable) {
//        List<AccountDto> list = accountRepository.findAll().stream()
//                .map(AccountDto::toAccountDto)
//                .collect(Collectors.toList());
//
//        int start = (int) pageable.getOffset();
//        int end = Math.min(start + pageable.getPageSize(), list.size());
//
//        return new PageImpl<>(list.subList(start, end), pageable, list.size());
//    }
//
//    public Page<Account> getAllWorkTime(Pageable pageable) {
//        return accountRepository.findAll(pageable);
//    }


}
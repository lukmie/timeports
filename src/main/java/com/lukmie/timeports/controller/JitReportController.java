package com.lukmie.timeports.controller;

import com.lukmie.timeports.dto.ReportEntryDto;
import com.lukmie.timeports.service.JitReportService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/jit-report")
public class JitReportController {

    private final JitReportService jitReportService;

    @GetMapping("/account-worktime")
    public ResponseEntity<Page<ReportEntryDto>> getAllWorkTimeByAccount(@PageableDefault(size = 15) Pageable pageable) {
        return ResponseEntity.status(HttpStatus.OK).body(jitReportService.getAllAccountsWorkTime(pageable));
    }

    @GetMapping("/account-worktime/{id}")
    public ResponseEntity<ReportEntryDto> getWorkTimeByAccount(@PathVariable("id") Long id) {
        return ResponseEntity.status(HttpStatus.OK).body(jitReportService.getWorkTimeSumByAccount(id));
    }

    @GetMapping("/project-worktime")
    public ResponseEntity<Page<ReportEntryDto>> getAllWorkTimeByProject(@PageableDefault(size = 15) Pageable pageable) {
        return ResponseEntity.status(HttpStatus.OK).body(jitReportService.getAllProjectsWorkTime(pageable));
    }

    @GetMapping("/project-worktime/{id}")
    public ResponseEntity<ReportEntryDto> getWorkTimeByProject(@PathVariable("id") Long id) {
        return ResponseEntity.status(HttpStatus.OK).body(jitReportService.getWorkTimeSumByProject(id));
    }

    @GetMapping("/project-worktime/{id}/accounts")
    public ResponseEntity<Page<ReportEntryDto>> getWorkTimeByProjectForAccount(@PageableDefault(size = 15) Pageable pageable,
                                                                               @PathVariable("id") Long id) {
        return ResponseEntity.status(HttpStatus.OK).body(jitReportService.getWorkTimeSumByProjectForAccount(id, pageable));
    }

    @GetMapping("/client-worktime")
    public ResponseEntity<Page<ReportEntryDto>> getAllWorkTimeByClient(@PageableDefault(size = 15) Pageable pageable) {
        return ResponseEntity.status(HttpStatus.OK).body(jitReportService.getAllClientsWorkTime(pageable));
    }

    @GetMapping("/client-worktime/{id}")
    public ResponseEntity<ReportEntryDto> getWorkTimeByClient(@PathVariable("id") Long id) {
        return ResponseEntity.status(HttpStatus.OK).body(jitReportService.getWorkTimeSumByClient(id));
    }

    @GetMapping("/client-worktime/{id}/accounts")
    public ResponseEntity<Page<ReportEntryDto>> getWorkTimeByClientForAccount(@PageableDefault(size = 15) Pageable pageable,
                                                                              @PathVariable("id") Long id) {
        return ResponseEntity.status(HttpStatus.OK).body(jitReportService.getWorkTimeSumByClientForAccount(id, pageable));
    }

    // TODO: 08.03.2020 below maybe not useful, to delete

//    @GetMapping
//    public Page<AccountDto> getAll(@PageableDefault(size = 15) Pageable pageable,
//                                   @RequestParam(defaultValue = "surname", required = false) String sortBy) {
//        return jitReportService.getAll(pageable);
//    }

//    @GetMapping("/d")
//    public Page<Account> getAllX(@PageableDefault(size = 15) Pageable pageable) {
//        return jitReportService.getAllWorkTime(pageable);
//    }

    @GetMapping("/dx")
    public Page<ReportEntryDto> getAllXX(@PageableDefault(size = 5) Pageable pageable) {
        return jitReportService.getAllAccountsWorkTime(pageable);
    }
}

package com.lukmie.timeports.controller;

import com.lukmie.timeports.entity.WorkTimeReportEntry;
import com.lukmie.timeports.service.CsvWriterService;
import com.lukmie.timeports.component.ReportUtils;
import com.lukmie.timeports.service.WorkTimeReportService;
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

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/work-time")
public class WorkTimeController {

    private final WorkTimeReportService workTimeReportService;
    private final CsvWriterService csvWriterService;

    @GetMapping("/report")
    public ResponseEntity<Page<WorkTimeReportEntry>> getWorkTimeReport(@PageableDefault(size = 15) Pageable pageable,
                                                                       @RequestParam(required = false, defaultValue = "2019") Integer yearFrom,
                                                                       @RequestParam(required = false, defaultValue = "2019") Integer yearTo,
                                                                       @RequestParam(required = false, defaultValue = "1") Integer monthFrom,
                                                                       @RequestParam(required = false, defaultValue = "12") Integer monthTo) {
        return ResponseEntity.status(HttpStatus.OK).body(workTimeReportService.getWorkTimeReport(pageable, yearFrom, yearTo, monthFrom, monthTo));
    }

    @GetMapping("/report/csv")
    public void getWorkTimeReportCsv(@PageableDefault(size = 15) Pageable pageable,
                                     @RequestParam(required = false, defaultValue = "2019") Integer yearFrom,
                                     @RequestParam(required = false, defaultValue = "2019") Integer yearTo,
                                     @RequestParam(required = false, defaultValue = "1") Integer monthFrom,
                                     @RequestParam(required = false, defaultValue = "12") Integer monthTo,
                                     HttpServletResponse response) throws IOException {
        Page<WorkTimeReportEntry> page = workTimeReportService.getWorkTimeReport(pageable, yearFrom, yearTo, monthFrom, monthTo);
        csvWriterService.configureCsvWriterAndPrint(response, page, ReportUtils.workTimeReportHeader, "report.csv");
    }

    @GetMapping("/report/accounts/{name-surname}")
    public ResponseEntity<Page<WorkTimeReportEntry>> getWorkTimeReportByAccount(@PageableDefault(size = 15) Pageable pageable,
                                                                                @PathVariable("name-surname") String nameSurname,
                                                                                @RequestParam(required = false, defaultValue = "2019") Integer yearFrom,
                                                                                @RequestParam(required = false, defaultValue = "2019") Integer yearTo,
                                                                                @RequestParam(required = false, defaultValue = "1") Integer monthFrom,
                                                                                @RequestParam(required = false, defaultValue = "12") Integer monthTo) {
        return ResponseEntity.status(HttpStatus.OK).body(workTimeReportService.getWorkTimeReportByAccountName(pageable, nameSurname, yearFrom, yearTo, monthFrom, monthTo));
    }

    @GetMapping("/report/accounts/{name-surname}/csv")
    public void getWorkTimeReportByAccountCsv(@PageableDefault(size = 15) Pageable pageable,
                                              @PathVariable("name-surname") String nameSurname,
                                              @RequestParam(required = false, defaultValue = "2019") Integer yearFrom,
                                              @RequestParam(required = false, defaultValue = "2019") Integer yearTo,
                                              @RequestParam(required = false, defaultValue = "1") Integer monthFrom,
                                              @RequestParam(required = false, defaultValue = "12") Integer monthTo,
                                              HttpServletResponse response) throws IOException {
        Page<WorkTimeReportEntry> page = workTimeReportService.getWorkTimeReportByAccountName(pageable, nameSurname, yearFrom, yearTo, monthFrom, monthTo);
        csvWriterService.configureCsvWriterAndPrint(response, page, ReportUtils.workTimeReportHeader, "report-by-account-name.csv");
    }

    @GetMapping("/report/projects/{project-name}")
    public ResponseEntity<Page<WorkTimeReportEntry>> getWorkTimeReportByProject(@PageableDefault(size = 15) Pageable pageable,
                                                                                @PathVariable("project-name") String name,
                                                                                @RequestParam(required = false, defaultValue = "2019") Integer yearFrom,
                                                                                @RequestParam(required = false, defaultValue = "2019") Integer yearTo,
                                                                                @RequestParam(required = false, defaultValue = "1") Integer monthFrom,
                                                                                @RequestParam(required = false, defaultValue = "12") Integer monthTo) {
        return ResponseEntity.status(HttpStatus.OK).body(workTimeReportService.getWorkTimeReportByProjectName(pageable, name, yearFrom, yearTo, monthFrom, monthTo));
    }

    @GetMapping("/report/projects/{project-name}/csv")
    public void getWorkTimeReportByProjectCsv(@PageableDefault(size = 15) Pageable pageable,
                                              @PathVariable("project-name") String name,
                                              @RequestParam(required = false, defaultValue = "2019") Integer yearFrom,
                                              @RequestParam(required = false, defaultValue = "2019") Integer yearTo,
                                              @RequestParam(required = false, defaultValue = "1") Integer monthFrom,
                                              @RequestParam(required = false, defaultValue = "12") Integer monthTo,
                                              HttpServletResponse response) throws IOException {
        Page<WorkTimeReportEntry> page = workTimeReportService.getWorkTimeReportByProjectName(pageable, name, yearFrom, yearTo, monthFrom, monthTo);
        csvWriterService.configureCsvWriterAndPrint(response, page, ReportUtils.workTimeReportHeader, "report-by-project-name.csv");
    }

    @GetMapping("/report/clients/{client-name}")
    public ResponseEntity<Page<WorkTimeReportEntry>> getWorkTimeReportByClient(@PageableDefault(size = 15) Pageable pageable,
                                                                               @PathVariable("client-name") String name,
                                                                               @RequestParam(required = false, defaultValue = "2019") Integer yearFrom,
                                                                               @RequestParam(required = false, defaultValue = "2019") Integer yearTo,
                                                                               @RequestParam(required = false, defaultValue = "1") Integer monthFrom,
                                                                               @RequestParam(required = false, defaultValue = "12") Integer monthTo) {
        return ResponseEntity.status(HttpStatus.OK).body(workTimeReportService.getWorkTimeReportByClientName(pageable, name, yearFrom, yearTo, monthFrom, monthTo));
    }

    @GetMapping("/report/clients/{client-name}/csv")
    public void getWorkTimeReportByClientCsv(@PageableDefault(size = 15) Pageable pageable,
                                             @PathVariable("client-name") String name,
                                             @RequestParam(required = false, defaultValue = "2019") Integer yearFrom,
                                             @RequestParam(required = false, defaultValue = "2019") Integer yearTo,
                                             @RequestParam(required = false, defaultValue = "1") Integer monthFrom,
                                             @RequestParam(required = false, defaultValue = "12") Integer monthTo,
                                             HttpServletResponse response) throws IOException {
        Page<WorkTimeReportEntry> page = workTimeReportService.getWorkTimeReportByClientName(pageable, name, yearFrom, yearTo, monthFrom, monthTo);
        csvWriterService.configureCsvWriterAndPrint(response, page, ReportUtils.workTimeReportHeader, "report-by-client-name.csv");
    }

    @GetMapping("/report/most-hours-per-day")
    public ResponseEntity<Page<WorkTimeReportEntry>> getMostHoursInOneDayReport(@PageableDefault(size = 15) Pageable pageable,
                                                                               @RequestParam(required = false, defaultValue = "2019") Integer yearFrom,
                                                                               @RequestParam(required = false, defaultValue = "2019") Integer yearTo,
                                                                               @RequestParam(required = false, defaultValue = "1") Integer monthFrom,
                                                                               @RequestParam(required = false, defaultValue = "12") Integer monthTo) {
        return ResponseEntity.status(HttpStatus.OK).body(workTimeReportService.getMostHoursInOneDayReport(pageable, yearFrom, yearTo, monthFrom, monthTo));
    }

    @GetMapping("/report/completing-timeports")
    public ResponseEntity<Page<WorkTimeReportEntry>> getTimeSheetsCompletingReport(@PageableDefault(size = 15) Pageable pageable,
                                                                               @RequestParam(required = false, defaultValue = "2019") Integer yearFrom,
                                                                               @RequestParam(required = false, defaultValue = "2019") Integer yearTo,
                                                                               @RequestParam(required = false, defaultValue = "1") Integer monthFrom,
                                                                               @RequestParam(required = false, defaultValue = "12") Integer monthTo) {
        return ResponseEntity.status(HttpStatus.OK).body(workTimeReportService.getTimeSheetsCompletingReport(pageable, yearFrom, yearTo, monthFrom, monthTo));
    }

    @GetMapping("/report/core-hours")
    public ResponseEntity<Page<WorkTimeReportEntry>> getCoreHoursReport(@PageableDefault(size = 15) Pageable pageable,
                                                                        @RequestParam(required = false, defaultValue = "2019") Integer yearFrom,
                                                                        @RequestParam(required = false, defaultValue = "2019") Integer yearTo,
                                                                        @RequestParam(required = false, defaultValue = "1") Integer monthFrom,
                                                                        @RequestParam(required = false, defaultValue = "12") Integer monthTo) {
        return ResponseEntity.status(HttpStatus.OK).body(workTimeReportService.getCoreHoursReport(pageable, yearFrom, yearTo, monthFrom, monthTo));
    }
}

package com.lukmie.timeports.controller;

import com.lukmie.timeports.entity.BusinessTripReportEntry;
import com.lukmie.timeports.service.BusinessTripReportService;
import com.lukmie.timeports.service.CsvWriterService;
import com.lukmie.timeports.component.ReportUtils;
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

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/business-trip")
public class BusinessTripController {

    private final BusinessTripReportService businessTripReportService;
    private final CsvWriterService csvWriterService;

    @GetMapping("/partial-report/accounts")
    public ResponseEntity<Page<BusinessTripReportEntry>> getBusinessTripAccountsReport(@PageableDefault(size = 15) Pageable pageable) {
        return ResponseEntity.status(HttpStatus.OK).body(businessTripReportService.getBusinessTripAccountsReport(pageable));
    }

    @GetMapping("/partial-report/accounts/csv")
    public void getBusinessTripAccountsReportCsv(@PageableDefault(size = 15) Pageable pageable,
                                                 HttpServletResponse response) throws IOException {
        Page<BusinessTripReportEntry> page = businessTripReportService.getBusinessTripAccountsReport(pageable);
        csvWriterService.configureCsvWriterAndPrint(response, page, ReportUtils.partialTripReportHeader, "partial-trip-accounts.csv");
    }

    @GetMapping("/partial-report/accounts/{name-surname}")
    public ResponseEntity<List<BusinessTripReportEntry>> getBusinessTripAccountsReportByAccountName(@PathVariable("name-surname") String name) {
        return ResponseEntity.status(HttpStatus.OK).body(businessTripReportService.getBusinessTripAccountsByName(name));
    }

    @GetMapping("/partial-report/projects")
    public ResponseEntity<Page<BusinessTripReportEntry>> getBusinessTripProjectsReport(@PageableDefault(size = 15) Pageable pageable) {
        return ResponseEntity.status(HttpStatus.OK).body(businessTripReportService.getBusinessTripProjectsReport(pageable));
    }

    @GetMapping("/partial-report/projects/csv")
    public void getBusinessTripProjectsReportCsv(@PageableDefault(size = 15) Pageable pageable,
                                                 HttpServletResponse response) throws IOException {
        Page<BusinessTripReportEntry> page = businessTripReportService.getBusinessTripProjectsReport(pageable);
        csvWriterService.configureCsvWriterAndPrint(response, page, ReportUtils.partialTripReportHeader, "partial-trip-reports.csv");
    }

    @GetMapping("/partial-report/projects/{name}")
    public ResponseEntity<List<BusinessTripReportEntry>> getBusinessTripProjectsReportByProjectName(@PathVariable("name") String name) {
        return ResponseEntity.status(HttpStatus.OK).body(businessTripReportService.getBusinessTripProjectsReportByName(name));
    }

    @GetMapping("/partial-report/clients")
    public ResponseEntity<Page<BusinessTripReportEntry>> getBusinessTripClientsReport(@PageableDefault(size = 15) Pageable pageable) {
        return ResponseEntity.status(HttpStatus.OK).body(businessTripReportService.getBusinessTripClientsReport(pageable));
    }

    @GetMapping("/partial-report/clients/csv")
    public void getBusinessTripClientsReportCsv(@PageableDefault(size = 15) Pageable pageable,
                                                HttpServletResponse response) throws IOException {
        Page<BusinessTripReportEntry> page = businessTripReportService.getBusinessTripClientsReport(pageable);
        csvWriterService.configureCsvWriterAndPrint(response, page, ReportUtils.partialTripReportHeader, "partial-trip-clients.csv");
    }

    @GetMapping("/partial-report/clients/{name}")
    public ResponseEntity<List<BusinessTripReportEntry>> getBusinessTripClientsReport(@PathVariable("name") String name) {
        return ResponseEntity.status(HttpStatus.OK).body(businessTripReportService.getBusinessTripClientsReportByName(name));
    }

    @GetMapping("/overall-report")
    public ResponseEntity<Page<BusinessTripReportEntry>> getAllWorkTime(@PageableDefault(size = 15) Pageable pageable) {
        return ResponseEntity.status(HttpStatus.OK).body(businessTripReportService.getAllWorkTimeReport(pageable));
    }

    @GetMapping("/overall-report/csv")
    public void getAllWorkTimeCsv(@PageableDefault(size = 15) Pageable pageable,
                                  HttpServletResponse response) throws IOException {
        Page<BusinessTripReportEntry> page = businessTripReportService.getAllWorkTimeReport(pageable);
        csvWriterService.configureCsvWriterAndPrint(response, page, ReportUtils.overallTripReportHeader, "overall-trip-accounts.csv");
    }

    @GetMapping("/overall-report/accounts/{name-surname}")
    public ResponseEntity<Page<BusinessTripReportEntry>> getBusinessTripByAccountName(@PageableDefault(size = 5) Pageable pageable,
                                                                                      @PathVariable("name-surname") String nameSurname) {
        return ResponseEntity.status(HttpStatus.OK).body(businessTripReportService.getBusinessTripPerAccountReport(pageable, nameSurname));
    }

    @GetMapping("/overall-report/accounts/{name-surname}/csv")
    public void getBusinessTripByAccountNameCsv(@PageableDefault(size = 5) Pageable pageable,
                                                @PathVariable("name-surname") String nameSurname,
                                                HttpServletResponse response) throws IOException {
        Page<BusinessTripReportEntry> page = businessTripReportService.getBusinessTripPerAccountReport(pageable, nameSurname);
        csvWriterService.configureCsvWriterAndPrint(response, page, ReportUtils.overallTripReportHeader, "overall-trip-accounts-by-name.csv");
    }

    @GetMapping("/overall-report/projects/{name}")
    public ResponseEntity<Page<BusinessTripReportEntry>> getBusinessTripByProjectName(@PageableDefault(size = 5) Pageable pageable,
                                                                                      @PathVariable("name") String projectName) {
        return ResponseEntity.status(HttpStatus.OK).body(businessTripReportService.getBusinessTripPerProjectReport(pageable, projectName));
    }

    @GetMapping("/overall-report/projects/{name}/csv")
    public void getBusinessTripByProjectNameCsv(@PageableDefault(size = 5) Pageable pageable,
                                                @PathVariable("name") String projectName,
                                                HttpServletResponse response) throws IOException {
        Page<BusinessTripReportEntry> page = businessTripReportService.getBusinessTripPerProjectReport(pageable, projectName);
        csvWriterService.configureCsvWriterAndPrint(response, page, ReportUtils.overallTripReportHeader, "overall-trip-projects-by-name.csv");
    }

    @GetMapping("/overall-report/clients/{name}")
    public ResponseEntity<Page<BusinessTripReportEntry>> getBusinessTripByClientName(@PageableDefault(size = 5) Pageable pageable,
                                                                                     @PathVariable("name") String clientName) {
        return ResponseEntity.status(HttpStatus.OK).body(businessTripReportService.getBusinessTripPerClientReport(pageable, clientName));
    }

    @GetMapping("/overall-report/clients/{name}/csv")
    public void getBusinessTripByClientName(@PageableDefault(size = 5) Pageable pageable,
                                            @PathVariable("name") String clientName,
                                            HttpServletResponse response) throws IOException {
        Page<BusinessTripReportEntry> page = businessTripReportService.getBusinessTripPerClientReport(pageable, clientName);
        csvWriterService.configureCsvWriterAndPrint(response, page, ReportUtils.overallTripReportHeader, "overall-trip-clients-by-name.csv");
    }
}

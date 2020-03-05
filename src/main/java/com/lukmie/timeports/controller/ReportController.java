package com.lukmie.timeports.controller;

import com.lukmie.timeports.entity.FlightReport;
import com.lukmie.timeports.entity.WorkReport;
import com.lukmie.timeports.service.ReportService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/report")
public class ReportController {

    private final ReportService reportService;

    @GetMapping("/hours-per-client/{parameter}")
    public ResponseEntity<WorkReport> getHoursByClient(@PathVariable("parameter") String parameter) {
        return ResponseEntity.ok(reportService.hoursByClient(parameter));
    }

    @GetMapping("/hours-per-project/{parameter}")
    public ResponseEntity<WorkReport> getHoursByProject(@PathVariable("parameter") String parameter) {
        return ResponseEntity.ok(reportService.hoursByProject(parameter));
    }

    @GetMapping("/flights-per-client")
    public ResponseEntity<FlightReport> getFlightsByClient() {
        return ResponseEntity.ok(reportService.flightsByClient());
    }

    @GetMapping("/csv/flights-per-client")
    public void getFlightsByClientCSV(HttpServletResponse resp) throws IOException {
        reportService.configureCsvWriterAndPrint(resp);
    }
}

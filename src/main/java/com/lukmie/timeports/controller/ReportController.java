package com.lukmie.timeports.controller;

import com.lukmie.timeports.entity.FlightReport;
import com.lukmie.timeports.entity.WorkReport;
import com.lukmie.timeports.service.ReportService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/report")
public class ReportController {

    private final ReportService reportService;

    @GetMapping("/hours-per-client")
    public ResponseEntity<WorkReport> getHoursByClient() {
        return ResponseEntity.ok(reportService.hoursByClient());
    }

    @GetMapping("/flights-per-client")
    public ResponseEntity<FlightReport> getFlightsByClient() {
        return ResponseEntity.ok(reportService.flightsByClient());
    }
}

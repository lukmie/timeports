package com.lukmie.timeports.service;

import com.lukmie.timeports.entity.FlightReport;
import com.lukmie.timeports.entity.WorkReport;
import com.lukmie.timeports.repository.ReportRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

import static com.lukmie.timeports.entity.FlightReport.buildFlightReport;
import static com.lukmie.timeports.entity.WorkReport.buildWorkReport;

@RequiredArgsConstructor
@Service
public class ReportService {

    private final ReportRepository reportRepository;

    public WorkReport hoursByClient() {
        List<Object[]> objects = reportRepository.fetchReportWorkHoursEntries();
        return buildWorkReport(objects);
    }

    public FlightReport flightsByClient() {
        List<Object[]> objects = reportRepository.fetchReportFlightsEntries();
        return buildFlightReport(objects);
    }
}

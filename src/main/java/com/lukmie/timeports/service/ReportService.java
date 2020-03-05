package com.lukmie.timeports.service;

import com.lukmie.timeports.entity.FlightReport;
import com.lukmie.timeports.entity.FlightReportEntry;
import com.lukmie.timeports.entity.WorkReport;
import com.lukmie.timeports.repository.ReportRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;
import org.supercsv.io.CsvBeanWriter;
import org.supercsv.io.ICsvBeanWriter;
import org.supercsv.prefs.CsvPreference;

import java.io.IOException;
import java.io.StringWriter;
import java.util.List;

import static com.lukmie.timeports.entity.FlightReport.buildFlightReport;
import static com.lukmie.timeports.entity.WorkReport.buildWorkReport;

@Slf4j
@RequiredArgsConstructor
@Service
public class ReportService {

    private final ReportRepository reportRepository;

    public WorkReport hoursByClient(String parameter) {
        List<Object[]> objects = reportRepository.fetchReportWorkHoursEntriesPerClient(parameter);
        return buildWorkReport(objects);
    }

    public WorkReport hoursByProject(String parameter) {
        List<Object[]> objects = reportRepository.fetchReportWorkHoursEntriesPerProject(parameter);
        return buildWorkReport(objects);
    }

    public FlightReport flightsByClient() {
        List<Object[]> objects = reportRepository.fetchReportFlightsEntries();
        return buildFlightReport(objects);
    }

    public String configureCsvWriterAndPrint() {
        StringWriter stringWriter = new StringWriter();
        try (ICsvBeanWriter csvWriter = new CsvBeanWriter(stringWriter, CsvPreference.EXCEL_NORTH_EUROPE_PREFERENCE)) {
            final String[] header = {"name", "surname", "client", "travelReservation", "sum"};
            for (FlightReportEntry entry : flightsByClient().getEntries()) {
                csvWriter.write(entry, header);
            }
        } catch (IOException e) {
            log.warn("Error while creating CSV file", e);
        }
        return stringWriter.toString();
    }
}

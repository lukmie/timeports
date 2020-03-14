package com.lukmie.timeports.service;

import com.lukmie.timeports.entity.BusinessTripReportEntry;
import com.lukmie.timeports.repository.BusinessTripRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Service;
import org.supercsv.io.CsvBeanWriter;
import org.supercsv.io.ICsvBeanWriter;
import org.supercsv.prefs.CsvPreference;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Slf4j
@RequiredArgsConstructor
@Service
public class BusinessTripReportService {

    private final static String[] partialReportHeader = {"name", "numberOfNights", "totalTrips"};
    private final static  String[] overallReportHeader = {"name", "projectName", "clientName", "numberOfNights", "totalTrips", "insuranceBy", "travelBy", "accommodationBy"};

    private final BusinessTripRepository businessTripRepository;

    public Page<BusinessTripReportEntry> getBusinessTripAccountsReport(Pageable pageable) {
        List<Object[]> result = businessTripRepository.getBusinessTripsAccounts(pageable);
        List<BusinessTripReportEntry> collect = createPartialReport(result);

        return createPageFromListOfBusinessTripReportEntry(collect, pageable);
    }

    public void getBusinessTripAccountsReportToCsv(HttpServletResponse resp, Pageable pageable) throws IOException {
        List<Object[]> result = businessTripRepository.getBusinessTripsAccounts(pageable);
        List<BusinessTripReportEntry> collect = createPartialReport(result);

        Page<BusinessTripReportEntry> page = createPageFromListOfBusinessTripReportEntry(collect, pageable);
        configureCsvWriterAndPrint(resp, page, partialReportHeader, "partial-trip-accounts.csv");
    }

    public List<BusinessTripReportEntry> getBusinessTripAccountsByName(String name) {
        List<Object[]> result = businessTripRepository.getBusinessTripsAccountsByAccountName(prepareInputData(name));

        return createPartialReport(result);
    }

    public Page<BusinessTripReportEntry> getBusinessTripProjectsReport(Pageable pageable) {
        List<Object[]> result = businessTripRepository.getBusinessTripsProjects(pageable);
        List<BusinessTripReportEntry> collect = createPartialReport(result);

        return createPageFromListOfBusinessTripReportEntry(collect, pageable);
    }

    public void getBusinessTripProjectsReportToCsv(HttpServletResponse resp, Pageable pageable) throws IOException {
        List<Object[]> result = businessTripRepository.getBusinessTripsProjects(pageable);
        List<BusinessTripReportEntry> collect = createPartialReport(result);

        Page<BusinessTripReportEntry> page = createPageFromListOfBusinessTripReportEntry(collect, pageable);
        configureCsvWriterAndPrint(resp, page, partialReportHeader, "partial-trip-reports.csv");
    }

    public List<BusinessTripReportEntry> getBusinessTripProjectsReportByName(String name) {
        List<Object[]> result = businessTripRepository.getBusinessTripsProjectsByProjectName(prepareInputData(name));

        return createPartialReport(result);
    }

    public Page<BusinessTripReportEntry> getBusinessTripClientsReport(Pageable pageable) {
        List<Object[]> result = businessTripRepository.getBusinessTripsClients(pageable);
        List<BusinessTripReportEntry> collect = createPartialReport(result);

        return createPageFromListOfBusinessTripReportEntry(collect, pageable);
    }

    public void getBusinessTripClientsReportToCsv(HttpServletResponse resp, Pageable pageable) throws IOException {
        List<Object[]> result = businessTripRepository.getBusinessTripsClients(pageable);
        List<BusinessTripReportEntry> collect = createPartialReport(result);

        Page<BusinessTripReportEntry> page = createPageFromListOfBusinessTripReportEntry(collect, pageable);
        configureCsvWriterAndPrint(resp, page, partialReportHeader, "partial-trip-clients.csv");
    }

    public List<BusinessTripReportEntry> getBusinessTripClientsReportByName(String name) {
        List<Object[]> result = businessTripRepository.getBusinessTripsClientsByClientName(prepareInputData(name));

        return createPartialReport(result);
    }

    public Page<BusinessTripReportEntry> getBusinessTripPerAccountReport(Pageable pageable) {
        List<Object[]> result = businessTripRepository.getAllBusinessTripFullData(pageable);
        List<BusinessTripReportEntry> collect = createOverallReport(result);

        return createPageFromListOfBusinessTripReportEntry(collect, pageable);
    }

    public void getBusinessTripPerAccountReportToCsv(HttpServletResponse resp, Pageable pageable) throws IOException {
        List<Object[]> result = businessTripRepository.getAllBusinessTripFullData(pageable);
        List<BusinessTripReportEntry> collect = createOverallReport(result);

        Page<BusinessTripReportEntry> page = createPageFromListOfBusinessTripReportEntry(collect, pageable);
        configureCsvWriterAndPrint(resp, page, overallReportHeader, "overall-trip-accounts.csv");
    }

    public Page<BusinessTripReportEntry> getBusinessTripPerAccountReport(Pageable pageable, String nameSurname) {
        List<Object[]> result = businessTripRepository.getBusinessTripPerAccountFullData(pageable, prepareInputData(nameSurname));
        List<BusinessTripReportEntry> collect = createOverallReport(result);

        return createPageFromListOfBusinessTripReportEntry(collect, pageable);
    }

    public void getBusinessTripPerAccountReportToCsv(HttpServletResponse resp, Pageable pageable, String nameSurname) throws IOException {
        List<Object[]> result = businessTripRepository.getBusinessTripPerAccountFullData(pageable, prepareInputData(nameSurname));
        List<BusinessTripReportEntry> collect = createOverallReport(result);

        Page<BusinessTripReportEntry> page = createPageFromListOfBusinessTripReportEntry(collect, pageable);
        configureCsvWriterAndPrint(resp, page, overallReportHeader, "overall-trip-accounts-by-name.csv");
    }

    public Page<BusinessTripReportEntry> getBusinessTripPerProjectReport(Pageable pageable, String projectName) {
        List<Object[]> result = businessTripRepository.getBusinessTripPerProjectFullData(pageable, prepareInputData(projectName));
        List<BusinessTripReportEntry> collect = createOverallReport(result);

        return createPageFromListOfBusinessTripReportEntry(collect, pageable);
    }

    public void getBusinessTripPerProjectReportToCsv(HttpServletResponse resp, Pageable pageable, String projectName) throws IOException {
        List<Object[]> result = businessTripRepository.getBusinessTripPerProjectFullData(pageable, prepareInputData(projectName));
        List<BusinessTripReportEntry> collect = createOverallReport(result);

        Page<BusinessTripReportEntry> page = createPageFromListOfBusinessTripReportEntry(collect, pageable);
        configureCsvWriterAndPrint(resp, page, overallReportHeader, "overall-trip-projects-by-name.csv");
    }

    public Page<BusinessTripReportEntry> getBusinessTripPerClientReport(Pageable pageable, String clientName) {
        List<Object[]> result = businessTripRepository.getBusinessTripPerClientFullData(pageable, prepareInputData(clientName));
        List<BusinessTripReportEntry> collect = createOverallReport(result);

        return createPageFromListOfBusinessTripReportEntry(collect, pageable);
    }

    public void getBusinessTripPerClientReportToCsv(HttpServletResponse resp, Pageable pageable, String clientName) throws IOException {
        List<Object[]> result = businessTripRepository.getBusinessTripPerClientFullData(pageable, prepareInputData(clientName));
        List<BusinessTripReportEntry> collect = createOverallReport(result);

        Page<BusinessTripReportEntry> page = createPageFromListOfBusinessTripReportEntry(collect, pageable);
        configureCsvWriterAndPrint(resp, page, overallReportHeader, "overall-trip-clients-by-name.csv");
    }

    private List<BusinessTripReportEntry> createPartialReport(List<Object[]> result) {
        return result.stream()
                .map(BusinessTripReportEntry::buildPartialEntry)
                .collect(Collectors.toList());
    }

    private List<BusinessTripReportEntry> createOverallReport(List<Object[]> result) {
        return result.stream()
                .map(BusinessTripReportEntry::buildOverallEntry)
                .collect(Collectors.toList());
    }

    private Page<BusinessTripReportEntry> createPageFromListOfBusinessTripReportEntry(List<BusinessTripReportEntry> collect, Pageable pageable) {
        int start = (int) pageable.getOffset();
        int end = Math.min(start + pageable.getPageSize(), collect.size());

        if (start > end) {
            return new PageImpl<>(collect.subList(0, end), PageRequest.of(0, pageable.getPageSize()), collect.size());
        }
        return new PageImpl<>(collect.subList(start, end), pageable, collect.size());
    }

    private String prepareInputData(String input) {
        if (Objects.nonNull(input)) {
            input = String.join(" ", input.split("[\\s]+"));
        }
        return input;
    }

    private void configureCsvWriterAndPrint(HttpServletResponse resp, Page<BusinessTripReportEntry> page, String[] header, String fileName) throws IOException {
        csvSetupProperties(resp, fileName);
        try (ICsvBeanWriter csvWriter = new CsvBeanWriter(resp.getWriter(), new CsvPreference.Builder('|', ',', "\n").build())) {
            csvWriter.writeHeader(header);
            List<BusinessTripReportEntry> content = page.getContent();
            for (BusinessTripReportEntry businessTripReportEntry : content) {
                csvWriter.write(businessTripReportEntry, header);
            }
        } catch (IOException e) {
            log.warn("Error while creating CSV file", e);
        }
    }

    private void csvSetupProperties(HttpServletResponse resp, String fileName) throws IOException {
        String headerValue = String.format("attachment; filename=\"%s\"", fileName);
        resp.setHeader(HttpHeaders.CONTENT_DISPOSITION, headerValue);
        resp.setContentType("text/csv");
        resp.setCharacterEncoding(StandardCharsets.UTF_8.name());
        resp.getWriter().write("\uFEFF");
    }
//    public BusinessTripReport getBusinessTripPerAccountReport(Pageable pageable) {
//
//        List<Object[]> result = businessTripRepository.getAllBusinessTripFullData(pageable);
//
//        return buildReport(result);
//    }
//    public BusinessTripReport getBusinessTripPerAccountReport(String name, String surname) {
//
//        List<Object[]> result = businessTripRepository.getBusinessTripPerAccountFullData(name, surname);
//
//        return buildReport(result);
//    }
//
//    public BusinessTripReport getBusinessTripPerProjectReport(String projectName) {
//
//        List<Object[]> result = businessTripRepository.getBusinessTripPerProjectFullData(projectName);
//
//        return buildReport(result);
//    }
//
//    public BusinessTripReport getBusinessTripPerClientReport(String clientName) {
//
//        List<Object[]> result = businessTripRepository.getBusinessTripPerClientFullData(clientName);
//
//        return buildReport(result);
//    }
}

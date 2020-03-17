package com.lukmie.timeports.service;

import com.lukmie.timeports.component.ReportUtils;
import com.lukmie.timeports.entity.BusinessTripReportEntry;
import com.lukmie.timeports.repository.BusinessTripRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@RequiredArgsConstructor
@Service
public class BusinessTripReportService {

    private final BusinessTripRepository businessTripRepository;
    private final ReportUtils reportUtils;

    public Page<BusinessTripReportEntry> getBusinessTripAccountsReport(Pageable pageable) {
        List<Object[]> result = businessTripRepository.getBusinessTripsAccounts(pageable);
        List<BusinessTripReportEntry> collect = createPartialReport(result);

        return reportUtils.createPageFromListOfBusinessTripReportEntry(collect, pageable);
    }

    public List<BusinessTripReportEntry> getBusinessTripAccountsByName(String name) {
        List<Object[]> result = businessTripRepository.getBusinessTripsAccountsByAccountName(reportUtils.prepareInputData(name));

        return createPartialReport(result);
    }

    public Page<BusinessTripReportEntry> getBusinessTripProjectsReport(Pageable pageable) {
        List<Object[]> result = businessTripRepository.getBusinessTripsProjects(pageable);
        List<BusinessTripReportEntry> collect = createPartialReport(result);

        return reportUtils.createPageFromListOfBusinessTripReportEntry(collect, pageable);
    }

    public List<BusinessTripReportEntry> getBusinessTripProjectsReportByName(String name) {
        List<Object[]> result = businessTripRepository.getBusinessTripsProjectsByProjectName(reportUtils.prepareInputData(name));

        return createPartialReport(result);
    }

    public Page<BusinessTripReportEntry> getBusinessTripClientsReport(Pageable pageable) {
        List<Object[]> result = businessTripRepository.getBusinessTripsClients(pageable);
        List<BusinessTripReportEntry> collect = createPartialReport(result);

        return reportUtils.createPageFromListOfBusinessTripReportEntry(collect, pageable);
    }

    public List<BusinessTripReportEntry> getBusinessTripClientsReportByName(String name) {
        List<Object[]> result = businessTripRepository.getBusinessTripsClientsByClientName(reportUtils.prepareInputData(name));

        return createPartialReport(result);
    }

    public Page<BusinessTripReportEntry> getAllWorkTimeReport(Pageable pageable) {
        List<Object[]> result = businessTripRepository.getAllBusinessTripFullData(pageable);
        List<BusinessTripReportEntry> collect = createOverallReport(result);

        return reportUtils.createPageFromListOfBusinessTripReportEntry(collect, pageable);
    }

    public Page<BusinessTripReportEntry> getBusinessTripPerAccountReport(Pageable pageable, String nameSurname) {
        List<Object[]> result = businessTripRepository.getBusinessTripPerAccountFullData(pageable, reportUtils.prepareInputData(nameSurname));
        List<BusinessTripReportEntry> collect = createOverallReport(result);

        return reportUtils.createPageFromListOfBusinessTripReportEntry(collect, pageable);
    }

    public Page<BusinessTripReportEntry> getBusinessTripPerProjectReport(Pageable pageable, String projectName) {
        List<Object[]> result = businessTripRepository.getBusinessTripPerProjectFullData(pageable, reportUtils.prepareInputData(projectName));
        List<BusinessTripReportEntry> collect = createOverallReport(result);

        return reportUtils.createPageFromListOfBusinessTripReportEntry(collect, pageable);
    }

    public Page<BusinessTripReportEntry> getBusinessTripPerClientReport(Pageable pageable, String clientName) {
        List<Object[]> result = businessTripRepository.getBusinessTripPerClientFullData(pageable, reportUtils.prepareInputData(clientName));
        List<BusinessTripReportEntry> collect = createOverallReport(result);

        return reportUtils.createPageFromListOfBusinessTripReportEntry(collect, pageable);
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
}

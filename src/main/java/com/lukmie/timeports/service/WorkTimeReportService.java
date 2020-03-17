package com.lukmie.timeports.service;

import com.lukmie.timeports.component.ReportUtils;
import com.lukmie.timeports.entity.WorkTimeReportEntry;
import com.lukmie.timeports.repository.WorkTimeRepository;
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
public class WorkTimeReportService {

    private final WorkTimeRepository workTimeRepository;
    private final ReportUtils reportUtils;

    public Page<WorkTimeReportEntry> getWorkTimeReport(Pageable pageable, Integer yearFrom, Integer yearTo,
                                                       Integer monthFrom, Integer monthTo) {
        List<Object[]> result = workTimeRepository.getWorkTimeReport(pageable, yearFrom, yearTo, monthFrom, monthTo);
        List<WorkTimeReportEntry> list = createReport(result);
        return reportUtils.createPageFromListOfBusinessTripReportEntry(list, pageable);
    }

    public Page<WorkTimeReportEntry> getWorkTimeReportByAccountName(Pageable pageable, String nameSurname, Integer yearFrom,
                                                                    Integer yearTo, Integer monthFrom, Integer monthTo) {
        List<Object[]> result = workTimeRepository.getWorkTimeReportByAccountName(pageable, reportUtils.prepareInputData(nameSurname), yearFrom, yearTo, monthFrom, monthTo);
        List<WorkTimeReportEntry> list = createReport(result);

        return reportUtils.createPageFromListOfBusinessTripReportEntry(list, pageable);
    }

    public Page<WorkTimeReportEntry> getWorkTimeReportByProjectName(Pageable pageable, String name, Integer yearFrom,
                                                                    Integer yearTo, Integer monthFrom, Integer monthTo) {
        List<Object[]> result = workTimeRepository.getWorkTimeReportByProjectName(pageable, reportUtils.prepareInputData(name), yearFrom, yearTo, monthFrom, monthTo);
        List<WorkTimeReportEntry> list = createReport(result);

        return reportUtils.createPageFromListOfBusinessTripReportEntry(list, pageable);
    }

    public Page<WorkTimeReportEntry> getWorkTimeReportByClientName(Pageable pageable, String name, Integer yearFrom,
                                                                   Integer yearTo, Integer monthFrom, Integer monthTo) {
        List<Object[]> result = workTimeRepository.getWorkTimeReportByClientName(pageable, reportUtils.prepareInputData(name), yearFrom, yearTo, monthFrom, monthTo);
        List<WorkTimeReportEntry> list = createReport(result);

        return reportUtils.createPageFromListOfBusinessTripReportEntry(list, pageable);
    }

    private List<WorkTimeReportEntry> createReport(List<Object[]> result) {
        return result.stream()
                .map(WorkTimeReportEntry::buildOverallEntry)
                .collect(Collectors.toList());
    }
}

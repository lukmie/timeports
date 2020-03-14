package com.lukmie.timeports.entity;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;
import java.util.stream.Collectors;

@Data
@AllArgsConstructor
public class BusinessTripReport {

    private List<BusinessTripReportEntry> entries;

    public static BusinessTripReport buildReport(List<Object[]> results) {
        return new BusinessTripReport(resultsToReportEntriesMapper(results));
    }

    private static List<BusinessTripReportEntry> resultsToReportEntriesMapper(List<Object[]> results) {
        return results.stream()
                .map(BusinessTripReportEntry::buildOverallEntry)
                .collect(Collectors.toList());
    }


}

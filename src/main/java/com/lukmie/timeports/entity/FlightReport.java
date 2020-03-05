package com.lukmie.timeports.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.stream.Collectors;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class FlightReport {
    private List<FlightReportEntry> entries;

    public static FlightReport buildFlightReport(List<Object[]> results) {
        return new FlightReport(mapResultsToReportEntries(results));
    }

    private static List<FlightReportEntry> mapResultsToReportEntries(List<Object[]> results) {
        return results.stream()
                .map(FlightReportEntry::buildEntry)
                .collect(Collectors.toList());
    }
}

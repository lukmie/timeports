package com.lukmie.timeports.component;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Objects;

@Component
public class ReportUtils {

    public final static String[] partialTripReportHeader = {"name", "numberOfNights", "totalTrips"};
    public final static String[] overallTripReportHeader = {"name", "projectName", "clientName", "numberOfNights",
            "totalTrips", "insuranceBy", "travelBy", "accommodationBy"};
    public final static String[] workTimeReportHeader = {"name", "month", "year", "workedHours", "project", "client"};

    public <T> Page<T> createPageFromListOfBusinessTripReportEntry(List<T> collect, Pageable pageable) {
        int start = (int) pageable.getOffset();
        int end = Math.min(start + pageable.getPageSize(), collect.size());

        if (start > end) {
            return new PageImpl<>(collect.subList(0, end), PageRequest.of(0, pageable.getPageSize()), collect.size());
        }
        return new PageImpl<>(collect.subList(start, end), pageable, collect.size());
    }

    public String prepareInputData(String input) {
        if (Objects.nonNull(input)) {
            input = String.join(" ", input.split("[\\s]+"));
        }
        return input;
    }
}

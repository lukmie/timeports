package com.lukmie.timeports.entity;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.math.RoundingMode;

@Getter
@Setter
@Builder
public class WorkTimeReportEntry {

    private String name;
    private Integer month;
    private Integer year;
    private BigDecimal workedHours;
    private String project;
    private String client;

    public static WorkTimeReportEntry buildOverallEntry(Object[] result) {
        return WorkTimeReportEntry.builder()
                .name((String) result[0])
                .month((Integer) result[1])
                .year((Integer) result[2])
                .workedHours(((BigDecimal) result[3]).setScale(2, RoundingMode.FLOOR))
                .project((String) result[4])
                .client((String) result[5])
                .build();
    }

    public static WorkTimeReportEntry buildPartialEntry(Object[] result) {
        return WorkTimeReportEntry.builder()
                .name((String) result[0])
                .workedHours(((BigDecimal) result[1]).setScale(2, RoundingMode.FLOOR))
                .build();
    }
}

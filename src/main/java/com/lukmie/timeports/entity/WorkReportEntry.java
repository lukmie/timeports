package com.lukmie.timeports.entity;

import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;

@Data
@Builder
public class WorkReportEntry {
    private String name;
    private String surname;
    private String client;
    private String project;
    private BigDecimal sum;

    public static WorkReportEntry buildEntry(Object[] results) {
        return WorkReportEntry.builder()
                .name((String) results[0])
                .surname((String) results[1])
                .client((String) results[2])
                .sum((BigDecimal) results[3])
                .project(results.length == 5 ? (String) results[4] : null)
                .build();
    }
}

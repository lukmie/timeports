package com.lukmie.timeports.entity;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.math.RoundingMode;

@Getter
@Setter
@Builder
public class WorkTimeReportEntry {

    private String name;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private Integer month;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private Integer year;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private BigDecimal workedHours;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private BigInteger numberInputsInMonth;
    private String project;
    private String client;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private String startTime;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private String endTime;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private boolean coreHours;

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

    public static WorkTimeReportEntry buildTimeSheetEntry(Object[] result) {
        return WorkTimeReportEntry.builder()
                .name((String) result[0])
                .month((Integer) result[1])
                .year((Integer) result[2])
                .numberInputsInMonth((BigInteger) result[3])
                .project((String) result[4])
                .client((String) result[5])
                .build();
    }

    public static WorkTimeReportEntry buildCoreHoursSheetEntry(Object[] result) {
        return WorkTimeReportEntry.builder()
                .name((String) result[0])
                .startTime(((String) result[1]))
                .endTime((String) result[2])
                .project((String) result[3])
                .client((String) result[4])
                .coreHours((boolean) result[5])
                .build();
    }
}

package com.lukmie.timeports.entity;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.math.BigInteger;

@Getter
@Setter
@Builder
public class BusinessTripReportEntry {

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private String name;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private String projectName;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private String clientName;
    private BigInteger numberOfNights;
    private BigInteger totalTrips;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private String insuranceBy;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private String travelBy;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private String accommodationBy;

    public static BusinessTripReportEntry buildOverallEntry(Object[] result) {
        return BusinessTripReportEntry.builder()
                .name((String) result[0])
                .projectName((String) result[1])
                .clientName((String) result[2])
                .numberOfNights((BigInteger) result[3])
                .totalTrips((BigInteger) result[4])
                .insuranceBy((String) result[5])
                .travelBy((String) result[6])
                .accommodationBy((String) result[7])
                .build();
    }

    public static BusinessTripReportEntry buildPartialEntry(Object[] result) {
        return BusinessTripReportEntry.builder()
                .name((String) result[0])
                .numberOfNights((BigInteger) result[1])
                .totalTrips((BigInteger) result[2])
                .build();
    }
}

package com.lukmie.timeports.entity;

import lombok.Builder;
import lombok.Data;

import java.math.BigInteger;

@Data
@Builder
public class FlightReportEntry {
    private String name;
    private String surname;
    private String client;
    private String travelReservation;
    private BigInteger sum;

    public static FlightReportEntry buildEntry(Object[] results) {
        return FlightReportEntry.builder()
                .name((String) results[0])
                .surname((String) results[1])
                .client((String) results[2])
                .travelReservation((String) results[3])
                .sum((BigInteger) results[4])
                .build();
    }
}

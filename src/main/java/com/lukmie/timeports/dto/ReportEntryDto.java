package com.lukmie.timeports.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class ReportEntryDto {

    private String name;
    private Double value;

}

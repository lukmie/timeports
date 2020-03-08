package com.lukmie.timeports.mapper;

import com.lukmie.timeports.dto.ReportEntryDto;
import com.lukmie.timeports.entity.Account;
import org.springframework.stereotype.Component;

@Component
public class FirstReportMapper {

    public ReportEntryDto toReportEntryDto(Account account, Double value) {
        return ReportEntryDto.builder()
                .name(account.getName() + " " + account.getSurname())
                .value(value)
                .build();
    }
}

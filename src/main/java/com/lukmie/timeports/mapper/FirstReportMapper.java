package com.lukmie.timeports.mapper;

import com.lukmie.timeports.dto.ReportEntryDto;
import com.lukmie.timeports.entity.Account;
import com.lukmie.timeports.entity.Project;
import org.springframework.stereotype.Component;

@Component
public class FirstReportMapper {

    public ReportEntryDto toAccountReportEntryDto(Account account, Double value) {
        return ReportEntryDto.builder()
                .name(account.getName() + " " + account.getSurname())
                .value(value)
                .build();
    }

    public ReportEntryDto toProjectReportEntryDto(Project project, Double value) {
        return ReportEntryDto.builder()
                .name(project.getName())
                .value(value)
                .build();
    }

}

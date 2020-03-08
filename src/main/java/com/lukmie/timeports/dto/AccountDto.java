package com.lukmie.timeports.dto;

import com.lukmie.timeports.entity.Account;
import com.lukmie.timeports.entity.Client;
import com.lukmie.timeports.entity.Project;
import com.lukmie.timeports.entity.TimeSheetReport;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;
import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AccountDto {

    private String name;
    private String surname;
    private String username;
    private Client defaultClient;
    private Set<Client> clients;
    private Project defaultProject;
    private Set<Project> projects;
    private List<TimeSheetReport> timeSheetReports;

    public static AccountDto toAccountDto(Account account) {
        return AccountDto.builder()
                .name(account.getName())
                .surname(account.getSurname())
                .username(account.getUsername())
                .defaultClient(account.getDefaultClient())
                .clients(account.getClients())
                .defaultProject(account.getDefaultProject())
                .projects(account.getProjects())
                .timeSheetReports(account.getTimeSheetReports())
                .build();
    }

}
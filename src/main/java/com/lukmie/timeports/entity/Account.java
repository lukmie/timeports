package com.lukmie.timeports.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import java.util.List;
import java.util.Set;

@Entity
//@EqualsAndHashCode(exclude = {""})
//@ToString(exclude = "")
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Account {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private String surname;
    private String username;

    @OneToOne
    @JoinColumn(name = "defaultclient")
    private Client defaultclient;

    @OneToOne
    @JoinColumn(name = "defaultproject")
    private Project defaultproject;

    @ManyToMany(fetch = FetchType.LAZY, mappedBy = "accounts")
    private Set<Client> clients;

    @ManyToMany(fetch = FetchType.LAZY, mappedBy = "accounts")
    private Set<Project> projects;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "account")
    private List<TimesheetReport> timesheetReports;
}

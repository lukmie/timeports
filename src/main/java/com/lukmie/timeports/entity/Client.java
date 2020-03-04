package com.lukmie.timeports.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
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
public class Client {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    @Enumerated(value = EnumType.STRING)
    private Status status;

    @ManyToMany(fetch = FetchType.LAZY, cascade = {CascadeType.MERGE, CascadeType.PERSIST})
    @JoinTable(
            name = "accountclients",
            inverseJoinColumns = {@JoinColumn(name = "accounts_id")},
            joinColumns = {@JoinColumn(name = "client_id")}
    )
    private Set<Account> accounts;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "client")
    private List<Project> project;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "client")
    private List<DailyTime> dailyTimes;
}

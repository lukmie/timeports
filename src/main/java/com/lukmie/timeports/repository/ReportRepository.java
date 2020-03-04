package com.lukmie.timeports.repository;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import java.util.List;

@RequiredArgsConstructor
@Repository
public class ReportRepository {

    private final EntityManager entityManager;

    public List<Object[]> fetchReportWorkHoursEntries() {
        Query query = entityManager.createNativeQuery(
                "select a.name, a.surname,  c.name as client, sum(d2.worktime/1000) from account a " +
                        "join timesheetreport t on a.id = t.accountid " +
                        "join dailytime d2 on t.id = d2.timesheetreportid " +
                        "join client c on d2.clientid = c.id " +
                        "group by a.surname, a.name, c.name");
        return query.getResultList();
    }

    public List<Object[]> fetchReportFlightsEntries() {
        Query query = entityManager.createNativeQuery(
                "select a.name, a.surname, c.name as client, b.travelreservationmadeby, count(b.id) from account a " +
                        "    join businesstrip b on a.id = b.accountid " +
                        "    join client c on b.clientid = c.id " +
                        "group by a.surname, a.name, c.name, b.travelreservationmadeby");
        return query.getResultList();
    }

}

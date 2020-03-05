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

    public List<Object[]> fetchReportWorkHoursEntriesPerClient(String parameter) {
        Query query = entityManager.createNativeQuery(
                "select a.name, a.surname,  c.name as client, sum(dt.worktime/3600000) from account a " +
                        "join timesheetreport t on a.id = t.accountid " +
                        "join dailytime dt on t.id = dt.timesheetreportid " +
                        "join client c on dt.clientid = c.id " +
                        "group by a.surname, a.name, c.name " +
                "order by " +
                        "case when :param = 'name' then a.name end asc, " +
                        "case when :param = 'surname' then a.surname end asc, " +
                        "case when :param = 'client' then c.name end asc"
        );
        query.setParameter("param" , parameter);
        return query.getResultList();
    }

    public List<Object[]> fetchReportWorkHoursEntriesPerProject(String parameter) {
        Query query = entityManager.createNativeQuery(
                "select a.name as name , a.surname as surname, c.name as client, sum(dt.worktime/3600000) as hours , p.name as project from account a " +
                        "left join timesheetreport t on a.id = t.accountid " +
                        "left join dailytime dt on t.id = dt.timesheetreportid " +
                        "left join client c on dt.clientid = c.id  " +
                        "left join project p on dt.projectid = p.id " +
                        "group by  a.surname, a.name, c.name, p.name " +
                " ORDER BY " +
                        "case when :param = 'name' then a.name end asc, " +
                        "case when :param = 'surname' then a.surname end asc, " +
                        "case when :param = 'project' then p.name end asc"
        );
        query.setParameter("param" , parameter);
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

package com.lukmie.timeports.repository;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import java.util.List;
import java.util.Objects;

@RequiredArgsConstructor
@Repository
public class WorkTimeRepository {

    private final static String SELECT_QUERY =
            "select concat(a.name, ' ', a.surname) as name,\n" +
                    "       t.month                        as month,\n" +
                    "       t.year                         as year,\n" +
                    "       sum(dt.worktime) / 3600000     as workedHours,\n" +
                    "       coalesce(p.name, 'No data')    as project,\n" +
                    "       coalesce(c.name, 'No data')    as client\n" +
                    "from dailytime dt\n" +
                    "       left join timesheetreport t on dt.timesheetreportid = t.id\n" +
                    "       left join account a on t.accountid = a.id\n" +
                    "       left join project p on dt.projectid = p.id\n" +
                    "       left join client c on p.clientid = c.id\n";

    private final EntityManager entityManager;

    public List<Object[]> getWorkTimeReport(Pageable pageable, Integer yearFrom, Integer yearTo, Integer monthFrom, Integer monthTo) {

        StringBuilder queryBuilder = new StringBuilder();
        queryBuilder.append(SELECT_QUERY);
        queryBuilder.append("where year between :yearFrom and :yearTo\n" +
                "and month between :monthFrom and :monthTo\n");
        queryBuilder.append("group by a.name, a.surname, t.month, t.year, p.name, c.name\n" +
                "having sum(dt.worktime) is not null\n");

        addSorting(pageable, queryBuilder);

        Query query = entityManager.createNativeQuery(queryBuilder.toString());
        query.setParameter("yearFrom", yearFrom)
                .setParameter("yearTo", yearTo)
                .setParameter("monthFrom", monthFrom)
                .setParameter("monthTo", monthTo);

        return query.getResultList();
    }

    public List<Object[]> getWorkTimeReportByAccountName(Pageable pageable, String nameSurname, Integer yearFrom, Integer yearTo, Integer monthFrom, Integer monthTo) {

        StringBuilder queryBuilder = new StringBuilder();
        queryBuilder.append(SELECT_QUERY);
        queryBuilder.append("where concat(a.name, ' ', a.surname) = :accountNameSurname\n" +
                "and year between :yearFrom and :yearTo\n" +
                "and month between :monthFrom and :monthTo\n" +
                "group by a.name, a.surname, t.month, t.year, p.name, c.name\n" +
                "having sum(dt.worktime) is not null");

        addSorting(pageable, queryBuilder);

        Query query = entityManager.createNativeQuery(queryBuilder.toString());
        query.setParameter("accountNameSurname", nameSurname)
                .setParameter("yearFrom", yearFrom)
                .setParameter("yearTo", yearTo)
                .setParameter("monthFrom", monthFrom)
                .setParameter("monthTo", monthTo);

        return query.getResultList();
    }

    public List<Object[]> getWorkTimeReportByProjectName(Pageable pageable, String name, Integer yearFrom, Integer yearTo, Integer monthFrom, Integer monthTo) {

        StringBuilder queryBuilder = new StringBuilder();
        queryBuilder.append(SELECT_QUERY);
        queryBuilder.append("where p.name = :projectName\n" +
                "and year between :yearFrom and :yearTo\n" +
                "and month between :monthFrom and :monthTo\n" +
                "group by a.name, a.surname, t.month, t.year, p.name, c.name\n" +
                "having sum(dt.worktime) is not null");

        addSorting(pageable, queryBuilder);

        Query query = entityManager.createNativeQuery(queryBuilder.toString());
        query.setParameter("projectName", name)
                .setParameter("yearFrom", yearFrom)
                .setParameter("yearTo", yearTo)
                .setParameter("monthFrom", monthFrom)
                .setParameter("monthTo", monthTo);

        return query.getResultList();
    }

    public List<Object[]> getWorkTimeReportByClientName(Pageable pageable, String name, Integer yearFrom, Integer yearTo, Integer monthFrom, Integer monthTo) {

        StringBuilder queryBuilder = new StringBuilder();
        queryBuilder.append(SELECT_QUERY);
        queryBuilder.append("where c.name = :clientName\n" +
                "and year between :yearFrom and :yearTo\n" +
                "and month between :monthFrom and :monthTo\n" +
                "group by a.name, a.surname, t.month, t.year, p.name, c.name\n" +
                "having sum(dt.worktime) is not null");

        addSorting(pageable, queryBuilder);

        Query query = entityManager.createNativeQuery(queryBuilder.toString());
        query.setParameter("clientName", name)
                .setParameter("yearFrom", yearFrom)
                .setParameter("yearTo", yearTo)
                .setParameter("monthFrom", monthFrom)
                .setParameter("monthTo", monthTo);

        return query.getResultList();
    }

    private void addSorting(Pageable pageable, StringBuilder queryBuilder) {
        if (Objects.nonNull(pageable.getSort()) && pageable.getSort().isSorted()) {
            Sort.Order order = pageable.getSort().iterator().next();
            String property = order.getProperty();
            queryBuilder.append(" order by ")
                    .append(property.equals("surname") ? "a.surname" : property).append(" ")
                    .append(order.getDirection().name());
        } else {
            queryBuilder.append(" order by t.month, t.year");
        }
    }
}

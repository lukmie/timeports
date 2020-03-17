package com.lukmie.timeports.repository;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import java.util.List;

@RequiredArgsConstructor
@Repository
public class BusinessTripRepository {

    private final static String OVERALL_REPORT_SELECT_QUERY =
            "select concat(a.name, ' ', a.surname)    as name,\n" +
                    "       coalesce(p.name, 'No data')       as projectName,\n" +
                    "       coalesce(c.name, 'No data')       as clientName,\n" +
                    "       sum(bt.numberofnights)            as numberOfNights,\n" +
                    "       count(bt.id)                      as totalTrips,\n" +
                    "       bt.insurancebuyby                 as insuranceBy,\n" +
                    "       bt.travelreservationmadeby        as travelBy,\n" +
                    "       bt.accommodationreservationmadeby as accommodationBy\n" +
                    "from businesstrip bt\n" +
                    "            left join account a on bt.accountid = a.id\n" +
                    "            left join project p on bt.projectid = p.id\n" +
                    "            left join client c on bt.clientid = c.id\n";

    private final EntityManager entityManager;


    public List<Object[]> getBusinessTripsAccounts(Pageable pageable) {

        StringBuilder queryBuilder = new StringBuilder();

        queryBuilder.append("select concat(a.name, ' ', a.surname)    as name,\n" +
                "       sum(bt.numberofnights)            as numberOfNights,\n" +
                "       count(bt.id)                      as totalTrips\n" +
                "from businesstrip bt\n" +
                "            left join account a on bt.accountid = a.id\n" +
                "group by a.name, a.surname");

        addSorting(pageable, queryBuilder);

        Query query = entityManager.createNativeQuery(queryBuilder.toString());

        return query.getResultList();
    }

    public List<Object[]> getBusinessTripsAccountsByAccountName(String nameSurname) {

        StringBuilder queryBuilder = new StringBuilder();

        queryBuilder.append("select concat(a.name, ' ', a.surname)    as name,\n" +
                "       sum(bt.numberofnights)            as numberOfNights,\n" +
                "       count(bt.id)                      as totalTrips\n" +
                "from businesstrip bt\n" +
                "            left join account a on bt.accountid = a.id\n" +
                "where concat(a.name, ' ', a.surname) = :accountNameSurname\n" +
                "group by a.name, a.surname");

        Query query = entityManager.createNativeQuery(queryBuilder.toString());
        query.setParameter("accountNameSurname", nameSurname);

        return query.getResultList();
    }

    public List<Object[]> getBusinessTripsProjects(Pageable pageable) {

        StringBuilder queryBuilder = new StringBuilder();

        queryBuilder.append("select coalesce(p.name, 'No data')    as name,\n" +
                "       sum(bt.numberofnights)            as numberOfNights,\n" +
                "       count(bt.id)                      as totalTrips\n" +
                "from businesstrip bt\n" +
                "            left join project p on bt.projectid = p.id\n" +
                "group by p.name");

        addSorting(pageable, queryBuilder);

        Query query = entityManager.createNativeQuery(queryBuilder.toString());

        return query.getResultList();
    }

    public List<Object[]> getBusinessTripsProjectsByProjectName(String name) {

        StringBuilder queryBuilder = new StringBuilder();

        queryBuilder.append("select coalesce(p.name, 'No data')    as name,\n" +
                "       sum(bt.numberofnights)            as numberOfNights,\n" +
                "       count(bt.id)                      as totalTrips\n" +
                "from businesstrip bt\n" +
                "            left join project p on bt.projectid = p.id\n" +
                "where p.name = :projectName\n" +
                "group by p.name");

        Query query = entityManager.createNativeQuery(queryBuilder.toString());
        query.setParameter("projectName", name);

        return query.getResultList();
    }

    public List<Object[]> getBusinessTripsClients(Pageable pageable) {

        StringBuilder queryBuilder = new StringBuilder();

        queryBuilder.append("select coalesce(c.name, 'No data')    as name,\n" +
                "       sum(bt.numberofnights)            as numberOfNights,\n" +
                "       count(bt.id)                      as totalTrips\n" +
                "from businesstrip bt\n" +
                "            left join client c on bt.clientid = c.id\n" +
                "group by c.name");

        addSorting(pageable, queryBuilder);

        Query query = entityManager.createNativeQuery(queryBuilder.toString());

        return query.getResultList();
    }

    public List<Object[]> getBusinessTripsClientsByClientName(String name) {

        StringBuilder queryBuilder = new StringBuilder();

        queryBuilder.append("select coalesce(c.name, 'No data')    as name,\n" +
                "       sum(bt.numberofnights)            as numberOfNights,\n" +
                "       count(bt.id)                      as totalTrips\n" +
                "from businesstrip bt\n" +
                "            left join client c on bt.clientid = c.id\n" +
                "where c.name = :clientName\n" +
                "group by c.name");

        Query query = entityManager.createNativeQuery(queryBuilder.toString());
        query.setParameter("clientName", name);

        return query.getResultList();
    }

    public List<Object[]> getAllBusinessTripFullData(Pageable pageable) {

        StringBuilder queryBuilder = new StringBuilder();
        queryBuilder.append(OVERALL_REPORT_SELECT_QUERY);
        queryBuilder.append("group by a.name, a.surname, p.name, c.name, bt.insurancebuyby, bt.travelreservationmadeby,\n" +
                "         bt.accommodationreservationmadeby");

        addSorting(pageable, queryBuilder);

        Query query = entityManager.createNativeQuery(queryBuilder.toString());

        return query.getResultList();
    }

    public List<Object[]> getBusinessTripPerAccountFullData(Pageable pageable, String nameSurname) {

        StringBuilder queryBuilder = new StringBuilder();
        queryBuilder.append(OVERALL_REPORT_SELECT_QUERY);
        queryBuilder.append("where concat(a.name, ' ', a.surname) = :accountNameSurname\n");
        queryBuilder.append("group by a.name, a.surname, p.name, c.name, bt.insurancebuyby, bt.travelreservationmadeby,\n" +
                "         bt.accommodationreservationmadeby");

        addSorting(pageable, queryBuilder);

        Query query = entityManager.createNativeQuery(queryBuilder.toString())
                .setParameter("accountNameSurname", nameSurname);

        return query.getResultList();
    }

    public List<Object[]> getBusinessTripPerProjectFullData(Pageable pageable, String projectName) {

        StringBuilder queryBuilder = new StringBuilder();
        queryBuilder.append(OVERALL_REPORT_SELECT_QUERY);
        queryBuilder.append("where p.name = :projectName\n");
        queryBuilder.append("group by a.name, a.surname, p.name, c.name, bt.insurancebuyby, bt.travelreservationmadeby,\n" +
                "         bt.accommodationreservationmadeby");

        addSorting(pageable, queryBuilder);

        Query query = entityManager.createNativeQuery(queryBuilder.toString())
                .setParameter("projectName", projectName);

        return query.getResultList();
    }

    public List<Object[]> getBusinessTripPerClientFullData(Pageable pageable, String clientName) {

        StringBuilder queryBuilder = new StringBuilder();
        queryBuilder.append(OVERALL_REPORT_SELECT_QUERY);
        queryBuilder.append("where c.name = :clientName\n");
        queryBuilder.append("group by a.name, a.surname, p.name, c.name, bt.insurancebuyby, bt.travelreservationmadeby,\n" +
                "         bt.accommodationreservationmadeby");

        addSorting(pageable, queryBuilder);

        Query query = entityManager.createNativeQuery(queryBuilder.toString())
                .setParameter("clientName", clientName);

        return query.getResultList();
    }

    private void addSorting(Pageable pageable, StringBuilder queryBuilder) {
        Sort sort = pageable.getSort();
        if (sort.isSorted()) {
            Sort.Order order = sort.iterator().next();
            String property = order.getProperty();
            queryBuilder.append(" order by ")
                    .append(property.equals("surname") ? "a.surname" : property).append(" ")
                    .append(order.getDirection().name());
        }
    }
}

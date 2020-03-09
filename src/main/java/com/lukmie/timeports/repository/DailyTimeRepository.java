package com.lukmie.timeports.repository;

import com.lukmie.timeports.entity.DailyTime;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface DailyTimeRepository extends JpaRepository<DailyTime, Long> {

    @Query(value = "select sum(dt.worktime) from DailyTime as dt left join TimeSheetReport as tsr on dt.timesheetreportid = tsr.id where dt.worktime is not null and tsr.accountid = :id", nativeQuery = true)
    Optional<Long> findSumWorkTimeForAccount(Long id);

    @Query("select sum(worktime) from DailyTime dt where dt.project.id is not null and worktime is not null and dt.project.id = :id")
    Optional<Long> findSumWorkTimeForProject(Long id);

    @Query("select sum(worktime) from DailyTime dt where dt.client.id is not null and worktime is not null and dt.client.id = :id")
    Optional<Long> findSumWorkTimeForClient(Long id);

//    @Query(value = "select dt.id, dt.worktime, dt.client, dt.project, dt.timeSheetReport from DailyTime dt join DailyTime.timeSheetReport t on dt.timesheetreportid = t.id where DailyTime.timeSheetReport. = :id")
//    List<DailyTime> findAllByxAccountId(Long id);

//    @Query(value = "select dt.id, dt.worktime, dt.clientid, dt.projectid, dt.timesheetreportid from DailyTime as dt left join TimeSheetReport as tsr on dt.timesheetreportid = tsr.id where tsr.accountid = :id", nativeQuery = true)
//    List<DailyTime> findAllByxAccountId(Long id);

//    @Query(value = "select new com.lukmie.timeports.dto.DailyTimeDto(dt.worktime) from DailyTime dt where dt.timeSheetReport id = 1016")
//    DailyTimeDto findAllByAccountIdX();

//    @Query(value = "select sum(dt.worktime) as worktime from DailyTime as dt left join TimeSheetReport as tsr on dt.timesheetreportid = tsr.id where dt.worktime is not null and tsr.accountid = 1335", nativeQuery = true)
//    DailyTimeDto findAllByAccountIdX();

//    @Query("select sum(dt.worktime) from DailyTime dt join timesheetreport t on dt.timesheetreportid = t.id where t.accountid = :id")
//    long findSumByAccountId(Long id);
}

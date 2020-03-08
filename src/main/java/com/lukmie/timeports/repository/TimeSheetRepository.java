package com.lukmie.timeports.repository;

import com.lukmie.timeports.entity.TimeSheetReport;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TimeSheetRepository extends JpaRepository<TimeSheetReport, Long> {


}

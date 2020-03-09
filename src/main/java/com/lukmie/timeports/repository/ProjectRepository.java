package com.lukmie.timeports.repository;

import com.lukmie.timeports.entity.Project;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProjectRepository extends JpaRepository<Project, Long> {

//    @Query("select p from Project p left join DailyTime dt on p.id = dt where sum(worktime)")
//    Page<Project> findAllX(Pageable pageable);

}

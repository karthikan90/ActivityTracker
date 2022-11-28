package com.acs.activitytracker.repo;

import com.acs.activitytracker.entity.Activity;
import com.acs.activitytracker.entity.ActivityLog;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

@Repository
public interface ActivityLogRepo extends JpaRepository<ActivityLog, Integer> {
    List<ActivityLog> findAllByActivity(final Activity activity);

    List<ActivityLog> findAllByActivityAndCreatedDateGreaterThanEqualAndCreatedDateLessThanEqual(final Activity activity,
                                                                                                 final Date startDate,
                                                                                                 final Date endDate);
}

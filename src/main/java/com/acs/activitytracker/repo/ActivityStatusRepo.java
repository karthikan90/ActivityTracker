package com.acs.activitytracker.repo;

import com.acs.activitytracker.entity.ActivityLog;
import com.acs.activitytracker.entity.ActivityStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ActivityStatusRepo extends JpaRepository<ActivityStatus, Integer> {
}

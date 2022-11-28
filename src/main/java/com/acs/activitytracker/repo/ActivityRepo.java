package com.acs.activitytracker.repo;

import com.acs.activitytracker.entity.Activity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.List;

@Repository
public interface ActivityRepo extends JpaRepository<Activity, Integer> {
    List<Activity> findAllByCreatedBy(Integer userId);

    List<Activity> findAllByUserId(Integer managerId);
}

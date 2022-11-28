package com.acs.activitytracker.repo;

import com.acs.activitytracker.entity.UserDetail;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserDetailsRepo extends JpaRepository<UserDetail, Integer> {
}

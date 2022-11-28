package com.acs.activitytracker.service;

import com.acs.activitytracker.entity.Activity;
import com.acs.activitytracker.entity.UserDetail;
import com.acs.activitytracker.repo.ActivityRepo;
import com.acs.activitytracker.repo.UserDetailsRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

public class ActivityService {


    @Autowired
    private UserDetailsRepo userDetailsRepo;

    @Autowired
    private ActivityRepo activityRepo;

    public List<Activity> getActivityDetailById(Integer userId, boolean isSelf) {
        StringUtils.hasText("");
        //Check whether userId belongs to Supervisor or not
        final Optional<UserDetail> userDetailOptional = userDetailsRepo.findById(userId);
        if(userDetailOptional.isPresent()) {
            UserDetail userDetail = userDetailOptional.get();
            //if user is belongs to supervisor
            if(userDetail.isSupervisor()){
                if (isSelf) {// if isSelf flag is true then he needs to fetch his own activities
                    // and also fetch his manager id because, his manager might have assigned activities for him
                    return getSelfActivities(userDetail);
                } else {
                    //If it is not self, then return activities created by him
                    return activityRepo.findAllByCreatedBy(userId);
                }
            } else {
                //If user id belongs to employee then will give his all activities
                return getSelfActivities(userDetail);
            }
        }
        return Collections.emptyList();
    }

    private List<Activity> getSelfActivities (UserDetail userDetail) {
        List<Activity> activities = activityRepo.findAllByCreatedBy(userDetail.getManagerId());
        activities.addAll(activityRepo.findAllByUserId(userDetail.getId()));
        return activities;
    }
}

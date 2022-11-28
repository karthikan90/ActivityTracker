package com.acs.activitytracker.util;

import com.acs.activitytracker.dto.LeaderBoardDTO;
import com.acs.activitytracker.entity.Activity;
import com.acs.activitytracker.entity.ActivityLog;
import com.acs.activitytracker.entity.UserDetail;
import com.acs.activitytracker.repo.ActivityLogRepo;
import com.acs.activitytracker.repo.ActivityRepo;
import com.acs.activitytracker.repo.UserDetailsRepo;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.junit.jupiter.MockitoSettings;
import org.mockito.quality.Strictness;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
@MockitoSettings(strictness = Strictness.WARN)
public class TopLeadersCalculatorTest {

    @Mock
    private UserDetailsRepo userDetailsRepo;

    @Mock
    private ActivityRepo activityRepo;

    @Mock
    private ActivityLogRepo activityLogRepo;

    @InjectMocks
    private TopLeadersCalculator topLeadersCalculator;

    @BeforeEach
    public void setUp() {
    }

    @Test
    public void topLeadersWithManagerIdHappyPath() {

        Integer managerId = 1;
        Date startDate = new Date();
        Date endDate = new Date();
        when(activityRepo.findAllByCreatedBy(managerId)).thenReturn(getActivities());
        for (int i = 2; i <= 7; i++) {
            when(userDetailsRepo.findById(2)).thenReturn(getUserDetail(i, "John-" + i));
        }

        for (int i = 0; i < getActivities().size(); i++) {
            Activity activity = getActivities().get(i);
            List<ActivityLog> activityLogs = getActivityLogs().stream()
                    .filter(activityLog -> Objects.equals(activityLog.getActivity().getId(), activity.getId())).collect(Collectors.toList());
            when(activityLogRepo.findAllByActivityAndCreatedDateGreaterThanEqualAndCreatedDateLessThanEqual(activity, startDate, endDate))
                    .thenReturn(activityLogs);
        }

        List<LeaderBoardDTO> topLeaders = topLeadersCalculator.getTopLeaders(managerId, startDate, endDate);
        assertNotNull(topLeaders);
    }

    private Optional<UserDetail> getUserDetail(int userId, String name) {
        UserDetail userDetail = new UserDetail();
        userDetail.setId(userId);
        userDetail.setName(name);
        return Optional.of(userDetail);
    }

    private List<ActivityLog> getActivityLogs() {
        List<ActivityLog> activityLogs = new ArrayList<>();

        activityLogs.add(getActivityLog(1, 1, 20, 2));
        activityLogs.add(getActivityLog(1, 1, 21, 2));
        activityLogs.add(getActivityLog(1, 2, 10, 3));
        activityLogs.add(getActivityLog(1, 2, 11, 3));
        activityLogs.add(getActivityLog(1, 3, 18, 4));
        activityLogs.add(getActivityLog(1, 3, 17, 4));
        activityLogs.add(getActivityLog(1, 4, 14, 5));
        activityLogs.add(getActivityLog(1, 4, 12, 5));
        activityLogs.add(getActivityLog(1, 5, 25, 6));
        activityLogs.add(getActivityLog(1, 5, 27, 6));
        activityLogs.add(getActivityLog(1, 6, 19, 7));
        activityLogs.add(getActivityLog(1, 6, 20, 7));

        return activityLogs;
    }

    private ActivityLog getActivityLog(int id, int activityId, int points, int userId) {
        ActivityLog activityLog = new ActivityLog();
        activityLog.setId(id);
        activityLog.setActivity(getActivity(activityId, 1, userId, false));
        activityLog.setPoints(points);
        return activityLog;
    }

    private List<Activity> getActivities() {
        List<Activity> activities = new ArrayList<>();
        activities.add(getActivity(1, 1, 2, false));
        activities.add(getActivity(2, 1, 3, false));
        activities.add(getActivity(3, 1, 4, false));
        activities.add(getActivity(4, 1, 5, false));
        activities.add(getActivity(5, 1, 6, false));
        activities.add(getActivity(6, 1, 7, false));
        return activities;
    }

    private Activity getActivity(Integer activityId, Integer managerId, Integer userId, boolean isSupervisor) {
        Activity activity = new Activity();
        activity.setId(activityId);
        activity.setCreatedBy(managerId);
        activity.setUserId(userId);
        activity.setSupervisor(isSupervisor);
        return activity;
    }
}

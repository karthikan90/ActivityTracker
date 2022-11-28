package com.acs.activitytracker.util;

import com.acs.activitytracker.dto.LeaderBoardDTO;
import com.acs.activitytracker.entity.Activity;
import com.acs.activitytracker.entity.ActivityLog;
import com.acs.activitytracker.entity.UserDetail;
import com.acs.activitytracker.repo.ActivityLogRepo;
import com.acs.activitytracker.repo.ActivityRepo;
import com.acs.activitytracker.repo.UserDetailsRepo;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.IntSummaryStatistics;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.OptionalDouble;

@Component
public class TopLeadersCalculator {

    @Autowired
    private UserDetailsRepo userDetailsRepo;

    @Autowired
    private ActivityRepo activityRepo;

    @Autowired
    private ActivityLogRepo activityLogRepo;

    public List<LeaderBoardDTO> getTopLeaders(final Integer userId, final Date startDate, final Date endDate) {
        final Optional<UserDetail> userDetail = userDetailsRepo.findById(userId);
        Integer managerId = userId;
        //if user is not a supervisor, then get the manager id
        if(userDetail.isPresent() && !userDetail.get().isSupervisor()) {
            managerId = userDetail.get().getManagerId();
        }

        //Let us say, we have 100 activities for total 50 users
        final List<Activity> activities = activityRepo.findAllByCreatedBy(managerId);

        //totalSummaryStatistics map contains key as user id and value as list of int summary
        Map<Integer, List<IntSummaryStatistics>> totalSummaryStatistics = getTotalSummaryStatistics(activities, startDate, endDate);// 3 user id ->

        //userPoints => key as user id and average points as value // 3 users
        final Map<Integer, Double> userPoints = getUserPointsForUsers(totalSummaryStatistics);

        //Now, we have total avg points for each user
        final List<LeaderBoardDTO> leaderBoardDTOS = new ArrayList<>();
        //This loop will iterate three times to fetch the top three users
        //After iteration, it removes the highest user from the map.
        int totalUsers = userPoints.size();// 10
        if(!userPoints.isEmpty()) {
            int i = 0;
            while (i < totalUsers) {
                Map.Entry<Integer, Double> highestPointsForUser = highestPointsForUser(userPoints);
                //after we get the highest points of the user, removing that user id from the map
                userPoints.remove(highestPointsForUser.getKey());
                leaderBoardDTOS.add(getLeaderBoardDTO((i + 1), highestPointsForUser, totalSummaryStatistics.get(highestPointsForUser.getKey())));
                i++;
            }
        }

        return leaderBoardDTOS;
    }

    private Map<Integer, Double> getUserPointsForUsers(Map<Integer, List<IntSummaryStatistics>> totalSummaryStatistics) {
        //userPoints => key as user id and average points as value // 3 users
        final Map<Integer, Double> userPoints = new HashMap<>();
        totalSummaryStatistics.forEach((userIdForActivity, value) -> {
            OptionalDouble optionalDouble = value.stream().mapToDouble(IntSummaryStatistics::getAverage).average();
            if (optionalDouble.isPresent()) {
                userPoints.put(userIdForActivity, optionalDouble.getAsDouble());
            }
        });
        return userPoints;
    }

    private Map<Integer, List<IntSummaryStatistics>> getTotalSummaryStatistics(List<Activity> activities, Date startDate, Date endDate) {
        //totalSummaryStatistics map contains key as user id and value as list of int summary
        Map<Integer, List<IntSummaryStatistics>> totalSummaryStatistics = new HashMap<>();
                activities.forEach(activity -> {//Iter1 -> 1st act -> 5 logs for user id - 1414 ==> //Iter2 -> 2nd act -> 5 logs for user id - 1414
            //Iter3 -> 1st act -> 5 logs for user id - 1414 ==> //Iter4 -> 2nd act -> 5 logs for user id - 141
            //let us say, we received 5 logs between start and edn
            final List<ActivityLog> activityLogs = activityLogRepo
                    .findAllByActivityAndCreatedDateGreaterThanEqualAndCreatedDateLessThanEqual(activity, startDate, endDate);
            Integer activityUserId = activity.getUserId();
            IntSummaryStatistics intSummaryStatistics = activityLogs.stream().mapToInt(ActivityLog::getPoints).summaryStatistics();
            if (totalSummaryStatistics.containsKey(activityUserId)) { //
                totalSummaryStatistics.get(activityUserId).add(intSummaryStatistics);
            } else {
                List<IntSummaryStatistics> summaryStatisticsForUser = new ArrayList<>();
                summaryStatisticsForUser.add(intSummaryStatistics);
                totalSummaryStatistics.put(activityUserId, summaryStatisticsForUser);
            }
        });
        return totalSummaryStatistics;
    }

    /**
     * This method will generate the leader board dto
     * @param rank it is 1,2,3, and etc.
     * @param highestPointsForUser it contains key as user id and values as average points
     * @param intSummaryStatistics which contains the total statistics of user
     * @return
     */
    private LeaderBoardDTO getLeaderBoardDTO(final int rank,
                                             final Map.Entry<Integer, Double> highestPointsForUser,
                                             final List<IntSummaryStatistics> intSummaryStatistics) {
        final LeaderBoardDTO leaderBoardDTO = new LeaderBoardDTO();
        //Calculating the total points of the user
        final int totalPoints = intSummaryStatistics.stream().mapToInt(res-> (int) res.getSum()).sum();
        final Optional<UserDetail> userDetail = userDetailsRepo.findById(highestPointsForUser.getKey());
        if(userDetail.isPresent()) {
            UserDetail user = userDetail.get();
            BeanUtils.copyProperties(user, leaderBoardDTO);
            leaderBoardDTO.setRank(rank);leaderBoardDTO.setTotalPoints(totalPoints);
        }
        return leaderBoardDTO;
    }

    /**
     * This method calculates the highest user points from the Map
     * @param map
     * @return
     * @param <K>
     * @param <V>
     */
    public <K, V extends Comparable<V>> Map.Entry<K, V> highestPointsForUser(final Map<K, V> map) {
        return Collections.max(map.entrySet(), Map.Entry.comparingByValue());
    }
}

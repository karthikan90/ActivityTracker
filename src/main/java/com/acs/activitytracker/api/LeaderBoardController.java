package com.acs.activitytracker.api;

import com.acs.activitytracker.dto.LeaderBoardDTO;
import com.acs.activitytracker.dto.LeaderBoardRequestDto;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/leaders")
public class LeaderBoardController {

    @PostMapping
    public ResponseEntity<List<LeaderBoardDTO>> getLeaders(@RequestBody LeaderBoardRequestDto leaderBoardRequestDto) {
        return ResponseEntity.ok(getAllLeaders());
    }


    private List<LeaderBoardDTO> getAllLeaders() {
        List<LeaderBoardDTO> leaderBoardDTOS = new ArrayList<>();
        leaderBoardDTOS.add(getLeader("John Doe", 100, 1, 250));
        leaderBoardDTOS.add(getLeader("John Doe", 100, 2, 245));
        leaderBoardDTOS.add(getLeader("John Doe", 100, 3, 240));
        leaderBoardDTOS.add(getLeader("John Doe", 100, 3, 240));
        return leaderBoardDTOS;
    }

    private LeaderBoardDTO getLeader(String name, Integer userId, Integer rank, Integer totalPoints) {
        LeaderBoardDTO leaderBoardDTO = new LeaderBoardDTO();
        leaderBoardDTO.setName(name);
        leaderBoardDTO.setRank(rank);
        leaderBoardDTO.setTotalPoints(totalPoints);
        leaderBoardDTO.setUserId(userId);
        leaderBoardDTO.setEmail(name + "@acsicorp.com");
        leaderBoardDTO.setLocation("GAR");
        leaderBoardDTO.setImageUrl("");
        return leaderBoardDTO;
    }
}

package com.acs.activitytracker.api;

import com.acs.activitytracker.dto.ActivityType;
import com.acs.activitytracker.dto.DashboardSummary;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/dashboard")
@CrossOrigin("*")
public class DashboardController {

    @GetMapping("/summary")
    public DashboardSummary getDashboardSummary(@RequestParam("userid") Long uid,
                                                @RequestParam("type") ActivityType activityType,
                                                @RequestParam(name = "members", required = false) List<String> members) {
        return  new DashboardSummary(1,2,3, 4);
    }
}

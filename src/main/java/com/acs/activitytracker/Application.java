package com.acs.activitytracker;

import com.acs.activitytracker.util.TopLeadersCalculator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.Date;

@SpringBootApplication
public class Application implements CommandLineRunner {

	@Autowired
	TopLeadersCalculator leadersCalculator;

	public static void main(String[] args) {
		SpringApplication.run(Application.class, args);
	}

	@Override
	public void run(String... args) {
		leadersCalculator.getTopLeaders(1, new Date(), new Date());
	}
}

package com.test.bookingsystem;

import com.test.bookingsystem.job.JobQueue;
import com.test.bookingsystem.job.JobRunner;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class BookingSystemApplication implements CommandLineRunner {
	public static void main(String args[]) { SpringApplication.run(BookingSystemApplication.class, args);
	}

	@Autowired
	JobRunner jobRunner;
	@Autowired
	JobQueue jobQueue;

	@Override
	public void run(String... args) throws Exception {
		if(args.length > 0 && args[0].equals("job-instance")) {
			jobRunner.run(jobQueue);
		}
	}
}

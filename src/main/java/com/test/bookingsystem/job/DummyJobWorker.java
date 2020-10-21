package com.test.bookingsystem.job;

import com.test.bookingsystem.repository.ShowRepository;
import com.test.bookingsystem.repository.TheatreRepository;
import com.test.bookingsystem.repository.TicketRepository;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Map;

public class DummyJobWorker extends JobWorker<String> {
    @Autowired
    ShowRepository showRepository;

    DummyJobWorker(Job job, ShowRepository showRepository) {
        super(job);
        this.showRepository = showRepository;
    }

    @Override
    void perform(String s) {
        System.out.println("Dummy perform start");
        System.out.println("Dummy perform start 2");
        System.out.println(showRepository.findAll());
        System.out.println("Dummy perform end");
    }

    @Override
    String transformJobInfo(Map<String, String> jobInfo) {
        return "Dummy";
    }
}

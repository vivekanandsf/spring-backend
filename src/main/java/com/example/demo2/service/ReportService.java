package com.example.demo2.service;

import com.example.demo2.exception.NoRecordExistsException;
import com.example.demo2.model.Report;
import com.example.demo2.model.Task;
import com.example.demo2.model.User;
import com.example.demo2.repository.ReportRepository;
import com.example.demo2.repository.UserRepository;
import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Log4j2
@AllArgsConstructor
public class ReportService {
    private final ReportRepository reportRepository;
    private final UserRepository userRepository;

    public List<Report> getUserReports(Integer uid) {
        userRepository.findById(uid).orElseThrow(() ->
                new NoRecordExistsException("User not found with id " + uid));
        List<Report> reports = reportRepository.findAllByUserId(uid);
        return reports;
    }

    public Report addReport(Report r, Integer uid) {
        User u = userRepository.findById(uid).orElseThrow(() ->
                new NoRecordExistsException("User not found with id " + uid));
        r.setUser(u);
        r.setTasks(new ArrayList<Task>());
        return reportRepository.save(r);
    }

    public void addReportsToAllUsers(Date date) {
        List<User> users = userRepository.findAll();
        List<Report> reports = users.stream().map(user -> {
            Report report = Report.builder()
                    .week_start(date)
                    .status("progress")
                    .tasks(new ArrayList<Task>())
                    .user(user)
                    .build();
            return report;
        }).collect(Collectors.toList());
        reportRepository.saveAll(reports);
    }

    public String deleteReport(Integer rid) {
        Report r = reportRepository.findById(rid).orElse(null);
        if (r == null) {
            return "Report Not Found With id: " + rid;
        } else {
            reportRepository.deleteById(rid);
            return "Success";
        }
    }
}

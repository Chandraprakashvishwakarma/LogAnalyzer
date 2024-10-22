package com.log.Loganalyzer.LogAnalyzer.services;


import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import javax.swing.SwingUtilities;

import com.log.Loganalyzer.LogAnalyzer.entities.LogEntry;
import com.log.Loganalyzer.LogAnalyzer.entities.LogSummary;
import com.log.Loganalyzer.LogAnalyzer.utils.ChartUtils;

public class LogAnalyzer {
    private List<LogEntry> logEntries;

    public LogAnalyzer(String logFilePath) throws IOException, InterruptedException {
        LogParser parser = new LogParser();
        logEntries = parser.parseLogFile(logFilePath);
    }

    public List<LogEntry> filterByLogLevel(String logLevel) {
        return logEntries.stream()
                .filter(entry -> entry.getLogLevel().equalsIgnoreCase(logLevel))
                .collect(Collectors.toList());
    }

    public List<LogEntry> filterByKeyword(String keyword) {
        return logEntries.stream()
                .filter(entry -> entry.getMessage().contains(keyword))
                .collect(Collectors.toList());
    }

    public void countLogLevels() {
        long errorCount = logEntries.stream().filter(entry -> entry.getLogLevel().equalsIgnoreCase("ERROR:")).count();
        long warnCount = logEntries.stream().filter(entry -> entry.getLogLevel().equalsIgnoreCase("WARN:")).count();
        long infoCount = logEntries.stream().filter(entry -> entry.getLogLevel().equalsIgnoreCase("INFO:")).count();

        logEntries.stream().forEach(entry->System.out.println(entry.getLogLevel()));

        System.out.println("Log Level Counts:");
        System.out.println("ERROR: " + errorCount);
        System.out.println("WARN: " + warnCount);
        System.out.println("INFO: " + infoCount);
    }

    public void printSummary() {
        System.out.println("Total Log Entries: " + logEntries.size());
        countLogLevels();
    }

    public void showChart() {
        Map<String, Long> logLevelCounts = new HashMap<>();
        logLevelCounts.put("ERROR", getCountByLogLevel("ERROR:"));
        logLevelCounts.put("WARN", getCountByLogLevel("WARN:"));
        logLevelCounts.put("INFO", getCountByLogLevel("INFO:"));

        SwingUtilities.invokeLater(() -> {
            ChartUtils chart = new ChartUtils("Log Level Statistics", logLevelCounts);
            chart.pack();
            chart.setVisible(true);
        });
    }

    private long getCountByLogLevel(String logLevel) {
        return logEntries.stream().filter(entry -> entry.getLogLevel().equalsIgnoreCase(logLevel)).count();
    }

    public List<LogEntry> getLogEntries() {
        return logEntries;
    }

    public LogSummary generateSummary() {
        LogSummary summary = new LogSummary();
        // Populate the summary with counts
        summary.setTotalEntries(logEntries.size());
        summary.setErrorCount(getCountByLogLevel("ERROR:"));
        summary.setWarnCount(getCountByLogLevel("WARN:"));
        summary.setInfoCount(getCountByLogLevel("INFO:"));
        return summary;
    }
    
}
        
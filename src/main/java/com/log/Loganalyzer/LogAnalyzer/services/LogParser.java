package com.log.Loganalyzer.LogAnalyzer.services;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

import com.log.Loganalyzer.LogAnalyzer.entities.LogEntry;

public class LogParser {
    private static final int NUM_THREADS = 4;

    public List<LogEntry> parseLogFile(String filePath) throws IOException, InterruptedException {
        ExecutorService executor = Executors.newFixedThreadPool(NUM_THREADS);
        List<Future<List<LogEntry>>> futures = new ArrayList<>();
        List<LogEntry> logEntries = Collections.synchronizedList(new ArrayList<>());

        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            String line;
            List<String> chunk = new ArrayList<>();
            while ((line = reader.readLine()) != null) {
                chunk.add(line);
                if (chunk.size() >= 100) {
                    List<String> chunkToProcess = new ArrayList<>(chunk);
                    futures.add(executor.submit(() -> processChunk(chunkToProcess)));
                    chunk.clear();
                }
            }

            if (!chunk.isEmpty()) {
                futures.add(executor.submit(() -> processChunk(chunk)));
            }

            for (Future<List<LogEntry>> future : futures) {
                logEntries.addAll(future.get());
            }
        } catch (ExecutionException e) {
            e.printStackTrace();
        } finally {
            executor.shutdown();
        }

        return logEntries;
    }

    private List<LogEntry> processChunk(List<String> chunk) {
        List<LogEntry> logEntries = new ArrayList<>();
        for (String line : chunk) {
            String[] parts = line.split(" ", 4);
            
            if (parts.length == 4) {
                parts[1] = parts[0]+"/"+parts[1];
                String timestamp = parts[1].replace("[", "").replace("]", "");
                String logLevel = parts[2].replace("[", "").replace("]", "");
                String message = parts[3];

                logEntries.add(new LogEntry(timestamp, logLevel, message));
            }
        }
        return logEntries;
    }
}
        
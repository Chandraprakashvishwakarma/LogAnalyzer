package com.log.Loganalyzer.LogAnalyzer.controller;

import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.log.Loganalyzer.LogAnalyzer.entities.LogEntry;
import com.log.Loganalyzer.LogAnalyzer.entities.LogSummary;
import com.log.Loganalyzer.LogAnalyzer.services.LogAnalyzer;

@Controller
class LogController {

    @GetMapping("/")
    public String home() {
        return "index";
    }
   
    

	@PostMapping("/analyze")
	public String analyzeLog(@RequestParam("logFilePath") String logFilePath, Model model) {
		try {
            LogAnalyzer analyzer = new LogAnalyzer(logFilePath);
            List<LogEntry> logEntries = analyzer.getLogEntries();
            LogSummary summary = analyzer.generateSummary();
            model.addAttribute("logEntries", logEntries);
            model.addAttribute("summary", summary);
        } catch (Exception e) {
            model.addAttribute("error", "Error reading log file: " + e.getMessage());
        }
        return "result";
	}
}
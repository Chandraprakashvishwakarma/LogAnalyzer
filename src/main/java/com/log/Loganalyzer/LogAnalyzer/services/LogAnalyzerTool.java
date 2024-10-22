package com.log.Loganalyzer.LogAnalyzer.services;


import java.io.IOException;

import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.CommandLineParser;
import org.apache.commons.cli.DefaultParser;
import org.apache.commons.cli.HelpFormatter;
import org.apache.commons.cli.Options;
import org.apache.commons.cli.ParseException;

public class LogAnalyzerTool {
    public static void main(String[] args) {
        Options options = new Options();

        options.addOption("f", "file", true, "Path to log file");
        options.addOption("l", "level", true, "Filter by log level (ERROR, WARN, INFO)");
        options.addOption("k", "keyword", true, "Filter logs by keyword");
        options.addOption("s", "startDate", true, "Filter logs starting from a specific date (YYYY-MM-DD)");
        options.addOption("e", "endDate", true, "Filter logs ending on a specific date (YYYY-MM-DD)");

        CommandLineParser parser = new DefaultParser();
        HelpFormatter formatter = new HelpFormatter();

        try {
            CommandLine cmd = parser.parse(options, args);
            if (!cmd.hasOption("f")) {
                formatter.printHelp("LogAnalyzerTool", options);
                return;
            }

            String logFilePath = cmd.getOptionValue("f");
            LogAnalyzer analyzer = new LogAnalyzer(logFilePath);

            if (cmd.hasOption("l")) {
                analyzer.filterByLogLevel(cmd.getOptionValue("l")).forEach(System.out::println);
            }
            if (cmd.hasOption("k")) {
                analyzer.filterByKeyword(cmd.getOptionValue("k")).forEach(System.out::println);
            }
            analyzer.printSummary();

        } catch (ParseException | IOException | InterruptedException e) {
            e.printStackTrace();
        }
    }
}
        
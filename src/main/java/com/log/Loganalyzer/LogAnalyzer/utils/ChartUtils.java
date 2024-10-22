package com.log.Loganalyzer.LogAnalyzer.utils;


import java.util.Map;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.ui.ApplicationFrame;
import org.jfree.data.category.DefaultCategoryDataset;

public class ChartUtils extends ApplicationFrame {

    public ChartUtils(String title, Map<String, Long> logLevelCounts) {
        super(title);
        JFreeChart barChart = createChart(logLevelCounts);
        ChartPanel chartPanel = new ChartPanel(barChart);
        chartPanel.setPreferredSize(new java.awt.Dimension(560, 370));
        setContentPane(chartPanel);
    }

    private JFreeChart createChart(Map<String, Long> logLevelCounts) {
        DefaultCategoryDataset dataset = new DefaultCategoryDataset();

        logLevelCounts.forEach((logLevel, count) -> dataset.addValue(count, logLevel, logLevel));

        return ChartFactory.createBarChart(
                "Log Level Statistics",
                "Log Level",
                "Count",
                dataset,
                PlotOrientation.VERTICAL,
                true, true, false);
    }
}
        
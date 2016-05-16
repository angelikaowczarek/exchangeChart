package com.github.angelikaowczarek;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.axis.DateAxis;
import org.jfree.chart.axis.ValueAxis;
import org.jfree.data.time.Day;
import org.jfree.data.time.TimeSeries;
import org.jfree.data.time.TimeSeriesCollection;

import java.text.SimpleDateFormat;

public class ExchangeChartPanel extends ChartPanel {

    TimeSeriesCollection dataset = new TimeSeriesCollection();
    TimeSeries series = new TimeSeries("TYTUL");

    public ExchangeChartPanel() {
        super(null);
        dataset.addSeries(series);

        JFreeChart chart = ChartFactory.
                createTimeSeriesChart
                        ("TYTUL", "OS_X", "OS_Y", dataset, true, true, false);

        ValueAxis rangeAxis = chart.getXYPlot().getRangeAxis();
        DateAxis xAxis = (DateAxis) chart.getXYPlot().getDomainAxis();
        xAxis.setVerticalTickLabels(true);
        xAxis.setDateFormatOverride(new SimpleDateFormat("dd-MM-yyy"));
        this.setChart(chart);
    }
}
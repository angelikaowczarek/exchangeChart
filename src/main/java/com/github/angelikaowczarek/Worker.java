package com.github.angelikaowczarek;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.axis.DateAxis;
import org.jfree.chart.axis.ValueAxis;
import org.jfree.data.time.Day;
import org.jfree.data.time.TimeSeries;
import org.jfree.data.time.TimeSeriesCollection;

import javax.swing.*;
import java.awt.*;
import java.io.IOException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.*;
import java.util.List;

public class Worker extends SwingWorker<Void, Double> {
    private DataListener dataListener;
    private JPanel jPanel;
    private Connector connector = new Connector();
    private double min = 10, max = 0;
    private LocalDate start, end;
    private TimeSeries series;
    private String dateStart, dateStop;

    public Worker(JPanel jPanel) throws IOException {
        this.jPanel = jPanel;
        initiate();
    }

    public void setDates(String dateStart, String dateStop) {
        this.dateStart = dateStart;
        this.dateStop = dateStop;
        Calendar startDate = Calendar.getInstance();
        Calendar endDate = Calendar.getInstance();

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd", Locale.GERMANY);
        try {
            startDate.setTime(sdf.parse(dateStart));
            endDate.setTime(sdf.parse(dateStop));
        } catch (ParseException e) {
            e.printStackTrace();
        }

        start = startDate.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
        end = endDate.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
    }

    @Override
    protected Void doInBackground() throws Exception {

        for (LocalDate localDate = start; !localDate.isEqual(end.plusDays(1)); localDate = localDate.plusDays(1)) {
            connector.connect(localDate);
            Date date = Date.from(localDate.atStartOfDay(ZoneId.systemDefault()).toInstant());
            series.add(new Day(date), connector.getRate());
            if (connector.getRate() < min)
                min = connector.getRate();
            if (connector.getRate() > max)
                max = connector.getRate();
            jPanel.repaint();
        }
        return null;
    }

    @Override
    protected void process (List<Double> chunks) {
    }

    @Override
    protected void done () {
        super.done();
        System.out.println("Min: " + min);
        System.out.println("Max: " + max);

    }

    public String getMinMax() {
        return new String("Min: " + min + "\tMax: " + max);
    }


    public void setDataListener(DataListener dataListener) {
        this.dataListener = dataListener;
    }

    private void initiate() throws IOException {
        series = new TimeSeries("PLN/EUR");
        TimeSeriesCollection dataset =  new TimeSeriesCollection();
        dataset.addSeries(series);
        JFreeChart chart = ChartFactory.createTimeSeriesChart("PLN/EUR",
                "Date", "Exchange Rate", dataset, true, true, false);

        DateAxis xAxis = (DateAxis)chart.getXYPlot().getDomainAxis();
        xAxis.setVerticalTickLabels(true);
        xAxis.setDateFormatOverride(new SimpleDateFormat("dd-MM-yyy"));
        ValueAxis yAxis = chart.getXYPlot().getRangeAxis();

        ChartPanel cp = new ChartPanel(chart);
        jPanel.setLayout(new BorderLayout());
        jPanel.add(cp);
    }
}

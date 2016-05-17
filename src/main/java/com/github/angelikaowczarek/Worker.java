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
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.*;

import javax.swing.*;
import java.util.List;
import java.util.Random;

public class Worker extends SwingWorker<Void, Double> {
    private DataListener dataListener;
    private JPanel jPanel;
    private Connector connector = new Connector();
    private double min = 10, max = 0;
    private LocalDate start, end;
    private TimeSeries series;

    public Worker(JPanel jPanel) throws IOException {
        this.jPanel = jPanel;
        connectAndDraw();
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
//        publish(fp);
        return null;
    }

    @Override
    protected void process (List<Double> chunks) {
        for(Double rate : chunks){
            if(dataListener != null){
                this.dataListener.onData(new
                        DataEvent(this, rate));
            }
        }
    }

    @Override
    protected void done () {
        super.done();
        System.out.println("Min: " + min);
        System.out.println("Max: " + max);
    }


    public void setDataListener(DataListener dataListener) {
        this.dataListener = dataListener;
    }

    private void connectAndDraw() throws IOException {
        Calendar startDate = Calendar.getInstance();
        startDate.set(2015, Calendar.NOVEMBER, 1);
        Calendar endDate = Calendar.getInstance();
        endDate.set(2016, Calendar.JANUARY,10 );
        start =
                startDate.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
        end =
                endDate.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();

        series = new TimeSeries("PLN/EUR");
        TimeSeriesCollection dataset =  new TimeSeriesCollection();
        dataset.addSeries(series);
        JFreeChart chart = ChartFactory.createTimeSeriesChart("PLN/EUR",
                "Date", "Exchange Rate", dataset, true, true, false);

        DateAxis xAxis = (DateAxis)chart.getXYPlot().getDomainAxis();
        xAxis.setVerticalTickLabels(true);
        xAxis.setDateFormatOverride(new SimpleDateFormat("dd-MM-yyy"));
        ValueAxis yAxis = chart.getXYPlot().getRangeAxis();

//        UtilDateModel model = new UtilDateModel();
//        JDatePanelImpl datePanel = new JDatePanelImpl(model);
//        JDatePickerImpl datePicker = new JDatePickerImpl(datePanel);
//        datePicker.setBounds(220,350,120,30);
//        dateFromPanel.add(datePicker);

        ChartPanel cp = new ChartPanel(chart);
        jPanel.setLayout(new BorderLayout());
        jPanel.add(cp);
    }
}

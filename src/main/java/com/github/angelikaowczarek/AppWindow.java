package com.github.angelikaowczarek;


import org.jdatepicker.DateModel;
import org.jdatepicker.impl.JDatePanelImpl;
import org.jdatepicker.impl.JDatePickerImpl;
import org.jdatepicker.impl.UtilDateModel;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.axis.Axis;
import org.jfree.chart.axis.DateAxis;
import org.jfree.chart.axis.ValueAxis;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.data.time.Day;
import org.jfree.data.xy.DefaultXYDataset;
import org.jfree.data.xy.XYDataset;
import org.jfree.data.time.TimeSeries;
import org.jfree.data.time.TimeSeriesCollection;
import javax.swing.*;
import java.awt.*;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class AppWindow extends JFrame {

    private JPanel dateFromPanel;
    private JPanel dateToPanel;
    private JPanel startButtonPanel;
    private JPanel chartPanel;
    private JPanel rootPane;
    private JButton startButton;
    private JTextField textField1;
    private JTextField textField2;
    private Connector connector = new Connector();

    TimeSeriesCollection dataset = new TimeSeriesCollection();
    TimeSeries series = new TimeSeries("TYTUL");

    public AppWindow() throws HeadlessException, IOException {
        super("Exchange rates");
        setContentPane(rootPane);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setSize(600, 400);
        setResizable(false);
        setVisible(true);
        textField1.setText("2015-01-01");
        textField2.setText("2016-01-10");

        int daysCounter = 0;
        Calendar startDate = Calendar.getInstance();
        startDate.set(2015, Calendar.JANUARY, 1);
        Calendar endDate = Calendar.getInstance();
        endDate.set(2016, Calendar.JANUARY,10 );
        LocalDate start =
                startDate.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
        LocalDate end =
                endDate.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();

        TimeSeries series = new TimeSeries("PLN/EUR");

        for (LocalDate localDate = start; !localDate.isEqual(end.plusDays(1)); localDate = localDate.plusDays(1)) {
            daysCounter++;
            connector.connect(localDate);
            System.out.println(connector.getRate());
            Date date = Date.from(localDate.atStartOfDay(ZoneId.systemDefault()).toInstant());
            series.add(new Day(date), connector.getRate());
        }


        TimeSeriesCollection dataset =  new TimeSeriesCollection();
        dataset.addSeries(series);
        JFreeChart chart = ChartFactory.createTimeSeriesChart("PLN/EUR",
                "Date", "Exchange Rate", dataset, true, true, false);

        DateAxis xAxis = (DateAxis)chart.getXYPlot().getDomainAxis();
        xAxis.setVerticalTickLabels(true);
        xAxis.setDateFormatOverride(new SimpleDateFormat("dd-MM-yyy"));

        ValueAxis yAxis = chart.getXYPlot().getRangeAxis();

//        XYDataset ds = createDataset();
//        JFreeChart chart = ChartFactory.createXYLineChart("PLN/EUR",
//                "Date", "Exchange Rate", ds, PlotOrientation.VERTICAL, true, true, false);

        ChartPanel cp = new ChartPanel(chart);
        chartPanel.setLayout(new BorderLayout());
        chartPanel.add(cp);
        //this.getContentPane().add(cp);

    }

    private static XYDataset createDataset() {

//        TimeSeriesCollection tsc = new TimeSeriesCollection();
//        TimeSeries ts = new TimeSeries("PLN/EUR");
        DefaultXYDataset ds = new DefaultXYDataset();

        double[][] data = {{0.1, 0.2, 0.3}, {1, 2, 3}};

        ds.addSeries("PLN/EUR", data);

        return ds;
    }


//        DateModel model = new UtilDateModel();
//        JDatePanelImpl datePanel = new JDatePanelImpl(model);
//        JDatePickerImpl datePicker = new JDatePickerImpl(datePanel);

}

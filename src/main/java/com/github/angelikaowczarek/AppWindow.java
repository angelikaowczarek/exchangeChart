package com.github.angelikaowczarek;


import org.jdatepicker.DateModel;
import org.jdatepicker.impl.JDatePanelImpl;
import org.jdatepicker.impl.JDatePickerImpl;
import org.jdatepicker.impl.UtilDateModel;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.data.xy.DefaultXYDataset;
import org.jfree.data.xy.XYDataset;
import org.jfree.data.time.TimeSeries;
import org.jfree.data.time.TimeSeriesCollection;
import javax.swing.*;
import java.awt.*;
import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;
import java.util.Date;
import java.util.Locale;

public class AppWindow extends JFrame {

    private JPanel dateFromPanel;
    private JPanel dateToPanel;
    private JPanel startButtonPanel;
    private JPanel chartPanel;
    private JPanel rootPane;
    private JButton startButton;
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

        LocalDate today = LocalDate.now();
        System.out.println(today);

        connector.connect(today);

        XYDataset ds = createDataset();
        JFreeChart chart = ChartFactory.createXYLineChart("PLN/EUR",
                "Date", "Exchange Rate", ds, PlotOrientation.VERTICAL, true, true, false);

//        ds.k

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

        ds.addSeries("series1", data);

        return ds;
    }


//        DateModel model = new UtilDateModel();
//        JDatePanelImpl datePanel = new JDatePanelImpl(model);
//        JDatePickerImpl datePicker = new JDatePickerImpl(datePanel);

}

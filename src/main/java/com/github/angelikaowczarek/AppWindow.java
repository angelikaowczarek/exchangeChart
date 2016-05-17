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
import java.util.Calendar;
import java.util.Date;

public class AppWindow extends JFrame {

    private JPanel dateFromPanel;
    private JPanel dateToPanel;
    private JPanel startButtonPanel;
    private JPanel chartPanel;
    private JPanel rootPane;
    private JButton startButton;
    private JTextField textField1;
    private JTextField textField2;
    private DataListener dataListener;

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
        textField2.setText("2016-11-10");

        Worker worker = new Worker(chartPanel);
        worker.setDataListener(new DataListener() {
            @Override
            public void onData(DataEvent e) {
                System.out.println("Data: " + e.getData());
            }
        });
        worker.execute();
    }

    public void setDataListener(DataListener dataListener) {
        this.dataListener = dataListener;
    }


}

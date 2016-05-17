package com.github.angelikaowczarek;


import org.jdatepicker.impl.JDatePanelImpl;
import org.jdatepicker.impl.JDatePickerImpl;
import org.jdatepicker.impl.UtilDateModel;
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
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Calendar;
import java.util.Date;
import java.util.Properties;

public class AppWindow extends JFrame {

    private JPanel dateFromPanel;
    private JPanel dateToPanel;
    private JPanel startButtonPanel;
    private JPanel chartPanel;
    private JPanel rootPane;
    private JButton startButton;
    private DataListener dataListener;
    private JDatePickerImpl datePicker1, datePicker2;

    TimeSeriesCollection dataset = new TimeSeriesCollection();
    TimeSeries series = new TimeSeries("TYTUL");

    public AppWindow() throws HeadlessException, IOException {
        super("Exchange rates");
        setContentPane(rootPane);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setSize(600, 400);
        setResizable(false);
        setVisible(true);

//        Properties properties = new Properties();
//        UtilDateModel m1 = new UtilDateModel();
//        UtilDateModel m2 = new UtilDateModel();
//        JDatePickerImpl jDatePicker =
//                new JDatePickerImpl(new JDatePanelImpl(m1, properties), new DateFormatter());
//        dateFromPanel.add(jDatePicker);


        Properties p = new Properties();
        UtilDateModel model1 = new UtilDateModel();
        p.put("text.today", "Today");
        p.put("text.month", "Month");
        p.put("text.year", "Year");
        JDatePanelImpl datePanel1 = new JDatePanelImpl(model1, p);
        datePicker1 = new JDatePickerImpl(datePanel1, new DateFormatter());
        dateFromPanel.setLayout(new GridBagLayout());
        dateFromPanel.add(datePicker1);


        UtilDateModel model2 = new UtilDateModel();
        JDatePanelImpl datePanel2 = new JDatePanelImpl(model2, p);
        datePicker2 = new JDatePickerImpl(datePanel2, new DateFormatter());
        dateFromPanel.add(datePicker2);

//        UtilDateModel model = new UtilDateModel();
//        JDatePanelImpl datePanel = new JDatePanelImpl(model);
//        JDatePickerImpl datePicker = new JDatePickerImpl(datePanel);
//        datePicker.setBounds(220,350,120,30);
//        dateFromPanel.add(datePicker);

        Worker worker = new Worker(chartPanel);
        worker.setDataListener(new DataListener() {
            @Override
            public void onData(DataEvent e) {
                System.out.println("Data: " + e.getData());
            }
        });

//        worker.execute();
        startButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
                DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
                Date start = (Date) datePicker1.getModel().getValue();
                Date end = (Date) datePicker2.getModel().getValue();
                String sDate = df.format(start);
                String eDate = df.format(end);
//                worker.setDates("2015-01-01", "2015-02-02");
                worker.setDates(sDate, eDate);
                worker.execute();
            }
        });
    }

    public void setDataListener(DataListener dataListener) {
        this.dataListener = dataListener;
    }


}

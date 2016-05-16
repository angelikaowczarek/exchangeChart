package com.github.angelikaowczarek;

import javax.swing.*;
import java.io.IOException;

public class Main {

    public static void main(String[] args) {

        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                try {
                    AppWindow appWindow = new AppWindow();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });
    }
}

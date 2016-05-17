package com.github.angelikaowczarek;

import java.util.EventObject;

public class DataEvent extends EventObject{
    private double data;

    public double getData() {
        return data;
    }

    public DataEvent(Object source, double data) {
        super(source);
        this.data = data;
    }
}

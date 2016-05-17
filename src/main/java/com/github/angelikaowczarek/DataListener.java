package com.github.angelikaowczarek;

import java.util.EventListener;

public interface DataListener extends EventListener {
    void onData(DataEvent e);
}
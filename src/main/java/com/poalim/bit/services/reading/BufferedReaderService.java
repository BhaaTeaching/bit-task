package com.poalim.bit.services.reading;

import org.springframework.stereotype.Component;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;

@Component
public class BufferedReaderService {
    private final Object lock = new Object();
    private BufferedReader bufferedReader;

    public BufferedReader getBufferedReader() {
        synchronized (lock) {
            return bufferedReader;
        }
    }

    public void createBufferedReader(String textUrl) throws IOException {
        URL url = new URL(textUrl);
        this.bufferedReader = new BufferedReader(new InputStreamReader(url.openStream()));
    }
}

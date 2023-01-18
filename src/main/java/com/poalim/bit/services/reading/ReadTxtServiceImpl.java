package com.poalim.bit.services.reading;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.BufferedReader;
import java.util.stream.Collectors;

@Component
public class ReadTxtServiceImpl implements ReadTxtService {

    private final BufferedReaderService bufferedReaderService;
    @Value("${number.of.lines}")
    private long numberOfLinesToRead;

    public ReadTxtServiceImpl(BufferedReaderService bufferedReaderService) {
        this.bufferedReaderService = bufferedReaderService;
    }

    @Override
    public String read(String textUrl, BufferedReader bufferedReader) {
        try {
            String collect = bufferedReaderService.getBufferedReader()
                    .lines().limit(numberOfLinesToRead)
                    .collect(Collectors.joining("\n"));
            return collect;
        } catch (Exception anyException) {
            throw new RuntimeException(anyException);
        }
    }
}

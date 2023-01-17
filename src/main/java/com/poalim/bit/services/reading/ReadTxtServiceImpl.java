package com.poalim.bit.services.reading;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.stream.Collectors;

@Component
public class ReadTxtServiceImpl implements ReadTxtService {

    @Value("${number.of.lines}")
    private long numberOfLinesToRead;
    private final BufferedReaderService bufferedReaderService;

    public ReadTxtServiceImpl(BufferedReaderService bufferedReaderService) {
        this.bufferedReaderService = bufferedReaderService;
    }

    @Override
    public String read(String textUrl) {
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

package com.poalim.bit.services;

import com.poalim.bit.controllers.dto.response.WordsResponseDto;
import com.poalim.bit.models.WordDetails;
import com.poalim.bit.services.aggregation.AggregationService;
import com.poalim.bit.services.match.MatcherService;
import com.poalim.bit.services.mongodb.WordsDatabaseOperationService;
import com.poalim.bit.services.reading.BufferedReaderService;
import com.poalim.bit.services.reading.ReadTxtService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

@Service
@AllArgsConstructor
public class SearchingServiceImpl implements SearchingService {

    private final BufferedReaderService bufferedReaderService;
    private final ReadTxtService readTxtService;
    private final MatcherService matcherService;
    private final WordsDatabaseOperationService wordsDatabaseOperationService;
    private final AggregationService aggregationService;

    @Override
    public WordsResponseDto search(String textUrl, List<String> words) throws ExecutionException, InterruptedException, IOException {
        bufferedReaderService.createBufferedReader(textUrl);
        ExecutorService executorService = Executors.newFixedThreadPool(5);

        for (int i = 0; i < 10; i++) {
            executorService.submit(() -> {
                String text = readTxtService.read(textUrl);
                Map<String, WordDetails> wordsToWordsDetails = matcherService.match(text, words);
                wordsDatabaseOperationService.save(wordsToWordsDetails.values());
            });
        }

        //todo: after all threads end --> do aggregation
            return aggregationService.aggregate();
    }
}

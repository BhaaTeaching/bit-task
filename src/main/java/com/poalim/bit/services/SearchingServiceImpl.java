package com.poalim.bit.services;

import com.poalim.bit.controllers.dto.response.WordsResponseDto;
import com.poalim.bit.exceptions.ValidationException;
import com.poalim.bit.models.WordDetails;
import com.poalim.bit.services.aggregation.AggregationService;
import com.poalim.bit.services.match.MatcherService;
import com.poalim.bit.services.mongodb.WordsDatabaseOperationService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.stream.IntStream;

@Service
@Slf4j
public class SearchingServiceImpl implements SearchingService {
    @Value("${number.of.threads:1}")
    private int numberOfLinesToRead;

    @Value("${number.of.lines.per.part:1000}")
    private int numberOfLinesPerPart;
    private final MatcherService matcherService;
    private final WordsDatabaseOperationService wordsDatabaseOperationService;
    private final AggregationService aggregationService;

    public SearchingServiceImpl(MatcherService matcherService, WordsDatabaseOperationService wordsDatabaseOperationService, AggregationService aggregationService) {
        this.matcherService = matcherService;
        this.wordsDatabaseOperationService = wordsDatabaseOperationService;
        this.aggregationService = aggregationService;
    }


    @Override
    public List<WordsResponseDto> search(String textUrl, List<String> words) throws IOException, ValidationException {
        if (textUrl == null || words == null) {
            log.info("Text URL cannot be empty ! It is mandatory for process");
            throw new ValidationException("Text URL cannot be empty ! It is mandatory for process");
        }
        URL url = new URL(textUrl);
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(url.openStream()));
        ExecutorService executorService = Executors.newFixedThreadPool(numberOfLinesToRead);
        var ref = new Object() {
            List<String> collect = null;
        };

        int partNumber = 0;
        while (!(ref.collect = bufferedReader.lines().limit(numberOfLinesPerPart).toList()).isEmpty()) {

            List<Callable<List<WordDetails>>> callables = new LinkedList<>();
            int finalPartNumber = partNumber;
            IntStream.range(0, ref.collect.size()).forEach(index -> {
                Callable<List<WordDetails>> callable = () -> {
                    Map<String, WordDetails> wordsToWordsDetails = null;
                    wordsToWordsDetails = matcherService.match(ref.collect.get(index), words, finalPartNumber * numberOfLinesPerPart + index);
                    return wordsDatabaseOperationService.save(wordsToWordsDetails.values());
                };
                callables.add(callable);
            });

            try {
                executorService.invokeAll(callables);
            } catch (InterruptedException interruptedException) {
                log.error("Failed to invoke all callables, part number: {}, thread name: {}", partNumber, Thread.currentThread().getName(), interruptedException);
                throw new RuntimeException(interruptedException);
            }
            partNumber++;
        }


        List<WordsResponseDto> aggregatedData = aggregationService.aggregate();
        aggregationService.clean();
        return aggregatedData;
    }
}

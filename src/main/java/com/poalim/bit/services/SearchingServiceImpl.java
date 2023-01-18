package com.poalim.bit.services;

import com.poalim.bit.controllers.dto.response.WordsResponseDto;
import com.poalim.bit.exceptions.ValidationException;
import com.poalim.bit.models.WordDetails;
import com.poalim.bit.services.aggregation.AggregationService;
import com.poalim.bit.services.match.MatcherService;
import com.poalim.bit.services.mongodb.WordsDatabaseOperationService;
import com.poalim.bit.services.reading.BufferedReaderService;
import com.poalim.bit.services.reading.ReadTxtService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.stream.Stream;

@Service
@AllArgsConstructor
@Slf4j
public class SearchingServiceImpl implements SearchingService {

//    private final BufferedReaderService bufferedReaderService;
//    private final ReadTxtService readTxtService;
    private final MatcherService matcherService;
    private final WordsDatabaseOperationService wordsDatabaseOperationService;
    private final AggregationService aggregationService;

    @Override
    public List<WordsResponseDto> search(String textUrl, List<String> words) throws IOException, ValidationException {
        if (textUrl == null || words == null) {
            log.info("Text URL cannot be empty ! It is mandatory for process");
            throw new ValidationException("Text URL cannot be empty ! It is mandatory for process");
        }
        URL url = new URL(textUrl);
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(url.openStream()));
        ExecutorService executorService = Executors.newFixedThreadPool(1);
        var ref = new Object() {
            List<String> collect = null;
        };

        while (!(ref.collect = bufferedReader.lines().limit(1000).toList()).isEmpty()) {
            executorService.submit(() -> {
                Map<String, WordDetails> wordsToWordsDetails = matcherService.match(String.join("\n", ref.collect), words);
                wordsDatabaseOperationService.save(wordsToWordsDetails.values());
            });
        }

        List<WordsResponseDto> aggregatedData = aggregationService.aggregate();
        aggregationService.clean();
        return aggregatedData;
    }
}

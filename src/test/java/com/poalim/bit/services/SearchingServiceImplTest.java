package com.poalim.bit.services;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.poalim.bit.controllers.dto.response.WordsResponseDto;
import com.poalim.bit.exceptions.ValidationException;
import com.poalim.bit.models.WordDetails;
import com.poalim.bit.pojo.Location;
import com.poalim.bit.pojo.WordLocations;
import com.poalim.bit.services.aggregation.AggregationService;
import com.poalim.bit.services.match.MatcherService;
import com.poalim.bit.services.mongodb.WordsDatabaseOperationService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;

import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.Path;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;

public class SearchingServiceImplTest {

    MatcherService matcherService = Mockito.mock(MatcherService.class);
    WordsDatabaseOperationService wordsDatabaseOperationService = Mockito.mock(WordsDatabaseOperationService.class);
    AggregationService aggregationService = Mockito.mock(AggregationService.class);

    private final SearchingService searchingService = new SearchingServiceImpl(matcherService, wordsDatabaseOperationService, aggregationService);

    @Test
    public void givenSearch_whenTextUrlIsNull_throwValidationException() {
        ValidationException validationException = assertThrows(ValidationException.class, () -> {
            searchingService.search(null, Collections.emptyList());
        });
        Assertions.assertEquals("Text URL cannot be empty ! It is mandatory for process", validationException.getMessage());
    }

    @Test
    public void givenSearch_whenListOfWordsIsNull_throwValidationException() {
        ValidationException validationException = assertThrows(ValidationException.class, () -> {
            searchingService.search(null, Collections.emptyList());
        });
        Assertions.assertEquals("Text URL cannot be empty ! It is mandatory for process", validationException.getMessage());
    }

    @Test
    public void givenSearch_whenTextUrlNotValid_throwValidationException() throws ValidationException, IOException {
        MalformedURLException actual = assertThrows(MalformedURLException.class, () -> searchingService.search("testNotValidUrl", Collections.emptyList()));
        Assertions.assertEquals("no protocol: testNotValidUrl", actual.getMessage());
    }


    @Test
    public void givenSearch_whenTextUrlIsValid_throwMatchTheWords() throws ValidationException, IOException {
        when(matcherService.match(Mockito.anyString(), Mockito.anyList(), Mockito.anyInt()))
                .thenReturn(Map.of("Project",
                        new WordDetails("Project", List.of(new Location(1, 5)), "thread-name", new Date())));

       when(wordsDatabaseOperationService.save(Mockito.anyList())).thenReturn(Collections.emptyList());
        when(aggregationService.aggregate()).thenReturn(List.of(
                new WordsResponseDto("test", List.of(new Location(1, 5)))
        ));
        doNothing().when(aggregationService).clean();

        List<WordsResponseDto> actual = searchingService.search(Path.of("src/test/resources/test.txt").toUri().toString(), List.of("Project"));
        Assertions.assertEquals(1, actual.size());
    }
}
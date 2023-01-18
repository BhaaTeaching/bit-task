package com.poalim.bit.services.aggregation;

import com.poalim.bit.controllers.dto.response.WordsResponseDto;
import com.poalim.bit.pojo.Location;
import com.poalim.bit.pojo.WordLocations;
import com.poalim.bit.services.mongodb.WordsDatabaseOperationService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class AggregationServiceImplTest {

    private final WordsDatabaseOperationService wordsDatabaseOperationService = Mockito.mock(WordsDatabaseOperationService.class);
    private final AggregationService aggregationService = new AggregationServiceImpl(wordsDatabaseOperationService);
    @Test
    public void givenAggregate_whenDataExistInMongoDB_thenReturnAggregatedData() {
        Mockito.when(wordsDatabaseOperationService.groupLocationsByWord()).thenReturn(List.of(
                new WordLocations("test", List.of(List.of(new Location(1, 5)))),
                new WordLocations("test1", List.of(List.of(new Location(1, 5))))
        ));

        List<WordsResponseDto> actual = aggregationService.aggregate();
        Assertions.assertEquals(2, actual.size());
    }

    @Test
    public void givenAggregate_whenNoDataExistInMongoDB_thenReturnAggregatedData() {
        Mockito.when(wordsDatabaseOperationService.groupLocationsByWord()).thenReturn(Collections.emptyList());

        List<WordsResponseDto> actual = aggregationService.aggregate();
        Assertions.assertTrue(actual.isEmpty());
    }
}
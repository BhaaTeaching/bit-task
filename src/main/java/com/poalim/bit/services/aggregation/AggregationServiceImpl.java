package com.poalim.bit.services.aggregation;

import com.poalim.bit.controllers.dto.response.WordsResponseDto;
import com.poalim.bit.pojo.WordLocations;
import com.poalim.bit.pojo.Location;
import com.poalim.bit.services.mongodb.WordsDatabaseOperationService;
import lombok.Data;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Component
@Data
public class AggregationServiceImpl implements AggregationService {

    private final WordsDatabaseOperationService wordsDatabaseOperationService;

    @Override
    public List<WordsResponseDto> aggregate() {
        List<WordLocations> aggregate = wordsDatabaseOperationService.aggregate(Collections.emptyList());
        List<WordsResponseDto> wordsResponseDtos = new ArrayList<>();
        aggregate.forEach(word -> {
            List<Location> locations = new ArrayList<>();
            word.getLocations().forEach(locations::addAll);
            wordsResponseDtos.add(new WordsResponseDto(word.get_id(), locations));
        });
        return wordsResponseDtos;
    }

    @Override
    public void clean() {
        wordsDatabaseOperationService.deleteAll();
    }


}

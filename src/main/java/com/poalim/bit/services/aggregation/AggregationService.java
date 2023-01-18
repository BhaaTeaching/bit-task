package com.poalim.bit.services.aggregation;

import com.poalim.bit.controllers.dto.response.WordsResponseDto;

import java.util.List;

public interface AggregationService {
    List<WordsResponseDto> aggregate();

    void clean();
}

package com.poalim.bit.services.match;

import com.poalim.bit.controllers.dto.response.WordsResponseDto;
import com.poalim.bit.models.WordDetails;

import java.util.List;
import java.util.Map;

public interface MatcherService {
    Map<String, WordDetails> match(String text, List<String> words);
}

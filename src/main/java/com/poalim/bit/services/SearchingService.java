package com.poalim.bit.services;

import com.poalim.bit.controllers.dto.response.WordsResponseDto;
import com.poalim.bit.exceptions.ValidationException;

import java.io.IOException;
import java.util.List;
import java.util.concurrent.ExecutionException;

public interface SearchingService {
    List<WordsResponseDto> search(String textUrl, List<String> words) throws IOException, ValidationException;
}

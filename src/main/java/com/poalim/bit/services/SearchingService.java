package com.poalim.bit.services;

import com.poalim.bit.controllers.dto.response.WordsResponseDto;

import java.io.IOException;
import java.util.List;
import java.util.concurrent.ExecutionException;

public interface SearchingService {
    WordsResponseDto search(String textUrl, List<String> words) throws ExecutionException, InterruptedException, IOException;
}

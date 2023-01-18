package com.poalim.bit.controllers;

import com.poalim.bit.controllers.dto.request.TextRequestDto;
import com.poalim.bit.controllers.dto.response.WordsResponseDto;
import com.poalim.bit.exceptions.ValidationException;
import com.poalim.bit.services.SearchingService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.util.List;
import java.util.concurrent.ExecutionException;

@RestController
@AllArgsConstructor
public class SearchingController {
    private final SearchingService searchingService;

    @PostMapping("/text-analysis/match")
    public ResponseEntity<List<WordsResponseDto>> searchWords(@RequestBody TextRequestDto textRequestDto) throws IOException, ValidationException {
        List<WordsResponseDto> response = searchingService.search(textRequestDto.getTextUrl(), textRequestDto.getWords());
        return ResponseEntity.ok(response);
    }
}

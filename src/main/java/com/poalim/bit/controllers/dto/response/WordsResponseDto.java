package com.poalim.bit.controllers.dto.response;

import com.poalim.bit.pojo.Location;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;
import java.util.Map;

@Data
@AllArgsConstructor
public class WordsResponseDto {
    private Map<String, List<Location>> wordToAppearanceLocation;
}

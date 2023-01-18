package com.poalim.bit.controllers.dto.response;

import com.poalim.bit.pojo.Location;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor
public class WordsResponseDto {
    private String word;
    private List<Location> locations;
//    private Map<String, List<Location>> wordToAppearanceLocation;
}

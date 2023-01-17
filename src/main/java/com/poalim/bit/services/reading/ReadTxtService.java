package com.poalim.bit.services.reading;

import com.poalim.bit.controllers.dto.response.WordsResponseDto;
import com.poalim.bit.models.WordDetails;

import java.io.BufferedReader;
import java.util.List;

public interface ReadTxtService {
    String read(String textUrl);
}

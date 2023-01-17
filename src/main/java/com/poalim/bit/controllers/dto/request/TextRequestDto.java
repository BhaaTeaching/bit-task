package com.poalim.bit.controllers.dto.request;

import lombok.Data;

import java.util.List;

@Data
public class TextRequestDto {
    private String TextUrl;
    private List<String> words;
}

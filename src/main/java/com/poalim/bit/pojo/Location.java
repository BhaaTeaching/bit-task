package com.poalim.bit.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class Location {
    private long lineOffset;
    private long charOffset;
}

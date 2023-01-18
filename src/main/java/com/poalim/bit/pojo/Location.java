package com.poalim.bit.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@AllArgsConstructor
@Document("Location")
public class Location {
    private long lineOffset;
    private long charOffset;
}

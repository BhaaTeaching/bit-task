package com.poalim.bit.models;

import com.poalim.bit.pojo.Location;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Date;
import java.util.List;

@Document("word-locations")
@AllArgsConstructor
@Data
public class WordDetails {
    private String word;
    private List<Location> locations;
    private String threadName;

    private Date added_at;
}

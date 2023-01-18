package com.poalim.bit.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import java.util.List;


@Data
@AllArgsConstructor
public class WordLocations {

    private String _id;
    private List<List<Location>> locations;

}

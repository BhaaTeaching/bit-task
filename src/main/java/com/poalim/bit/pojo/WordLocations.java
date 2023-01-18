package com.poalim.bit.pojo;

import lombok.Data;
import java.util.List;


@Data
public class WordLocations {

    private String _id;
    private List<List<Location>> locations;

}

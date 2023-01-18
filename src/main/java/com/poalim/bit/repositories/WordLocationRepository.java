package com.poalim.bit.repositories;

import com.poalim.bit.models.WordDetails;
import com.poalim.bit.pojo.WordLocations;
import org.springframework.data.mongodb.repository.Aggregation;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface WordLocationRepository extends MongoRepository<WordDetails, String> {
    @Aggregation(pipeline = {
            "            { $group: { _id: $word, locations: { $addToSet: $locations } }} " +
                    "{ $addFields: { locations: { $reduce: { input: $locations, initialValue: [], in: { $setUnion: [ $$value, $$this ] } } } }}"
    })
    List<WordLocations> findWords();
}

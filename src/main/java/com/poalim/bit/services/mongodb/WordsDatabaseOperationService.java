package com.poalim.bit.services.mongodb;

import com.poalim.bit.models.WordDetails;
import com.poalim.bit.pojo.WordLocations;

import java.util.Collection;
import java.util.List;

public interface WordsDatabaseOperationService {
    List<WordDetails> save(Collection<WordDetails> wordDetails);

    List<WordLocations> aggregate(List<String> words);

    void deleteAll();
}

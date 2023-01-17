package com.poalim.bit.services.mongodb;

import com.poalim.bit.models.WordDetails;

import java.util.Collection;
import java.util.List;

public interface WordsDatabaseOperationService {
    List<WordDetails> save(Collection<WordDetails> wordDetails);

}

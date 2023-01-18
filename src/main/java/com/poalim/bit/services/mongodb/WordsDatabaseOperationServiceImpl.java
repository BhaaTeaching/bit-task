package com.poalim.bit.services.mongodb;

import com.poalim.bit.models.WordDetails;
import com.poalim.bit.pojo.WordLocations;
import com.poalim.bit.repositories.WordLocationRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Collection;
import java.util.List;

@Component
@AllArgsConstructor
public class WordsDatabaseOperationServiceImpl implements WordsDatabaseOperationService {
    private final WordLocationRepository wordLocationRepository;

    @Override
    public List<WordDetails> save(Collection<WordDetails> wordDetails) {
        return wordLocationRepository.insert(wordDetails);
    }

    @Override
    public List<WordLocations> aggregate(List<String> words) {
        return wordLocationRepository.findWords();
    }

    @Override
    public void deleteAll() {
        wordLocationRepository.deleteAll();
    }
}

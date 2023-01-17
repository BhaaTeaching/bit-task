package com.poalim.bit.repositories;

import com.poalim.bit.models.WordDetails;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface WordLocationRepository extends MongoRepository<WordDetails, String> {
}

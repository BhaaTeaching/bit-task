package com.poalim.bit.services.match;

import com.poalim.bit.models.WordDetails;
import com.poalim.bit.pojo.Location;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.*;
import java.util.stream.IntStream;

@Component
@AllArgsConstructor
@Slf4j
public class MatcherServiceImpl implements MatcherService {

    @Override
    public Map<String, WordDetails> match(String text, List<String> words) {
        if (text == null || words == null) {
            log.info("Text or words set cannot be empty !");
            return Collections.emptyMap();
        }

        Map<String, WordDetails> wordsToWordsDetails = words.stream().collect(HashMap::new, (m, v) -> m.put(v, null), HashMap::putAll);
        String[] lines = text.split("\n");
        words.forEach(word -> {
            List<Location> locations = new ArrayList<>();
            IntStream.range(0, lines.length).forEach(index -> {
                for (int charOffset = -1; (charOffset = lines[index].indexOf(word, charOffset + 1)) != -1; charOffset++) {
                    System.out.println(charOffset);
                    locations.add(new Location(index + 1, charOffset));

                }
            });
            if (!locations.isEmpty()) {
                wordsToWordsDetails.put(word, new WordDetails(word, locations, Thread.currentThread().getName(), new Date()));
            }
        });
        return wordsToWordsDetails;
    }
}

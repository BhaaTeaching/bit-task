package com.poalim.bit.services.match;

import com.poalim.bit.models.WordDetails;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.internal.matchers.Null;

import java.util.Collections;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

public class MatcherServiceImplTest {

    private final MatcherService matcherService = new MatcherServiceImpl();

    @Test
    public void givenMatch_whenTextContainsWords_theReturnMapOfWordAndLocation() {
        Map<String, WordDetails> actual = matcherService.match("Here test string \n test is good practice", List.of("test"));
        Assertions.assertFalse(actual.isEmpty());
        Assertions.assertTrue(actual.containsKey("test"));
        Assertions.assertEquals(actual.get("test").getWord(), "test");
        Assertions.assertEquals(actual.get("test").getLocations().size(), 2);
        Assertions.assertEquals(actual.get("test").getLocations().get(0).getLineOffset(), 1);
        Assertions.assertEquals(actual.get("test").getLocations().get(0).getCharOffset(), 5);
        Assertions.assertEquals(actual.get("test").getLocations().get(1).getLineOffset(), 2);
        Assertions.assertEquals(actual.get("test").getLocations().get(1).getCharOffset(), 1);
    }

    @Test
    public void givenMatch_whenTextNotContainsWords_theReturnMapWithNullAtLocation() {
        Map<String, WordDetails> actual = matcherService.match("Here test string \n test is good practice", List.of("foo"));
        assertEquals(1, actual.size());
        Assertions.assertTrue(actual.containsKey("foo"));
        Assertions.assertNull(actual.get("foo"));
    }

    @Test
    public void givenMatch_whenTheWordsIsEmpty_theReturnEmptyMap() {
        Map<String, WordDetails> actual = matcherService.match("Here test string \n test is good practice", Collections.emptyList());
        assertEquals(0, actual.size());
    }

    @Test
    public void givenMatch_whenTheWordsIsNull_theReturnEmptyMap() {
        Map<String, WordDetails> actual = matcherService.match("Here test string \n test is good practice", null);
        assertEquals(0, actual.size());
    }

    @Test
    public void givenMatch_whenTheTextIsNull_theReturnEmptyMap() {
        Map<String, WordDetails> actual = matcherService.match(null, Collections.emptyList());
        assertEquals(0, actual.size());
    }
}
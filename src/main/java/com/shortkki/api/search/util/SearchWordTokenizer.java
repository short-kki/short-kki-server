package com.shortkki.api.search.util;

import java.util.Set;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

public class SearchWordTokenizer {

    private static final Pattern TOKEN_DELIMITER = Pattern.compile("[\\s\\p{Punct}]+");

    public static Set<String> tokenize(String searchWord) {
        if (searchWord == null) {
            return Set.of();
        }

        String s = searchWord.trim();
        if (s.isEmpty()) {
            return Set.of();
        }

        return TOKEN_DELIMITER.splitAsStream(s)
                .map(String::trim)
                .filter(token -> !token.isEmpty())
                .collect(Collectors.toUnmodifiableSet());
    }
}

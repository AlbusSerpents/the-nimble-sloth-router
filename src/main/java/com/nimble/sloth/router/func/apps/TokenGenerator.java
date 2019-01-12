package com.nimble.sloth.router.func.apps;

import org.springframework.stereotype.Service;

import java.util.Random;

import static java.util.stream.Collectors.joining;
import static java.util.stream.IntStream.range;

@SuppressWarnings("WeakerAccess")
@Service
public class TokenGenerator {
    private static final int SYMBOLS_PER_TOKEN = 32;

    private static final String[] SYMBOLS = {
            "a", "b", "c", "d", "e", "f", "g", "h", "i", "j", "k", "l", "m", "n", "o", "p", "q", "r", "s", "t", "u", "v", "w", "x", "y", "z",
            "A", "B", "C", "D", "E", "F", "G", "H", "I", "J", "K", "L", "M", "N", "O", "P", "Q", "R", "S", "T", "U", "V", "W", "X", "Y", "Z",
            "0", "1", "2", "3", "4", "5", "6", "7", "8", "9" };

    private final Random random = new Random();

    public String makeToken() {
        return range(0, SYMBOLS_PER_TOKEN)
                .map(i -> random.nextInt(SYMBOLS.length))
                .mapToObj(index -> SYMBOLS[index])
                .collect(joining(""));
    }
}

package com.shortener.backend.business;

import org.springframework.stereotype.Service;

import java.util.StringJoiner;
import java.util.concurrent.ThreadLocalRandom;

/**
 * @author M.dehghan
 * @since 2020-09-02
 */
@Service
public class ShortURLConstruction {
    private static String[] characters = {
            "a","b","c","d","e","f","g","h","i","j","k","l","m","n","o","p","q","r","s","t","u","v","w","x","y","z",
            "A","B","C","D","E","F","G","H","I","J","K","L","M","N","O","P","Q","R","S","T","U","V","W","X","Y","Z",
            "0", "1", "2", "3", "4", "5", "6", "7", "8", "9"
    };

    public String  generate() {
        return new StringJoiner("", "", "")
                .add(getRandomCharacter())
                .add(getRandomCharacter())
                .add(getRandomCharacter())
                .add(getRandomCharacter())
                .add(getRandomCharacter())
                .toString();
    }

    private static String getRandomCharacter() {
        int randomNum = ThreadLocalRandom.current().nextInt(0, characters.length);
        return characters[randomNum];
    }
}

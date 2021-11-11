/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Enum.java to edit this template
 */
package com.mycompany.boerenbridge;

import java.util.Optional;
import java.util.stream.Stream;

/**
 *
 * @author b.smeets
 */
public enum CardValue {
    TWO(2,'2'),
    THREE(3,'3'),
    FOUR(4,'4'),
    FIVE(5,'5'),
    SIX(6,'6'),
    SEVEN(7,'7'),
    EIGHT(8,'8'),
    NINE(9,'9'),
    TEN(10,'t'),
    JACK(11,'j'),
    QUEEN(12,'q'),
    KING(13,'k'),
    ACE(14,'a');

    private final int value;
    private final char id;

    CardValue(int value, char id) {
            this.value = value;
            this.id = id;
    }

    public char getId() {
            return id;
    }

    public int getValue() {
            return value;
    }

    public static CardValue create(char id) {
            Optional<CardValue> matchingValue = Stream.of(CardValue.values()).filter(suit -> suit.getId() == id).findFirst();
            if (matchingValue.isPresent()) {
                    return matchingValue.get();
            }
            throw new IllegalArgumentException("There is no value with id " + id);
    }
}
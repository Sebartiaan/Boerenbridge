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
    TWO(2,'2',"Twee"),
    THREE(3,'3',"Drie"),
    FOUR(4,'4',"Vier"),
    FIVE(5,'5',"Vijf"),
    SIX(6,'6',"Zes"),
    SEVEN(7,'7',"Zeven"),
    EIGHT(8,'8',"Acht"),
    NINE(9,'9', "Negen"),
    TEN(10,'t',"Tien"),
    JACK(11,'j',"Boer"),
    QUEEN(12,'q',"Vrouw"),
    KING(13,'k',"Koning"),
    ACE(14,'a',"Aas");

    private final int value;
    private final char id;
    private final String nlNaam;

    CardValue(int value, char id, String nlNaam) {
            this.value = value;
            this.id = id;
            this.nlNaam = nlNaam;
    }

    public char getId() {
            return id;
    }

    public int getValue() {
            return value;
    }
    
    public String getNlNaam(){
        return this.nlNaam;
    }

    public static CardValue create(int value) {
            Optional<CardValue> matchingValue = Stream.of(CardValue.values()).filter(suit -> suit.getValue() == value).findFirst();
            if (matchingValue.isPresent()) {
                    return matchingValue.get();
            }
            throw new IllegalArgumentException("There is no value with value " + value);
    }
}
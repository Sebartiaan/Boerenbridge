/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Enum.java to edit this template
 */
package com.mycompany.boerenbridge;

import java.util.Optional;
import java.util.stream.Stream;
import javax.swing.ImageIcon;

/**
 *
 * @author b.smeets
 */
public enum Suit {
    DIAMONDS('d',"Ruiten"), 
    CLUBS('c',"Klaveren"), 
    HEARTS('h',"Harten"), 
    SPADES('s',"Schoppen");

    private final char id;
    private final String nlNaam;

    Suit(char id, String nlNaam) {
            this.id = id;
            this.nlNaam = nlNaam;
    }

    public static Suit create(char id) {
        Optional<Suit> matchingSuit = Stream.of(Suit.values()).filter(suit -> suit.getId() == id).findFirst();
        if (matchingSuit.isPresent()) {
                return matchingSuit.get();
        }
        throw new IllegalArgumentException("There is no suit with id " + id);
    }

    public char getId() {
            return id;
    }

    public String getNlNaam() {
            return nlNaam;
    }

    @Override
    public String toString() {
            return nlNaam;
    }
    
    public ImageIcon getImage(){
        return new ImageIcon(getClass().getResource("/" + getId() + ".png"));
    }
}
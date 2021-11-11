/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.boerenbridge;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 *
 * @author b.smeets
 */
public abstract class AbstractPlayer {
    
    private List<Card> currentCards = new ArrayList<>();
    private final String name;
    
    public AbstractPlayer(String name) {
        this.name = name;
    }

    public List<Card> getCurrentCards() {
        return currentCards;
    }

    public void setCurrentCards(List<Card> currentCards) {
        this.currentCards = currentCards;
        Collections.sort(this.currentCards);
    }
    
    public void addCard(Card card) {
        this.currentCards.add(card);
    }
    
    public void clearCards() {
        this.currentCards.clear();
    }
    
    public void removeCard(Card card) {
        if (!this.currentCards.remove(card)) {
            throw new IllegalArgumentException("Something went wrong, card " + card + " does not belong to player " + getName());
        }
    }
    
    public abstract boolean isRobotPlayer();

    public String getName() {
        return this.name;
    }
    
    
    
}

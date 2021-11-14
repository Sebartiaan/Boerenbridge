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

    private List<Card> cards = new ArrayList<>();
    private final String name;
    private final Position position;
    private int score;

    public AbstractPlayer(String name, Position position) {
        this.name = name;
        this.position = position;
    }

    public List<Card> getCards() {
        return cards;
    }

    public void setCards(List<Card> cards) {
        this.cards = cards;
        Collections.sort(this.cards);
    }
    
    public void addCard(Card card) {
        this.cards.add(card);
    }
    
    public void clearCards() {
        this.cards.clear();
    }
    
    public void removeCard(Card card) {
        if (!this.cards.remove(card)) {
            throw new IllegalArgumentException("Something went wrong, card " + card + " does not belong to player " + getName());
        }
    }
    
     public void reset() {
        clearCards();
    }
    
    public abstract boolean isRobotPlayer();

    public String getName() {
        return this.name;
    }
    
    public Position getPosition(){
        return this.position;
    }

    @Override
    public String toString() {
        return "AbstractPlayer{" + "name=" + name + ", position=" + position + '}';
    }
    
    public int getScore() {
        return score;
    }

    public void increaseScore(int increaseBy) {
        this.score = this.score + increaseBy;
    }
}

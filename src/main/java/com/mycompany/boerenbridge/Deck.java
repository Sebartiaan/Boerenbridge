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
public class Deck {
    
    private final List<Card> cards = new ArrayList<>();
    
    public Deck() {
        create();
    }
    
    private void create() {
        for (Suit suit : Suit.values()) {
            for (CardValue value : CardValue.values()) {
                cards.add(new Card(suit, value));
            }
        }
    }
    
    public void shuffle(){
        Collections.shuffle(cards);
    }
    
    public List<Card> getCards() {
        return this.cards;
    }
    
    public List<Card> drawCards(int n) {
        List<Card> drawnCards = new ArrayList<>();
        for (int i = 0 ; i<n ; i++) {
            Card drawnCard = drawCard();
            if (drawnCard != null) {
                drawnCards.add(drawnCard);
            }
        }
        return drawnCards;
    }
    
    private Card drawCard(){
        if (cards.isEmpty()){
            return null;
        }
        return cards.remove(0); 
    }
    
}

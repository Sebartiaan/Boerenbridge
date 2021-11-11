/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.boerenbridge;

import com.mycompany.boerenbridge.screens.RondeScreen;

/**
 *
 * @author b.smeets
 */
public class Round {
    
    private final int numberOfCards;
    private final int roundNumber;
    private AbstractPlayer firstPlayer;

    public Round(int numberOfCards, int roundNumber) {
        this.numberOfCards = numberOfCards;
        this.roundNumber = roundNumber;
    }

    public int getNumberOfCards() {
        return numberOfCards;
    }

    public int getRoundNumber() {
        return roundNumber;
    }

    public void start() {
        new RondeScreen(this).setVisible(true);
    }
    
    void setFirstPlayer(AbstractPlayer firstPlayer) {
        this.firstPlayer = firstPlayer;
    }
    
    public AbstractPlayer getFirstPlayer(){
        return this.firstPlayer;
    }
    
}

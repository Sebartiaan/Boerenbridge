/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.boerenbridge;

import java.util.Random;
import java.util.stream.Stream;

/**
 *
 * @author b.smeets
 */
public class RobotPlayer extends AbstractPlayer {

    public RobotPlayer(String name, Position position) {
        super(name, position);
    }

    @Override
    public boolean isRobotPlayer() {
        return true;
    }

    public int guessSlagen(Round round) {
        int notAllowedGuess = -1;
        if (round.getNotAllowedGuess() != -1) {
            notAllowedGuess = round.getNotAllowedGuess();
        }
        
        int guess;
        int numberOfCards = getCards().size();
        int averageSlagen = numberOfCards/Game.NUMBER_OF_PLAYERS;
        if (numberOfCards%Game.NUMBER_OF_PLAYERS == 0) {
            guess = averageSlagen;
        } else {
            Random random = new Random();
            guess = random.nextInt(averageSlagen, averageSlagen +2);
        }
        if (notAllowedGuess == guess) {
            Random random = new Random();
            if (random.nextBoolean()) {
                return guess+1;
            } else {    
                return guess-1 < 0 ? guess+1 : guess-1;
            }    
        } else {
            return guess;
        }
    }
    
    public Suit maakTroef(){
        //Kiest de meest voorkomende kleur als troef
        return Suit.DIAMONDS;
    }

    //TODO make smarter
    public Card pickCard() {
        if (getCards().size() == 1) {
            return getCards().get(0);
        }
        Random random = new Random();
        int randomInt = random.nextInt(0, getCards().size());
        return getCards().get(randomInt);
    }
    
}

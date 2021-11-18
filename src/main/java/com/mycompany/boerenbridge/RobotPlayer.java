/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.boerenbridge;

import ai.AIDifficulty;
import ai.EasyAI;
import ai.MediumAI;
import ai.RobotAI;

/**
 *
 * @author b.smeets
 */
public class RobotPlayer extends AbstractPlayer {

    private final RobotAI robotAi;

    public RobotPlayer(String name, Position position) {
        super(name, position);
        this.robotAi = getAiDifficulty();
    }

    public int guessSlagen(Round round) {
        return this.robotAi.guessSlagen(round);
    }
    
    public RobotAI getAI(){
        return this.robotAi;
    }
    
    public Suit maakTroef(){
        return this.robotAi.maakTroef();
    }

    public Card pickCard() {
        return this.robotAi.pickCard();
    }

    private RobotAI getAiDifficulty() {
        AIDifficulty aiDifficulty = Game.getSingleton().getAIDifficulty();
        switch (aiDifficulty) {
            case EASY -> {
                return new EasyAI(this);
            }
            case MEDIUM -> {
                return new MediumAI(this);
            }
            case HARD -> {
                throw new UnsupportedOperationException();
            }
            default -> throw new AssertionError(aiDifficulty.name());
            
        }
    }
    
}

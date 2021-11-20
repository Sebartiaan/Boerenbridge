/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.boerenbridge;

import ai.AIDifficulty;
import ai.EasyAI;
import ai.HardAI;
import ai.HarderAI;
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

    public Card pickCard(Hand currentHand) {
        return this.robotAi.pickCard(currentHand);
    }

    private RobotAI getAiDifficulty() {
        AIDifficulty aiDifficulty = Game.getSingleton().getAIDifficulty();
        if (aiDifficulty == AIDifficulty.EASY) {
			return new EasyAI(this);
		} else if (aiDifficulty == AIDifficulty.MEDIUM) {
			return new MediumAI(this);
		} else if (aiDifficulty == AIDifficulty.HARD) {
			return new HardAI(this);
		} else if (aiDifficulty == AIDifficulty.HARDER) {
			return new HarderAI(this);
		} else {
			throw new AssertionError(aiDifficulty.name());
		}
    }

    
}

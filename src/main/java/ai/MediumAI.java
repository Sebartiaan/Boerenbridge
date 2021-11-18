/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ai;

import com.mycompany.boerenbridge.Card;
import com.mycompany.boerenbridge.RobotPlayer;
import com.mycompany.boerenbridge.Round;
import com.mycompany.boerenbridge.Suit;

/**
 *
 * @author b.smeets
 */
public class MediumAI implements RobotAI {

    private final RobotPlayer robot;

    public MediumAI(RobotPlayer robot) {
        this.robot = robot;
    }

    @Override
    public int guessSlagen(Round round) {
        return AIHelper.getAverageAmountOfSlagen(round, robot.getCards());
    }

    @Override
    public Card pickCard() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public Suit maakTroef() {
        return AIHelper.findMostCommonSuit(robot.getCards());
    }
    
}

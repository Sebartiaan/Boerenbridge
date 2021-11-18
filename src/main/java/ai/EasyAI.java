/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ai;

import com.mycompany.boerenbridge.Card;
import com.mycompany.boerenbridge.RobotPlayer;
import com.mycompany.boerenbridge.Round;
import com.mycompany.boerenbridge.Suit;
import java.util.List;
import java.util.Random;

/**
 *
 * @author b.smeets
 * Mean: 112, stdev: 22.315913604421397
 */


public class EasyAI implements RobotAI{

    private final RobotPlayer robot;

    public EasyAI(RobotPlayer robot) {
        this.robot = robot;
    }
    
    @Override
    public int guessSlagen(Round round) {
        return AIHelper.getAverageAmountOfSlagen(round, robot.getCards());
    }

    @Override
    public Card pickCard() {
        List<Card> cards = robot.getCards();
        if (cards.size() == 1) {
            return cards.get(0);
        }
        Random random = new Random();
        int randomInt = random.nextInt(0, cards.size());
        return cards.get(randomInt);
    }

    @Override
    public Suit maakTroef() {
        return AIHelper.findMostCommonSuit(robot.getCards());
    }
}

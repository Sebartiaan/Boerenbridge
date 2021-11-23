/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ai;

import com.mycompany.boerenbridge.Card;
import com.mycompany.boerenbridge.Hand;
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
	private Round round;

    public EasyAI(RobotPlayer robot) {
        this.robot = robot;
    }
    
    @Override
    public int guessSlagen() {
    	return new SlagenGuesser(robot, round).getAverageAmountOfSlagen();
    }

    @Override
    public Card pickCard(Hand hand) {
        Card firstCard = hand.getFirstCard();
        List<Card> legalCards = AIHelper.getLegalCardsToPlay(robot.getCards(), firstCard == null ? null : firstCard.getSuit());
        if (legalCards.size() == 1) {
            return legalCards.get(0);
        }
        Random random = new Random();
        int randomInt = random.nextInt(legalCards.size());
        return legalCards.get(randomInt);
    }

    @Override
    public Suit maakTroef() {
    	return new TroefMaker(robot).findMostCommonSuit();
    }
    
    @Override
	public void setRound(Round round) {
		this.round = round;
	}
}

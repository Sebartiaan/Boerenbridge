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

/**
 *
 * @author b.smeets
 */
public class MediumAI implements RobotAI {

    private final RobotPlayer robot;
    private Round round;
	private CardPicker cardPicker;

    public MediumAI(RobotPlayer robot) {
        this.robot = robot;
        
    }

    @Override
    public int guessSlagen(Round round) {
        this.round = round;
        return new SlagenGuesser(robot, round).getAverageAmountOfSlagen();
    }

    @Override
    public Card pickCard(Hand hand) {
        int guessed = round.getSlagenFor(robot);
        int current = round.getScoreFor(robot);
        
        if (hand.getFirstCard() == null) {
            return getCardPicker().getStartingCard(guessed, current, hand);
        }
        
        if (guessed > current) {
        	return getCardPicker().getGoodCard(hand);
        } else {
            return getCardPicker().getBadCard(hand);
        }
    }

    @Override
    public Suit maakTroef() {
    	return new TroefMaker(robot).findMostCommonSuit();
    }


    private CardPicker getCardPicker() {
    	if (this.cardPicker == null) {
    		this.cardPicker = new CardPicker(round, robot);
    	}
    	return this.cardPicker;
    }
    
}

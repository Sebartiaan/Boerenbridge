package ai;

import java.util.ArrayList;
import java.util.List;

import com.mycompany.boerenbridge.Card;
import com.mycompany.boerenbridge.Hand;
import com.mycompany.boerenbridge.RobotPlayer;
import com.mycompany.boerenbridge.Round;
import com.mycompany.boerenbridge.Suit;

public class HarderAI implements RobotAI {
	
	
	private Round round;
	private final RobotPlayer robot;
	private List<Card> markedCards = new ArrayList<>();
	private CardPicker cardPicker;

	public HarderAI(RobotPlayer robot) {
		this.robot = robot;
	}

	@Override
	public int guessSlagen(Round round) {
		this.round = round;
		this.markedCards.clear();
		return new SlagenGuesser(robot, round).getAmountOfSlagenBasedOnCardValues(this.markedCards);
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
		return new TroefMaker(robot).findSuitWithBestValue();
	}

	private CardPicker getCardPicker() {
		if (this.cardPicker == null) {
			this.cardPicker = new CardPicker(round, robot);
		}
		return this.cardPicker;
	}


}

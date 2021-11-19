package ai;

import java.util.ArrayList;
import java.util.List;

import com.mycompany.boerenbridge.Card;
import com.mycompany.boerenbridge.CardValue;
import com.mycompany.boerenbridge.Hand;
import com.mycompany.boerenbridge.RobotPlayer;
import com.mycompany.boerenbridge.Round;
import com.mycompany.boerenbridge.Suit;

import ai.TroefMaker.SuitWithValue;

public class HarderAI implements RobotAI {
	
	
	private Round round;
	private final RobotPlayer robot;
	private List<Card> markedCards = new ArrayList<>();
	private CardPicker cardPicker;
	private SuitWithValue suitWithValue;

	public HarderAI(RobotPlayer robot) {
		this.robot = robot;
	}

	@Override
	public int guessSlagen(Round round) {
		this.round = round;
		this.markedCards.clear();
		this.suitWithValue = new TroefMaker(robot).findSuitWithBestValue();
		return new HarderSlagenGuesser(robot, round, suitWithValue).getAmountOfSlagenBasedOnCardValues(this.markedCards);
		
	}

	@Override
	public Card pickCard(Hand hand) {
		int guessed = round.getSlagenFor(robot);
		int current = round.getScoreFor(robot);
		
		current += getNumberOfCertainWinCards();
		
		if (hand.getFirstCard() == null) {
			return getCardPicker().getStartingCard(guessed, current, hand);
		}

		if (guessed > current) {
			return getCardPicker().getGoodCard(hand);
		} else {
			return getCardPicker().getBadCard(hand);
		}
	}

	private int getNumberOfCertainWinCards() {
		int numberOfCertainWinCards = 0;
		List<Card> cards = robot.getCards();
		List<Card> troefsInHand = AIHelper.getCardsOfSuit(cards, round.getTroef());
		for (Card troef : troefsInHand) {
			List<Card> troefsSeen = AIHelper.getCardsOfSuit(round.getCardsSeen(), round.getTroef());
			troefsSeen.addAll(troefsInHand);
			if (isCertainWinTroef(troef, troefsSeen)) {
				numberOfCertainWinCards++;
			}
		}
		
		if (round.getNumberOfCards() > 9) {
			numberOfCertainWinCards += AIHelper.getCardsOfValue(AIHelper.getCardsNotOfSuit(robot.getCards(), round.getTroef()), CardValue.ACE).size();
		}
		
		return numberOfCertainWinCards;
	}

	private boolean isCertainWinTroef(Card card, List<Card> troefsSeen) {
		boolean started = false;
		boolean isHighest = true;
		for (CardValue cardValue : CardValue.values()) {
			if (started) {
				isHighest = troefsSeen.stream().anyMatch(troef -> troef.getValue() == cardValue);
				if (!isHighest) {
					break;
				}
			}
			if (card.getValue() == cardValue) {
				started = true;
			}
		}
		
		return isHighest;
	}

	@Override
	public Suit maakTroef() {
		return this.suitWithValue.getSuit();

	}
	
	private CardPicker getCardPicker() {
		if (this.cardPicker == null) {
			this.cardPicker = new CardPicker(round, robot);
		}
		return this.cardPicker;
	}


}

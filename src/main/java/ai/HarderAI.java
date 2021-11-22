package ai;

import java.util.ArrayList;
import java.util.List;

import com.mycompany.boerenbridge.Card;
import com.mycompany.boerenbridge.CardValue;
import com.mycompany.boerenbridge.Hand;
import com.mycompany.boerenbridge.RobotPlayer;
import com.mycompany.boerenbridge.Round;
import com.mycompany.boerenbridge.Suit;

public class HarderAI implements RobotAI {
	
	
	private Round round;
	private final RobotPlayer robot;
	private List<Card> markedCards = new ArrayList<>();

	public HarderAI(RobotPlayer robot) {
		this.robot = robot;
	}

	@Override
	public int guessSlagen(Round round) {
		this.round = round;
		this.markedCards.clear();
		return new HarderSlagenGuesser(robot, round).getAmountOfSlagenBasedOnCardValues(this.markedCards);
		
	}

	@Override
	public Card pickCard(Hand hand) {
		int guessed = round.getSlagenFor(robot);
		int current = round.getScoreFor(robot);
		
		current += getNumberOfCertainWinCards();
		
		Card pickedCard;
		if (hand.getFirstCard() == null) {
			pickedCard = new HarderCardPicker(round, robot, markedCards).getStartingCard(guessed, current);
			this.markedCards.remove(pickedCard);
			return pickedCard;
		}

		if (guessed > current) {
			pickedCard = new CardPicker(round, robot).getCardToWin(hand, this.markedCards);
		} else {
			pickedCard = new HarderCardPicker(round, robot, markedCards).getCardToLose(hand);
		}
		this.markedCards.remove(pickedCard);
		return pickedCard;
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
		return new TroefMaker(robot).findSuitWithBestValue().getSuit();
	}
}

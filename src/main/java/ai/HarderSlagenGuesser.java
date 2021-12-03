package ai;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

import com.mycompany.boerenbridge.Card;
import com.mycompany.boerenbridge.CardValue;
import com.mycompany.boerenbridge.RobotPlayer;
import com.mycompany.boerenbridge.Round;

import ai.TroefMaker.SuitWithValue;

public class HarderSlagenGuesser extends SlagenGuesser {

	private boolean onlyHighCards = false;

	public HarderSlagenGuesser(RobotPlayer robot, Round round) {
		super(robot, round);
	}
	
	@Override
	protected List<Card> findInterestingCards() {
		int guessedUntilNow = getRound().getSlagen().values().stream().filter(Objects::nonNull).mapToInt(Integer::intValue).sum();
		
		//Als er al veel slagen gegokt zijn, bekijk dan alleen de azen
		if (guessedUntilNow >= getRound().getNumberOfCards()) {
			onlyHighCards = true;
		} 
		List<CardValue> interestingCards = getInterestingHighCardValues();
		List<Card> cards = getRobot().getCards();
		List<Card> markedCards = new ArrayList<>();
		
		SuitWithValue potentialTroef = new TroefMaker(getRobot()).findSuitWithBestValue();
		if (potentialTroef.getValue() > getRoundThreshold(getRound())) {
			List<Card> troefsInHand = AIHelper.getCardsOfSuit(cards, potentialTroef.getSuit());
			List<Card> troefsSeen = new ArrayList<>(troefsInHand);
			for (Card card : troefsInHand) {
				if (isCertainWinTroef(card, troefsSeen)) {
					markedCards.add(card);
				}
			}
		}

		for (Card card : cards) {
			for (CardValue interestingCard : interestingCards) {
				if (!markedCards.contains(card) && card.getValue() == interestingCard) {
					markedCards.add(card);
					break;
				}
			}
		}

		return markedCards;
	}
	
	public boolean isCertainWinTroef(Card card, List<Card> troefsSeen) {
		boolean started = false;
		boolean isHighest = true;
		
		int numberOfCardsInGame = getRound().getNumberOfCards() * 4;
		int numberOfKnownCardsInGame = getRobot().getCards().size();
		int numberOfUnknownCardsInGame = numberOfCardsInGame - numberOfKnownCardsInGame;
		double likelyHoodCardinGame = numberOfUnknownCardsInGame/52d;
		double likelyHoodCardNotInGame = 1 - likelyHoodCardinGame;
		
		for (CardValue cardValue : CardValue.values()) {
			if (started) {
				isHighest = troefsSeen.stream().anyMatch(troef -> troef.getValue() == cardValue);
				if (!isHighest) {
					if (likelyHoodCardNotInGame > 0.3) {
						likelyHoodCardNotInGame = likelyHoodCardNotInGame*likelyHoodCardNotInGame;
						continue;
					}
					troefsSeen.add(new Card(card.getSuit(), cardValue));
					break;
				}
			}
			if (card.getValue() == cardValue) {
				started = true;
			}
		}
		
		return isHighest;
	}
	
	/**
	 * Determines threshold for the given round for when the player should go all in with troef
	 * @param round
	 * @return
	 */
	public static int getRoundThreshold(Round round) {
		int base = 16;
		int duplicator = (round.getNumberOfCards()-2)*4;
		return base + duplicator;
	}
	
	@Override
	protected List<CardValue> getInterestingHighCardValues() {
		if (onlyHighCards) {
			return Arrays.asList(CardValue.ACE);
		} 
		return Arrays.asList(CardValue.ACE, CardValue.KING, CardValue.QUEEN);
	}
}

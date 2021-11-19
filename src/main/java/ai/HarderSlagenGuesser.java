package ai;

import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import com.mycompany.boerenbridge.Card;
import com.mycompany.boerenbridge.CardValue;
import com.mycompany.boerenbridge.RobotPlayer;
import com.mycompany.boerenbridge.Round;

import ai.TroefMaker.SuitWithValue;

public class HarderSlagenGuesser extends SlagenGuesser {

	private final SuitWithValue suitwithValue;
	private boolean onlyHighCards = false;

	public HarderSlagenGuesser(RobotPlayer robot, Round round, SuitWithValue suitwithValue) {
		super(robot, round);
		this.suitwithValue = suitwithValue;
	}
	
	@Override
	protected int findInterestingCards(List<Card> markedCards) {
		int guessedUntilNow = getRound().getSlagen().values().stream().filter(Objects::nonNull).mapToInt(Integer::intValue).sum();
		if (guessedUntilNow >= getRound().getNumberOfCards() || suitwithValue.getValue() > getRoundThreshold(getRound())) {
			onlyHighCards = true;
		}
		int amountOfInterestingCards = super.findInterestingCards(markedCards);
		if (getRound().getNumberOfCards() > 3 && suitwithValue.getValue() > getRoundThreshold(getRound())) {
			List<Card> potentialTroefCards = AIHelper.getCardsOfSuit(getRobot().getCards(), suitwithValue.getSuit());
			List<Card> lowPotentialTroefCards = potentialTroefCards.stream().filter(card -> !getInterestingHighValueCards().contains(card.getValue())).collect(Collectors.toList());
			amountOfInterestingCards += lowPotentialTroefCards.size();
		}
		
		if (getRound().getNumberOfCards() == 2 && amountOfInterestingCards == 2) {
			if (suitwithValue.getValue() <= CardValue.ACE.getValue()) {
				return 1;
			}
		} else if (getRound().getNumberOfCards() == 3 && amountOfInterestingCards == 3 && suitwithValue.getValue() <= 20) {
			if (suitwithValue.getValue() <=14) {
				return 1;
			}
			return 2;
		}
		return amountOfInterestingCards;
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
	protected List<CardValue> getInterestingHighValueCards() {
		if (onlyHighCards) {
			return Arrays.asList(CardValue.ACE);
		} 
		return Arrays.asList(CardValue.ACE, CardValue.KING, CardValue.QUEEN);
	}

}

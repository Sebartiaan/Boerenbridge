package ai;

import java.util.Arrays;
import java.util.List;
import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;

import com.mycompany.boerenbridge.Card;
import com.mycompany.boerenbridge.CardValue;
import com.mycompany.boerenbridge.Game;
import com.mycompany.boerenbridge.RobotPlayer;
import com.mycompany.boerenbridge.Round;

public class SlagenGuesser {

	private final RobotPlayer robot;
	private final Round round;
	private Random random;

	public SlagenGuesser(RobotPlayer robot, Round round) {
		this.robot = robot;
		this.round = round;
	}

	public int getAmountOfSlagenBasedOnCardValues(List<Card> markedCards) {
		int wantedPrediction = findInterestingCards(markedCards);

		int notAllowedGuess = round.getNotAllowedGuess();
		if (notAllowedGuess == -1) {
			return wantedPrediction;
		}

		if (wantedPrediction != notAllowedGuess) {
			return wantedPrediction;
		} else {
			int lower = wantedPrediction - 1;
			int higher = wantedPrediction + 1;
			if (lower < 0) {
				return higher;
			} else if (higher > round.getNumberOfCards()) {
				return lower;
			} else {
				if (new Random().nextBoolean()) {
					return lower;
				} else {
					return higher;
				}
			}
		}
	}

	protected int findInterestingCards(List<Card> markedCards) {
		List<CardValue> interestingCards = getInterestingHighValueCards();
		List<Card> cards = robot.getCards();

		for (Card card : cards) {
			for (CardValue interestingCard : interestingCards) {
				if (card.getValue() == interestingCard) {
					markedCards.add(card);
					break;
				}
			}
		}

		int wantedPrediction = markedCards.size();
		return wantedPrediction;
	}

	protected List<CardValue> getInterestingHighValueCards() {
		return Arrays.asList(CardValue.ACE, CardValue.KING, CardValue.QUEEN);
	}

	public int getAverageAmountOfSlagen() {
		List<Card> cards = robot.getCards();
		int notAllowedGuess = -1;
		if (round.getNotAllowedGuess() != -1) {
			notAllowedGuess = round.getNotAllowedGuess();
		}

		int guess;
		int numberOfCards = cards.size();
		int averageSlagen = numberOfCards / Game.NUMBER_OF_PLAYERS;
		if (numberOfCards % Game.NUMBER_OF_PLAYERS == 0) {
			guess = averageSlagen;
		} else {
			guess = ThreadLocalRandom.current().nextInt(averageSlagen, averageSlagen + 2);
		}
		if (notAllowedGuess == guess) {
			if (getRandom().nextBoolean()) {
				return guess + 1;
			} else {
				return guess - 1 < 0 ? guess + 1 : guess - 1;
			}
		} else {
			return guess;
		}
	}
	
	protected final RobotPlayer getRobot() {
		return this.robot;
	}
	protected final Round getRound() {
		return this.round;
	}


	public Random getRandom() {
		if (this.random == null) {
			this.random = new Random();
		}
		return this.random;
	}

}

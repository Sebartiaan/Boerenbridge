package ai;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import com.mycompany.boerenbridge.Card;
import com.mycompany.boerenbridge.CardValue;
import com.mycompany.boerenbridge.Hand;
import com.mycompany.boerenbridge.RobotPlayer;
import com.mycompany.boerenbridge.Round;

public class CardPicker {

	private final Round round;
	private final RobotPlayer robot;

	public CardPicker(Round round, RobotPlayer robot) {
		this.round = round;
		this.robot = robot;
	}

	public Card getGoodCard(Hand hand) {
		Card firstCard = hand.getFirstCard();

		List<Card> legalCardsToPlay = AIHelper.getLegalCardsToPlay(robot.getCards(), firstCard.getSuit());
		Set<Card> playedCards = hand.getPlayedCards();

		Card cardToPlay;

		// Is het misschien mogelijk een troef te spelen?
		if (AIHelper.containsCardOfSuit(legalCardsToPlay, round.getTroef())) {
			List<Card> troefCards = AIHelper.getCardsOfSuit(legalCardsToPlay, round.getTroef());
			Card currentlyWinningCard = hand.getCurrentlyWinningCard();
			Card lowestWinningTroef = AIHelper.getLowestCardMatchingSuitHigherThan(troefCards, currentlyWinningCard);
			if (lowestWinningTroef != null) {
				cardToPlay = lowestWinningTroef;
			} else {
				// cannot win, dump
				cardToPlay = getLowestCardToDump(legalCardsToPlay);
			}
		} else {
			// We hebben geen troef
			if (AIHelper.containsCardOfSuit(playedCards, round.getTroef())) {
				// er is troef gespeeld en wij hebben geen troef, dus dumpen
				cardToPlay = getLowestCardToDump(legalCardsToPlay);
			} else {
				// er is geen troef gespeeld, misschien kunnen we nog winnen
				Card winningNonTroefCard = AIHelper.getHighestCardOfSuit(playedCards, firstCard.getSuit());
				Card highestCardMatchingSuit = AIHelper.getHighestCardMatchingSuitHigherThan(legalCardsToPlay,
						winningNonTroefCard);
				if (highestCardMatchingSuit != null) {
					// can still win, throw high card of matching first card suit
					cardToPlay = highestCardMatchingSuit;
				} else {
					cardToPlay = getLowestCardToDump(legalCardsToPlay);
				}

			}
		}
		return cardToPlay;
	}

	public Card getBadCard(Hand hand) {
		Card firstCard = hand.getFirstCard();
		List<Card> legalCardsToPlay = AIHelper.getLegalCardsToPlay(robot.getCards(), firstCard.getSuit());
		return getHighestCardToDump(legalCardsToPlay, hand);
	}
	
	public Card getStartingCard(int guessed, int current, Hand hand) {
        Card startingCard;
        List<Card> cards = robot.getCards();
        if (guessed > current) {
            List<Card> aces = AIHelper.getCardsOfValue(cards, CardValue.ACE);
            if (aces.isEmpty()) {
                startingCard = getLowestCardToDump(cards);
            } else {
                startingCard = AIHelper.getRandomCard(aces);
            }
        } else {
            startingCard = getHighestCardToDump(cards, hand);
        }
        return startingCard;
    }

	private Card getLowestCardToDump(List<Card> cards) {
		List<Card> lowestCards = AIHelper.getLowestCards(cards);
		List<Card> lowestNonTroefCards = lowestCards.stream().filter(card -> card.getSuit() != round.getTroef())
				.collect(Collectors.toList());
		if (lowestNonTroefCards.isEmpty()) {
			return AIHelper.getRandomCard(lowestCards);
		}
		return AIHelper.getRandomCard(lowestNonTroefCards);
	}

	private Card getHighestCardToDump(List<Card> cards, Hand hand) {
		Card bestCard;
		if (hand.getFirstCard() == null) {
			List<Card> cardsOfValueLessThan10 = AIHelper.getCardsOfValueLessThan(cards, CardValue.TEN);
			if (cardsOfValueLessThan10.isEmpty()) {
				bestCard = AIHelper.getRandomCard(cards);
			} else {
				bestCard = AIHelper.getRandomCard(cardsOfValueLessThan10);
			}
		} else {
			Card firstCard = hand.getFirstCard();
			List<Card> cardsMatchingFirstCard = AIHelper.getCardsOfSuit(cards, firstCard.getSuit());
			if (cardsMatchingFirstCard.isEmpty()) {
				if (!AIHelper.containsCardOfSuit(cards, round.getTroef())) {
					bestCard = getHighestNonTroefCard(cards);
				} else {
					bestCard = getLowestCardToDump(cards);
				}
			} else {
				// we moeten bekennen, probeer onder de al gespeelde kaarten te blijven
				Set<Card> playedCards = hand.getPlayedCards();
				// zit er al een troef in de gespeelde kaarten?
				if (AIHelper.containsCardOfSuit(playedCards, round.getTroef())) {
					// kunnen we een troef dumpen?
					List<Card> troefs = AIHelper.getCardsOfSuit(playedCards, round.getTroef());
					Card highestTroefPlayed = AIHelper.getHighestCardOfSuit(troefs, round.getTroef());
					Card bestTroefToPlay = AIHelper.getHighestCardMatchingSuitLowerThan(cards, highestTroefPlayed);
					if (bestTroefToPlay != null) {
						bestCard = bestTroefToPlay;
					} else {
						bestCard = getLowestCardToDump(cards);
					}
				} else {
					bestCard = getHighestNonTroefCard(cards);
				}
			}
		}
		return bestCard;
	}

	private Card getHighestNonTroefCard(List<Card> cards) {
		// we hoeven niet te bekennen, pak de hoogst mogelijke niet-troef kaart
		List<Card> nonTroefCards = AIHelper.getCardsNotOfSuit(cards, round.getTroef());
		List<Card> highestCards = AIHelper.getHighestCards(nonTroefCards);
		return AIHelper.getRandomCard(highestCards);
	}

}

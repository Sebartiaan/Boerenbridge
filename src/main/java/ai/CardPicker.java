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

	protected final Round round;
	protected final RobotPlayer robot;

	public CardPicker(Round round, RobotPlayer robot) {
		this.round = round;
		this.robot = robot;
	}

	public Card getCardToWin(Hand hand, List<Card> markedCards) {
		Card firstCard = hand.getFirstCard();
		Card currentlyWinningCard = hand.getCurrentlyWinningCard();
		
		List<Card> myCards = robot.getCards();
		List<Card> legalCardsToPlay = AIHelper.getLegalCardsToPlay(myCards, firstCard.getSuit());
		
		Card cardToWin;
		
		if (AIHelper.containsCardOfSuit(legalCardsToPlay, firstCard.getSuit())) {
			//We moeten bekennen
			Card lowestWinningCard = AIHelper.getLowestCardMatchingSuitHigherThan(legalCardsToPlay, currentlyWinningCard);
			if (lowestWinningCard != null) {
				//We kunnen winnen
				if (hand.getNumberOfPlayedCards() == 3) {
					//Het is de laatste kaart
					//Gooi de laagste bekennende kaart
					cardToWin = lowestWinningCard;
				} else {
					//Het is niet de laatste kaart
					Card highestWinningCard = AIHelper.getHighestCardMatchingSuitHigherThan(legalCardsToPlay, currentlyWinningCard);
					if (markedCards.contains(highestWinningCard)) {
						//De hoogste kaart is een gemarkeerde kaart
						//Gooi de hoogste kaart
						cardToWin = highestWinningCard;
					} else {
						//De hoogste kaart is niet een gemarkeerde kaart
						//Gooi de laagste bekennende kaart
						cardToWin = lowestWinningCard;
					}
				}
			} else {
				//We kunnen niet winnen
				//Dump de laagste bekennende kaart
				cardToWin = AIHelper.getLowestCardOfSuit(legalCardsToPlay, firstCard.getSuit());
			}
				
				
		} else {
			//We kunnen niet bekennen
			List<Card> myTroefs = AIHelper.getCardsOfSuit(legalCardsToPlay, round.getTroef());
			if (!myTroefs.isEmpty()) {
				//We kunnen troef gooien
				List<Card> playedTroefs = AIHelper.getCardsOfSuit(hand.getPlayedCards(), round.getTroef());
				if (!playedTroefs.isEmpty()) {
					//Er is al troef gegooid
					Card lowestWinningTroef = AIHelper.getLowestCardMatchingSuitHigherThan(legalCardsToPlay, hand.getCurrentlyWinningCard());
					if (lowestWinningTroef != null) {
						//We hebben een hogere troef
						//Gooi de eerste troef die hoger is dan de hoogste troef tot nu toe
						cardToWin = lowestWinningTroef;
					} else {
						//We hebben geen hogere troef
						//Dump de laagste niet bekennende kaart
						cardToWin = getLowestCardToDump(legalCardsToPlay);
					}
				} else {
					//Er is nog geen troef gegooid
					//Gooi de laagste troef
					cardToWin = AIHelper.getLowestCardOfSuit(legalCardsToPlay, round.getTroef());
				}
			} else {
				//We kunnen geen troef gooien
				//Dump de laagste niet bekennende kaart
				cardToWin = getLowestCardToDump(legalCardsToPlay);
			}
		}
		
		return cardToWin;
	}

	
	public Card getCardToLose(Hand hand) {
		Card firstCard = hand.getFirstCard();
		List<Card> legalCardsToPlay = AIHelper.getLegalCardsToPlay(robot.getCards(), firstCard.getSuit());
		List<Card> cardsMatchingFirstCard = AIHelper.getCardsOfSuit(legalCardsToPlay, firstCard.getSuit());
		Card cardToLose;
		if (cardsMatchingFirstCard.isEmpty()) {
			if (!AIHelper.containsCardOfSuit(legalCardsToPlay, round.getTroef())) {
				cardToLose = getHighestNonTroefCard(legalCardsToPlay);
			} else {
				cardToLose = getLowestCardToDump(legalCardsToPlay);
			}
		} else {
			// we moeten bekennen, probeer onder de al gespeelde kaarten te blijven
			Set<Card> playedCards = hand.getPlayedCards();
			// zit er al een troef in de gespeelde kaarten?
			if (AIHelper.containsCardOfSuit(playedCards, round.getTroef())) {
				// kunnen we een troef dumpen?
				List<Card> troefs = AIHelper.getCardsOfSuit(playedCards, round.getTroef());
				Card highestTroefPlayed = AIHelper.getHighestCardOfSuit(troefs, round.getTroef());
				Card bestTroefToPlay = AIHelper.getHighestCardMatchingSuitLowerThan(legalCardsToPlay, highestTroefPlayed);
				if (bestTroefToPlay != null) {
					cardToLose = bestTroefToPlay;
				} else {
					cardToLose = getLowestCardToDump(legalCardsToPlay);
				}
			} else {
				cardToLose = getHighestNonTroefCard(legalCardsToPlay);
			}
		}
		return cardToLose;
	}
	
	public Card getStartingCard(int guessed, int current) {
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
            startingCard = getLowestCardToDump(cards);
        }
        return startingCard;
    }

	protected Card getLowestCardToDump(List<Card> cards) {
		List<Card> lowestCards = AIHelper.getLowestCards(cards);
		List<Card> lowestNonTroefCards = lowestCards.stream().filter(card -> card.getSuit() != round.getTroef())
				.collect(Collectors.toList());
		if (lowestNonTroefCards.isEmpty()) {
			return AIHelper.getRandomCard(lowestCards);
		}
		return AIHelper.getRandomCard(lowestNonTroefCards);
	}


	private Card getHighestNonTroefCard(List<Card> cards) {
		// we hoeven niet te bekennen, pak de hoogst mogelijke niet-troef kaart
		List<Card> nonTroefCards = AIHelper.getCardsNotOfSuit(cards, round.getTroef());
		List<Card> highestCards = AIHelper.getHighestCards(nonTroefCards);
		return AIHelper.getRandomCard(highestCards);
	}

}

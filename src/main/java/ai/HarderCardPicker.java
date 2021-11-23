package ai;

import java.util.List;

import com.mycompany.boerenbridge.Card;
import com.mycompany.boerenbridge.CardValue;
import com.mycompany.boerenbridge.Hand;
import com.mycompany.boerenbridge.RobotPlayer;
import com.mycompany.boerenbridge.Round;

public class HarderCardPicker extends CardPicker {

	public HarderCardPicker(Round round, RobotPlayer robot) {
		super(round, robot);
	}
	
	@Override
	public Card getStartingCard(int guessed, int current) {
		List<Card> markedCards = new HarderSlagenGuesser(robot, round).getMarkedCards();
		Card startingCard;
        List<Card> cards = robot.getCards();
        if (guessed > current) {
    		// Hebben we een aas
    		List<Card> aces = AIHelper.getCardsOfValue(cards, CardValue.ACE);
    		List<Card> nonTroefAces = AIHelper.getCardsNotOfSuit(aces, round.getTroef());
    		if (nonTroefAces.isEmpty()) {
    			startingCard = getSmartStartCard(cards, markedCards);
    		} else {
    			startingCard = AIHelper.getRandomCard(nonTroefAces);
    		}
        } else {
            startingCard = getLowestCardToDump(cards);
        }
        return startingCard;
	}
	
	private Card getSmartStartCard(List<Card> cards, List<Card> markedCards) {
		Card startCard;
		List<Card> lowCards = AIHelper.getCardsOfValueLessThan(cards, CardValue.NINE);
		if (!lowCards.isEmpty()) {
			//We have a low card
			List<Card> lowCardsThatAccompanyMarkedCard = AIHelper.getMatchingSuitCards(lowCards, markedCards);
			if (!lowCardsThatAccompanyMarkedCard.isEmpty()) {
				//We have a low card that accompanies a marked card
				//Throw the lowest low card
					startCard = AIHelper.getRandomCard(AIHelper.getLowestCards(lowCardsThatAccompanyMarkedCard));
			} else {
				//We don't have a low card that accompanies a marked card
				List<Card> standaloneLowCards = AIHelper.getStandaloneCards(lowCards);
				if (!standaloneLowCards.isEmpty()) {
					//We have a low card that stands alone
					//Throw the lowest card that stands alone
					startCard = AIHelper.getRandomCard(AIHelper.getLowestCards(standaloneLowCards));	
				} else {
					//We only have low cards that do not stand alone
					//Throw the lowest card
					startCard = AIHelper.getRandomCard(AIHelper.getLowestCards(lowCards));
				}
			}
		} else {
			//We don't have a low card
			List<Card> mediumCards = AIHelper.getCardsOfValueLessThan(cards, CardValue.JACK);
			if (!mediumCards.isEmpty()) {
				//We have medium cards
				List<Card> standaloneMediumCards = AIHelper.getStandaloneCards(mediumCards);
				//Is one of the medium cards an alone card
				if (!standaloneMediumCards.isEmpty()) {
					startCard = AIHelper.getRandomCard(AIHelper.getLowestCards(standaloneMediumCards));
				} else {
					//None of the medium cards is an alone card
					//Throw the lowest random medium card
					startCard = AIHelper.getRandomCard(AIHelper.getLowestCards(mediumCards));
				}
			} else {
				//We only have high cards
				//Throw the lowest high card
				startCard = AIHelper.getRandomCard(AIHelper.getLowestCards(cards));
			}
		}
		return startCard;
	}

	@Override
	public Card getCardToLose(Hand hand) {
		Card firstCard = hand.getFirstCard();
		List<Card> legalCardsToPlay = AIHelper.getLegalCardsToPlay(robot.getCards(), firstCard.getSuit());
		
		Card cardToLose;
		if (AIHelper.containsCardOfSuit(legalCardsToPlay, firstCard.getSuit())) {
			//We moeten bekennen
			if (hand.getCurrentlyWinningCard().getSuit() == round.getTroef()) {
				// De huidig winnende kaart is een troef
				if (firstCard.getSuit() == round.getTroef()) {
					// De eerste kaart is een troef
					Card highestCardMatchingSuitLowerThan = AIHelper.getHighestCardMatchingSuitLowerThan(legalCardsToPlay, hand.getCurrentlyWinningCard());
					if (highestCardMatchingSuitLowerThan != null) {
						// We kunnen een kaart zo dicht mogelijk onder de huidig winnende kaart spelen
						cardToLose = highestCardMatchingSuitLowerThan;
					} else {
						// We kunnen niet een kaart zo dicht mogelijk onder de huidig winnende kaart spelen
						cardToLose = minimizeLosses(hand, legalCardsToPlay);
					}
				} else {
					// De eerste kaart is geen troef
					//Gooi de hoogste mogelijke bekennende kaart
					cardToLose = AIHelper.getHighestCardOfSuit(legalCardsToPlay, firstCard.getSuit());
				}
			} else {
				// De huidig winnende kaart is geen troef
				Card highestCardMatchingSuitLowerThan = AIHelper.getHighestCardMatchingSuitLowerThan(legalCardsToPlay, hand.getCurrentlyWinningCard());
				if (highestCardMatchingSuitLowerThan != null) {
					// We kunnen onder de huidig winnende kaart blijven
					//Gooi de kaart zo dicht mogelijk onder de huidig winnende kaart
					cardToLose = highestCardMatchingSuitLowerThan;
				} else {
					// We kunnen niet een kaart zo dicht mogelijk onder de huidig winnende kaart spelen
					cardToLose = minimizeLosses(hand, legalCardsToPlay);
				}
			}
		} else {
			//We kunnen niet bekennen
			if (AIHelper.containsCardOfSuit(hand.getPlayedCards(), round.getTroef())) {
				// Er is troef gegooid
				Card highestCardMatchingSuitLowerThan = AIHelper.getHighestCardMatchingSuitLowerThan(legalCardsToPlay, hand.getCurrentlyWinningCard());
				if (highestCardMatchingSuitLowerThan != null) {
					// Wij hebben een troef die lager is dan de huidig winnende troef
					// Gooi de troef die zo dicht mogelijk onder de huidig winnende troef zit
					cardToLose = highestCardMatchingSuitLowerThan;
				} else {
					// Wij hebben geen troef die lager is dan de huidig winnende troef
					if (AIHelper.getCardsOfSuit(legalCardsToPlay, round.getTroef()).size() == legalCardsToPlay.size()) {
						// We mogen alleen troef spelen
						//Gooi de laagste troef
						cardToLose = AIHelper.getLowestCardOfSuit(legalCardsToPlay, round.getTroef());
					} else {
						// We hoeven niet alleen troef te spelen
						// We gooien een zo hoog mogelijke niet troef kaart
						List<Card> nonTroefs = AIHelper.getCardsNotOfSuit(legalCardsToPlay, round.getTroef());
						List<Card> highestNonTroefs = AIHelper.getHighestCards(nonTroefs);
						cardToLose = AIHelper.getRandomCard(highestNonTroefs);
					}
				}
				
			} else {
				// Er is geen troef gegooid
				if (AIHelper.getCardsOfSuit(legalCardsToPlay, round.getTroef()).size() == legalCardsToPlay.size()) {
					// We mogen alleen troef spelen
					//Gooi de laagste troef
					cardToLose = AIHelper.getLowestCardOfSuit(legalCardsToPlay, round.getTroef());
				} else {
					// We hoeven niet alleen troef te spelen
					// We gooien een zo hoog mogelijke niet troef kaart
					List<Card> nonTroefs = AIHelper.getCardsNotOfSuit(legalCardsToPlay, round.getTroef());
					List<Card> highestNonTroefs = AIHelper.getHighestCards(nonTroefs);
					cardToLose = AIHelper.getRandomCard(highestNonTroefs);
				}
			}
		}
				
		return cardToLose;
		
	}

	private Card minimizeLosses(Hand hand, List<Card> legalCardsToPlay) {
		Card cardToLose;
		if (hand.getNumberOfPlayedCards() == 3) {
			// Onze winst is onvermijdelijk
			if (round.getScoreFor(robot) < round.getSlagenFor(robot)) {
				// We kunnen nog steeds uitkomen op een goed aantal slagen
				// Pak de hand met de hoogst mogelijke kaart
				cardToLose = AIHelper.getHighestCardOfSuit(legalCardsToPlay, hand.getFirstCard().getSuit());
			} else {
				// We kunnen niet meer uitkomen op een goed aantal slagen
				// Pak de hand met de laagste mogelijke kaart
				cardToLose = AIHelper.getLowestCardOfSuit(legalCardsToPlay, hand.getFirstCard().getSuit());
			}
		} else {
			// Onze winst is niet onvermijdelijk
				//Gooi een kaart zo dicht mogelijk boven de huidig winnende kaart
			cardToLose = AIHelper.getLowestCardMatchingSuitHigherThan(legalCardsToPlay, hand.getCurrentlyWinningCard());
		}
			
		return cardToLose;
	}

}

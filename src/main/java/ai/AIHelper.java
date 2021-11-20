package ai;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

import com.mycompany.boerenbridge.Card;
import com.mycompany.boerenbridge.CardValue;
import com.mycompany.boerenbridge.Suit;

/**
 *
 * @author b.smeets
 */
public class AIHelper {
    
    private static final Random random = new Random();

    private AIHelper(){
        
    }
    
    public static List<Card> getLegalCardsToPlay(List<Card> cards, Suit firstCardSuit) {
        if (firstCardSuit == null) {
            return cards;
        }
        List<Card> cardsOfSuit = getCardsOfSuit(cards, firstCardSuit);
        if (cardsOfSuit.isEmpty()) {
            return cards;
        } else {
            return cardsOfSuit;
        }
    }
    
    public static List<Card> getCardsOfSuit(Collection<Card> cards, Suit suit){ 
        return cards.stream().filter(card -> card.getSuit() == suit).collect(Collectors.toList());
    }
    
    public static List<Card> getCardsNotOfSuit(Collection<Card> cards, Suit excludingSuit) {
        return cards.stream().filter(card -> card.getSuit() != excludingSuit).collect(Collectors.toList());
    }
    
    public static boolean containsCardOfSuit(Collection<Card> cards, Suit suit) {
        return getCardsOfSuit(cards, suit).size() > 0;
    }
    
    public static List<Card> getLowestCards(List<Card> cards) {
        CardValue lowestValue = findLowestValue(cards);
        return cards.stream().filter(card -> card.getValue() == lowestValue).collect(Collectors.toList());
    }
    
    public static List<Card> getHighestCards(List<Card> cards) {
        CardValue lowestValue = findHighestValue(cards);
        return cards.stream().filter(card -> card.getValue() == lowestValue).collect(Collectors.toList());
    }
    
    public static List<Card> getCardsOfValue(List<Card> cards, CardValue value) {
        return cards.stream().filter(card -> card.getValue()== value).collect(Collectors.toList());
    }
    
    public static List<Card> getCardsOfValueLessThan(List<Card> cards, CardValue value) {
        return cards.stream().filter(card -> card.getValue().getValue() < value.getValue()).collect(Collectors.toList());
    }
    
    public static Card getRandomCard(List<Card> cards) {
        return cards.get(random.nextInt(cards.size()));
    }
    
    private static CardValue findLowestValue(List<Card> cards) {
        CardValue lowestValue = null;
        for (Card card : cards) {
            if (lowestValue == null) {
                lowestValue = card.getValue();
            } else if (card.getValue().getValue() < lowestValue.getValue()) {
                lowestValue = card.getValue();
            }
        }
        return lowestValue;
    }
    private static CardValue findHighestValue(List<Card> cards) {
        CardValue highestValue = null;
        for (Card card : cards) {
            if (highestValue == null) {
                highestValue = card.getValue();
            } else if (card.getValue().getValue() > highestValue.getValue()) {
                highestValue = card.getValue();
            }
        }
        return highestValue;
    }
    
    public static Card getLowestCardMatchingSuitHigherThan(Collection<Card> cards, Card card){
        final List<Card> eligibleCards = cards
                .stream()
                .filter(matchingCard -> matchingCard.getSuit() == card.getSuit())
                .filter(matchingCard -> matchingCard.getValue().getValue() > card.getValue().getValue())
                .collect(Collectors.toList());
        if (eligibleCards.isEmpty()) {
            return null;
        } else {
            return Collections.min(eligibleCards);
        }
    }
    public static Card getHighestCardMatchingSuitHigherThan(Collection<Card> cards, Card card){
        final List<Card> eligibleCards = cards
                .stream()
                .filter(matchingCard -> matchingCard.getSuit() == card.getSuit())
                .filter(matchingCard -> matchingCard.getValue().getValue() > card.getValue().getValue())
                .collect(Collectors.toList());
        if (eligibleCards.isEmpty()) {
            return null;
        } else {
            return Collections.max(eligibleCards);
        }
    }
    public static Card getHighestCardMatchingSuitLowerThan(Collection<Card> cards, Card card){
        final List<Card> eligibleCards = cards
                .stream()
                .filter(matchingCard -> matchingCard.getSuit() == card.getSuit())
                .filter(matchingCard -> matchingCard.getValue().getValue() < card.getValue().getValue())
                .collect(Collectors.toList());
        if (eligibleCards.isEmpty()) {
            return null;
        } else {
            return Collections.max(eligibleCards);
        }
    }
    
    public static Card getHighestCardOfSuit(Collection<Card> cards, Suit suit){
        final List<Card> eligibleCards = cards.stream().filter(card -> card.getSuit() == suit).collect(Collectors.toList());
        if (eligibleCards.isEmpty()) {
            return null;
        } 
        return Collections.max(eligibleCards);
    }
    
     public static Card getLowestCardOfSuit(List<Card> cards, Suit suit){
        final List<Card> eligibleCards = cards.stream().filter(card -> card.getSuit() == suit).collect(Collectors.toList());
        if (eligibleCards.isEmpty()) {
            return null;
        } 
        return Collections.min(eligibleCards);
    }

	public static List<Card> getMatchingSuitCards(List<Card> toMatch, List<Card> matchWith) {
		List<Card> matchingSuitCards = new ArrayList<>();
		for (Card card : toMatch) {
			if (matchWith.stream().anyMatch(m -> m.getSuit() == card.getSuit())) {
				matchingSuitCards.add(card);
			}
		}
		return matchingSuitCards;
	}
	
	public static List<Card> getStandaloneCards(List<Card> cards) {
		int numberOfSpades = 0;
		int numberOfDiamonds = 0;
		int numberOfClubs = 0;
		int numberOfHearts = 0;
		for (Card card : cards) {
			Suit suit = card.getSuit();
			switch(suit) {
			case CLUBS:
				numberOfClubs++;
				break;
			case DIAMONDS:
				numberOfDiamonds++;
				break;
			case HEARTS:
				numberOfHearts++;
				break;
			case SPADES:
				numberOfSpades++;
				break;
			default:
				break;
			}
		}
		
		List<Card> standaloneCards = new ArrayList<>();
		if (numberOfSpades == 1) {
			standaloneCards.add(getCardsOfSuit(cards, Suit.SPADES).get(0));
		}
		if (numberOfDiamonds == 1) {
			standaloneCards.add(getCardsOfSuit(cards, Suit.DIAMONDS).get(0));
		}
		if (numberOfClubs == 1) {
			standaloneCards.add(getCardsOfSuit(cards, Suit.CLUBS).get(0));
		}
		if (numberOfHearts == 1) {
			standaloneCards.add(getCardsOfSuit(cards, Suit.HEARTS).get(0));
		}
		
		return standaloneCards;
	}
	
}

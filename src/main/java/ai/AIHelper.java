package ai;

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
}

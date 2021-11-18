/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ai;

import com.mycompany.boerenbridge.Card;
import com.mycompany.boerenbridge.CardValue;
import com.mycompany.boerenbridge.Hand;
import com.mycompany.boerenbridge.RobotPlayer;
import com.mycompany.boerenbridge.Round;
import com.mycompany.boerenbridge.Suit;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

/**
 *
 * @author b.smeets
 */
public class MediumAI implements RobotAI {

    private final RobotPlayer robot;
    private Round round;

    public MediumAI(RobotPlayer robot) {
        this.robot = robot;
    }

    @Override
    public int guessSlagen(Round round) {
        this.round = round;
        return AIHelper.getAverageAmountOfSlagen(round, robot.getCards());
    }

    @Override
    public Card pickCard(Hand hand) {
        int guessed = round.getSlagenFor(robot);
        int current = round.getScoreFor(robot);
        
        if (hand.getFirstCard() == null) {
            return getStartingCard(guessed, current, hand);
        }
        
        if (guessed > current) {
            return getGoodCard(hand);
        } else {
            return getBadCard(hand);
        }
    }

    @Override
    public Suit maakTroef() {
        return AIHelper.findMostCommonSuit(robot.getCards());
    }

    private Card getGoodCard(Hand hand) {
        Set<Card> playedCards = hand.getPlayedCards();
        Card firstCard = hand.getFirstCard();
        
        Card cardToPlay;
        List<Card> legalCardsToPlay = AIHelper.getLegalCardsToPlay(robot.getCards(), firstCard.getSuit());
        if (AIHelper.containsCardOfSuit(legalCardsToPlay, round.getTroef())) {
            List<Card> troefCards = AIHelper.getCardsOfSuit(legalCardsToPlay, round.getTroef());
            Card currentlyWinningCard = hand.getCurrentlyWinningCard();
            Card lowestWinningTroef = AIHelper.getHighestCardMatchingSuitHigherThan(troefCards, currentlyWinningCard);
            if (lowestWinningTroef != null) {
                cardToPlay = lowestWinningTroef;
            } else {
                // cannot win, dump
                cardToPlay = getWorstCardToDump(legalCardsToPlay);
            }
        } else {
            if (AIHelper.containsCardOfSuit(playedCards, round.getTroef())) {
                // cannot win, dump
                cardToPlay = getWorstCardToDump(legalCardsToPlay);
            } else {
                Card winningNonTroefCard = AIHelper.getHighestCardOfSuit(playedCards, firstCard.getSuit());
                Card lowestWinningOwnCard = AIHelper.getHighestCardMatchingSuitHigherThan(playedCards, winningNonTroefCard);
                if (lowestWinningOwnCard != null) {
                // can still win, throw high card of matching first card suit
                    cardToPlay = lowestWinningOwnCard;
                } else {
                    cardToPlay = getWorstCardToDump(legalCardsToPlay);
                }
                
            }
        }
        return cardToPlay;
    }

    private Card getBadCard(Hand hand) {
        Card firstCard = hand.getFirstCard();
        List<Card> legalCardsToPlay = AIHelper.getLegalCardsToPlay(robot.getCards(), firstCard.getSuit());
        return getBestCardToDump(legalCardsToPlay, hand);
    }

    private Card getBestCardToDump(List<Card> cards, Hand hand) {
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
                    bestCard = getWorstCardToDump(cards);
                }
            } else {
                //we moeten bekennen, probeer onder de al gespeelde kaarten te blijven
                Set<Card> playedCards = hand.getPlayedCards();
                //zit er al een troef in de gespeelde kaarten?
                if (AIHelper.containsCardOfSuit(playedCards, round.getTroef())) {
                    //kunnen we een troef dumpen?
                    List<Card> troefs = AIHelper.getCardsOfSuit(playedCards, round.getTroef());
                    Card highestTroefPlayed = AIHelper.getHighestCardOfSuit(troefs, round.getTroef());
                    Card bestTroefToPlay = AIHelper.getHighestCardMatchingSuitLowerThan(cards, highestTroefPlayed);
                    if (bestTroefToPlay != null) {
                        bestCard = bestTroefToPlay;
                    } else {
                        bestCard = getWorstCardToDump(cards);
                    }
                } else {
                    bestCard = getHighestNonTroefCard(cards);
                }
            }
        }
        return bestCard;
    }

    public Card getHighestNonTroefCard(List<Card> cards) {
        //we hoeven niet te bekennen, pak de hoogst mogelijke niet-troef kaart
        List<Card> nonTroefCards = AIHelper.getCardsNotOfSuit(cards, round.getTroef());
        List<Card> highestCards = AIHelper.getHighestCards(nonTroefCards);
        return AIHelper.getRandomCard(highestCards);
    }
    
    private Card getWorstCardToDump(List<Card> cards) {
        List<Card> lowestCards = AIHelper.getLowestCards(cards);
        List<Card> lowestNonTroefCards = lowestCards.stream().filter(card -> card.getSuit() != round.getTroef()).collect(Collectors.toList());
        if (lowestNonTroefCards.isEmpty()) {
            return AIHelper.getRandomCard(lowestCards);
        }
        return AIHelper.getRandomCard(lowestNonTroefCards);
    }

    private Card getStartingCard(int guessed, int current, Hand hand) {
        Card startingCard;
        List<Card> cards = robot.getCards();
        if (guessed > current) {
            List<Card> aces = AIHelper.getCardsOfValue(cards, CardValue.ACE);
            if (aces.isEmpty()) {
                startingCard = getWorstCardToDump(cards);
            } else {
                startingCard = AIHelper.getRandomCard(aces);
            }
        } else {
            startingCard = getBestCardToDump(cards, hand);
        }
        return startingCard;
    }
    
}

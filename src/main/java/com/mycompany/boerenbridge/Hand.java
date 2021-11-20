/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.boerenbridge;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

/**
 *
 * @author b.smeets
 */
public class Hand {
    
    private final Suit troef;
    private Card firstCard;
    private final Map<Card, AbstractPlayer> playedCardsByPlayer = new LinkedHashMap<>();
    private boolean troefPlayed = false;
    private AbstractPlayer winningPlayer;
    private Card winningCard;
    private AbstractPlayer firstPlayer;
    private final List<AbstractPlayer> playersInOrder = new ArrayList<>();
    
    public Hand (Suit troef){
        this.troef = troef;
    }
    
    private void fillPlayers() {
        this.playersInOrder.add(this.firstPlayer);
        final AbstractPlayer secondPlayer = Game.getSingleton().getPlayerAfter(this.firstPlayer);
        this.playersInOrder.add(secondPlayer);
        final AbstractPlayer thirdPlayer = Game.getSingleton().getPlayerAfter(secondPlayer);
        this.playersInOrder.add(thirdPlayer);
        final AbstractPlayer lastPlayer = Game.getSingleton().getPlayerAfter(thirdPlayer);
        this.playersInOrder.add(lastPlayer);
    }
    
    public boolean playCard(Card card, AbstractPlayer player) {
        if (isLegal(card, player) && firstPlayer != null) {
            if (this.firstCard == null) {
                this.firstCard = card;
            }
            playedCardsByPlayer.put(card, player);

            if (card.getSuit() == troef) {
                troefPlayed = true;
            }

            if (playedCardsByPlayer.size() == Game.NUMBER_OF_PLAYERS) {
                winningPlayer = determineWinningPlayer();
            }
            player.removeCard(card);
            return true;
        }
        return false;
    }
    
    public int getNumberOfPlayedCards(){
        return this.playedCardsByPlayer.size();
    }
    
    public Set<Card> getPlayedCards(){
        return this.playedCardsByPlayer.keySet();
    }
    
    public Card getCurrentlyWinningCard(){
        if (this.firstCard == null) {
            return null;
        }
        return determineCurrentlyWinningCard();
    }
    
    public boolean isLegal(Card card, AbstractPlayer player) {
        if (this.firstCard == null) {
            return true;
        } else {
            if (firstCard.getSuit() == card.getSuit()) {
                return true;
            } else {
                List<Card> cards = player.getCards();
                return cards.stream().noneMatch(c -> c.getSuit() == firstCard.getSuit());
            }
        }
    }

    private AbstractPlayer determineWinningPlayer() {
        this.winningCard = determineCurrentlyWinningCard();
        return playedCardsByPlayer.get(winningCard);
    }

    private Card determineCurrentlyWinningCard() {
        Card highestCard;
        if (troefPlayed) {
            highestCard = getHighestCardOfSuit(troef);
        } else {
            highestCard = getHighestCardOfSuit(firstCard.getSuit());
        }
        return highestCard;
    }

    private Card getHighestCardOfSuit(Suit suit) {
        int highestValue = -1;
        Card highestCard = null;
        for (Card card : playedCardsByPlayer.keySet()) {
            if (card.getSuit() == suit && card.getValue().getValue() > highestValue) {
                highestCard = card;
                highestValue = card.getValue().getValue();
            }
        }
        return highestCard;
    }
    
    public AbstractPlayer getWinningPlayer(){
        return this.winningPlayer;
    }
    
    public Card getWinningCard() {
        return this.winningCard;
    }
    
    public Card getFirstCard(){
        return this.firstCard;
    }

    public void setFirstPlayer(AbstractPlayer firstPlayer) {
        this.firstPlayer = firstPlayer;
        fillPlayers();
    }
    
    public List<RobotPlayer> getRobotsBeforePlayer(){
        List<RobotPlayer> robotsBefore = new ArrayList<>();
        for (AbstractPlayer player : playersInOrder) {
            if (player instanceof RobotPlayer) { 
            	RobotPlayer robot = (RobotPlayer)player;
                robotsBefore.add(robot);
            } else {
                break;
            }
        }
        return robotsBefore;
    }
    
    public List<RobotPlayer> getRobotsAfterPlayer(){
        boolean realPlayerFound = false;
        List<RobotPlayer> robotsAfter = new ArrayList<>();
        for (AbstractPlayer player : playersInOrder) {
            if (realPlayerFound && player instanceof RobotPlayer) { 
            	RobotPlayer robot = (RobotPlayer)player;
                robotsAfter.add(robot);
            } else if (player instanceof RealPlayer) {
                realPlayerFound = true;
            }
        }
        return robotsAfter;
    }
    
    public List<AbstractPlayer> getPlayersInOrder(){
        return this.playersInOrder;
    }
    
    @Override
    public String toString() {
    	return this.playedCardsByPlayer.keySet().stream().map(Card::toString).collect(Collectors.joining("-"));
    }
}

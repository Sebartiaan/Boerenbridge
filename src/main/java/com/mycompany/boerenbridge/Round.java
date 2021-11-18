/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.boerenbridge;

import com.mycompany.boerenbridge.screens.RoundScreen;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Objects;
import java.util.Optional;
import java.util.Random;
import java.util.Set;
import java.util.stream.Collectors;
import javax.swing.JFrame;
import javax.swing.JOptionPane;

/**
 *
 * @author b.smeets
 */
public class Round {
    
    private final int numberOfCards;
    private final int roundNumber;
    private final AbstractPlayer firstPlayer;
    private final AbstractPlayer lastPlayer;
    protected final Map<AbstractPlayer, Integer> playersWithSlagen = new LinkedHashMap<>();
    private final Map<AbstractPlayer, Integer> playersScore = new LinkedHashMap<>();
    private RoundScreen rondeScreen;
    private Suit troef;
    protected Hand currentHand;
    private int handCounter = 0;
    private static Deck deck;

    public Round(int numberOfCards, int roundNumber, AbstractPlayer firstPlayer) {
        this.numberOfCards = numberOfCards;
        this.roundNumber = roundNumber;
        this.firstPlayer = firstPlayer;
        this.lastPlayer = Game.getSingleton().getPlayerBefore(firstPlayer);
        fillPlayers();
    }

    private void fillPlayers() {
        this.playersWithSlagen.put(this.firstPlayer, null);
        final AbstractPlayer secondPlayer = Game.getSingleton().getPlayerAfter(this.firstPlayer);
        this.playersWithSlagen.put(secondPlayer, null);
        final AbstractPlayer thirdPlayer = Game.getSingleton().getPlayerAfter(secondPlayer);
        this.playersWithSlagen.put(thirdPlayer, null);
        this.playersWithSlagen.put(lastPlayer, null);
    }

    public int getNumberOfCards() {
        return numberOfCards;
    }

    public int getRoundNumber() {
        return roundNumber;
    }

    public void showRondeScreen() {
        rondeScreen = new RoundScreen(this);
        rondeScreen.setExtendedState(JFrame.MAXIMIZED_BOTH); 
        rondeScreen.setVisible(true);
        rondeScreen.startRonde();
    }
    
    public RoundScreen getRondeScreen(){
        return this.rondeScreen;
    }
    
    public AbstractPlayer getFirstPlayer(){
        return this.firstPlayer;
    }
    
    public AbstractPlayer getLastPlayer() {
        return this.lastPlayer;
    }
    
    public Set<AbstractPlayer> getPlayersInStartingOrder(){
        return this.playersWithSlagen.keySet();
    }
    
    public List<RobotPlayer> getRobotsBeforePlayer(){
        List<RobotPlayer> robotsBefore = new ArrayList<>();
        for (AbstractPlayer player : playersWithSlagen.keySet()) {
            if (player instanceof RobotPlayer robot) { 
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
        for (AbstractPlayer player : playersWithSlagen.keySet()) {
            if (realPlayerFound && player instanceof RobotPlayer robot) { 
                robotsAfter.add(robot);
            } else if (player instanceof RealPlayer){
                realPlayerFound = true;
            }
        }
        return robotsAfter;
    }
    
    public int getNotAllowedGuess(){
        if (this.playersWithSlagen.values().stream().filter(Objects::nonNull).filter(value -> value == -1).count() == 0) {
            Integer totalAlreadyGuessed = this.playersWithSlagen.values().stream().filter(Objects::nonNull).reduce(0, Integer::sum);
            final int delta = getNumberOfCards() - totalAlreadyGuessed;
            return delta > -1 ? delta : -1;
        }
        return -1;
    }
    
    public void setSlagenFor(AbstractPlayer player, int numberOfSlagen) {
        this.playersWithSlagen.put(player, numberOfSlagen);
        if (this.playersWithSlagen.values().stream().noneMatch(Objects::isNull)) {
            determineTroef();
        }
    }
    
    public int getSlagenFor(AbstractPlayer player){
        return this.playersWithSlagen.get(player);
    }

    protected void determineTroef() {
        Optional<Integer> optional = playersWithSlagen.values().stream().max(Integer::compare);
        if (optional.isPresent()) {
            int mostGuesses = optional.get();
            List<AbstractPlayer> troefCompeters =  playersWithSlagen.entrySet().stream().filter(entry -> entry.getValue() == mostGuesses).map(Entry::getKey).collect(Collectors.toList());
            AbstractPlayer troefMaker = determineTroefMaker(troefCompeters, mostGuesses);
            if (troefMaker instanceof RobotPlayer robot) {
                final Suit robotTroef = robot.maakTroef();
                setTroef(robotTroef);
                JOptionPane.showMessageDialog(rondeScreen, robot.getName() + " maakt " + robotTroef.getNlNaam().toLowerCase() + " troef!");
            } else {
                rondeScreen.createTroefChooser();
            }
           
        } else {
            throw new IllegalStateException("Something went wrong while determining troef");
        }
    }

    
    public void increaseScoreFor(AbstractPlayer winner) {
        if (playersScore.isEmpty()) {
            Game.getSingleton().getPlayers().forEach(player -> playersScore.put(player, 0));
        }
        Integer score = playersScore.get(winner);
        playersScore.put(winner, ++score);
        winner.increaseScore(1);
    }
    
    public int getScoreFor(AbstractPlayer player){
        final Integer score = this.playersScore.get(player);
        if (score == null) {
            return 0;
        }
        return score;
    }
    
    public Map<AbstractPlayer, Integer> getScores(){
        return this.playersScore;
    }
    
    public Map<AbstractPlayer, Integer> getSlagen(){
        return this.playersWithSlagen;
    }

    protected AbstractPlayer determineTroefMaker(List<AbstractPlayer> troefCompeters, int mostGuesses) {
        if (troefCompeters.size() == 1) {
            return troefCompeters.get(0);
        } else {
            Random random = new Random();
            int randomPlayerIndex = random.nextInt(0, troefCompeters.size());
            AbstractPlayer troefMaker = troefCompeters.get(randomPlayerIndex);
            StringBuilder message = new StringBuilder();
            message.append("De volgende spelers hebben ");
            message.append(mostGuesses);
            message.append(" slagen geraden:\n");
            String competerNames = troefCompeters.stream().map(AbstractPlayer::getName).collect(Collectors.joining(", "));
            message.append(competerNames);
            message.append("\nNa loting is bepaald dat ");
            message.append(troefMaker.getName());
            message.append(" troef mag maken");
            JOptionPane.showMessageDialog(rondeScreen, message.toString());
            return troefMaker;
        }
    }

    public void setTroef(Suit troef) {
        if (this.troef == null) {
            this.troef = troef;
        }
    }
    
    public Suit getTroef(){
        return this.troef;
    }

    public boolean hasNextHand(){
        return handCounter < numberOfCards;
    }
    
    public Hand getNextHand(){
        if (troef != null) {
            handCounter++;
            this.currentHand = new Hand(troef);
            return currentHand;
        } else {
            throw new IllegalStateException("No troef set yet");
        }
    }
    
    public Hand getCurrentHand(){
        return this.currentHand;
    }

    public void dealCards() {
        deck = new Deck();
        deck.shuffle();
        for (AbstractPlayer player : Game.getSingleton().getPlayers()) {
            player.setCards(deck.drawCards(getNumberOfCards()));
        }
    }
    
}

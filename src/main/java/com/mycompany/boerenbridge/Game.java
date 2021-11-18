/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.boerenbridge;

import ai.AIDifficulty;
import ai.RobotAI;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Deque;
import java.util.List;

/**
 *
 * @author b.smeets
 */
public class Game {
    
    public static final int NUMBER_OF_PLAYERS = 4;
    
    Deque<Round> rounds = new ArrayDeque<>();
    
    private final List<AbstractPlayer> players = new ArrayList<>();
    private static Game singletonGame;
    private AIDifficulty aiDifficulty;

    private Game() {
        
    }
    
    public static Game getSingleton(){
        if (singletonGame == null) {
            singletonGame = new Game();
        }
        return singletonGame;
    }

    private void createRounds() {
        int numberOfCardsCounter = 13;
        int firstPlayerCounter = 0;
        boolean reached2 = false;
        for (int i = 0 ; i<23 ; i++) {
            final Round round = new Round(numberOfCardsCounter, i+1, getPlayers().get(firstPlayerCounter));
            rounds.add(round);

            firstPlayerCounter++;
            if (firstPlayerCounter > getPlayers().size()-1) {
                firstPlayerCounter=0;
            }
            if (!reached2) {
                numberOfCardsCounter--;
            } else {
                numberOfCardsCounter++;
            }
            if (numberOfCardsCounter == 2) {
                reached2 = true;
            }
        }
    }
    
    public void setRounds(List<Round> rounds) {
        this.rounds.clear();
        this.rounds.addAll(rounds);
    }

    public void start() {
        if (aiDifficulty == null) {
            throw new IllegalStateException("No AI selected");
        }
        if (players.isEmpty()) {
            throw new IllegalStateException("First call createPlayer() before starting the game");
        }
        createRobotPlayers(3);
        createRounds();
        getNextRound().showRondeScreen();
    }
    
    public RealPlayer getRealPlayer(){
        return players.stream()
                .filter(RealPlayer.class::isInstance)
                .map(RealPlayer.class::cast)
                .findFirst().get();
    }
    
    public List<AbstractPlayer> getPlayers(){
        return this.players;
    }
    
    public Round getNextRound(){
        if (rounds.isEmpty()) {
            return null;
        } else {
            return rounds.pollFirst();
        }
    }
    
    public AbstractPlayer getPlayerAfter(AbstractPlayer player) {
        int indexOfPlayer = this.players.indexOf(player);
        if (++indexOfPlayer > this.players.size()-1) {
            return this.players.get(0);
        }
        return this.players.get(indexOfPlayer);
    }
    
    public AbstractPlayer getPlayerBefore(AbstractPlayer player) {
        int indexOfPlayer = this.players.indexOf(player);
        if (--indexOfPlayer < 0) {
            return this.players.get(this.players.size()-1);
        }
        return this.players.get(indexOfPlayer);
    }

    public void createRobotPlayers(int numberOfRobotPlayers) {
        for (int i = 1 ; i<numberOfRobotPlayers+1 ; i++) {
            players.add(new RobotPlayer("Robot " + i, Position.values()[i-1]));
        }
    }

    public void createPlayer(String playerName) {
        players.add(new RealPlayer(playerName, Position.BOTTOM));
    }

    public void setAIDifficulty(AIDifficulty aiDifficulty) {
        this.aiDifficulty = aiDifficulty;
    }
    
    public AIDifficulty getAIDifficulty(){
        return this.aiDifficulty;
    }
}

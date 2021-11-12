/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.boerenbridge;

import com.mycompany.boerenbridge.screens.EndScreen;
import com.mycompany.boerenbridge.screens.StartRondeScreen;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Deque;
import java.util.List;
import java.util.function.Predicate;
import javax.swing.JFrame;

/**
 *
 * @author b.smeets
 */
public class Game {
    
    public static final int NUMBER_OF_PLAYERS = 4;
    
    Deque<Round> rounds = new ArrayDeque<>();
    
    private final List<AbstractPlayer> players = new ArrayList<>();
    private static Game singletonGame;

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

    public void start() {
        if (players.isEmpty()) {
            throw new IllegalStateException("First call createPlayer() before starting the game");
        }
        createRobotPlayers();
        createRounds();
        final StartRondeScreen startRondeScreen = new StartRondeScreen();
        startRondeScreen.setExtendedState(JFrame.MAXIMIZED_BOTH); 
        startRondeScreen.setVisible(true);
    }
    
    public RealPlayer getRealPlayer(){
        return (RealPlayer)players.stream().filter(Predicate.not(AbstractPlayer::isRobotPlayer)).findFirst().get();
    }
    
    public List<AbstractPlayer> getPlayers(){
        return this.players;
    }
    
    public Round getNextRound(){
        if (rounds.isEmpty()) {
            new EndScreen().setVisible(true);
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

    private void createRobotPlayers() {
        for (int i = 1 ; i<4 ; i++) {
            players.add(new RobotPlayer("Robot " + i, Position.values()[i-1]));
        }
    }

    public void createPlayer(String playerName) {
        players.add(new RealPlayer(playerName, Position.BOTTOM));
    }
}

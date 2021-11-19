/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ai;

import com.mycompany.boerenbridge.AbstractPlayer;
import com.mycompany.boerenbridge.Card;
import com.mycompany.boerenbridge.Game;
import com.mycompany.boerenbridge.RobotPlayer;
import com.mycompany.boerenbridge.Round;
import com.mycompany.boerenbridge.Suit;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Random;
import java.util.Set;
import java.util.stream.Collectors;

/**
 *
 * @author b.smeets
 */
public class AiRound extends Round {
    
    public AiRound(int numberOfCards, int roundNumber, AbstractPlayer firstPlayer) {
        super(numberOfCards, roundNumber, firstPlayer);
    }
    
    public void startRoundUnitTest(){
         getPlayersInStartingOrder().forEach(AbstractPlayer::reset);
         dealCards();
         doRobotSlagenGuesses(getPlayersInStartingOrder());
         determineTroef();
         
         if (getNumberOfCards() == playersWithSlagen.values().stream().mapToInt(Integer::intValue).sum()) {
             throw new IllegalStateException("Aantal kaarten mag nooit gelijk zijn aan het totaal aantal gegokte slagen");
         }
         
        currentHand = getNextHand();
        currentHand.setFirstPlayer(getFirstPlayer());
        for (int i = 0 ; i<getNumberOfCards() ;i++) {
            playHand();
            AbstractPlayer winningPlayer = currentHand.getWinningPlayer();
            currentHand = getNextHand();
            currentHand.setFirstPlayer(winningPlayer);
            increaseScoreFor(winningPlayer);
        }
        
        end();
         
    }
    

    public void playHand() {
        Card firstCard = null;
        Card winningCard;
        for (AbstractPlayer player : currentHand.getPlayersInOrder()) {
            if (player instanceof RobotPlayer) {
            	RobotPlayer robot = (RobotPlayer)player;
                Card card;
                do {
                    card = robot.pickCard(currentHand);
                    if (player.equals(currentHand.getPlayersInOrder().get(0))) {
                       firstCard = card;
                    } 
                } while (!currentHand.playCard(card, robot));
                addCardSeen(card);
            }
        }
        winningCard = currentHand.getWinningCard();
        if (firstCard != null && winningCard.getSuit() != getTroef() && winningCard.getSuit() != firstCard.getSuit()) {
            throw new IllegalStateException("Winnende kaart kan alleen kleur hebben van troef of eerst gespeelde kaart");
        }
    }
    
    public void doRobotSlagenGuesses(Set<AbstractPlayer> robots ){
        for (AbstractPlayer robot : robots) {
            final int guess = ((RobotPlayer)robot).guessSlagen(this);
            setSlagenFor(robot, guess);
        }
    }

    @Override
    public void determineTroef() {
        Optional<Integer> optional = playersWithSlagen.values().stream().max(Integer::compare);
        if (optional.isPresent()) {
            int mostGuesses = optional.get();
            List<AbstractPlayer> troefCompeters =  playersWithSlagen.entrySet().stream().filter(entry -> entry.getValue() == mostGuesses).map(Map.Entry::getKey).collect(Collectors.toList());
            AbstractPlayer troefMaker = determineTroefMaker(troefCompeters, mostGuesses);
            if (troefMaker instanceof RobotPlayer) {
            	RobotPlayer robot = (RobotPlayer)troefMaker;
                final Suit robotTroef = robot.maakTroef();
                setTroef(robotTroef);
            }
           
        } else {
            throw new IllegalStateException("Something went wrong while determining troef");
        }
    }
    
    @Override
    protected AbstractPlayer determineTroefMaker(List<AbstractPlayer> troefCompeters, int mostGuesses) {
        if (troefCompeters.size() == 1) {
            return troefCompeters.get(0);
        } else {
            Random random = new Random();
            int randomPlayerIndex = random.nextInt(0, troefCompeters.size());
            AbstractPlayer troefMaker = troefCompeters.get(randomPlayerIndex);
            return troefMaker;
        }
    }
    
    
    
}

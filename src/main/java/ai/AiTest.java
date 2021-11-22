/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ai;

import com.mycompany.boerenbridge.AbstractPlayer;
import com.mycompany.boerenbridge.Game;
import com.mycompany.boerenbridge.Position;
import com.mycompany.boerenbridge.RobotPlayer;
import com.mycompany.boerenbridge.Round;
import java.util.ArrayList;
import java.util.List;


/**
 *
 * @author b.smeets
 */
public class AiTest {
    
    private static AiRound nextRound;
    public static final int aantal = 6000;
    
   public static void main(String[] args) {
       
        Game game = Game.getSingleton();
        game.setAIDifficulty(AIDifficulty.HARDER);
        List<Integer> dataset = new ArrayList<>();
//        List<Integer> easy = new ArrayList<>();
//        List<Integer> hard = new ArrayList<>();
        for (int i = 0 ; i<aantal ; i++) {
            game.getPlayers().clear();
            game.createRobotPlayers(4);
            List<AbstractPlayer> players2 = game.getPlayers();
//            ((RobotPlayer)players2.get(0)).setAIDifficulty(AIDifficulty.HARDER);
//            ((RobotPlayer)players2.get(1)).setAIDifficulty(AIDifficulty.HARD);
//            ((RobotPlayer)players2.get(2)).setAIDifficulty(AIDifficulty.HARD);
//            ((RobotPlayer)players2.get(3)).setAIDifficulty(AIDifficulty.HARDER);
//            
            createAIRounds(game);
            nextRound = (AiRound) game.getNextRound();
            do {
                nextRound.startRoundUnitTest();
//                System.out.println("Cards:  " + nextRound.getNumberOfCards() + "   Gegokt: " + nextRound.getSlagen().values().stream().mapToInt(Integer::valueOf).sum());
                nextRound = (AiRound) game.getNextRound();
            } while (nextRound != null);
            
            
            List<AbstractPlayer> players = game.getPlayers();
            for (AbstractPlayer player : players) {
                dataset.add(player.getScore());
//                if (player.getPosition() == Position.BOTTOM || player.getPosition() == Position.LEFT) {
//                	easy.add(player.getScore());
//                } else {
//                	hard.add(player.getScore());
//                }
           }
            
            
        }
        
        int sum = dataset.stream().mapToInt(Integer::intValue).sum();
        int mean = sum/dataset.size();
//        int sum2 = hard.stream().mapToInt(Integer::intValue).sum();
//        int mean2 = sum2/hard.size();
        
        int sumOfAllFears = dataset.stream().mapToInt(value -> (value - mean)*(value - mean)).sum();
        
        double stdev = Math.sqrt(sumOfAllFears/dataset.size());
        
        System.out.println("Mean: " + mean + ", stdev: " + stdev );
//        System.out.println("Mean: " + mean2 + ", stdev: " );
    }
    public static void createAIRounds(Game game) {
        int numberOfCardsCounter = 13;
        int firstPlayerCounter = 0;
        boolean reached2 = false;
        List<Round> rounds = new ArrayList<>();
        for (int i = 0 ; i<23 ; i++) {
            final AiRound round = new AiRound(numberOfCardsCounter, i+1, game.getPlayers().get(firstPlayerCounter));
            rounds.add(round);

            firstPlayerCounter++;
            if (firstPlayerCounter > game.getPlayers().size()-1) {
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
        game.setRounds(rounds);
    }

    
}



/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ai;

import ai.*;
import com.mycompany.boerenbridge.AbstractPlayer;
import com.mycompany.boerenbridge.Game;
import com.mycompany.boerenbridge.Round;
import java.util.ArrayList;
import java.util.List;


/**
 *
 * @author b.smeets
 */
public class AiTest {
    
    private static AiRound nextRound;
    public static final int aantal = 3;
    
   public static void main(String[] args) {
       
        Game game = Game.getSingleton();
        int totalscore = 0;
        for (int i = 0 ; i<aantal ; i++) {
            game.getPlayers().clear();
            game.createRobotPlayers(4);
            createAIRounds(game);
            nextRound = (AiRound) game.getNextRound();
            do {
                nextRound.startRoundUnitTest();
                nextRound = (AiRound) game.getNextRound();
            } while (nextRound != null);
            List<AbstractPlayer> players = game.getPlayers();
            for (AbstractPlayer player : players) {
                totalscore = totalscore + player.getScore();
           }
        }
        System.out.println("Average = " + totalscore/(aantal*4));
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



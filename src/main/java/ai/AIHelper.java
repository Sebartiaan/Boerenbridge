/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ai;

import com.mycompany.boerenbridge.Card;
import com.mycompany.boerenbridge.Game;
import com.mycompany.boerenbridge.Round;
import com.mycompany.boerenbridge.Suit;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 *
 * @author b.smeets
 */
public class AIHelper {
    
    private AIHelper(){
        
    }
    
    public static int getAverageAmountOfSlagen(Round round, List<Card> cards) {
        int notAllowedGuess = -1;
        if (round.getNotAllowedGuess() != -1) {
            notAllowedGuess = round.getNotAllowedGuess();
        }
        
        int guess;
        int numberOfCards = cards.size();
        int averageSlagen = numberOfCards/Game.NUMBER_OF_PLAYERS;
        if (numberOfCards%Game.NUMBER_OF_PLAYERS == 0) {
            guess = averageSlagen;
        } else {
            Random random = new Random();
            guess = random.nextInt(averageSlagen, averageSlagen +2);
        }
        if (notAllowedGuess == guess) {
            Random random = new Random();
            if (random.nextBoolean()) {
                return guess+1;
            } else {    
                return guess-1 < 0 ? guess+1 : guess-1;
            }    
        } else {
            return guess;
        }
    }
    
    public static Suit findMostCommonSuit(List<Card> cards) {
        return cards.stream() // part 1
            .collect(Collectors.groupingBy(Function.identity(), Collectors.counting()))
            .entrySet().stream() // part 2
            .collect(Collectors.groupingBy(Map.Entry::getValue))
            .entrySet().stream() // part 3
            .max(Map.Entry.comparingByKey())
            .map(Map.Entry::getValue)
            .map(v -> v.get(0).getKey().getSuit()).get();
    }
    
}

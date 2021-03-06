/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package ai;

import com.mycompany.boerenbridge.Card;
import com.mycompany.boerenbridge.Hand;
import com.mycompany.boerenbridge.Round;
import com.mycompany.boerenbridge.Suit;

/**
 *
 * @author b.smeets
 */
public interface RobotAI {
    
    /**
     * Should return a legal amount of slagen for the current round
     * @return 
     */
    int guessSlagen();
    
    /**
     * Should return the card to be played. Can be random, but if it always determines the same card, it should be a legal one
     * @return 
     */
    Card pickCard(Hand hand);
    
    /**
     * Return the troef for the current round. No restrictions 
     */
    Suit maakTroef();
    
    /**
     * Sets the current round
     * @param round
     */
    void setRound(Round round);
    
}

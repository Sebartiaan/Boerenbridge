/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package com.mycompany.boerenbridge;

import com.mycompany.boerenbridge.screens.RondeScreen;
import com.mycompany.boerenbridge.screens.StartScreen;
import javax.swing.JFrame;

/**
 *
 * @author b.smeets
 */
public class NewMain {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        final StartScreen startScreen = new StartScreen();
        startScreen.setExtendedState(JFrame.MAXIMIZED_BOTH); 
        startScreen.setVisible(true);
    }
    
}

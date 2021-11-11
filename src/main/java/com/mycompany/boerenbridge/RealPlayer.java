/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.boerenbridge;

/**
 *
 * @author b.smeets
 */
public class RealPlayer extends AbstractPlayer {

    public RealPlayer(String name) {
        super(name);
    }

    @Override
    public boolean isRobotPlayer() {
        return false;
    }
    
}

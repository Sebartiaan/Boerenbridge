/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package com.mycompany.boerenbridge.screens;

import com.mycompany.boerenbridge.AbstractPlayer;
import com.mycompany.boerenbridge.Card;
import com.mycompany.boerenbridge.Deck;
import com.mycompany.boerenbridge.Game;
import com.mycompany.boerenbridge.Hand;
import com.mycompany.boerenbridge.RealPlayer;
import com.mycompany.boerenbridge.RobotPlayer;
import com.mycompany.boerenbridge.Round;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.ListIterator;
import java.util.Map.Entry;
import java.util.Set;
import java.util.regex.Pattern;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.Timer;
import javax.swing.WindowConstants;

/**
 *
 * @author b.smeets
 */
public class RondeScreen extends javax.swing.JFrame {
    
    LinkedHashMap<JButton, Card> cardButtons = new LinkedHashMap<>();
    private final Round round;
    private final Game game;
    private boolean userInputAllowed = false;
    private Hand currentHand;

    /**
     * Creates new form RondeScreen
     */
    public RondeScreen(Round round) {
        this.round = round;
        this.game = Game.getSingleton();
        initComponents();
    }

    public void startRonde() {
        fillNames();
        fillCardButtons();
        Deck deck = new Deck();
        deck.shuffle();
        for (AbstractPlayer player : game.getPlayers()) {
            player.setCards(deck.drawCards(round.getNumberOfCards()));
        }
        RealPlayer realPlayer = game.getRealPlayer();
        final List<Card> cardsOfPlayer = realPlayer.getCards();
        paintCards(cardsOfPlayer);
        
        doRobotSlagenGuesses(round.getRobotsBeforePlayer());
        doRealPlayerSlagenGuess();
        
        fillSlagen();
        
        currentHand = round.getNextHand();
        currentHand.setFirstPlayer(round.getFirstPlayer());
        startNextHand(currentHand);
    }

    public void doRealPlayerSlagenGuess() {
        AantalSlagenDialog aantalSlagenDialog = new AantalSlagenDialog(this, rootPaneCheckingEnabled, round);
        aantalSlagenDialog.setLocationRelativeTo(this);
        aantalSlagenDialog.setDefaultCloseOperation(WindowConstants.DO_NOTHING_ON_CLOSE);
        aantalSlagenDialog.setVisible(true);
    }
    

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        card1 = new javax.swing.JButton();
        card2 = new javax.swing.JButton();
        card3 = new javax.swing.JButton();
        card4 = new javax.swing.JButton();
        card5 = new javax.swing.JButton();
        card6 = new javax.swing.JButton();
        card7 = new javax.swing.JButton();
        card8 = new javax.swing.JButton();
        card9 = new javax.swing.JButton();
        card10 = new javax.swing.JButton();
        card11 = new javax.swing.JButton();
        card12 = new javax.swing.JButton();
        card13 = new javax.swing.JButton();
        notAllowedSlagenLabel = new javax.swing.JLabel();
        topCard = new javax.swing.JButton();
        rightCard = new javax.swing.JButton();
        leftCard = new javax.swing.JButton();
        bottomCard = new javax.swing.JButton();
        topPlayerInfo = new java.awt.List();
        leftPlayerInfo = new java.awt.List();
        bottomPlayerInfo = new java.awt.List();
        rightPlayerInfo = new java.awt.List();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        card1.setIconTextGap(0);
        card1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                card1ActionPerformed(evt);
            }
        });

        card2.setIconTextGap(0);
        card2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                card2ActionPerformed(evt);
            }
        });

        card3.setIconTextGap(0);
        card3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                card3ActionPerformed(evt);
            }
        });

        card4.setIconTextGap(0);
        card4.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                card4ActionPerformed(evt);
            }
        });

        card5.setIconTextGap(0);
        card5.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                card5ActionPerformed(evt);
            }
        });

        card6.setIconTextGap(0);
        card6.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                card6ActionPerformed(evt);
            }
        });

        card7.setIconTextGap(0);
        card7.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                card7ActionPerformed(evt);
            }
        });

        card8.setIconTextGap(0);
        card8.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                card8ActionPerformed(evt);
            }
        });

        card9.setIconTextGap(0);
        card9.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                card9ActionPerformed(evt);
            }
        });

        card10.setIconTextGap(0);
        card10.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                card10ActionPerformed(evt);
            }
        });

        card11.setIconTextGap(0);
        card11.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                card11ActionPerformed(evt);
            }
        });

        card12.setIconTextGap(0);
        card12.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                card12ActionPerformed(evt);
            }
        });

        card13.setIconTextGap(0);
        card13.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                card13ActionPerformed(evt);
            }
        });

        topCard.setIconTextGap(0);

        rightCard.setIconTextGap(0);
        rightCard.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                rightCardActionPerformed(evt);
            }
        });

        leftCard.setIconTextGap(0);

        bottomCard.setIconTextGap(0);

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(649, 649, 649)
                        .addComponent(notAllowedSlagenLabel)
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(topCard, javax.swing.GroupLayout.PREFERRED_SIZE, 73, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(bottomCard, javax.swing.GroupLayout.PREFERRED_SIZE, 73, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(topPlayerInfo, javax.swing.GroupLayout.PREFERRED_SIZE, 63, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(0, 5, Short.MAX_VALUE)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addComponent(leftPlayerInfo, javax.swing.GroupLayout.PREFERRED_SIZE, 63, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(leftCard, javax.swing.GroupLayout.PREFERRED_SIZE, 73, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(84, 84, 84)
                                .addComponent(rightCard, javax.swing.GroupLayout.PREFERRED_SIZE, 73, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addComponent(card1, javax.swing.GroupLayout.PREFERRED_SIZE, 73, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(card2, javax.swing.GroupLayout.PREFERRED_SIZE, 73, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(card3, javax.swing.GroupLayout.PREFERRED_SIZE, 73, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(card4, javax.swing.GroupLayout.PREFERRED_SIZE, 73, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(card5, javax.swing.GroupLayout.PREFERRED_SIZE, 73, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(card6, javax.swing.GroupLayout.PREFERRED_SIZE, 73, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(bottomPlayerInfo, javax.swing.GroupLayout.PREFERRED_SIZE, 63, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addGroup(jPanel1Layout.createSequentialGroup()
                                        .addComponent(card7, javax.swing.GroupLayout.PREFERRED_SIZE, 73, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(card8, javax.swing.GroupLayout.PREFERRED_SIZE, 73, javax.swing.GroupLayout.PREFERRED_SIZE)))))
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(card9, javax.swing.GroupLayout.PREFERRED_SIZE, 73, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(card10, javax.swing.GroupLayout.PREFERRED_SIZE, 73, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(card11, javax.swing.GroupLayout.PREFERRED_SIZE, 73, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(card12, javax.swing.GroupLayout.PREFERRED_SIZE, 73, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(card13, javax.swing.GroupLayout.PREFERRED_SIZE, 73, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 337, Short.MAX_VALUE)
                                .addComponent(rightPlayerInfo, javax.swing.GroupLayout.PREFERRED_SIZE, 63, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(topPlayerInfo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 78, Short.MAX_VALUE)
                .addComponent(topCard, javax.swing.GroupLayout.PREFERRED_SIZE, 97, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(14, 14, 14)
                        .addComponent(rightCard, javax.swing.GroupLayout.PREFERRED_SIZE, 97, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(leftCard, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 97, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(leftPlayerInfo, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(rightPlayerInfo, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(bottomCard, javax.swing.GroupLayout.PREFERRED_SIZE, 97, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 48, Short.MAX_VALUE)
                .addComponent(notAllowedSlagenLabel)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 24, Short.MAX_VALUE)
                .addComponent(bottomPlayerInfo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(card13, javax.swing.GroupLayout.PREFERRED_SIZE, 97, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(card12, javax.swing.GroupLayout.PREFERRED_SIZE, 97, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(card11, javax.swing.GroupLayout.PREFERRED_SIZE, 97, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(card10, javax.swing.GroupLayout.PREFERRED_SIZE, 97, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(card9, javax.swing.GroupLayout.PREFERRED_SIZE, 97, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(card8, javax.swing.GroupLayout.PREFERRED_SIZE, 97, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(card7, javax.swing.GroupLayout.PREFERRED_SIZE, 97, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(card6, javax.swing.GroupLayout.PREFERRED_SIZE, 97, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(card5, javax.swing.GroupLayout.PREFERRED_SIZE, 97, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(card4, javax.swing.GroupLayout.PREFERRED_SIZE, 97, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(card3, javax.swing.GroupLayout.PREFERRED_SIZE, 97, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(card2, javax.swing.GroupLayout.PREFERRED_SIZE, 97, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(card1, javax.swing.GroupLayout.PREFERRED_SIZE, 97, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(20, 20, 20))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap(157, Short.MAX_VALUE)
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(159, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap(100, Short.MAX_VALUE)
                .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap(89, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void rightCardActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_rightCardActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_rightCardActionPerformed

    private void card1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_card1ActionPerformed
        realPlayerPicksCard(card1);
    }//GEN-LAST:event_card1ActionPerformed

    private void card2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_card2ActionPerformed
         realPlayerPicksCard(card2);
    }//GEN-LAST:event_card2ActionPerformed

    private void card3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_card3ActionPerformed
         realPlayerPicksCard(card3);
    }//GEN-LAST:event_card3ActionPerformed

    private void card4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_card4ActionPerformed
         realPlayerPicksCard(card4);
    }//GEN-LAST:event_card4ActionPerformed

    private void card5ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_card5ActionPerformed
         realPlayerPicksCard(card5);
    }//GEN-LAST:event_card5ActionPerformed

    private void card6ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_card6ActionPerformed
         realPlayerPicksCard(card6);
    }//GEN-LAST:event_card6ActionPerformed

    private void card7ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_card7ActionPerformed
         realPlayerPicksCard(card7);
    }//GEN-LAST:event_card7ActionPerformed

    private void card8ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_card8ActionPerformed
         realPlayerPicksCard(card8);
    }//GEN-LAST:event_card8ActionPerformed

    private void card9ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_card9ActionPerformed
         realPlayerPicksCard(card9);
    }//GEN-LAST:event_card9ActionPerformed

    private void card10ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_card10ActionPerformed
         realPlayerPicksCard(card10);
    }//GEN-LAST:event_card10ActionPerformed

    private void card11ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_card11ActionPerformed
         realPlayerPicksCard(card11);
    }//GEN-LAST:event_card11ActionPerformed

    private void card12ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_card12ActionPerformed
         realPlayerPicksCard(card12);
    }//GEN-LAST:event_card12ActionPerformed

    private void card13ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_card13ActionPerformed
         realPlayerPicksCard(card13);
    }//GEN-LAST:event_card13ActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton bottomCard;
    private java.awt.List bottomPlayerInfo;
    private javax.swing.JButton card1;
    private javax.swing.JButton card10;
    private javax.swing.JButton card11;
    private javax.swing.JButton card12;
    private javax.swing.JButton card13;
    private javax.swing.JButton card2;
    private javax.swing.JButton card3;
    private javax.swing.JButton card4;
    private javax.swing.JButton card5;
    private javax.swing.JButton card6;
    private javax.swing.JButton card7;
    private javax.swing.JButton card8;
    private javax.swing.JButton card9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JButton leftCard;
    private java.awt.List leftPlayerInfo;
    private javax.swing.JLabel notAllowedSlagenLabel;
    private javax.swing.JButton rightCard;
    private java.awt.List rightPlayerInfo;
    private javax.swing.JButton topCard;
    private java.awt.List topPlayerInfo;
    // End of variables declaration//GEN-END:variables

    private void fillCardButtons() {
        cardButtons.put(card1, null);
        cardButtons.put(card2, null);
        cardButtons.put(card3, null);
        cardButtons.put(card4, null);
        cardButtons.put(card5, null);
        cardButtons.put(card6, null);
        cardButtons.put(card7, null);
        cardButtons.put(card8, null);
        cardButtons.put(card9, null);
        cardButtons.put(card10, null);
        cardButtons.put(card11, null);
        cardButtons.put(card12, null);
        cardButtons.put(card13, null);
    }

    private void paintCards(List<Card> cardsOfPlayer) {
        int counter = 0;
        for (Card card : cardsOfPlayer) {
            JButton cardButton = getNthElement(cardButtons.keySet(), counter);
            cardButtons.put(cardButton, card);
            cardButton.setIcon(card.getImage());
            counter++;
        }
    }


    public void doRemainingRobotGuesses() {
        for (RobotPlayer robot : round.getRobotsAfterPlayer()) {
            final int guess = robot.guessSlagen(round);
            round.setSlagenFor(robot, guess);
        }
    }
    
    public void doRobotSlagenGuesses(List<RobotPlayer> robots ){
        for (RobotPlayer robot : robots) {
            final int guess = robot.guessSlagen(round);
            round.setSlagenFor(robot, guess);
        }
    }

    public void createTroefScreen() {
        final TroefDialog troefDialog = new TroefDialog(this.round, this, true);
        troefDialog.setLocationRelativeTo(this);
        troefDialog.setDefaultCloseOperation(WindowConstants.DO_NOTHING_ON_CLOSE);
        troefDialog.setVisible(true);
    }

    private void fillNames() {
        for (AbstractPlayer player : game.getPlayers()) {
            switch (player.getPosition()) {
                case LEFT:
                    leftPlayerInfo.add(player.getName());
                    break;
                case TOP:
                    topPlayerInfo.add(player.getName());
                    break;
                case RIGHT:
                    rightPlayerInfo.add(player.getName());
                    break;
                case BOTTOM:
                    bottomPlayerInfo.add(player.getName());
                    break;
                default:
                    throw new AssertionError(player.getPosition().name());
            }
        }
    }
    
    private void fillSlagen() {
        for (AbstractPlayer player : game.getPlayers()) {
            final String slagen = "Gegokt: " + String.valueOf(round.getSlagenFor(player));
            switch (player.getPosition()) {
                case LEFT:
                    leftPlayerInfo.add("");
                    leftPlayerInfo.add(slagen);
                    break;
                case TOP:
                    topPlayerInfo.add("");
                    topPlayerInfo.add(slagen);
                    break;
                case RIGHT:
                    rightPlayerInfo.add("");
                    rightPlayerInfo.add(slagen);
                    break;
                case BOTTOM:
                    bottomPlayerInfo.add("");
                    bottomPlayerInfo.add(slagen);
                    break;
                default:
                    throw new AssertionError(player.getPosition().name());
            }
        }
    }
    private void increaseScore(AbstractPlayer winner) {
        round.increaseScoreFor(winner);
        switch (winner.getPosition()) {
            case LEFT:
                increaseScore(leftPlayerInfo);
                break;
            case TOP:
                increaseScore(topPlayerInfo);
                break;
            case RIGHT:
                increaseScore(rightPlayerInfo);
                break;
            case BOTTOM:
                increaseScore(bottomPlayerInfo);
                break;
            default:
                throw new AssertionError("");
        }
    }

    public void increaseScore(java.awt.List list) throws NumberFormatException {
        if (list.getItemCount() == 4) {
            String item = list.getItem(3);
            list.remove(3);
            Pattern pattern = Pattern.compile("[^0-9]");
            int value = Integer.valueOf(pattern.matcher(item).replaceAll(""));
            list.add("Score: " + String.valueOf(++value),3);
        } else {
            list.add("Score: 1",3);
        }
    }

    private void startNextHand(Hand nextHand) {
        resetMiddleCards();
        List<RobotPlayer> robotsBeforePlayer = nextHand.getRobotsBeforePlayer();
        robotsPickCards(robotsBeforePlayer);
        userInputAllowed = true;
    }

    
    
    public void robotsPickCards(List<RobotPlayer> robots) {
        for (RobotPlayer robot : robots) {
            Card card;
            do {
                card = robot.pickCard();
            } while (!currentHand.playCard(card, robot));
            
            drawCard(card, robot);
        }
    }

    private void drawCard(Card card, AbstractPlayer player) {
        switch (player.getPosition()) {
            case LEFT:
                leftCard.setIcon(card.getImage());
                break;
            case TOP:
                topCard.setIcon(card.getImage());
                break;
            case RIGHT:
                rightCard.setIcon(card.getImage());
                break;
            case BOTTOM:
                bottomCard.setIcon(card.getImage());
                break;
            default:
                throw new AssertionError(player.getPosition().name());
        }
    }

    private JButton getNthElement(Set<JButton> buttons, int n) {
        int counter = 0;
        for (JButton button : buttons) {
            if (n == counter) {
                return button;
            }
            counter++;
        }
        return null;
    }

    private void realPlayerPicksCard(JButton cardButton) {
        if (userInputAllowed) {
            Card card = cardButtons.get(cardButton);
            if (card != null) {
                if (currentHand.playCard(card, game.getRealPlayer())) {
                    drawCard(card, game.getRealPlayer());
                    moveCardsToTheLeft(cardButton);
                    userInputAllowed = false;
                    final List<RobotPlayer> robotsAfterPlayer = currentHand.getRobotsAfterPlayer();
                    robotsPickCards(robotsAfterPlayer);
                    AbstractPlayer winningPlayer = currentHand.getWinningPlayer();
                    increaseScore(winningPlayer);
                    if (round.hasNextHand()) {
                        currentHand = round.getNextHand();
                        currentHand.setFirstPlayer(winningPlayer);
                        startNextHand(currentHand);
                    } else {
                        this.dispose();
                        handOutBonusesForGuessingRight();
                        createEndRondeScreen();
                    }
                } else {
                    Card firstCard = currentHand.getFirstCard();
                    JOptionPane.showMessageDialog(this, card.getHumanReadableString() + " mag niet gespeeld worden omdat je nog " + firstCard.getSuit().getNlNaam().toLowerCase() + " hebt");
                }
            }    
        }
    }

    public void createEndRondeScreen() {
        EndRondeScreen endRondeScreen = new EndRondeScreen(round.getScores(), round);
        endRondeScreen.setExtendedState(JFrame.MAXIMIZED_BOTH); 
        endRondeScreen.setVisible(true);
    }

    private void moveCardsToTheLeft(JButton pressedButton) {
        pressedButton.setIcon(null);
        cardButtons.put(pressedButton, null);
    }
    
    
    public static <K, V> LinkedHashMap<K, V> reverse(LinkedHashMap<K, V> map) {
        LinkedHashMap<K, V> reversedMap = new LinkedHashMap<K, V>();

        ListIterator<Entry<K, V>> it = new ArrayList<>(map.entrySet()).listIterator(map.entrySet().size());

        while (it.hasPrevious())
        {
            Entry<K, V> el = it.previous();
            reversedMap.put(el.getKey(), el.getValue());
        }

        return reversedMap;
    }

    private void resetMiddleCards() {
        if (leftCard.getIcon() != null) {
            leftCard.setIcon(null);
            rightCard.setIcon(null);
            bottomCard.setIcon(null);
            topCard.setIcon(null);
        }
    }

    private void handOutBonusesForGuessingRight() {
        for (AbstractPlayer player : game.getPlayers()) {
            int predictedSlagen = round.getSlagenFor(player);
            int actualSlagen = round.getScoreFor(player);
            if (predictedSlagen == actualSlagen) {
                player.increaseScore(10);
            }
        }
    }
}

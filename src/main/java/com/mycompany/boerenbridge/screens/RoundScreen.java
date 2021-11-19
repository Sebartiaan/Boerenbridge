/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package com.mycompany.boerenbridge.screens;

import com.mycompany.boerenbridge.AbstractPlayer;
import com.mycompany.boerenbridge.Card;
import com.mycompany.boerenbridge.Game;
import com.mycompany.boerenbridge.Hand;
import com.mycompany.boerenbridge.RealPlayer;
import com.mycompany.boerenbridge.RobotPlayer;
import com.mycompany.boerenbridge.Round;
import java.awt.Color;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.ListIterator;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Objects;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.SwingConstants;
import javax.swing.Timer;
import javax.swing.WindowConstants;

/**
 *
 * @author b.smeets
 */
public class RoundScreen extends javax.swing.JFrame {

    LinkedHashMap<JButton, Card> cardButtons = new LinkedHashMap<>();
    private final Round round;
    private final Game game;
    private boolean userInputAllowed = false;
    private Hand currentHand;
    private Timer t;
    private Timer t2;
    private ImageIcon pauseImage = new ImageIcon(getClass().getResource("/pause.png"));
    private ImageIcon playImage = new ImageIcon(getClass().getResource("/play.png"));
    
    /**
     * Creates new form RoundScreen
     */
    public RoundScreen(Round round) {
        this.round = round;
        this.game = Game.getSingleton();
        initComponents();
        resetMiddleCards();
        initPauserButton();
        initTroefViewer();
        setTitle("Ronde " + round.getRoundNumber() + ". Aantal kaarten: " + round.getNumberOfCards());
        setBackGroundColor();
    }

    public void setBackGroundColor() {
        Container contentPane = getContentPane();
        contentPane.setBackground(Color.white);
    }
    private void initPauserButton() {
        pauser.setPreferredSize(new Dimension(81, 60));
        pauser.setEnabled(false);
        pauser.setOpaque(false);
        pauser.setContentAreaFilled(false);
    }
    
    private void initTroefViewer() {
        troefViewer.setEnabled(false);
        troefViewer.setBorder(BorderFactory.createEmptyBorder());
    }
    
    public void startRonde() {
        fillNames();
        fillCardButtons();
        round.dealCards();
        RealPlayer realPlayer = game.getRealPlayer();
        final List<Card> cardsOfPlayer = realPlayer.getCards();
        paintCards(cardsOfPlayer);
        
        determineTroef();
        
        String currentTitle = getTitle();
        setTitle(currentTitle + ". " + round.getTroef().getNlNaam() + " is troef");
        setTroefIcon();
        
        currentHand = round.getNextHand();
        currentHand.setFirstPlayer(round.getFirstPlayer());
        startNextHand(currentHand);
    }

    public void determineTroef() {
        doRobotSlagenGuesses(round.getRobotsBeforePlayer());
        doRealPlayerSlagenGuess();
    }
    
     private void setTroefIcon() {
        troefViewer.setIcon(round.getTroef().getImage());
        pauser.setIcon(round.getTroef().getSmallImage());
        pauser.setHorizontalTextPosition(SwingConstants.CENTER);    
     }

    public void doRealPlayerSlagenGuess() {
        AantalSlagenDialog aantalSlagenDialog = new AantalSlagenDialog(this, rootPaneCheckingEnabled, round);
        aantalSlagenDialog.setLocationRelativeTo(this);
        aantalSlagenDialog.setDefaultCloseOperation(WindowConstants.DO_NOTHING_ON_CLOSE);
        aantalSlagenDialog.setVisible(true);
    }
    
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
        centerCards();
    }

    public void doRobotSlagenGuesses(List<RobotPlayer> robots ){
        for (RobotPlayer robot : robots) {
            final int guess = robot.guessSlagen(round);
            round.setSlagenFor(robot, guess);
            addPlayerInfo(robot);
        }
        if (round.getTroef() != null && round.getTroefMaker() instanceof RobotPlayer) {
            RobotPlayer robot = (RobotPlayer)round.getTroefMaker();
            JOptionPane.showMessageDialog(this, robot.getName() + " maakt " + round.getTroef().getNlNaam().toLowerCase() + " troef!");
        }
    }

    public void createTroefChooser() {
        final TroefChooser troefDialog = new TroefChooser(this.round, this, true);
        troefDialog.setLocationRelativeTo(this);
        troefDialog.setDefaultCloseOperation(WindowConstants.DO_NOTHING_ON_CLOSE);
        troefDialog.setVisible(true);
    }

    private void fillNames() {
        for (AbstractPlayer player : game.getPlayers()) {
            switch (player.getPosition()) {
                case LEFT -> leftPlayerInfo.add(player.getName());
                case TOP -> topPlayerInfo.add(player.getName());
                case RIGHT -> rightPlayerInfo.add(player.getName());
                case BOTTOM -> bottomPlayerInfo.add(player.getName());
                default -> throw new AssertionError(player.getPosition().name());
            }
        }
    }
    
    public void addPlayerInfo(AbstractPlayer player) {
        final String slagen = "Gegokt: " + String.valueOf(round.getSlagenFor(player));
        final String roundScore = "Slagen: " + String.valueOf(round.getScoreFor(player));
        final String totalScore = "Totaal: " + String.valueOf(player.getScore());
        switch (player.getPosition()) {
            case LEFT -> {
                leftPlayerInfo.add("");
                leftPlayerInfo.add(slagen);
                leftPlayerInfo.add(roundScore);
                leftPlayerInfo.add(totalScore);
            }
            case TOP -> {
                topPlayerInfo.add("");
                topPlayerInfo.add(slagen);
                topPlayerInfo.add(roundScore);
                topPlayerInfo.add(totalScore);
            }
            case RIGHT -> {
                rightPlayerInfo.add("");
                rightPlayerInfo.add(slagen);
                rightPlayerInfo.add(roundScore);
                rightPlayerInfo.add(totalScore);
            }
            case BOTTOM -> {
                bottomPlayerInfo.add("");
                bottomPlayerInfo.add(slagen);
                bottomPlayerInfo.add(roundScore);
                bottomPlayerInfo.add(totalScore);
            }
            default -> throw new AssertionError(player.getPosition().name());
        }
    }
    private void increaseScore(AbstractPlayer winner) {
        leftPlayerInfo.setBackground(Color.WHITE);
        topPlayerInfo.setBackground(Color.WHITE);
        rightPlayerInfo.setBackground(Color.WHITE);
        bottomPlayerInfo.setBackground(Color.WHITE);
        round.increaseScoreFor(winner);
        switch (winner.getPosition()) {
            case LEFT:
                increaseScore(leftPlayerInfo, winner);
                leftPlayerInfo.setBackground(Color.green);
                break;
            case TOP:
                increaseScore(topPlayerInfo, winner);
                topPlayerInfo.setBackground(Color.green);
                break;
            case RIGHT:
                increaseScore(rightPlayerInfo, winner);
                rightPlayerInfo.setBackground(Color.green);
                break;
            case BOTTOM:
                increaseScore(bottomPlayerInfo, winner);
                bottomPlayerInfo.setBackground(Color.green);
                break;
            default:
                throw new AssertionError("");
        }
    }
    

    public void increaseScore(java.awt.List list, AbstractPlayer winner) throws NumberFormatException {
        list.remove(3);
        list.add("Score: " + String.valueOf(round.getScoreFor(winner)),3);
        list.remove(4);
        list.add("Totaal: " + String.valueOf(winner.getScore()),4);
    }

    private void startNextHand(Hand nextHand) {
        resetMiddleCards();
        List<RobotPlayer> robotsBeforePlayer = nextHand.getRobotsBeforePlayer();
        robotsPickCards(robotsBeforePlayer);
    }
    
    
    public void robotsPickCards(List<RobotPlayer> robots) {
        if (!robots.isEmpty()) {
            userInputAllowed = false;
            t = new Timer(500,null);
            t.addActionListener(new ActionListener(){
                 int i=0;
                 public void actionPerformed(ActionEvent e){
                    Card card;
                    RobotPlayer robot;
                    do {
                        robot = robots.get(i);
                        card = robot.pickCard(currentHand);
                    } while (!currentHand.playCard(card, robot));
                    drawCard(card, robot);
                    round.addCardSeen(card);

                    if(robots.size() == i+1){
                        t.stop();
                        userInputAllowed = true;
                        if (currentHand.getWinningPlayer() != null) {
                            handleWinningPlayer();
                        }
                    }
                    i++;
                 }
            });
            t.setRepeats(true);
            t.start();
        } else {
            userInputAllowed = true;
            if (currentHand.getWinningPlayer() != null) {
                handleWinningPlayer();
            }
            System.out.println("com.mycompany.boerenbridge.screens.RondeScreen.robotsPickCards()");
        }
    }

    private void drawCard(Card card, AbstractPlayer player) {
        switch (player.getPosition()) {
            case LEFT:
                leftCard.setIcon(card.getImage());
                createFirstCardBorder(leftCard);
                break;
            case TOP:
                topCard.setIcon(card.getImage());
                createFirstCardBorder(topCard);
                break;
            case RIGHT:
                rightCard.setIcon(card.getImage());
                createFirstCardBorder(rightCard);
                break;
            case BOTTOM:
                bottomCard.setIcon(card.getImage());
                createFirstCardBorder(bottomCard);
                break;
            default:
                throw new AssertionError(player.getPosition().name());
        }
    }

    public void createFirstCardBorder(JButton cardButton) {
        if (currentHand.getNumberOfPlayedCards() == 1){
            cardButton.setBorder(BorderFactory.createLineBorder(Color.RED, 2));
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
                	round.addCardSeen(card);
                    drawCard(card, game.getRealPlayer());
                    moveCardsToTheLeft(cardButton);
                    centerCards();
                    final List<RobotPlayer> robotsAfterPlayer = currentHand.getRobotsAfterPlayer();
                    robotsPickCards(robotsAfterPlayer);
                } else {
                    Card firstCard = currentHand.getFirstCard();
                    JOptionPane.showMessageDialog(this, card.getHumanReadableString() + " mag niet gespeeld worden omdat je nog " + firstCard.getSuit().getNlNaam().toLowerCase() + " hebt");
                }
            }    
        }
    }

    public void handleWinningPlayer() {
        final HandleWinningPlayerPauser handleWinningPlayerPauser = new HandleWinningPlayerPauser();
        pauser.addActionListener(handleWinningPlayerPauser);
        pauser.setEnabled(true);
        pauser.setIcon(pauseImage);
        userInputAllowed = false;
        t2 = new Timer(2000, handleWinningPlayerPauser);
        t2.setRepeats(false);
        t2.start();
    }

    public void createEndRondeScreen() {
        EndRondeScreen endRondeScreen = new EndRondeScreen(round.getScores(), round);
        endRondeScreen.setExtendedState(JFrame.MAXIMIZED_BOTH); 
        endRondeScreen.setVisible(true);
    }

    public static <K, V> LinkedHashMap<K, V> reverse(LinkedHashMap<K, V> map) {
        LinkedHashMap<K, V> reversedMap = new LinkedHashMap<K, V>();

        ListIterator<Map.Entry<K, V>> it = new ArrayList<>(map.entrySet()).listIterator(map.entrySet().size());

        while (it.hasPrevious())
        {
            Map.Entry<K, V> el = it.previous();
            reversedMap.put(el.getKey(), el.getValue());
        }

        return reversedMap;
    }

    private void resetMiddleCards() {
        makeButtonInvisible(leftCard);
        makeButtonInvisible(rightCard);
        makeButtonInvisible(bottomCard);
        makeButtonInvisible(topCard);
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
        pauser = new javax.swing.JButton();
        troefViewer = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setBackground(new java.awt.Color(255, 255, 255));
        setForeground(java.awt.Color.white);

        jPanel1.setBackground(new java.awt.Color(255, 255, 255));

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

        pauser.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        pauser.setBorder(null);
        pauser.setMaximumSize(new java.awt.Dimension(80, 23));
        pauser.setMinimumSize(new java.awt.Dimension(80, 23));

        try {
            jPanel1 = new JPanelWithBackground(getClass().getResource("/sjopper.png"));
            jPanel1.setBackground(new java.awt.Color(255, 255, 255));
        } catch (IOException ex) {
            Logger.getLogger(RoundScreen.class.getName()).log(Level.SEVERE, null, ex);
        }

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
                            .addComponent(topPlayerInfo, javax.swing.GroupLayout.PREFERRED_SIZE, 73, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(bottomPlayerInfo, javax.swing.GroupLayout.PREFERRED_SIZE, 73, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addComponent(leftPlayerInfo, javax.swing.GroupLayout.PREFERRED_SIZE, 73, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(leftCard, javax.swing.GroupLayout.PREFERRED_SIZE, 73, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(pauser, javax.swing.GroupLayout.PREFERRED_SIZE, 67, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
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
                                .addComponent(card7, javax.swing.GroupLayout.PREFERRED_SIZE, 73, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(card8, javax.swing.GroupLayout.PREFERRED_SIZE, 73, javax.swing.GroupLayout.PREFERRED_SIZE)))
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
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(rightPlayerInfo, javax.swing.GroupLayout.PREFERRED_SIZE, 73, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(topPlayerInfo, javax.swing.GroupLayout.PREFERRED_SIZE, 97, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(topCard, javax.swing.GroupLayout.PREFERRED_SIZE, 97, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(leftPlayerInfo, javax.swing.GroupLayout.PREFERRED_SIZE, 97, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(28, 28, 28))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addGap(14, 14, 14)
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(rightPlayerInfo, javax.swing.GroupLayout.PREFERRED_SIZE, 97, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(rightCard, javax.swing.GroupLayout.PREFERRED_SIZE, 97, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(leftCard, javax.swing.GroupLayout.PREFERRED_SIZE, 97, javax.swing.GroupLayout.PREFERRED_SIZE)))
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addGap(26, 26, 26)
                                .addComponent(pauser, javax.swing.GroupLayout.PREFERRED_SIZE, 74, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addGap(24, 24, 24)))
                .addComponent(bottomCard, javax.swing.GroupLayout.PREFERRED_SIZE, 97, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(notAllowedSlagenLabel)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(bottomPlayerInfo, javax.swing.GroupLayout.PREFERRED_SIZE, 97, javax.swing.GroupLayout.PREFERRED_SIZE)
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

        troefViewer.setContentAreaFilled(false);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addGap(0, 0, Short.MAX_VALUE)
                .addComponent(troefViewer, javax.swing.GroupLayout.PREFERRED_SIZE, 144, javax.swing.GroupLayout.PREFERRED_SIZE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(troefViewer, javax.swing.GroupLayout.PREFERRED_SIZE, 140, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void card1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_card1ActionPerformed
        // TODO add your handling code here:
        realPlayerPicksCard(card1);
    }//GEN-LAST:event_card1ActionPerformed

    private void card2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_card2ActionPerformed
        // TODO add your handling code here:
        realPlayerPicksCard(card2);
    }//GEN-LAST:event_card2ActionPerformed

    private void card3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_card3ActionPerformed
        // TODO add your handling code here:
        realPlayerPicksCard(card3);
    }//GEN-LAST:event_card3ActionPerformed

    private void card4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_card4ActionPerformed
        // TODO add your handling code here:
        realPlayerPicksCard(card4);
    }//GEN-LAST:event_card4ActionPerformed

    private void card5ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_card5ActionPerformed
        // TODO add your handling code here:
        realPlayerPicksCard(card5);
    }//GEN-LAST:event_card5ActionPerformed

    private void card6ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_card6ActionPerformed
        // TODO add your handling code here:
        realPlayerPicksCard(card6);
    }//GEN-LAST:event_card6ActionPerformed

    private void card7ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_card7ActionPerformed
        // TODO add your handling code here:
        realPlayerPicksCard(card7);
    }//GEN-LAST:event_card7ActionPerformed

    private void card8ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_card8ActionPerformed
        // TODO add your handling code here:
        realPlayerPicksCard(card8);
    }//GEN-LAST:event_card8ActionPerformed

    private void card9ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_card9ActionPerformed
        // TODO add your handling code here:
        realPlayerPicksCard(card9);
    }//GEN-LAST:event_card9ActionPerformed

    private void card10ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_card10ActionPerformed
        // TODO add your handling code here:
        realPlayerPicksCard(card10);
    }//GEN-LAST:event_card10ActionPerformed

    private void card11ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_card11ActionPerformed
        // TODO add your handling code here:
        realPlayerPicksCard(card11);
    }//GEN-LAST:event_card11ActionPerformed

    private void card12ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_card12ActionPerformed
        // TODO add your handling code here:
        realPlayerPicksCard(card12);
    }//GEN-LAST:event_card12ActionPerformed

    private void card13ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_card13ActionPerformed
        // TODO add your handling code here:
        realPlayerPicksCard(card13);
    }//GEN-LAST:event_card13ActionPerformed

    private void rightCardActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_rightCardActionPerformed

    }//GEN-LAST:event_rightCardActionPerformed


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
    private javax.swing.JButton pauser;
    private javax.swing.JButton rightCard;
    private java.awt.List rightPlayerInfo;
    private javax.swing.JButton topCard;
    private java.awt.List topPlayerInfo;
    private javax.swing.JButton troefViewer;
    // End of variables declaration//GEN-END:variables

    private class HandleWinningPlayerPauser implements ActionListener {
        private boolean paused = false;
        private boolean hasExecuted = false;
        
        @Override
        public void actionPerformed(ActionEvent ae) {
            boolean fromPauser = ae.getSource() == pauser;
            if (fromPauser) {
                paused = !paused;
                if (!paused) {
                    pauser.setEnabled(false);
                } else {
                    pauser.setIcon(playImage);
                }
            }
            if (!paused && !hasExecuted) {
                pauser.setEnabled(false);
                pauser.setIcon(round.getTroef().getSmallImage());
                hasExecuted = true;
                userInputAllowed = false;
                AbstractPlayer winningPlayer = currentHand.getWinningPlayer();
                increaseScore(winningPlayer);
                if (round.hasNextHand()) {
                    currentHand = round.getNextHand();
                    currentHand.setFirstPlayer(winningPlayer);
                    userInputAllowed = true;
                    startNextHand(currentHand);
                } else {
                    round.end();
                    RoundScreen.this.dispose();
                    createEndRondeScreen();
                }
            }
        }
    }
    
    private void moveCardsToTheLeft(JButton pressedButton) {
        Card cardToMove = null;
        for (Entry<JButton, Card> entry : reverse(cardButtons).entrySet()) {
            JButton button = entry.getKey();
            Card card = entry.getValue();
            if (!button.equals(pressedButton)) {
                cardButtons.put(button, cardToMove);
                if (cardToMove != null) {
                    button.setIcon(cardToMove.getImage());
                } else {
                    makeButtonInvisible(button);
                }
            } else {
                cardButtons.put(button, cardToMove);
                if (cardToMove == null) {
                    makeButtonInvisible(button);
                } else {
                    button.setIcon(cardToMove.getImage());
                }
                break;
            }
            cardToMove = card;
        }
    }
    
    public void centerCards(){
        int firstActualCard = 0;
        
        for (Card card : cardButtons.values()) {
            if (card == null) {
                firstActualCard++;
            } else {
                break;
            }
        }
        
        int nullCards = (int) cardButtons.values().stream().filter(Objects::isNull).count();
        int startIndex = nullCards/2;
        while (firstActualCard < startIndex) {
            Card cardToMove = null;
            for (Entry<JButton, Card> entry : cardButtons.entrySet()) {
                JButton button = entry.getKey();
                Card card = entry.getValue();
                cardButtons.put(button, cardToMove);
                if (cardToMove == null) {
                    makeButtonInvisible(button);
                } else {
                    button.setIcon(cardToMove.getImage());
                }
                cardToMove = card;
            }
            firstActualCard++;
        }
        
        
    }

    public void makeButtonInvisible(JButton button) {
        button.setIcon(null);
        button.setBorder(null);
        button.setOpaque(false);
        button.setContentAreaFilled(false);
    }
    
    
}

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package com.mycompany.boerenbridge.screens;

import com.mycompany.boerenbridge.AbstractPlayer;
import com.mycompany.boerenbridge.Game;
import com.mycompany.boerenbridge.Round;
import java.awt.Component;
import java.awt.Font;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import javax.swing.JLabel;
import javax.swing.JTable;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import org.apache.commons.lang.ArrayUtils;

/**
 *
 * @author b.smeets
 */
public class EndRondeScreen extends javax.swing.JFrame {

    public static DefaultTableModel tableModel;
    public Round nextRound;
    /**
     * Creates new form EndRondeScreen
     */
    EndRondeScreen(Map<AbstractPlayer, Integer> scores, Round round) {
        initComponents();
        setTitle("Stand na ronde nummer " + round.getRoundNumber() + " (" + round.getNumberOfCards() + " kaarten)");
        nextRound = Game.getSingleton().getNextRound();
        if (nextRound == null) {
            nextRoundButton.setLabel("Spel beëindigen");
            List<AbstractPlayer> winners = Game.getSingleton().getWinners();
            String winnerText;
            if (winners.size() == 1) {
                winnerText = "De winnaar is " + winners.get(0).getName() + " met een score van " + winners.get(0).getScore() + "!";
            } else {
                winnerText = "We hebben een gelijke stand! De winnaars van dit spel zijn: " + winners.stream().map(AbstractPlayer::getName).collect(Collectors.joining(", ")) + " met een score van " + winners.get(0).getScore() + "!" ;
            }
            winnerLabel.setText(winnerText);
        }
        
        jTable1.setDefaultRenderer(Object.class, new TotalScoresAreBold());
        
        if (tableModel == null) {
            tableModel = new DefaultTableModel();
        }
        
        if (round.getRoundNumber() == 1) {
            tableModel.addColumn("");
            for (AbstractPlayer player : scores.keySet()) {
                tableModel.addColumn(player.getName());
            }
        }
        
        List<Integer> slagenInGoedeVolgorde = getSlagenInGoedeVolgorde(round);
        Object[] slagenArray = ArrayUtils.add(slagenInGoedeVolgorde.toArray(), 0, "Gegokt");
        tableModel.addRow(slagenArray);
        Object[] scoresArray = scores.values().toArray();
        scoresArray = ArrayUtils.add(scoresArray, 0, "Verkregen");
        tableModel.addRow(scoresArray);
        Object[] totalScoreArray = scores.keySet().stream().map(AbstractPlayer::getScore).collect(Collectors.toList()).toArray();
        totalScoreArray = ArrayUtils.add(totalScoreArray, 0, "Totaal na ronde " + round.getRoundNumber() + " (" + round.getNumberOfCards() + " kaarten)");
        tableModel.addRow(totalScoreArray);

        jTable1.setModel(tableModel);
    }
    

    public List<Integer> getSlagenInGoedeVolgorde(Round round) {
        Map<AbstractPlayer, Integer> slagen = round.getSlagen();
        List<AbstractPlayer> players = Game.getSingleton().getPlayers();
        List<Integer> slagenInGoedeVolgorde = new ArrayList<>();
        for (AbstractPlayer player : players) {
            slagenInGoedeVolgorde.add(slagen.get(player));
        }
        return slagenInGoedeVolgorde;
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jScrollPane1 = new javax.swing.JScrollPane();
        jTable1 = new javax.swing.JTable();
        nextRoundButton = new java.awt.Button();
        winnerLabel = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jTable1.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "Title 4"
            }
        ));
        jScrollPane1.setViewportView(jTable1);

        nextRoundButton.setActionCommand("nextRound");
        nextRoundButton.setLabel("Volgende ronde");
        nextRoundButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                nextRoundButtonActionPerformed(evt);
            }
        });

        winnerLabel.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(winnerLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 923, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 923, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(nextRoundButton, javax.swing.GroupLayout.PREFERRED_SIZE, 121, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(winnerLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 99, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 537, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(39, 39, 39)
                .addComponent(nextRoundButton, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(23, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void nextRoundButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_nextRoundButtonActionPerformed
        this.dispose();
        if (this.nextRound != null) {
            nextRound.showRondeScreen();
        }
        
    }//GEN-LAST:event_nextRoundButtonActionPerformed
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTable jTable1;
    private java.awt.Button nextRoundButton;
    private javax.swing.JLabel winnerLabel;
    // End of variables declaration//GEN-END:variables

    private static class TotalScoresAreBold extends DefaultTableCellRenderer {

        @Override
        public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
            JLabel parent = (JLabel)super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
            if ((row+1)%3==0) {
                parent.setFont(
                parent.getFont().deriveFont(Font.BOLD));
            }
            return parent;
        }
    }
}

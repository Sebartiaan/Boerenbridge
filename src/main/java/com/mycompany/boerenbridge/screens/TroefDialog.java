/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JDialog.java to edit this template
 */
package com.mycompany.boerenbridge.screens;

import com.mycompany.boerenbridge.Card;
import com.mycompany.boerenbridge.CardValue;
import com.mycompany.boerenbridge.Round;
import com.mycompany.boerenbridge.Suit;
import javax.swing.JOptionPane;

/**
 *
 * @author b.smeets
 */
public class TroefDialog extends javax.swing.JDialog {

    private final Round round;

    /**
     * Creates new form TroefDialog
     */
    public TroefDialog(Round round, java.awt.Frame parent, boolean modal) {
        super(parent, modal);
        setTitle("Troef maken");
        this.round = round;
        initComponents();
        hartenTroef.setIcon(new Card(Suit.HEARTS, CardValue.ACE).getImage());
        klaverenTroef.setIcon(new Card(Suit.CLUBS, CardValue.ACE).getImage());
        schoppenTroef.setIcon(new Card(Suit.SPADES, CardValue.ACE).getImage());
        ruitenTroef.setIcon(new Card(Suit.DIAMONDS, CardValue.ACE).getImage());
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
        ruitenTroef = new javax.swing.JButton();
        hartenTroef = new javax.swing.JButton();
        jLabel1 = new javax.swing.JLabel();
        klaverenTroef = new javax.swing.JButton();
        schoppenTroef = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);

        ruitenTroef.setIconTextGap(0);
        ruitenTroef.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ruitenTroefActionPerformed(evt);
            }
        });

        hartenTroef.setIconTextGap(0);
        hartenTroef.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                hartenTroefActionPerformed(evt);
            }
        });

        jLabel1.setText("Kies uw troef");

        klaverenTroef.setIconTextGap(0);
        klaverenTroef.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                klaverenTroefActionPerformed(evt);
            }
        });

        schoppenTroef.setIconTextGap(0);
        schoppenTroef.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                schoppenTroefActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(klaverenTroef, javax.swing.GroupLayout.PREFERRED_SIZE, 73, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(schoppenTroef, javax.swing.GroupLayout.PREFERRED_SIZE, 73, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(ruitenTroef, javax.swing.GroupLayout.PREFERRED_SIZE, 73, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(hartenTroef, javax.swing.GroupLayout.PREFERRED_SIZE, 73, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(119, 119, 119)
                        .addComponent(jLabel1)))
                .addContainerGap(25, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addGap(31, 31, 31)
                .addComponent(jLabel1)
                .addGap(18, 18, 18)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(hartenTroef, javax.swing.GroupLayout.PREFERRED_SIZE, 97, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(ruitenTroef, javax.swing.GroupLayout.PREFERRED_SIZE, 97, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(klaverenTroef, javax.swing.GroupLayout.PREFERRED_SIZE, 97, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(schoppenTroef, javax.swing.GroupLayout.PREFERRED_SIZE, 97, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(40, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void ruitenTroefActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ruitenTroefActionPerformed
        showMessage(Suit.DIAMONDS);
    }//GEN-LAST:event_ruitenTroefActionPerformed

    private void hartenTroefActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_hartenTroefActionPerformed
        showMessage(Suit.HEARTS);
    }//GEN-LAST:event_hartenTroefActionPerformed

    private void klaverenTroefActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_klaverenTroefActionPerformed
        showMessage(Suit.CLUBS);
    }//GEN-LAST:event_klaverenTroefActionPerformed

    private void schoppenTroefActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_schoppenTroefActionPerformed
        showMessage(Suit.SPADES);
    }//GEN-LAST:event_schoppenTroefActionPerformed

    private void showMessage(Suit troef) {
        int showConfirmDialog = JOptionPane.showConfirmDialog(round.getRondeScreen(), "Weet u zeker dat u " + troef.getNlNaam() + " troef wil maken?");
        if (showConfirmDialog == JOptionPane.OK_OPTION) {
            round.setTroef(troef);
            this.setVisible(false);
            this.dispose();
        } 
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton hartenTroef;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JButton klaverenTroef;
    private javax.swing.JButton ruitenTroef;
    private javax.swing.JButton schoppenTroef;
    // End of variables declaration//GEN-END:variables
}

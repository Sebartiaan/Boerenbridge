/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.boerenbridge;

import java.util.Comparator;
import javax.swing.ImageIcon;

/**
 *
 * @author b.smeets
 */
public class Card implements Comparable<Card> {
    //=================================================================== fields
    private final Suit suit;
    private final CardValue value;
    
    //============================================================== constructor
    public Card(Suit suit, CardValue value) {
        this.suit = suit;
        this.value = value;
    }
    
    @Override
    public String toString() {
    	return getValue().name() + " of " + getSuit().name();
    }

	@Override
	public int compareTo(Card o) {
		return Comparator
				.comparing(Card::getSuit)
				.thenComparing(card -> card.getValue().getValue())
				.compare(this, o);
			
		
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((suit == null) ? 0 : suit.hashCode());
		result = prime * result + ((value == null) ? 0 : value.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Card other = (Card) obj;
		if (suit != other.suit)
			return false;
		if (value != other.value)
			return false;
		return true;
	}

	public Suit getSuit() {
		return suit;
	}

	public CardValue getValue() {
            return value;
	}
        
        public String getHumanReadableString(){
            return suit.getNlNaam() + " "+ value.getNlNaam().toLowerCase();
        }
        
        public ImageIcon getImage(){
            StringBuilder builder = new StringBuilder();
            builder.append('/');
            builder.append(value.getId());
            builder.append(suit.getId());
            builder.append(".gif");
            return new ImageIcon(getClass().getResource(builder.toString()));
        }
}
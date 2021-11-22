package ai.test;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

import com.mycompany.boerenbridge.Card;
import com.mycompany.boerenbridge.CardValue;
import com.mycompany.boerenbridge.Suit;

import ai.TroefMaker;
import ai.TroefMaker.SuitWithValue;

public class TroefmakerTest extends AITest{
	
	@Test
	public void scenario1Test() {
		List<Card> cardsInHand = new ArrayList<>();
		cardsInHand.add(new Card(Suit.DIAMONDS, CardValue.ACE));
		cardsInHand.add(new Card(Suit.DIAMONDS, CardValue.KING));
		cardsInHand.add(new Card(Suit.DIAMONDS, CardValue.QUEEN));
		cardsInHand.add(new Card(Suit.DIAMONDS, CardValue.JACK));
		cardsInHand.add(new Card(Suit.DIAMONDS, CardValue.TEN));
		cardsInHand.add(new Card(Suit.CLUBS, CardValue.NINE));
		cardsInHand.add(new Card(Suit.CLUBS, CardValue.EIGHT));
		cardsInHand.add(new Card(Suit.CLUBS, CardValue.SEVEN));
		cardsInHand.add(new Card(Suit.CLUBS, CardValue.SIX));
		cardsInHand.add(new Card(Suit.CLUBS, CardValue.FIVE));
		cardsInHand.add(new Card(Suit.CLUBS, CardValue.FOUR));
		cardsInHand.add(new Card(Suit.CLUBS, CardValue.THREE));
		cardsInHand.add(new Card(Suit.CLUBS, CardValue.TWO));
		SuitWithValue suitWithBestValue = new TroefMaker(getMockedRobot(cardsInHand)).findSuitWithBestValue();
		assertEquals(Suit.DIAMONDS, suitWithBestValue.getSuit());
	}

}

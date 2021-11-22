package ai.test;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import com.mycompany.boerenbridge.Card;
import com.mycompany.boerenbridge.CardValue;
import com.mycompany.boerenbridge.RobotPlayer;
import com.mycompany.boerenbridge.Round;
import com.mycompany.boerenbridge.Suit;

import ai.HarderSlagenGuesser;

public class HardeSlagenGuesserTest extends AITest {
	
	@Test
	public void scenario1Test() {
		List<Card> cardsInHand = new ArrayList<>();
		cardsInHand.add(new Card(Suit.CLUBS, CardValue.ACE));
		cardsInHand.add(new Card(Suit.DIAMONDS, CardValue.ACE));
		RobotPlayer mockRobot = getMockedRobot(cardsInHand);
		Round mockRound = getMockedRound(cardsInHand);
		int amountOfSlagenBasedOnCardValues = new HarderSlagenGuesser(mockRobot, mockRound).getAmountOfSlagenBasedOnCardValues(new ArrayList<>());
		Assertions.assertEquals(amountOfSlagenBasedOnCardValues, 2);
	}
	
	@Test
	public void scenario2Test() {
		List<Card> cardsInHand = new ArrayList<>();
		cardsInHand.add(new Card(Suit.CLUBS, CardValue.ACE));
		cardsInHand.add(new Card(Suit.DIAMONDS, CardValue.JACK));
		RobotPlayer mockRobot = getMockedRobot(cardsInHand);
		Round mockRound = getMockedRound(cardsInHand);
		int amountOfSlagenBasedOnCardValues = new HarderSlagenGuesser(mockRobot, mockRound).getAmountOfSlagenBasedOnCardValues(new ArrayList<>());
		Assertions.assertEquals(amountOfSlagenBasedOnCardValues, 2);
	}
	
	@Test
	public void scenario3Test() {
		List<Card> cardsInHand = new ArrayList<>();
		cardsInHand.add(new Card(Suit.CLUBS, CardValue.ACE));
		cardsInHand.add(new Card(Suit.CLUBS, CardValue.FIVE));
		RobotPlayer mockRobot = getMockedRobot(cardsInHand);
		Round mockRound = getMockedRound(cardsInHand);
		int amountOfSlagenBasedOnCardValues = new HarderSlagenGuesser(mockRobot, mockRound).getAmountOfSlagenBasedOnCardValues(new ArrayList<>());
		Assertions.assertEquals(amountOfSlagenBasedOnCardValues, 2);
	}
	
	@Test
	public void scenario4Test() {
		List<Card> cardsInHand = new ArrayList<>();
		cardsInHand.add(new Card(Suit.CLUBS, CardValue.ACE));
		cardsInHand.add(new Card(Suit.CLUBS, CardValue.KING));
		cardsInHand.add(new Card(Suit.CLUBS, CardValue.QUEEN));
		cardsInHand.add(new Card(Suit.CLUBS, CardValue.JACK));
		cardsInHand.add(new Card(Suit.CLUBS, CardValue.TEN));
		cardsInHand.add(new Card(Suit.CLUBS, CardValue.NINE));
		cardsInHand.add(new Card(Suit.CLUBS, CardValue.EIGHT));
		cardsInHand.add(new Card(Suit.CLUBS, CardValue.SEVEN));
		cardsInHand.add(new Card(Suit.CLUBS, CardValue.SIX));
		cardsInHand.add(new Card(Suit.CLUBS, CardValue.FIVE));
		cardsInHand.add(new Card(Suit.CLUBS, CardValue.FOUR));
		cardsInHand.add(new Card(Suit.CLUBS, CardValue.THREE));
		cardsInHand.add(new Card(Suit.CLUBS, CardValue.TWO));
		RobotPlayer mockRobot = getMockedRobot(cardsInHand);
		Round mockRound = getMockedRound(cardsInHand);
		int amountOfSlagenBasedOnCardValues = new HarderSlagenGuesser(mockRobot, mockRound).getAmountOfSlagenBasedOnCardValues(new ArrayList<>());
		Assertions.assertEquals(amountOfSlagenBasedOnCardValues, 13);
	}

}

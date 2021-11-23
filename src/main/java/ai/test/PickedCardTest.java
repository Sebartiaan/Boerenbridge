package ai.test;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.junit.Test;
import org.junit.jupiter.api.Assertions;
import org.mockito.Mockito;

import com.mycompany.boerenbridge.Card;
import com.mycompany.boerenbridge.CardValue;
import com.mycompany.boerenbridge.Hand;
import com.mycompany.boerenbridge.RobotPlayer;
import com.mycompany.boerenbridge.Round;
import com.mycompany.boerenbridge.Suit;

import ai.HarderAI;

public class PickedCardTest extends AITest {

	@Test
	public void scenario1Test() {
		int guessed = 2;
		int current = 1;
		
		Suit troef = Suit.CLUBS;
		Card twoOfClubs = new Card(Suit.CLUBS, CardValue.TWO);
		Card aceOfClubs = new Card(Suit.CLUBS, CardValue.ACE);
		List<Card> cardsInHand = Arrays.asList(twoOfClubs, aceOfClubs);
		RobotPlayer mockedRobot = getMockedRobot(cardsInHand);
		Round mockedRound = getMockedRound(Collections.emptyList());
		Mockito.when(mockedRound.getTroef()).thenReturn(troef);
		Mockito.when(mockedRound.getScoreFor(mockedRobot)).thenReturn(current);
		Mockito.when(mockedRound.getSlagenFor(mockedRobot)).thenReturn(guessed);
		
		Hand hand = new Hand(troef);
		
		HarderAI harderAI = new HarderAI(mockedRobot);
		harderAI.setRound(mockedRound);
		Card pickedCard = harderAI.pickCard(hand);
		
		Assertions.assertEquals(twoOfClubs, pickedCard);
		
	}
	
}

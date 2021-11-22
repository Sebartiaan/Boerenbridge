package ai.test;

import java.util.List;

import org.mockito.Mockito;

import com.mycompany.boerenbridge.Card;
import com.mycompany.boerenbridge.RobotPlayer;
import com.mycompany.boerenbridge.Round;

public class AITest {
	
	protected final Round getMockedRound(List<Card> cardsInHand) {
		Round mockRound = Mockito.mock(Round.class);
		Mockito.when(mockRound.getNotAllowedGuess()).thenReturn(-1);
		Mockito.when(mockRound.getNumberOfCards()).thenReturn(cardsInHand.size());
		return mockRound;
	}

	protected final RobotPlayer getMockedRobot(List<Card> cardsInHand) {
		RobotPlayer mockRobot = Mockito.mock(RobotPlayer.class);
		Mockito.when(mockRobot.getCards()).thenReturn(cardsInHand);
		return mockRobot;
	}

}

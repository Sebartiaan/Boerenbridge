package ai;

import java.util.Arrays;
import java.util.List;
import java.util.Objects;

import com.mycompany.boerenbridge.Card;
import com.mycompany.boerenbridge.CardValue;
import com.mycompany.boerenbridge.RobotPlayer;
import com.mycompany.boerenbridge.Round;

public class HarderSlagenGuesser extends SlagenGuesser {

	private boolean onlyHighCards = false;

	public HarderSlagenGuesser(RobotPlayer robot, Round round) {
		super(robot, round);
	}
	
	@Override
	protected int findInterestingCards(List<Card> markedCards) {
		int guessedUntilNow = getRound().getSlagen().values().stream().filter(Objects::nonNull).mapToInt(Integer::intValue).sum();
		
		//Als er al veel slagen gegokt zijn, bekijk dan alleen de azen
		if (guessedUntilNow >= getRound().getNumberOfCards()) {
			onlyHighCards = true;
		}
		int amountOfInterestingCards = super.findInterestingCards(markedCards);
		
		return amountOfInterestingCards;
	}
	
	@Override
	protected List<CardValue> getInterestingHighValueCards() {
		if (onlyHighCards) {
			return Arrays.asList(CardValue.ACE);
		} 
		return Arrays.asList(CardValue.ACE, CardValue.KING, CardValue.QUEEN);
	}

}

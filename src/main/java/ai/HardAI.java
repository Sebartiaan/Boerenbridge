package ai;

import java.util.ArrayList;
import java.util.List;

import com.mycompany.boerenbridge.Card;
import com.mycompany.boerenbridge.Hand;
import com.mycompany.boerenbridge.RobotPlayer;
import com.mycompany.boerenbridge.Round;
import com.mycompany.boerenbridge.Suit;

public class HardAI implements RobotAI {

	private Round round;
	private final RobotPlayer robot;
	private List<Card> markedCards = new ArrayList<>();

	public HardAI(RobotPlayer robot) {
		this.robot = robot;
	}

	@Override
	public int guessSlagen() {
		this.markedCards.clear();
		return new SlagenGuesser(robot, round).getAmountOfSlagenBasedOnCardValues();
	}

	@Override
	public Card pickCard(Hand hand) {
		int guessed = round.getSlagenFor(robot);
		int current = round.getScoreFor(robot);

		if (hand.getFirstCard() == null) {
			return new CardPicker(round, robot).getStartingCard(guessed, current);
		}

		if (guessed > current) {
			return new CardPicker(round, robot).getCardToWin(hand);
		} else {
			return new CardPicker(round, robot).getCardToLose(hand);
		}
	}

	@Override
	public Suit maakTroef() {
		return new TroefMaker(robot).findSuitWithBestValue().getSuit();
	}

	@Override
	public void setRound(Round round) {
		this.round = round;
	}
}

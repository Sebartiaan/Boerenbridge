package ai;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import com.mycompany.boerenbridge.Card;
import com.mycompany.boerenbridge.Hand;
import com.mycompany.boerenbridge.RobotPlayer;
import com.mycompany.boerenbridge.Round;
import com.mycompany.boerenbridge.Suit;

public class HarderAI implements RobotAI {
	
	
	private Round round;
	private final RobotPlayer robot;

	public HarderAI(RobotPlayer robot) {
		this.robot = robot;
	}

	@Override
	public int guessSlagen() {
		HarderSlagenGuesser harderSlagenGuesser = getHarderSlagenGuesser();
		return harderSlagenGuesser.getAmountOfSlagenBasedOnCardValues();
	}

	private HarderSlagenGuesser getHarderSlagenGuesser() {
		return new HarderSlagenGuesser(robot, round);
	}

	@Override
	public Card pickCard(Hand hand) {
		int guessed = round.getSlagenFor(robot);
		int current = round.getScoreFor(robot);
		
		List<Card> certainWinCards = getCertainWinCards();
		current += certainWinCards.size();
		current += getNumberOfLikelyWinCards(certainWinCards)/3;
		
		Card pickedCard;
		if (hand.getFirstCard() == null) {
			pickedCard = new HarderCardPicker(round, robot).getStartingCard(guessed, current);
			return pickedCard;
		}

		if (guessed > current) {
			pickedCard = new CardPicker(round, robot).getCardToWin(hand);
		} else {
			pickedCard = new HarderCardPicker(round, robot).getCardToLose(hand);
		}
		return pickedCard;
	}

	private int getNumberOfLikelyWinCards(List<Card> certainWinCards) {
		List<Card> likelyWinCards = new ArrayList<>();
		likelyWinCards.addAll(getHarderSlagenGuesser().findInterestingCards());
		likelyWinCards.addAll(AIHelper.getCardsOfSuit(robot.getCards(), round.getTroef()));
		List<Card> filteredLikelyWinCards = likelyWinCards.stream().filter(card -> !certainWinCards.contains(card)).collect(Collectors.toList());
		return filteredLikelyWinCards.size();
	}

	private List<Card> getCertainWinCards() {
		List<Card> certainWinCards = new ArrayList<>();
		List<Card> cards = robot.getCards();
		List<Card> troefsInHand = AIHelper.getCardsOfSuit(cards, round.getTroef());
		List<Card> troefsSeen = AIHelper.getCardsOfSuit(round.getCardsSeen(), round.getTroef());
		troefsSeen.addAll(troefsInHand);
		for (Card troef : troefsInHand) {
			if (getHarderSlagenGuesser().isCertainWinTroef(troef, troefsSeen)) {
				certainWinCards.add(troef);
			}
		}
		//If we have 3 or more troefs that will certainly win, the others should be able to win too
		if (certainWinCards.size() > 2) {
			certainWinCards = troefsInHand;
		}
		
		return certainWinCards;
	}

	@Override
	public Suit maakTroef() {
		TroefMaker troefMaker = new TroefMaker(robot);
		if (round.getSlagenFor(robot) == 0) {
			return troefMaker.findSuitWithWorstValue().getSuit();
		}
		return troefMaker.findSuitWithBestValue().getSuit();
	}
	
	public void setRound(Round round) {
		this.round = round;
	}
}

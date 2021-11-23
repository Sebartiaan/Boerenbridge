package ai;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import com.mycompany.boerenbridge.Card;
import com.mycompany.boerenbridge.CardValue;
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
		HarderSlagenGuesser harderSlagenGuesser = new HarderSlagenGuesser(robot, round);
		return harderSlagenGuesser.getAmountOfSlagenBasedOnCardValues();
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
		likelyWinCards.addAll(new HarderSlagenGuesser(robot, round).findInterestingCards());
		likelyWinCards.addAll(AIHelper.getCardsOfSuit(robot.getCards(), round.getTroef()));
		List<Card> filteredLikelyWinCards = likelyWinCards.stream().filter(card -> !certainWinCards.contains(card)).collect(Collectors.toList());
		return filteredLikelyWinCards.size();
	}

	private List<Card> getCertainWinCards() {
		List<Card> certainWinCards = new ArrayList<>();
		List<Card> cards = robot.getCards();
		List<Card> troefsInHand = AIHelper.getCardsOfSuit(cards, round.getTroef());
		for (Card troef : troefsInHand) {
			List<Card> troefsSeen = AIHelper.getCardsOfSuit(round.getCardsSeen(), round.getTroef());
			troefsSeen.addAll(troefsInHand);
			if (isCertainWinTroef(troef, troefsSeen)) {
				certainWinCards.add(troef);
			}
		}
		//If we have 3 or more troefs that will certainly win, the others should be able to win too
		if (certainWinCards.size() > 2) {
			certainWinCards = troefsInHand;
		}
		
		return certainWinCards;
	}

	private boolean isCertainWinTroef(Card card, List<Card> troefsSeen) {
		boolean started = false;
		boolean isHighest = true;
		
		int numberOfCardsInGame = round.getNumberOfCards() * 4;
		int numberOfUnknownCardsInGame = numberOfCardsInGame - round.getCardsSeen().size();
		double likelyHoodCardinGame = numberOfUnknownCardsInGame/52d;
		double likelyHoodCardNotInGame = 1 - likelyHoodCardinGame;
		
		for (CardValue cardValue : CardValue.values()) {
			if (started) {
				isHighest = troefsSeen.stream().anyMatch(troef -> troef.getValue() == cardValue);
				if (!isHighest) {
					if (likelyHoodCardNotInGame > 0.5) {
						likelyHoodCardNotInGame = likelyHoodCardNotInGame*likelyHoodCardNotInGame;
						continue;
					}
					break;
				}
			}
			if (card.getValue() == cardValue) {
				started = true;
			}
		}
		
		return isHighest;
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

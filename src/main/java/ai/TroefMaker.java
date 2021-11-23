package ai;

import java.util.ArrayList;
import java.util.Collections;
import java.util.EnumMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Optional;
import java.util.Random;
import java.util.function.Function;
import java.util.stream.Collectors;

import com.mycompany.boerenbridge.Card;
import com.mycompany.boerenbridge.RobotPlayer;
import com.mycompany.boerenbridge.Suit;

public class TroefMaker {

	
	private final RobotPlayer robot;

	public TroefMaker(RobotPlayer robot) {
		this.robot = robot;
	}
	
	public SuitWithValue findSuitWithBestValue() {
		Map<Suit, Integer> suitValues = new EnumMap<>(Suit.class);
		for (Card card : robot.getCards()) {
			var suit = card.getSuit();
			var integer = suitValues.get(suit);
			if (integer == null) {
				integer = 0;
			} 
			integer += card.getValue().getValue();
			suitValues.put(suit, integer);
		}

		Integer max = Collections.max(suitValues.values());

		List<Suit> bestSuits = new ArrayList<>();
		for (Entry<Suit, Integer> entry : suitValues.entrySet()) {
			if (entry.getValue().equals(max)) {
				bestSuits.add(entry.getKey());
			}
		}

		if (bestSuits.size() == 1) {
			return new SuitWithValue(bestSuits.get(0), max);
		} else {
			return new SuitWithValue(bestSuits.get(new Random().nextInt(bestSuits.size())), max);
		}
	}
	public SuitWithValue findSuitWithWorstValue() {
		Map<Suit, Integer> suitValues = new EnumMap<>(Suit.class);
		for (Suit suit : Suit.values()) {
			suitValues.put(suit, 0);
		}
		
		for (Card card : robot.getCards()) {
			var suit = card.getSuit();
			var integer = suitValues.get(suit);
			if (integer == null) {
				integer = 0;
			} 
			integer += card.getValue().getValue();
			suitValues.put(suit, integer);
		}
		
		Integer min = Collections.min(suitValues.values());
		
		List<Suit> bestSuits = new ArrayList<>();
		for (Entry<Suit, Integer> entry : suitValues.entrySet()) {
			if (entry.getValue().equals(min)) {
				bestSuits.add(entry.getKey());
			}
		}
		
		if (bestSuits.size() == 1) {
			return new SuitWithValue(bestSuits.get(0), min);
		} else {
			return new SuitWithValue(bestSuits.get(new Random().nextInt(bestSuits.size())), min);
		}
	}
	
	public Suit findMostCommonSuit() {
		Optional<Suit> optional = robot.getCards().stream()
	            .collect(Collectors.groupingBy(Function.identity(), Collectors.counting()))
	            .entrySet().stream()
	            .collect(Collectors.groupingBy(Map.Entry::getValue))
	            .entrySet().stream()
	            .max(Map.Entry.comparingByKey())
	            .map(Map.Entry::getValue)
	            .map(v -> v.get(0).getKey().getSuit());
		if (optional.isPresent()) {
			return optional.get();
		} else {
			throw new IllegalStateException("robot.getCards() returns zero cards, which should never happen");
		}
	}
	
	public class SuitWithValue {
		private final Suit suit;
		private final Integer value;
		
		public SuitWithValue(Suit suit, Integer value) {
			this.suit = suit;
			this.value = value;
		}

		public Suit getSuit() {
			return suit;
		}

		public Integer getValue() {
			return value;
		}
	}
}

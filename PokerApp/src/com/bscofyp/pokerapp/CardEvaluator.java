package com.bscofyp.pokerapp;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class CardEvaluator {
	public static int evaluate(Card[] player, Card[] dealer) {
		int size, max = 0, tmp = 0;
		List<Card> cards = new ArrayList<Card>(Arrays.asList(player));
		cards.addAll(new ArrayList<Card>(Arrays.asList(dealer)));
		Collections.sort(cards);
		size = cards.size();
		for (int i = 0; i < size - 4; i++) {
			for (int j = i + 1; j < size - 3; j++) {
				for (int k = j + 1; k < size - 2; k++) {
					for (int l = k + 1; l < size - 1; l++) {
						for (int m = l + 1; m < size; m++) {
							tmp = handStrength(cards.get(i), cards.get(j), cards.get(k), cards.get(l), cards.get(m));
							max = tmp > max ? tmp : max;
						}
					}
				}
			}
		}
		return max;
	}

	private static int handStrength(Card c1, Card c2, Card c3, Card c4, Card c5) {
		boolean flush = c1.getSuit() == c2.getSuit() && c2.getSuit() == c3.getSuit() && c3.getSuit() == c4.getSuit()
				&& c4.getSuit() == c5.getSuit();
		int diff = 0;
		if (c1.getRank() != c2.getRank())
			diff++;
		if (c2.getRank() != c3.getRank())
			diff++;
		if (c3.getRank() != c4.getRank())
			diff++;
		if (c4.getRank() != c5.getRank())
			diff++;
		boolean straight = false;
		if (diff == 4) {
			if (c5.getRank() - c1.getRank() == 4)
				straight = true;
			// if c4 is 5 & c5 is ace
			else if (c4.getRank() == 3 && c5.getRank() == 12)
				straight = true;
		}

		// straight-flush
		if (flush && straight)
			return 10000000 + c5.getRank();

		// only 2 numbered ranks, must be 4 of a kind or full house
		if (diff == 1) {
			// 4 of a kind ABBBB
			if (c1.getRank() == c4.getRank())
				return 8000000 + c3.getRank() * 5 + c5.getRank();
			// 4 of a kind AAAAB
			else if (c2.getRank() == c5.getRank())
				return 8000000 + c3.getRank() * 5 + c1.getRank();
			// full house AAABB
			else if (c1.getRank() == c3.getRank())
				return 7000000 + c3.getRank() * 5 + c4.getRank();
			// full house AABBB
			else if (c3.getRank() == c5.getRank())
				return 7000000 + c3.getRank();
		}
		// if flush
		if (flush)
			return 6000000 + c5.getRank() * 5 + c4.getRank() * 4 + c3.getRank() * 3 + c2.getRank() * 2 + c1.getRank();
		// if straight
		if (straight)
			return 5000000 + c5.getRank();
		// 3 of a kind
		if (diff == 2) {
			// ABCCC
			if (c5.getRank() == c3.getRank())
				return 4000000 + c3.getRank() * 5 + c1.getRank() * 2 + c2.getRank();
			// ABBBC
			if (c4.getRank() == c2.getRank())
				return 4000000 + c3.getRank() * 5 + c1.getRank() * 2 + c5.getRank();
			// AAABC
			if (c3.getRank() == c1.getRank())
				return 4000000 + c3.getRank() * 5 + c4.getRank() * 2 + c5.getRank();
		}
		// if 2 pair
		if (diff == 2) {
			if (c1.getRank() == c2.getRank()) {
				// AABBC
				if (c3.getRank() == c4.getRank())
					return 3000000 + c1.getRank() * 5 + c3.getRank() * 2 + c5.getRank() * 2;
				// AABCC
				if (c4.getRank() == c5.getRank())
					return 3000000 + c1.getRank() * 5 + c4.getRank() * 2 + c3.getRank() * 2;
			}
			// ABBCC
			if (c2.getRank() == c3.getRank() && c4.getRank() == c5.getRank())
				return 3000000 + c2.getRank() * 5 + c4.getRank() * 2 + c1.getRank();
		}
		// if 1 pair
		if (diff == 3) {
			// AABCD
			if (c1.getRank() == c2.getRank())
				return 2000000 + c1.getRank() * 5 + c3.getRank() * 3 + c4.getRank() * 2 + c5.getRank();
			// ABBCD
			if (c2.getRank() == c3.getRank())
				return 2000000 + c2.getRank() * 5 + c1.getRank() * 3 + c4.getRank() * 2 + c5.getRank();
			// ABCCD
			if (c3.getRank() == c4.getRank())
				return 2000000 + c3.getRank() * 5 + c1.getRank() * 3 + c2.getRank() * 2 + c5.getRank();
			// ABCDD
			if (c4.getRank() == c5.getRank())
				return 2000000 + c4.getRank() * 5 + c1.getRank() * 3 + c2.getRank() * 2 + c3.getRank();
		}
		// high card
		return c5.getRank() * 5 + c4.getRank() * 4 + c3.getRank() * 3 + c2.getRank() * 2 + c1.getRank();
	}
}

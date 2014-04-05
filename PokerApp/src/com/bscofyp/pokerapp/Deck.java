package com.bscofyp.pokerapp;

import java.util.ArrayList;
import java.util.List;

public class Deck {
	private List<Card> deckArray = new ArrayList<Card>();

	Deck() {
		for (int i = 0; i < 4; i++) {
			for (int j = 0; j < 13; j++) {
				deckArray.add(new Card(i, j));
			}
		}
		shuffle();
	}

	private void shuffle() {
		Card temp;
		int rand;
		for (int i = 0; i < deckArray.size(); i++) {
			rand = (int) (Math.random() * deckArray.size());
			temp = deckArray.get(rand);
			deckArray.set(rand, deckArray.get(i));
			deckArray.set(i, temp);
		}
	}

	public Card serveCard() {
		return deckArray.remove(0);
	}

	public int remainingCards() {
		return deckArray.size();
	}
}

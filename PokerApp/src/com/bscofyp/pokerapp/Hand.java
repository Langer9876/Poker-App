package com.bscofyp.pokerapp;

public class Hand {
	private Card[] cards;
	private int strength;
	private boolean decision = true;

	Hand(Deck d, int c) {
		cards = new Card[c];
		for (int i = 0; i < c; i++) {
			cards[i] = d.serveCard();
		}
	}
	public void fold(){
		decision = false;
	}
	public boolean getDecision(){
		return decision;
	}

	public Card[] getHandCards() {
		return cards;
	}

	public void setStrength(int str) {
		strength = str;
	}

	public int getHandVal() {
		return strength;
	}

	public String getStrength() {
		if (strength >= 10000000)
			return "Straight Flush";
		if (strength >= 8000000)
			return "Four of a kind";
		if (strength >= 7000000)
			return "Full House";
		if (strength >= 6000000)
			return "Flush";
		if (strength >= 5000000)
			return "Straight";
		if (strength >= 4000000)
			return "Three of a kind";
		if (strength >= 3000000)
			return "Two Pair";
		if (strength >= 2000000)
			return "One Pair";
		else
			return "High Card";

	}
}

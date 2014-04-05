package com.bscofyp.pokerapp;

public class Round {
	Deck deck = new Deck();
	private Hand[] players;
	private Hand dealer = new Hand(deck, 5);

	Round(int pl) {
		players = new Hand[pl];
		for (int i = 0; i < pl; i++) {
			players[i] = new Hand(deck, 2);
			players[i].setStrength(CardEvaluator.evaluate(players[i].getHandCards(), dealer.getHandCards()));
		}
	}

	public Card[] getPlayerCards(int pl) {
		return players[pl].getHandCards();
	}

	public String getPlayerStrength(int pl) {
		return players[pl].getStrength();
	}	

	public int getPlayerVal(int pl) {
		return players[pl].getHandVal();
	}

	public Card[] getDealerCards() {
		return dealer.getHandCards();
	}
}

package com.bscofyp.pokerapp;


public class Card implements Comparable<Card> {
	private static String[] suits = { "hearts", "spades", "diamonds", "clubs" };
	private static String[] ranks = { "2", "3", "4", "5", "6", "7", "8", "9", "10", "jack", "queen", "king", "ace" };
	private static String cardPattern = "card_back_2";
	private int suit, rank;

	Card(int suit, int rank) {
		this.suit = suit;
		this.rank = rank;
	}
	Card(){
		this.rank = -1;
		this.suit = -1;
	}

	public String toString() {
		if(this.rank ==-1 || this.suit == -1)
			return cardPattern;
		else
			return suits[this.suit]+"_"+ranks[this.rank];
	}

	public int getRank() {
		return this.rank;
	}

	public int getSuit() {
		return this.suit;
	}

	public int compareTo(Card that) {
		if (this.rank >= that.getRank())
			return 1;
		else
			return -1;
	}
}

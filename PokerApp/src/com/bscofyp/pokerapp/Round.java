package com.bscofyp.pokerapp;

import java.util.ArrayList;
import java.util.List;


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
	
	public Integer[] getWinners(){
		int max = players[0].getHandVal();
		int tmp;
		List<Integer> winners = new ArrayList<Integer>();
		for(int i = 0;i<players.length;i++){
			tmp = players[i].getHandVal();
			if(tmp > max){
				winners.clear();
			}
			if(tmp >= max){
				winners.add(i);
			}
			max = tmp > max ? tmp:max;
		}
		return winners.toArray(new Integer[winners.size()]);
	}

	public String[] getPlayerStrength() {
		String[] strengths = new String[players.length];
		for(int i=0;i<strengths.length;i++){
			strengths[i] = players[i].getStrength();
		}
		return strengths;
	}	

	public int getPlayerVal(int pl) {
		return players[pl].getHandVal();
	}

	public Card[] getDealerCards() {
		return dealer.getHandCards();
	}
}

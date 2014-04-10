package com.bscofyp.pokerapp;

import android.util.Log;


public class Game {
	private int endScore;
	private Round round;
	private int stage = 0;
	private int[] playerScores;

	Game(int endscore, int players) {
		playerScores = new int[players];
		endScore = endscore;
		round = new Round(players);
	}

	public void roundEnd(){
		Integer[] winners = round.getWinners();
		Log.d("MyMessage",winners[0]+"-"+(winners.length>1?winners[1]:"")+"-"+round.getDecision(0));
		if(winners.length>1){
			playerScores[0]+=round.getDecision(0)?3:2;
			for(int i=1;i<playerScores.length;i++)
				playerScores[i]+=round.getDecision(0)?2:3;
		}
		else{
			if(winners[0] == 0){
				playerScores[0]+= round.getDecision(0)?5:0;
				playerScores[1]+= round.getDecision(0)?0:3;
			}
			else{
				playerScores[0]+= round.getDecision(0)?0:3;
				playerScores[1]+= round.getDecision(0)?5:0;
			}
		}
		stage++;
	}
	public void fold(int pl){
		round.fold(pl);
	}
	public boolean getPlayerDecision(int pl){
		return round.getDecision(pl);
	}
	public void newRound(){
		for(int i : playerScores){
			if(i>=endScore)
				stage = 6;
			else{
				this.round = new Round(playerScores.length);
				stage = 0;
			}
		}
	}
	
	public Integer[] roundWinners(){
		return round.getWinners();
	}

	public int getEndScore() {
		return endScore;
	}

	public Card[] getPlayerCards(int pl) {
		return round.getPlayerCards(pl);
	}

	public Card[] getDealerCards() {
		return round.getDealerCards();
	}

	public String[] getPlayerStrength() {
		return round.getPlayerStrength();
	}

	public int getPlayerVal(int pl) {
		return round.getPlayerVal(pl);
	}

	public int getStage() {
		return stage;
	}

	public void setStage(int numb) {
		stage = numb;
	}

	public int[] getPlayerScores() {
		return playerScores;
	}
}

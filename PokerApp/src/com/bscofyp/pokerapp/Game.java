package com.bscofyp.pokerapp;


public class Game {
	private int endScore;
	private Round round;
	private int stage = 0;
	private int[] playerScores;

	Game(int endscore,int players) {
		playerScores = new int[players];
		endScore = endscore;
		round = new Round(players);
	}
	public void newRound(){
		this.round = new Round(playerScores.length);
		stage = 0;
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

	public String getPlayerStrength(int pl) {
		return round.getPlayerStrength(pl);
	}

	public int getPlayerVal(int pl) {
		return round.getPlayerVal(pl);
	}
	public int getStage(){
		return stage;
	}
	public void setStage(int numb){
		stage=numb;
	}
	public int[] getPlayerScores(){
		return playerScores;
	}
}

package com.bscofyp.pokerapp;

import java.util.HashMap;
import java.util.Map;

public class GameControl {
	private Game game;
	
	GameControl(Game game){
		this.game = game;
	}
	public int decision(boolean ans){
		int stage = game.getStage();
		if(ans){
			if(stage==5)
				game.roundEnd();
			else
				game.setStage(stage+1);
		}
		else if(stage != 0)
			game.setStage(5);
		
		return game.getStage();
	}
	public int getStage(){
		return game.getStage();
	}

	public int getEndScore(){
		return game.getEndScore();
	}
	public Map<String,Card[]> getGameCards(){
		Map<String,Card[]> map = new HashMap<String,Card[]>();
		map.put("player",game.getPlayerCards(0));
		map.put("dealer",game.getDealerCards());
		map.put("computer",game.getPlayerCards(1));
		return map;
	}

	public int[] getPlayerScores(){
		return game.getPlayerScores();
	}
	public int gameWinner(){
		int max = 0;
		int[] scores = game.getPlayerScores();
		for(int i=0;i<scores.length;i++){
			max = scores[i]>scores[max]?i:max;
		}
		return max;
	}
	
	public Integer[] getRoundWinners(){
		return game.roundWinners();
	}
	
	public String[] getHandTitles(){
		return game.getPlayerStrength();
	}
}

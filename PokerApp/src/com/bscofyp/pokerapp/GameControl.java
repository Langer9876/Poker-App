package com.bscofyp.pokerapp;

import java.util.HashMap;
import java.util.Map;

public class GameControl {
	private Game game;
	

	
	public void setGame(Game game){
		this.game = game;
	}
	/*public GameControl(Game game){
		this.game = game;
	}*/
	public int decision(boolean ans){
		int stage = game.getStage();
		if(ans){
			if(stage==4)
				game.roundEnd();
			else if(stage==5)
				game.newRound();
			else
				game.setStage(stage+1);
		}
		else if(stage != 0){
			if(stage <=4){
				game.setStage(4);
				game.roundEnd();
			}
			else
				game.newRound();
		}
		
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
	
	public String sysMessage(){
		Integer[] winners = getRoundWinners();
		String[] handTitles = getHandTitles();
		if(game.getPlayerDecision(0)){
			if(winners.length>1 && winners[0] == 0){
				return GlobalVars.tips?handTitles[0]+" vs "+handTitles[1]+"\nThe game was a tie, but you continued to play. You gain 3 points, Computer gains 2 points"
						:("Tie round - \n"+handTitles[0]+" vs "+handTitles[1]+"\nYou+3pts\nComputer+2pts");
			}
			else if(winners[0] == 0){
				return GlobalVars.tips?handTitles[0]+" vs "+handTitles[1]+"\nYou played the winning hand and won. You gain 5 points"
						:("You win with \n"+handTitles[0]+" vs "+handTitles[1]+"\nYou +5pts");
			}
			else{
				return GlobalVars.tips?handTitles[0]+" vs "+handTitles[1]+"\nYou played the losing hand, and did not fold. Computer gains 5 points"
						:("You lose with \n"+handTitles[0]+" vs "+handTitles[1]+"\nComputer +5pts");
			}
		}
		else{
			if(winners.length>1 && winners[0] == 0){
				return GlobalVars.tips?handTitles[0]+" vs "+handTitles[1]+"\nIf you had not folded, the game would been a tie. As such, you are awarded 2 points while your "
						+ "opponent gets 3 points, as it was close. Normally you would lose the round"
						:("Tie round - \n"+handTitles[0]+" vs "+handTitles[1]+", but folded\nYou+2pts\nComputer+3pts");
			}
			else if(winners[0] == 0){
				return GlobalVars.tips?handTitles[0]+" vs "+handTitles[1]+"\nYou folded too soon with the winning hand, computer gains 3 points"
						:("You win with \n"+handTitles[0]+" vs "+handTitles[1]+", but folded\nComputer+3pts");
			}
			else{
				return GlobalVars.tips?handTitles[0]+" vs "+handTitles[1]+"\nIf you had continued to play, you would have lost. You folded correctly and gain 3 points"
						:("You lose with \n"+handTitles[0]+" vs "+handTitles[1]+", but folded\nYou+3pts");
			}
		}
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
	public void playerFold(int pl){
		game.fold(pl);
	}
	public Integer[] getRoundWinners(){
		return game.roundWinners();
	}
	
	public String[] getHandTitles(){
		return game.getPlayerStrength();
	}
}

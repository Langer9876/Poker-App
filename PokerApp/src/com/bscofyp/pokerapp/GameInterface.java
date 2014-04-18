package com.bscofyp.pokerapp;

import java.util.Map;

import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.Html;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

public class GameInterface extends CustomMenuActivity {
	public static GameControl game = new GameControl();
	int playTo;


	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_game_interface);

		if (savedInstanceState == null) {
			getSupportFragmentManager().beginTransaction().add(R.id.container, new PlaceholderFragment()).commit();
		}
		if(!GlobalVars.gameActive){
			playTo = getIntent().getExtras().getInt("playTo", 50);
			game.setGame(new Game(playTo,2));
			GlobalVars.gameActive = true;
		}
		setOptions();
	}
	/**
	 * A placeholder fragment containing a simple view.
	 */
	public static class PlaceholderFragment extends Fragment {
		public PlaceholderFragment() {
		}

		@Override
		public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
			View rootView = inflater.inflate(R.layout.fragment_game_interface, container, false);
			setScreen(rootView);
			return rootView;
		}
	}
	
	//play button pressed, proceed to next stage of round
	public void play(View view) {
		int stage = game.decision(true);
		stageFlip(stage);
		setScreen2(view);
		if(stage >= 5){
			roundEnd(view);
		}
		return;
	}
	//fold button pressed, skip to end of round
	public void fold(View view) {
		int pre = game.getStage();
		if(pre == 0 || pre >= 5)
			return;
		game.playerFold(0);
		int stage = game.decision(false);
		for (int i = pre + 1; i <= stage; i++) {
			stageFlip(i);
		}
		setScreen2(view);
		roundEnd(view);
		return;
	}
	public void roundEnd(View view){
		int[] scores = game.getPlayerScores();
		int endScore = game.getEndScore();
		String sysMessage = game.sysMessage();
		Resources res = getResources();
		((TextView)findViewById(R.id.endScore)).setText(Html.fromHtml(String.format(res.getString(R.string.endScore), endScore)));
		((TextView)findViewById(R.id.player1)).setText(Html.fromHtml(String.format(res.getString(R.string.player_score), scores[0])));
		((TextView)findViewById(R.id.computer)).setText(Html.fromHtml(String.format(res.getString(R.string.computer_score), scores[1])));
		if(game.getStage() == 6){
			gameEnd();
		}
		else
			print(sysMessage);
	}
	
	// TODO Create winner screen
	public void gameEnd(){
		Intent intent = new Intent(this,EndScreen.class);
		int winner = game.gameWinner();
		if(winner == 0){
			intent.putExtra("result", true);
		}
		else{
			intent.putExtra("result", false);
		}
		GlobalVars.gameActive = false;
		startActivity(intent);
		this.finish();
	}
	
	public void print(CharSequence txt){
		if(GlobalVars.tips){
			alert(txt);
		}
		else{
			toast(txt);
		}
	}
	public void alert(CharSequence txt){
		new AlertDialog.Builder(this)
	    .setTitle("Tips")
	    .setMessage(txt)
	    .setPositiveButton(android.R.string.yes, null)
	    .setIcon(R.drawable.ic_launcher)
	     .show();
	}
	public void toast(CharSequence txt){
		Context context = getApplicationContext();
		int duration = Toast.LENGTH_SHORT;
		/*Toast toast = Toast.makeText(context, txt, duration);
		toast.setGravity(Gravity.CENTER_VERTICAL, 0, 0);
		toast.show();*/
        Toast toast = Toast.makeText(context,txt, duration);
        toast.setGravity(Gravity.CENTER, 0,0);

        TextView textView = new TextView(context);
        textView.setBackgroundColor(Color.DKGRAY);
        textView.setTextColor(Color.WHITE);
        textView.setTextSize(25);
        //Typeface typeface = Typeface.create("serif", Typeface.BOLD);
        //textView.setTypeface(typeface);
        textView.setPadding(10, 10, 10, 10);
        textView.setText(txt);

        toast.setView(textView);
        toast.show();
	}
	
	//select front & back of card (2 separate imageview)
	//hide one while showing second to animate flip effect
	public void flip(View cardHolder) {
		LinearLayout lin = (LinearLayout) cardHolder;
		ImageView cardFace = (ImageView) lin.getChildAt(0);
		ImageView cardBack = (ImageView) lin.getChildAt(1);
		FlipAnimation flipAnimation = new FlipAnimation(cardFace, cardBack);

		if (cardFace.getVisibility() == View.GONE) {
			flipAnimation.reverse();
		}
		lin.startAnimation(flipAnimation);
	}
	//call on flip based on what stage of round currently in
	public void stageFlip(int stage) {
		LinearLayout dealer = (LinearLayout) findViewById(R.id.linearLayoutDealerCards);
		LinearLayout player = (LinearLayout) findViewById(R.id.linearLayoutPlayerCards);
		LinearLayout computer = (LinearLayout) findViewById(R.id.linearLayoutComputerCards);
		//round 0 - hide all cards
		if (stage == 0) {
			for (int i = 0; i < 5; i++) {
				if (i < 2) {
					flip(player.getChildAt(i));
					flip(computer.getChildAt(i));
				}
				flip(dealer.getChildAt(i));
			}
		}
		// stage 1 - show player cards
		if (stage == 1) {
			flip(player.getChildAt(0));
			flip(player.getChildAt(1));
		}
		// stage 2 - flop (first 3 community cards)
		if (stage == 2) {
			flip(dealer.getChildAt(0));
			flip(dealer.getChildAt(1));
			flip(dealer.getChildAt(2));
		}
		// stage 3 - turn (4th community card)
		if (stage == 3) {
			flip(dealer.getChildAt(3));
		}
		// stage 4 - river (5th community card)
		if (stage == 4) {
			flip(dealer.getChildAt(4));
		}
		// stage 5 - show opponent cards
		if (stage == 5) {
			flip(computer.getChildAt(0));
			flip(computer.getChildAt(1));
		}
	}
	// select fragment view elements for population at create 
	public static void setScreen(View view) {
		LinearLayout dealer = (LinearLayout) view.findViewById(R.id.linearLayoutDealerCards);
		LinearLayout player = (LinearLayout) view.findViewById(R.id.linearLayoutPlayerCards);
		LinearLayout computer = (LinearLayout) view.findViewById(R.id.linearLayoutComputerCards);
		int[] scores = game.getPlayerScores();
		int endScore = game.getEndScore();
		((TextView)view.findViewById(R.id.endScore)).setText(Html.fromHtml(String.format(view.getContext().getString(R.string.endScore), endScore)));
		((TextView)view.findViewById(R.id.player1)).setText(Html.fromHtml(String.format(view.getContext().getString(R.string.player_score), scores[0])));
		((TextView)view.findViewById(R.id.computer)).setText(Html.fromHtml(String.format(view.getContext().getString(R.string.computer_score), scores[1])));
		populateCards(view, dealer, player, computer);
	}
	// select fragment view elements for population after creation (player actions)
	public void setScreen2(View view) {
		LinearLayout dealer = (LinearLayout) findViewById(R.id.linearLayoutDealerCards);
		LinearLayout player = (LinearLayout) findViewById(R.id.linearLayoutPlayerCards);
		LinearLayout computer = (LinearLayout) findViewById(R.id.linearLayoutComputerCards);
		int[] scores = game.getPlayerScores();
		int endScore = game.getEndScore();
		Resources res = getResources();
		Log.d("Score", endScore+"/"+scores[0]+"/"+scores[1]);
		((TextView)findViewById(R.id.endScore)).setText(Html.fromHtml(String.format(res.getString(R.string.endScore), endScore)));
		((TextView)findViewById(R.id.player1)).setText(Html.fromHtml(String.format(res.getString(R.string.player_score), scores[0])));
		((TextView)findViewById(R.id.computer)).setText(Html.fromHtml(String.format(res.getString(R.string.computer_score), scores[1])));
		populateCards(view, dealer, player, computer);
	}
	// populate screen views taking current context as input (at/after creation)
	public static void populateCards(View view, LinearLayout dealer, LinearLayout player, LinearLayout computer) {
		int src, i = 0;
		Log.d("Message", "MF'ing i man: " + i);
		ImageView imgFront = null, imgBack = null;
		int stage = game.getStage();
		Map<String, Card[]> cards = game.getGameCards();
		for (String x : cards.keySet()) {
			i = 0;
			for (Card y : cards.get(x)) {
				src = view.getResources().getIdentifier("drawable/" + y.toString(), null, view.getContext().getPackageName());
				if (x == "dealer") {
					imgBack = (ImageView) ((LinearLayout) dealer.getChildAt(i)).getChildAt(0);
					imgFront = (ImageView) ((LinearLayout) dealer.getChildAt(i)).getChildAt(1);
					if ((stage >= 2 && i < 3) || (stage >= 3 && i < 4) || (stage >= 4)) {
						imgBack.setVisibility(8);
						imgFront.setVisibility(0);
					} else {
						imgBack.setVisibility(0);
						imgFront.setVisibility(8);
					}
				}
				if (x == "player") {
					imgBack = (ImageView) ((LinearLayout) player.getChildAt(i)).getChildAt(0);
					imgFront = (ImageView) ((LinearLayout) player.getChildAt(i)).getChildAt(1);
					if (stage >= 1) {
						imgBack.setVisibility(8);
						imgFront.setVisibility(0);
					} else {
						imgBack.setVisibility(0);
						imgFront.setVisibility(8);
					}
				}
				if (x == "computer") {
					imgBack = (ImageView) ((LinearLayout) computer.getChildAt(i)).getChildAt(0);
					imgFront = (ImageView) ((LinearLayout) computer.getChildAt(i)).getChildAt(1);
					if (stage == 5) {
						imgBack.setVisibility(8);
						imgFront.setVisibility(0);
					} else {
						imgBack.setVisibility(0);
						imgFront.setVisibility(8);
					}
				}
				imgFront.setImageResource(src);
				Log.d("Message", x + "/Card +" + i + " Populated - Clear");
				Log.d("Message", "Image src: " + y.toString());
				i++;
			}
		}
	}
}
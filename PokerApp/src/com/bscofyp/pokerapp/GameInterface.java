package com.bscofyp.pokerapp;

import java.util.Map;

import android.content.res.Resources;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.ActionBarActivity;
import android.text.Html;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

public class GameInterface extends ActionBarActivity {
	static GameControl game = new GameControl(new Game(100, 2));

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_game_interface);

		if (savedInstanceState == null) {
			getSupportFragmentManager().beginTransaction().add(R.id.container, new PlaceholderFragment()).commit();
		}

	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {

		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.game_interface, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.action_settings) {
			return true;
		}
		return super.onOptionsItemSelected(item);
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
		return;
	}
	//fold button pressed, skip to end of round
	public void fold(View view) {
		int pre = game.getStage();
		int stage = game.decision(false);
		for (int i = pre + 1; i <= stage; i++) {
			stageFlip(i);
		}
		return;
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
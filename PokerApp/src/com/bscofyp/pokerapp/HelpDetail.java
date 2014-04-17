package com.bscofyp.pokerapp;

import android.graphics.Color;
import android.os.Bundle;
import android.text.Html;
import android.view.Gravity;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.TextSwitcher;
import android.widget.TextView;
import android.widget.ViewSwitcher.ViewFactory;

public class HelpDetail extends CustomMenuActivity {
	TextSwitcher mSwitcher;
	Button btnNext, btnPrev, btnClose;
	Bundle extras;
	int currentIndex = 0;
	int messageCount;
	String textToShow[];
	int helpNumb;

	// Array of String to Show In TextSwitcher
	String allText[][] = {
			{
					"This poker app was designed and developed by Padraig O'Regan, Kelvin Stuart & Hamza Ardo, for the purposes of taking a classic game such as poker, and to alter the gameplay slightly in a way which takes the focus off the gambling aspect, and still remain as a fun game.",
					"Poker is a game where players compete against each other using the two cards which they are assigned in conjunction with the five community cards on the table. While knowing that most of the cards available are out in full view to all players.",
					"Players are encouraged to weigh their own odds of winning in order to best their opponent." },
			{
					"At the fist stage of play, each player is assigned two cards each.",
					"Following this is the flop, where three community cards are dealt on the table.",
					"After this is the turn, where another card is dealt to the table.",
					"Finally the river, which is the fifth & last card drawn to the table.",
					"These table cards (also known as community cards) can be used by all players, in conjunction with their own starting two cards in order to forum a 5 card set, which has it's own cumulative strength, and the winner will be the player with the best hand.",
					"Typically, between each stage of play is a session where players bet against each other in order to achieve higher gains or bluff the other players into folding out of the round. In our game however, points are awarded based on the decision of the player, taking into account whether they continue to play or fold correctly." },
			{ 
					"Points are awarded based on performance of play:", 
					"If you play and win a hand, you gain 5 points.",
					"If you play and lose a hand, your opponent gains 5 points.",
					"If you play and tie, you gain 3 points, while your opponent gains 2 points.",
					"If you fold and win a hand, your opponent gains 3 points.", "If you fold and lose a hand, you gain 3 points.",
					"If you fold and tie, you gain 2 points, while your opponent gains 3 points." }, {} };

	// to keep current Index of text
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		setContentView(R.layout.fragment_help_detail);

		// get The references
		btnNext = (Button) findViewById(R.id.buttonNext);
		btnPrev = (Button) findViewById(R.id.buttonPrev);
		btnClose = (Button) findViewById(R.id.close);
		mSwitcher = (TextSwitcher) findViewById(R.id.textSwitcher);

		extras = getIntent().getExtras();
		helpNumb = extras.getInt("helpNumb", 0);
		textToShow = allText[helpNumb];
		messageCount = textToShow.length;

		if (helpNumb == 3) {
			mSwitcher.setBackground(getResources().getDrawable(R.drawable.pokerhands));
			btnNext.setVisibility(View.INVISIBLE);
			btnPrev.setVisibility(View.INVISIBLE);
		}

		// Set the ViewFactory of the TextSwitcher that will create TextView
		// object when asked
		mSwitcher.setFactory(new ViewFactory() {

			public View makeView() {
				// TODO Auto-generated method stub
				// create new textView and set the properties like clolr, size
				// etc
				TextView myText = new TextView(HelpDetail.this);
				// myText.setGravity(Gravity.TOP | Gravity.CENTER_HORIZONTAL);
				myText.setTextSize(25);
				myText.setTextColor(Color.WHITE);
				myText.setBackgroundColor(Color.BLACK);
				myText.setGravity(Gravity.CENTER_VERTICAL | Gravity.CENTER_HORIZONTAL);
				return myText;
			}
		});

		// Declare the in and out animations and initialize them
		Animation in = AnimationUtils.loadAnimation(this, android.R.anim.slide_in_left);
		Animation out = AnimationUtils.loadAnimation(this, android.R.anim.slide_out_right);

		// set the animation type of textSwitcher
		mSwitcher.setInAnimation(in);
		mSwitcher.setOutAnimation(out);

		// ClickListener for NEXT button
		// When clicked on Button TextSwitcher will switch between texts
		// The current Text will go OUT and next text will come in with
		// specified animation
		btnNext.setOnClickListener(new View.OnClickListener() {

			public void onClick(View v) {
				// TODO Auto-generated method stub
				currentIndex++;
				// If index reaches maximum reset it
				if (currentIndex == messageCount)
					currentIndex = 0;
				mSwitcher.setText(Html.fromHtml(currentIndex + 1 + "/" + messageCount + "<br />" + textToShow[currentIndex]));
			}
		});
		btnPrev.setOnClickListener(new View.OnClickListener() {

			public void onClick(View v) {
				// TODO Auto-generated method stub
				currentIndex--;
				// If index reaches maximum reset it
				if (currentIndex < 0)
					currentIndex = messageCount - 1;
				mSwitcher.setText(Html.fromHtml(currentIndex + 1 + "/" + messageCount + "<br />" + textToShow[currentIndex]));
			}
		});
		btnClose.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				finish();
			}
		});
		if (helpNumb != 3) {
			mSwitcher.setText(Html.fromHtml(currentIndex + 1 + "/" + messageCount + "<br />" + textToShow[0]));
		}

	}
}

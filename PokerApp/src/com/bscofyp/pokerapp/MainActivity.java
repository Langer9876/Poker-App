package com.bscofyp.pokerapp;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;

public class MainActivity extends CustomMenuActivity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		if (savedInstanceState == null) {
			getSupportFragmentManager().beginTransaction().add(R.id.container, new PlaceholderFragment()).commit();
		}
		setOptions();
	}

	/**
	 * A placeholder fragment containing a simple view.
	 */
	public static class PlaceholderFragment extends Fragment {
		Button resume;
		public PlaceholderFragment() {
		}

		@Override
		public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
			View rootView = inflater.inflate(R.layout.fragment_main, container, false);
			resume = (Button)rootView.findViewById(R.id.resume);
			if(GlobalVars.gameActive)
				resume.setVisibility(View.VISIBLE);
			return rootView;
		}
		@Override
		public void onResume() {
			super.onResume();
			Log.d("Message",""+GlobalVars.gameActive);
			if(GlobalVars.gameActive)
				resume.setVisibility(View.VISIBLE);
			else resume.setVisibility(View.GONE);
		}
	}
	public void resumeGame(View view){
		startGame(view);
	}
	public void newGame(View view){
		final View v = view;
		if(GlobalVars.gameActive){
			new AlertDialog.Builder(this)
		    .setTitle("New Game")
		    .setMessage("A game already exits. Continue to erase the previous game and start a new one?")
		    .setPositiveButton(android.R.string.yes,new OnClickListener() {
				@Override
				public void onClick(DialogInterface dialog, int which) {
					GlobalVars.gameActive = false;
					startGame(v);
				}
			})
		    .setNegativeButton(android.R.string.no, null)
		    .setIcon(R.drawable.ic_launcher)
		     .show();
		}
		else
			startGame(v);
	}
	public void startGame(View view) {
		final Intent intent = new Intent(this, GameInterface.class);
		if(!GlobalVars.gameActive){
			final Spinner input = new Spinner(this);
			Integer[] items = new Integer[]{10,20,30,50,75,100,150,200,250,500};
			input.setAdapter(new ArrayAdapter<Integer>(this, android.R.layout.simple_spinner_dropdown_item, items));
			input.setSelection(3);
			new AlertDialog.Builder(this)
		    .setTitle("New Game")
		    .setMessage("How many points do you want to play until:")
		    .setView(input)
		    .setPositiveButton(android.R.string.yes,new OnClickListener() {
				@Override
				public void onClick(DialogInterface dialog, int which) {
					int t = Integer.parseInt(input.getSelectedItem().toString());
					intent.putExtra("playTo", t);
					startActivity(intent);
				}
			})
		    .setIcon(R.drawable.ic_launcher)
		     .show();
		}
		else{
			startActivity(intent);
		}
	}

	public void showHelp(View view) {
		Intent intent = new Intent(this, HelpOverview.class);
		startActivity(intent);
	}
}

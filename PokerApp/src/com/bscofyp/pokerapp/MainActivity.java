package com.bscofyp.pokerapp;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

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

		public PlaceholderFragment() {
		}

		@Override
		public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
			View rootView = inflater.inflate(R.layout.fragment_main, container, false);
			return rootView;
		}
	}
	public void resumeGame(View view){
		startGame(view);
	}
	public void newGame(View view){
		startGame(view);
	}
	public void startGame(View view) {
		Intent intent = new Intent(this, GameInterface.class);
		startActivity(intent);
	}

	public void showHelp(View view) {
		Intent intent = new Intent(this, HelpCategoryListActivity.class);
		startActivity(intent);
	}
}

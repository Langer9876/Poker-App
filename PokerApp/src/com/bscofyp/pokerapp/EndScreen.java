package com.bscofyp.pokerapp;

import android.app.Fragment;
import android.content.res.Resources;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.TextView;

public class EndScreen extends CustomMenuActivity {
	static boolean result;
	static Resources res;
	static String pack;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_end_screen);

		if (savedInstanceState == null) {
			getFragmentManager().beginTransaction().add(R.id.container, new PlaceholderFragment()).commit();
		}
		res = getResources();
		pack = getPackageName();
		result = getIntent().getExtras().getBoolean("result");
		setOptions();
		final MediaPlayer mp = result? MediaPlayer.create(this, R.raw.win):MediaPlayer.create(this, R.raw.lose);;
		if(GlobalVars.sound)
			mp.start();
	}
	/**
	 * A placeholder fragment containing a simple view.
	 */
	public static class PlaceholderFragment extends Fragment {

		public PlaceholderFragment() {
		}

		@Override
		public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
			View rootView = inflater.inflate(R.layout.fragment_end_screen, container, false);
			TextView t = (TextView)rootView.findViewById(R.id.result);
			if(result){
				t.setText("You Win.");
				rootView.setBackground(getResources().getDrawable(R.drawable.winner));
			}
			else{
				t.setText("You Lose.");
				rootView.setBackground(getResources().getDrawable(R.drawable.loser));
			}
			t.startAnimation((Animation)AnimationUtils.loadAnimation(rootView.getContext(),R.anim.scrolltext));
			return rootView;
		}
	}
}

package com.bscofyp.pokerapp;

import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class HelpOverview extends CustomMenuActivity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_help_overview);

		if (savedInstanceState == null) {
			getFragmentManager().beginTransaction().add(R.id.container, new PlaceholderFragment()).commit();
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
			View rootView = inflater.inflate(R.layout.fragment_help_overview, container, false);
			return rootView;
		}
	}
	public void helpDetail(View view){
		int helpNumb;
		switch(view.getId()){
		case R.id.imageButton1: helpNumb = 0; break;
		case R.id.imageButton2: helpNumb = 1; break;
		case R.id.imageButton3: helpNumb = 3; break;
		case R.id.imageButton4: helpNumb = 2; break;
		default: return;
		}
		Intent intent = new Intent(this,HelpDetail.class);
		intent.putExtra("helpNumb", helpNumb);
		startActivity(intent);
	}

}

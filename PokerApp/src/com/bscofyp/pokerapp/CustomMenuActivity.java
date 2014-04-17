package com.bscofyp.pokerapp;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

public class CustomMenuActivity extends ActionBarActivity{
	MenuItem tipsCheck;
	@Override
	protected void onResume() {
		setOptions();
		super.onResume();
	}
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {  
		MenuInflater inflater = getMenuInflater();
		inflater.inflate(R.menu.main, menu);
		tipsCheck = menu.findItem(R.id.tips);
		tipsCheck.setChecked(GlobalVars.tips);
		return true;
	}
	@Override
	public boolean onMenuOpened(int featureId, Menu menu) {
	//tipsCheck.setChecked(GlobalVars.tips);
		return super.onMenuOpened(featureId, menu);
	}
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {  
		switch(item.getItemId()) {
			case R.id.help:
				showHelp();
				break;
			case R.id.tips:
				item.setChecked(!item.isChecked());
				GlobalVars.tips = item.isChecked();
				break;
			case R.id.orien_land:
				setLandscape();
				break;
			case R.id.orien_port:
				setPortrait();
				break;
			case R.id.orien_normal:
				setNormal();
				break;
			case R.id.back:
				this.finish();
			default:
				break;
		}
		//return true;
		return true;
	}

	private void showHelp(){
		Intent intent = new Intent(this, HelpOverview.class);
		startActivity(intent);
	}
	private void setLandscape(){
		GlobalVars.orientation = 1;
		setOptions();
	}
	private void setPortrait(){
		GlobalVars.orientation = -1;
		setOptions();
	}
	private void setNormal(){
		GlobalVars.orientation = 0;
		setOptions();
	}
	
	protected void setOptions(){
		switch(GlobalVars.orientation){
		case 1:
			setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
			break;
		case -1:
			setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
			break;
		case 0:
			setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_SENSOR);
			break;
		}
	}
}

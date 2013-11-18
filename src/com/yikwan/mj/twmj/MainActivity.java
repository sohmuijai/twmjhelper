package com.yikwan.mj.twmj;

import android.app.ActionBar;
import android.app.ActionBar.Tab;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.view.Menu;

public class MainActivity extends FragmentActivity implements
		ActionBar.TabListener {

	private static final String STATE_SELECTED_NAVIGATION_ITEM = "selected_navigation_item";
	CalculatorFragment calcFragment;
	ThisGameFragment thisGameFragment;
	HistoryFragment gameHistoryFragment;
	ReferenceFragment lookupFragment;

	// static final String STATE_PULL_SCORES = "statePullScores";
	// ArrayList<Integer> mPullScoresList = new ArrayList<Integer>();
	// static final String STATE_CALC_TOTAL = "stateCalcTotal";
	// int mTotal;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		Log.v("MainActivity", "onCreate()");
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		// Set up the action bar.
		final ActionBar actionBar = getActionBar();
		actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);

		calcFragment = new CalculatorFragment();
		thisGameFragment = new ThisGameFragment();
		gameHistoryFragment = new HistoryFragment();
		lookupFragment = new ReferenceFragment();

		// For each of the sections in the app, add a tab to the action bar.
		actionBar.addTab(actionBar.newTab().setText(R.string.title_calculator)
				.setTabListener(this));
		actionBar.addTab(actionBar.newTab().setText(R.string.title_thisgame)
				.setTabListener(this));
		actionBar.addTab(actionBar.newTab().setText(R.string.title_history)
				.setTabListener(this));
		actionBar.addTab(actionBar.newTab().setText(R.string.title_reference)
				.setTabListener(this));
	}

	@Override
	public void onRestoreInstanceState(Bundle savedInstanceState) {
		Log.v("MainActivity", "onRestoreInstanceState()");
		if (savedInstanceState.containsKey(STATE_SELECTED_NAVIGATION_ITEM)) {
			getActionBar().setSelectedNavigationItem(
					savedInstanceState.getInt(STATE_SELECTED_NAVIGATION_ITEM));
		}
	}

	@Override
	public void onSaveInstanceState(Bundle outState) {
		Log.v("MainActivity", "onSaveInstanceState()");
		outState.putInt(STATE_SELECTED_NAVIGATION_ITEM, getActionBar()
				.getSelectedNavigationIndex());
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	// @Override
	// public boolean onOptionsItemSelected(MenuItem item) {
	// // Handle presses on the action bar items
	// switch (item.getItemId()) {
	// case R.id.action_reference:
	// openReference();
	// return true;
	// case R.id.action_settings:
	// openSettings();
	// return true;
	// default:
	// return super.onOptionsItemSelected(item);
	// }
	// }

	public void openReference() {
	}

	public void openSettings() {

	}

	@Override
	public void onTabReselected(Tab arg0, FragmentTransaction arg1) {
	}

	@Override
	public void onTabSelected(Tab tab, FragmentTransaction ft) {

		/**
		 * On first tab we will show our list
		 */
		if (tab.getPosition() == 0) {
			// Bundle args = new Bundle();
			// args.putIntegerArrayList(CalculatorFragment.STATE_PULL_SCORES,
			// mPullScoresList);
			// fragment.setArguments(args);
			android.support.v4.app.FragmentTransaction transaction = getSupportFragmentManager()
					.beginTransaction();
			if (calcFragment.isAdded()) {
				transaction.show(calcFragment);
			} else {
				transaction.addToBackStack(null);
				transaction.replace(R.id.container, calcFragment);
			}
			transaction.commit();
		} else if (tab.getPosition() == 1) {
			android.support.v4.app.FragmentTransaction transaction = getSupportFragmentManager()
					.beginTransaction();
			if (thisGameFragment.isAdded()) {
				transaction.show(thisGameFragment);
			} else {
				transaction.addToBackStack(null);
				transaction.replace(R.id.container, thisGameFragment);
			}
			transaction.commit();
		} else if (tab.getPosition() == 2) {
			android.support.v4.app.FragmentTransaction transaction = getSupportFragmentManager()
					.beginTransaction();
			if (gameHistoryFragment.isAdded()) {
				transaction.show(gameHistoryFragment);
			} else {
				transaction.addToBackStack(null);
				transaction.replace(R.id.container, gameHistoryFragment);
			}
			transaction.commit();
		} else if (tab.getPosition() == 3) {
			android.support.v4.app.FragmentTransaction transaction = getSupportFragmentManager()
					.beginTransaction();
			if (lookupFragment.isAdded()) {
				transaction.show(lookupFragment);
			} else {
				transaction.addToBackStack(null);
				transaction.replace(R.id.container, lookupFragment);
			}
			transaction.commit();
		}
	}

	@Override
	public void onTabUnselected(Tab arg0, FragmentTransaction arg1) {
	}

}

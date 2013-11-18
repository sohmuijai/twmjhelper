package com.yikwan.mj.twmj;

import java.util.ArrayList;

import android.app.ActionBar;
import android.app.ActionBar.Tab;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.view.Menu;
import android.view.View;
import android.view.ViewGroup.LayoutParams;
import android.widget.EditText;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends FragmentActivity implements
		ActionBar.TabListener {

	private static final String STATE_SELECTED_NAVIGATION_ITEM = "selected_navigation_item";
	static final String STATE_PULL_SCORES = "statePullScores";
	ArrayList<Integer> mPullScoresList = new ArrayList<Integer>();
	static final String STATE_CALC_TOTAL = "stateCalcTotal";
	int mTotal;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		// Set up the action bar.
		final ActionBar actionBar = getActionBar();
		actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);

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
		if (savedInstanceState.containsKey(STATE_SELECTED_NAVIGATION_ITEM)) {
			getActionBar().setSelectedNavigationItem(
					savedInstanceState.getInt(STATE_SELECTED_NAVIGATION_ITEM));
		}
	}

	@Override
	public void onSaveInstanceState(Bundle outState) {
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
			CalculatorFragment fragment = new CalculatorFragment();
			Bundle args = new Bundle();
			args.putIntegerArrayList(CalculatorFragment.STATE_PULL_SCORES,
					mPullScoresList);
			fragment.setArguments(args);
			getSupportFragmentManager().beginTransaction()
					.replace(R.id.container, fragment).commit();
		} else if (tab.getPosition() == 1) {
			ThisGameFragment fragment = new ThisGameFragment();
			getSupportFragmentManager().beginTransaction()
					.replace(R.id.container, fragment).commit();
		} else if (tab.getPosition() == 2) {
			HistoryFragment fragment = new HistoryFragment();
			getSupportFragmentManager().beginTransaction()
					.replace(R.id.container, fragment).commit();
		} else if (tab.getPosition() == 3) {
			ReferenceFragment fragment = new ReferenceFragment();
			getSupportFragmentManager().beginTransaction()
					.replace(R.id.container, fragment).commit();
		}
	}

	@Override
	public void onTabUnselected(Tab arg0, FragmentTransaction arg1) {
	}

	public void calcAddScore(View view, CalculatorFragment fragment) {
		fragment.calcAddScore(view);
	}

	public void calcFinish(View view) {
		EditText result_text = (EditText) findViewById(R.id.result_text);
		// Log.v("debug", "result_text : " + result_text);
		// Log.v("debug", "result_text : " + result_text.getText());
		mTotal = calculateAndReset();
		result_text.setText("" + mTotal);
	}

	public void calcHalve(View view) {
		EditText result_text = (EditText) findViewById(R.id.result_text);
		result_text.setText("" + Math.round(mTotal / 2));
	}

	private void addScore() {
		EditText newScore_input = (EditText) findViewById(R.id.newScore_input);
		if (null == newScore_input.getText()) {
			return;
		}
		String newScore_s = newScore_input.getText().toString();

		try {
			Integer newScore = Integer.valueOf(newScore_s);
			mPullScoresList.add(newScore);
		} catch (NumberFormatException e) {
			Toast.makeText(getApplicationContext(),
					"new score [" + newScore_s + "] is not numeric!",
					Toast.LENGTH_SHORT).show();
			return;
		}

		// update table of laai-jong history
		TableLayout history_table = (TableLayout) findViewById(R.id.history_table);
		TableRow row;
		if (mPullScoresList.size() % 5 == 1) {
			row = new TableRow(this);
			history_table.addView(row, new TableLayout.LayoutParams(
					LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT));
		} else {
			row = (TableRow) history_table.getChildAt(history_table
					.getChildCount() - 1);
		}
		TextView newScore = new TextView(this);
		newScore.setPadding(5, 5, 5, 5);
		newScore.setText(newScore_s);
		if (null != row)
			row.addView(newScore);

		newScore_input.setText("");
	}

	private void redrawCalcHistory() {
		TableLayout history_table = (TableLayout) findViewById(R.id.history_table);
		TableRow row;
		for (int i = 0; i < mPullScoresList.size(); i++) {
			if (i % 5 == 1) {
				row = new TableRow(this);
				history_table.addView(row, new TableLayout.LayoutParams(
						LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT));
			} else {
				row = (TableRow) history_table.getChildAt(history_table
						.getChildCount() - 1);
			}
			TextView newScore = new TextView(this);
			newScore.setPadding(5, 5, 5, 5);
			newScore.setText(mPullScoresList.get(i).toString());
			if (null != row)
				row.addView(newScore);
		}
	}

	private int calculateAndReset() {
		if (null == this.mPullScoresList) {
			return 0;
		}

		int total = 0;
		for (int i = 0; i < mPullScoresList.size(); i++) {
			total = (int) (Math.round((total * 1.5) + mPullScoresList.get(i)));
		}
		mPullScoresList.clear();
		return total;
	}
}

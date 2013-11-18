package com.yikwan.mj.twmj;

import java.util.ArrayList;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.widget.EditText;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

public class CalculatorFragment extends Fragment {

	static final String STATE_PULL_SCORES = "statePullScores";
	ArrayList<Integer> mPullScoresList = new ArrayList<Integer>();
	static final String STATE_CALC_TOTAL = "stateCalcTotal";
	int mTotal;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		return inflater.inflate(R.layout.fragment_calculator, container, false);
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		redrawCalcHistory();
	}

	public void calcAddScore(View view) {
		addScore();
	}

	public void calcFinish(View view) {
		EditText result_text = (EditText) getActivity().findViewById(
				R.id.result_text);
		// Log.v("debug", "result_text : " + result_text);
		// Log.v("debug", "result_text : " + result_text.getText());
		mTotal = calculateAndReset();
		result_text.setText("" + mTotal);
	}

	public void calcHalve(View view) {
		EditText result_text = (EditText) getActivity().findViewById(
				R.id.result_text);
		result_text.setText("" + Math.round(mTotal / 2));
	}

	private void addScore() {
		EditText newScore_input = (EditText) getActivity().findViewById(
				R.id.newScore_input);
		if (null == newScore_input.getText()) {
			return;
		}
		String newScore_s = newScore_input.getText().toString();

		try {
			Integer newScore = Integer.valueOf(newScore_s);
			mPullScoresList.add(newScore);
		} catch (NumberFormatException e) {
			Toast.makeText(getActivity().getApplicationContext(),
					"new score [" + newScore_s + "] is not numeric!",
					Toast.LENGTH_SHORT).show();
			return;
		}

		// update table of laai-jong history
		TableLayout history_table = (TableLayout) getActivity().findViewById(
				R.id.history_table);
		TableRow row;
		if (mPullScoresList.size() % 5 == 1) {
			row = new TableRow(getActivity());
			history_table.addView(row, new TableLayout.LayoutParams(
					LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT));
		} else {
			row = (TableRow) history_table.getChildAt(history_table
					.getChildCount() - 1);
		}
		TextView newScore = new TextView(getActivity());
		newScore.setPadding(5, 5, 5, 5);
		newScore.setText(newScore_s);
		if (null != row)
			row.addView(newScore);

		newScore_input.setText("");
	}

	private void redrawCalcHistory() {
		TableLayout history_table = (TableLayout) getActivity().findViewById(
				R.id.history_table);
		TableRow row;
		for (int i = 0; i < mPullScoresList.size(); i++) {
			if (i % 5 == 1) {
				row = new TableRow(getActivity());
				history_table.addView(row, new TableLayout.LayoutParams(
						LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT));
			} else {
				row = (TableRow) history_table.getChildAt(history_table
						.getChildCount() - 1);
			}
			TextView newScore = new TextView(getActivity());
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

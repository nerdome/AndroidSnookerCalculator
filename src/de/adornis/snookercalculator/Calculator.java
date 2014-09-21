package de.adornis.snookercalculator;

import android.os.AsyncTask;
import android.util.Log;
import android.widget.TextView;

/**
 * Created by fightcookie on 9/9/2014.
 */
class Calculator extends AsyncTask<Integer, Void, Integer> {

	private final TextView textview;

	Calculator(TextView newTextView) {
		textview = newTextView;
	}

	@Override
	protected Integer doInBackground(Integer... params) {
		int player = params[0];
		int ballValue = params[1];

		try {
			Thread.sleep(5 * 1000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		Log.e("Calculator AsyncTask doInBackground", "asynctask after sleeping for 5 seconds");

		Table table1 = new Table();
		table1.resetTable();
		return table1.remainingPoints();
	}

	@Override
	protected void onPostExecute(Integer remainingPoints) {
//		try {
//			Thread.sleep(5000);
//		} catch (InterruptedException e) {
//			e.printStackTrace();
//		}
		Log.e("Calculator AsyncTask onPostExecute", "asynctask after sleeping for 5 seconds");

		textview.setText(String.valueOf(remainingPoints));
		String remainingPointsFromTextView = (String) textview.getText();
		Log.e("onPostExecute", remainingPointsFromTextView);
	}
}

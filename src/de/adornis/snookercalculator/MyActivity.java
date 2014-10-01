package de.adornis.snookercalculator;

import android.app.Activity;
import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class MyActivity extends Activity {

	private Table table1;
	private Handler uiHandler;

	private TestService testService;
	private ServiceConnection serviceConnection = new ServiceConnection () {

		@Override
		public void onServiceConnected(ComponentName name, IBinder service) {
			Log.e("MyActivity", "onServiceConnected");
			TestService.TestBinder testBinder = (TestService.TestBinder) service;
			testService = testBinder.getService();
		}

		@Override
		public void onServiceDisconnected(ComponentName name) {
			Log.e("MyActivity", "onServiceDisconnected");
		}
	};


	/**
	 * Called when the activity is first created.
	 */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);

		Intent serviceIntent = new Intent(this, TestService.class);
		bindService(serviceIntent, serviceConnection, BIND_AUTO_CREATE);
		Log.e("MyActivity", "bound service...hopefully?");
		Log.e("MyActivity", testService != null ? "service exists" : "nooooooo");

		table1 = new Table();
		table1.resetTable();
		uiHandler = new Handler() {
			@Override
			public void handleMessage(Message msg) {
				Bundle bundle = msg.getData();
				int scorePlayer1 = bundle.getInt("scorePlayer1");
				int scorePlayer2 = bundle.getInt("scorePlayer2");
				int remainingPoints = bundle.getInt("remainingPoints");
				((TextView) findViewById(R.id.scorePlayer1)).setText("Score: " + String.valueOf(scorePlayer1));
				((TextView) findViewById(R.id.scorePlayer2)).setText("Score: " + String.valueOf(scorePlayer2));
				((TextView) findViewById(R.id.remainingPointsTextView)).setText("Remaining points: " + String.valueOf(remainingPoints));
				if (remainingPoints == 0) {
					TextView winnerTextView = (TextView) findViewById(R.id.winner);
					if (scorePlayer1 > scorePlayer2) {
						winnerTextView.setText("Player 1 won!");
					} else if (scorePlayer2 > scorePlayer1) {
						winnerTextView.setText("Player 2 won!");
					} else {
						winnerTextView.setText("Tie! Respotted Black!");
					}
				}
			}
		};
	}

	public void calcResult(View ballValueSubmitButtonView) {
		String player = (String) ballValueSubmitButtonView.getTag();
		new Thread(new Runnable() {
			@Override
			public void run() {
				Button ballValueSubmitButtonButton = (Button) ballValueSubmitButtonView;
				String ballValueString = (String) ballValueSubmitButtonButton.getText();
				int ballValue = 0;
				switch (ballValueString) {
					case "red":
						ballValue = 1;
						break;
					case "yellow":
						ballValue = 2;
						break;
					case "green":
						ballValue = 3;
						break;
					case "brown":
						ballValue = 4;
						break;
					case "blue":
						ballValue = 5;
						break;
					case "pink":
						ballValue = 6;
						break;
					case "black":
						ballValue = 7;
						break;
					default:
						break;
				}
				table1.processPottedBall(player, ballValue);
				int scorePlayer1 = table1.getScorePlayer1();
				int scorePlayer2 = table1.getScorePlayer2();
				int remainingPoints = table1.remainingPoints();
				Bundle tableInfo = new Bundle(3);
				tableInfo.putInt("remainingPoints", remainingPoints);
				tableInfo.putInt("scorePlayer1", scorePlayer1);
				tableInfo.putInt("scorePlayer2", scorePlayer2);
				Message tableUpdate = uiHandler.obtainMessage();
				tableUpdate.setData(tableInfo);
				uiHandler.sendMessage(tableUpdate);
			}
		}).start();
	}

	@Override
	protected void onDestroy() {
		super.onDestroy();
	}
}

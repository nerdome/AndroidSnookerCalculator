package de.adornis.snookercalculator;

/**
 * Created by fightcookie on 9/12/2014.
 */
class Table {
	//use hashmap for balls, at least for colored, good for remainingPoints method
	private int redBalls;
	private boolean yellowBall;
	private boolean greenBall;
	private boolean brownBall;
	private boolean blueBall;
	private boolean pinkBall;
	private boolean blackBall;

	private int scorePlayer1;
	private int scorePlayer2;

	void resetTable() {
		redBalls = 15;
		yellowBall = true;
		greenBall = true;
		brownBall = true;
		blueBall = true;
		pinkBall = true;
		blackBall = true;
	}

	int getScorePlayer1() {
		return scorePlayer1;
	}

	int getScorePlayer2() {
		return scorePlayer2;
	}

	void processPottedBall(String player, int ballValue) {
		//validate parameters
		if (ballValue > 0 && ballValue < 8 && (player.equals("player1") || player.equals("player2"))) {
			if (player.equals("player1")) {
				scorePlayer1 += ballValue;
			} else {
				scorePlayer2 += ballValue;
			}
			if (ballValue == 1 && redBalls > 0) {
				redBalls--;
			}
			if (redBalls == 0) {
				switch (ballValue) {
					case 2:
						yellowBall = false;
						break;
					case 3:
						greenBall = false;
						break;
					case 4:
						brownBall = false;
						break;
					case 5:
						blueBall = false;
						break;
					case 6:
						pinkBall = false;
						break;
					case 7:
						blackBall = false;
						break;
					default:
						break;
				}
			}
		}
	}

	int remainingPoints() {
		int remainingPoints = 0;
		remainingPoints += redBalls * 8;
		if (yellowBall) {
			remainingPoints += 2;
		}

		if (greenBall) {
			remainingPoints += 3;
		}

		if (brownBall) {
			remainingPoints += 4;
		}

		if (blueBall) {
			remainingPoints += 5;
		}

		if (pinkBall) {
			remainingPoints += 6;
		}

		if (blackBall) {
			remainingPoints += 7;
		}
		return remainingPoints;
	}
}

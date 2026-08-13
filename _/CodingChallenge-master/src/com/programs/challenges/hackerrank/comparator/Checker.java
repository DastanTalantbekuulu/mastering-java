package com.programs.challenges.hackerrank.comparator;

import java.util.Comparator;

class Checker implements Comparator<Player> {
	// complete this method
	public int compare(Player a, Player b) {

		int scoreChk = b.score - a.score;
		if (scoreChk == 0) {
			return a.name.compareTo(b.name);
		} else {
			return scoreChk;
		}
	}

}
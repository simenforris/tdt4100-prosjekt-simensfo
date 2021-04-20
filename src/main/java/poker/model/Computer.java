package poker.model;

import java.util.Arrays;

public class Computer extends Hand {
	private final String combinations[] = {"012", "013", "014", "023", "024", "034", "123", "124", "134", "234"};

	public Computer(int handSize) {
		super(handSize);
	}

	public Play makePlay() {
		Play[] plays = new Play[combinations.length];
		for (int i = 0; i < combinations.length; i++) {
			Play play = new Play("Computer Play:", false);
			for (String j : combinations[i].split("")) {
				play.push(cards[Integer.valueOf(j)]); 
			}
			plays[i] = play;
		}
		Arrays.sort(plays, (play1, play2) -> play2.getScore() - play1.getScore());

		for (int i = 0; i < plays[0].size(); i++) {
			playCard(plays[0].getCard(i));
		}

		return plays[0];
	}

	public Play makePlay(Boolean noModel) {
		Play[] plays = new Play[combinations.length];
		for (int i = 0; i < combinations.length; i++) {
			Play play = new Play(true);
			for (String j : combinations[i].split("")) {
				play.push(cards[Integer.valueOf(j)]); 
			}
			plays[i] = play;
		}
		Arrays.sort(plays, (play1, play2) -> play2.getScore() - play1.getScore());

		for (int i = 0; i < plays[0].size(); i++) {
			playCard(plays[0].getCard(i));
		}

		return plays[0];
	}
}

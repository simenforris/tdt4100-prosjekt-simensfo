package poker.model;

import java.util.Stack;

public class Game {
	private int handSize;
	private int round;
	private Deck deck;
	private Hand playerHand;
	private Hand computerHand;
	private Stack<Card> playerWon;
	private Stack<Card> computerWon;
	private Stack<Card> warCards;

	public Game() {
		this.handSize = 5;
		this.round = 1;
		this.deck = new Deck();
		this.playerHand = new Hand(handSize);
		this.computerHand = new Hand(handSize);
		this.playerWon = new Stack<Card>();
		this.computerWon = new Stack<Card>();
		this.warCards = new Stack<Card>();
	}

	public int getRound() {
		return this.round;
	}

	public static void main(String[] args) {
	}
}

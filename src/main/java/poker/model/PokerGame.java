package poker.model;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.LinkedList;
import java.util.Stack;

public class PokerGame implements Serializable {
	private int round;
	private Deck deck;
	private Hand playerHand;
	private Computer computerHand;
	private Stack<Card> playerWon;
	private Stack<Card> computerWon;
	private Stack<Card> warCards;
	private Play playerPlay;
	private Play computerPlay;
	private String winner;

	public PokerGame(int handSize) {
		this.round = 1;
		this.deck = new Deck();
		this.deck.shuffle();
		this.playerHand = new Hand(handSize);
		this.computerHand = new Computer(handSize);
		this.playerWon = new Stack<Card>();
		this.computerWon = new Stack<Card>();
		this.warCards = new Stack<Card>();
		this.playerPlay = new Play("Player Play:", true);
		this.winner = "";
	}

	public int getRound() {
		return this.round;
	}

	public Deck getDeck() {
		return this.deck;
	}

	public Hand getPlayerHand() {
		return this.playerHand;
	}

	public Hand getComputerHand() {
		return this.computerHand;
	}

	public Stack<Card> getPlayerWon() {
		return this.playerWon;
	}

	public Stack<Card> getComputerWon() {
		return this.computerWon;
	}

	public Stack<Card> getWarCards() {
		return this.warCards;
	}

	public Play getPlayerPlay() {
		return this.playerPlay;
	}

	public Play getComputerPlay() {
		return this.computerPlay;
	}

	public String getWinner() {
		return this.winner;
	}

	public void refillHands() {
		for (int i = 0; i < this.playerHand.size(); i++) {
			if (this.playerHand.getCard(i) == null) {
				this.playerHand.addCard(i, this.deck.draw());
			}
			if (this.computerHand.getCard(i) == null) {
				this.computerHand.addCard(i, this.deck.draw());
			}
		}
	}

	public void makePlays(LinkedList<Card> selectedCards) {
		this.computerPlay = computerHand.makePlay();

		for (Card card : selectedCards) {
			Card cardToPlay = this.playerHand.playCard(card);
			this.playerPlay.push(cardToPlay);
		}
		// Move this down into components
		this.computerPlay.updateModel();
		this.playerPlay.updateModel();
	}

	public void calculateWinner() {
		int playerScore = playerPlay.getScore();
		int computerScore = computerPlay.getScore();
		if (playerScore > computerScore) {
			winner = "player";
			while (playerPlay.size() > 0) {
				playerWon.push(playerPlay.pop());
				playerWon.push(computerPlay.pop());
			}
			while (warCards.size() > 0) {
				playerWon.push(warCards.pop());
			}
		} else if (computerScore > playerScore) {
			winner = "computer";
			while (playerPlay.size() > 0) {
				computerWon.push(playerPlay.pop());
				computerWon.push(computerPlay.pop());
			}
			while (warCards.size() > 0) {
				computerWon.push(warCards.pop());
			}
		} else {
			while (playerPlay.size() > 0) {
				warCards.push(playerPlay.pop());
				warCards.push(computerPlay.pop());
			}
			winner = "tie";
		}

		this.round++;
		if (round <= 8) {
			refillHands();
		}
	}

	@SuppressWarnings("unchecked")
	private void readObject(ObjectInputStream input) throws ClassNotFoundException, IOException {
		round = input.readInt();
		deck = (Deck) input.readObject();
		playerHand = (Hand) input.readObject();
		computerHand = (Computer) input.readObject();
		playerWon = (Stack<Card>) input.readObject();
		computerWon = (Stack<Card>) input.readObject();
		warCards = (Stack<Card>) input.readObject();
		playerPlay = (Play) input.readObject();
		computerPlay = (Play) input.readObject();
		winner = input.readUTF();
	}

	private void writeObject(ObjectOutputStream output) throws IOException {
		output.writeInt(round);
		output.writeObject(deck);
		output.writeObject(playerHand);
		output.writeObject(computerHand);
		output.writeObject(playerWon);
		output.writeObject(computerWon);
		output.writeObject(warCards);
		output.writeObject(playerPlay);
		output.writeObject(computerPlay);
		output.writeUTF(winner);
	}
}

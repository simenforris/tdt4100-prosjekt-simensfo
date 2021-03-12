package poker.model;

public class Card {
	private final char suit;
	private final int face;

	public Card(char suit, int face) throws IllegalArgumentException {
		if (!(suit == 'S' || suit == 'H' || suit == 'D' || suit == 'C')) {
			throw new IllegalArgumentException("Card suit must be either S, H, D, or C");
		} else if (!(face >= 1 && face <= 13)) {
			throw new IllegalArgumentException("Card face must be between 1 and 13");
		}
		this.suit = suit;
		this.face = face;
	}

	public char getSuit() {
		return this.suit;
		// switch (this.suit) {
		// 	case('S'):
		// 		return "\u2660";
		// 	case('H'):
		// 		return "\u2665";
		// 	case('D'):
		// 		return "\u2666";
		// 	case('C'):
		// 		return "\u2663";
		// 	default:
		// 		return "?";
		// }
	}

	public int getFace() {
		return this.face;
		// switch (this.face) {
		// 	case(1):
		// 		return "A";
		// 	case(11):
		// 		return "J";
		// 	case(12):
		// 		return "Q";
		// 	case(13):
		// 		return "K";
		// 	default:
		// 		return String.valueOf(this.face);
		// }
	}

	@Override
	public String toString() {
		String suit;
		switch (this.suit) {
			case('S'):
				suit = "\u2660";
			case('H'):
				suit = "\u2665";
			case('D'):
				suit = "\u2666";
			case('C'):
				suit = "\u2663";
			default:
				suit = "?";
		}
		String face;
		switch (this.face) {
			case(1):
				face = "A";
			case(11):
				face = "J";
			case(12):
				face = "Q";
			case(13):
				face = "K";
			default:
				face = String.valueOf(this.face);
		}

		return suit + face;
	}
}

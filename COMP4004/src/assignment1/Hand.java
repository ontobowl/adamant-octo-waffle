package assignment1;

import java.util.ArrayList;
import java.util.List;

import assignment1.Card.Rank;
import assignment1.Card.Suit;

public class Hand {
	// Represents both the player and all of their cards

	public enum PokerRank {
		HIGHHAND(1), ONEPAIR(2), TWOPAIR(3), THREEOFAKIND(4), STRAIGHT(5), FLUSH(6), FULLHOUSE(7), FOUROFAKIND(
				8), STRAIGHTFLUSH(9), ROYALFLUSH(10);

		public int rank;

		private PokerRank(int r) {
			rank = r;
		}
	}

	private int id; // player ID
	private List<Card> cards = new ArrayList<Card>(); // cards in the hand

	// public Hand() {
	// }

	// Takes an ID and five Cards
	public Hand(String h) throws IllegalArgumentException {
		parseHandFromString(h);
	}

	public void parseHandFromString(String h) throws IllegalArgumentException {
		String[] tokens = h.split("[ ]+");

		if (tokens.length != 6)
			throw new IllegalArgumentException();

		id = Integer.parseInt(tokens[0]);
		if (id < 1 || id > 4)
			throw new IllegalArgumentException();

		// Fails if any Card is invalid or a duplicate
		if (!addCard(Card.parseCardFromString(tokens[1]).getRank(), Card.parseCardFromString(tokens[1]).getSuit())
				|| !addCard(Card.parseCardFromString(tokens[2]).getRank(),
						Card.parseCardFromString(tokens[2]).getSuit())
				|| !addCard(Card.parseCardFromString(tokens[3]).getRank(),
						Card.parseCardFromString(tokens[3]).getSuit())
				|| !addCard(Card.parseCardFromString(tokens[4]).getRank(),
						Card.parseCardFromString(tokens[4]).getSuit())
				|| !addCard(Card.parseCardFromString(tokens[5]).getRank(),
						Card.parseCardFromString(tokens[5]).getSuit()))
			throw new IllegalArgumentException();
	}

	// A hand is valid if
	public Boolean isComplete() {
		return cards.size() == 5 && id != 0;
	}

	public Boolean addCard(Rank r, Suit s) {
		Card c = new Card(r, s);
		if (cards.size() == 5 || cards.contains(c)) {
			return false;
		}

		cards.add(c);
		return true;

	}

	public Boolean setID(int newID) {
		if (newID < 1 || newID > 5)
			return false;
		id = newID;
		return true;
	}

	// Getters
	public int getID() {
		return id;
	}

	public Card getCard1() {
		return cards.get(0);
	}

	public Card getCard2() {
		return cards.get(1);
	}

	public Card getCard3() {
		return cards.get(2);
	}

	public Card getCard4() {
		return cards.get(3);
	}

	public Card getCard5() {
		return cards.get(4);
	}

	public int compareTo(Hand h) {

		// get sorted order

		List<Card> sortedCards1 = new ArrayList<Card>();
		for (Card c : cards)
			sortedCards1.add(c);

		Card tmpCard;
		for (int i = 0; i < 4; i++) {
			for (int j = i + 1; j < 5; j++) {
				if (sortedCards1.get(i).getRank().rank > sortedCards1.get(j).getRank().rank) {
					tmpCard = sortedCards1.get(i);
					sortedCards1.set(i, sortedCards1.get(j));
					sortedCards1.set(j, tmpCard);
				}
			}
		}

		List<Card> sortedCards2 = new ArrayList<Card>();
		sortedCards2.add(h.getCard1());
		sortedCards2.add(h.getCard2());
		sortedCards2.add(h.getCard3());
		sortedCards2.add(h.getCard4());
		sortedCards2.add(h.getCard5());

		for (int i = 0; i < 4; i++) {
			for (int j = i + 1; j < 5; j++) {
				if (sortedCards2.get(i).getRank().rank > sortedCards2.get(j).getRank().rank) {
					tmpCard = sortedCards2.get(i);
					sortedCards2.set(i, sortedCards2.get(j));
					sortedCards2.set(j, tmpCard);
				}
			}
		}

		// get hand ranking
		PokerRank pRank1 = getHandRanking();
		PokerRank pRank2 = h.getHandRanking();

		if (pRank1.rank < pRank2.rank)
			return -1;
		if (pRank1.rank > pRank2.rank)
			return 1;

		Card highCard1 = sortedCards1.get(4);
		Card highCard2 = sortedCards2.get(4);

		Card kicker1;
		Card kicker2;

		switch (pRank1) {
		case ROYALFLUSH:
			return 0;
		case STRAIGHTFLUSH:
			if (highCard1.getRank().rank > highCard2.getRank().rank)
				return 1;
			else if (highCard1.getRank().rank < highCard2.getRank().rank)
				return -1;
			else
				return 0;
		case FOUROFAKIND:
			Card nonKicker1;
			Card nonKicker2;

			// get majority card and kicker card for this hand
			if (sortedCards1.get(0).getRank() == sortedCards1.get(1).getRank()) {
				nonKicker1 = sortedCards1.get(0);
				kicker1 = sortedCards1.get(4);
			} else {
				nonKicker1 = sortedCards1.get(4);
				kicker1 = sortedCards1.get(0);
			}

			// get majority card and kicker card for the other hand
			if (sortedCards2.get(0).getRank() == sortedCards2.get(1).getRank()) {
				nonKicker2 = sortedCards2.get(0);
				kicker2 = sortedCards2.get(4);
			} else {
				nonKicker2 = sortedCards2.get(4);
				kicker2 = sortedCards2.get(0);
			}

			if (nonKicker1.getRank().rank > nonKicker2.getRank().rank) {
				return 1;
			} else if (nonKicker1.getRank().rank < nonKicker2.getRank().rank) {
				return -1;
			} else {
				if (kicker1.getRank().rank > kicker2.getRank().rank) {
					return 1;
				} else if (kicker1.getRank().rank < kicker2.getRank().rank) {
					return -1;
				} else {
					return 0;
				}
			}

		case FULLHOUSE:
			if (sortedCards1.get(2).getRank().rank > sortedCards2.get(2).getRank().rank)
				return 1;
			else if (sortedCards1.get(2).getRank().rank < sortedCards2.get(2).getRank().rank)
				return -1;
			else {
				if (sortedCards1.get(0).getRank().rank > sortedCards2.get(0).getRank().rank
						|| sortedCards1.get(4).getRank().rank > sortedCards2.get(4).getRank().rank) {
					return 1;
				} else if (sortedCards1.get(0).getRank().rank < sortedCards2.get(0).getRank().rank
						|| sortedCards1.get(4).getRank().rank < sortedCards2.get(4).getRank().rank) {
					return -1;
				} else
					return 0;
			}

		case FLUSH:
			// compare the highest cards of each hand, if one card's rank is
			// greater then that hand is greater
			// otherwise, compare the next highest card and so on. if both
			// hands' cards have equivalent ranks,
			// then they have equal rank
			int rankDiff;

			for (int i = 4; i >= 0; i--) {
				rankDiff = sortedCards1.get(i).getRank().rank - sortedCards2.get(i).getRank().rank;
				if (rankDiff > 0)
					return 1;
				else if (rankDiff < 0)
					return -1;
			}
			return 0;

		case STRAIGHT:
			// following the assumption that aces are only high and not high/low

			if (sortedCards1.get(4).getRank().rank > sortedCards2.get(4).getRank().rank)
				return 1;
			else if (sortedCards1.get(4).getRank().rank < sortedCards2.get(4).getRank().rank)
				return -1;
			else
				return 0;

		case THREEOFAKIND:
			if (sortedCards1.get(4).getRank().rank > sortedCards2.get(4).getRank().rank)
				return 1;
			else if (sortedCards1.get(4).getRank().rank < sortedCards2.get(4).getRank().rank)
				return -1;
			else {
				if (sortedCards1.get(1).getRank().rank > sortedCards2.get(1).getRank().rank)
					return 1;
				else if (sortedCards1.get(1).getRank().rank < sortedCards2.get(1).getRank().rank)
					return -1;
				else {
					if (sortedCards1.get(0).getRank().rank > sortedCards2.get(0).getRank().rank)
						return 1;
					else if (sortedCards1.get(0).getRank().rank < sortedCards2.get(0).getRank().rank)
						return -1;
					else
						return 0;
				}
			}
		case TWOPAIR:
			Card highPair1;
			Card lowPair1;
			Card highPair2;
			Card lowPair2;

			// figure out each two-pairs' forms
			// form of this hand
			if (sortedCards1.get(3).getRank() == sortedCards1.get(4).getRank()) {
				highPair1 = sortedCards1.get(3);
				if (sortedCards1.get(1).getRank() == sortedCards1.get(2).getRank()) {
					lowPair1 = sortedCards1.get(1);
					kicker1 = sortedCards1.get(0);
				} else {
					lowPair1 = sortedCards1.get(0);
					kicker1 = sortedCards1.get(2);
				}
			} else {
				highPair1 = sortedCards1.get(3);
				kicker1 = sortedCards1.get(4);
				lowPair1 = sortedCards1.get(0);
			}

			// form of the other hand
			if (sortedCards2.get(3).getRank() == sortedCards2.get(4).getRank()) {
				highPair2 = sortedCards2.get(3);
				if (sortedCards2.get(1).getRank() == sortedCards2.get(2).getRank()) {
					lowPair2 = sortedCards2.get(1);
					kicker2 = sortedCards2.get(0);
				} else {
					lowPair2 = sortedCards2.get(0);
					kicker2 = sortedCards2.get(2);
				}
			} else {
				highPair2 = sortedCards2.get(3);
				kicker2 = sortedCards2.get(4);
				lowPair2 = sortedCards2.get(0);
			}

			if (highPair1.getRank().rank > highPair2.getRank().rank)
				return 1;
			else if (highPair1.getRank().rank < highPair2.getRank().rank)
				return -1;
			else {
				if (lowPair1.getRank().rank > lowPair2.getRank().rank)
					return 1;
				else if (lowPair1.getRank().rank < lowPair2.getRank().rank)
					return -1;
				else {
					if (kicker1.getRank().rank > kicker2.getRank().rank)
						return 1;
					else if (kicker1.getRank().rank < kicker2.getRank().rank)
						return -1;
					else
						return 0;
				}
			}
		case ONEPAIR:
			highPair1 = sortedCards1.get(4);
			highPair2 = sortedCards2.get(4);
			for (int i = 3; i >= 0; i--) {
				if (sortedCards1.get(i).getRank() == sortedCards1.get(i + 1).getRank())
					highPair1 = sortedCards1.get(i);
				if (sortedCards2.get(i).getRank() == sortedCards2.get(i + 1).getRank())
					highPair2 = sortedCards2.get(i);
			}
			if (highPair1.getRank().rank > highPair2.getRank().rank)
				return 1;
			else if (highPair1.getRank().rank < highPair2.getRank().rank)
				return -1;
			else {
				for (int i = 4; i >= 0; i--) {
					if (sortedCards1.get(i).getRank().rank > sortedCards2.get(i).getRank().rank)
						return 1;
					else if (sortedCards1.get(i).getRank().rank > sortedCards2.get(i).getRank().rank)
						return -1;
				}
				return 0;
			}
		case HIGHHAND:
			for (int i = 4; i >= 0; i--) {
				if (sortedCards1.get(i).getRank().rank > sortedCards2.get(i).getRank().rank)
					return 1;
				else if (sortedCards1.get(i).getRank().rank < sortedCards2.get(i).getRank().rank)
					return -1;
			}
			return 0;
		}

		return 0;
	}

	public void setCards(List<Card> someCards) {
		cards = someCards;
	}

	public PokerRank getHandRanking() {
		// get sorted order

		List<Card> sortedCards = new ArrayList<Card>();
		for (Card c : cards)
			sortedCards.add(c);

		Card tmpCard;
		for (int i = 0; i < 4; i++) {
			for (int j = i + 1; j < 5; j++) {
				if (sortedCards.get(i).getRank().rank > sortedCards.get(j).getRank().rank) {
					tmpCard = sortedCards.get(i);
					sortedCards.set(i, sortedCards.get(j));
					sortedCards.set(j, tmpCard);
				}
			}
		}

		// check royal flush
		if ((sortedCards.get(0).getRank().toString() == "TEN" && sortedCards.get(1).getRank().toString() == "JACK"
				&& sortedCards.get(2).getRank().toString() == "QUEEN"
				&& sortedCards.get(3).getRank().toString() == "KING"
				&& sortedCards.get(4).getRank().toString() == "ACE")
				&& (sortedCards.get(0).getSuit() == sortedCards.get(1).getSuit()
						&& sortedCards.get(1).getSuit() == sortedCards.get(2).getSuit()
						&& sortedCards.get(2).getSuit() == sortedCards.get(3).getSuit()
						&& sortedCards.get(3).getSuit() == sortedCards.get(4).getSuit()))
			return PokerRank.ROYALFLUSH;

		// check straight flush
		int lowestRank = sortedCards.get(0).getRank().rank;
		if ((sortedCards.get(1).getRank().rank == lowestRank + 1 && sortedCards.get(2).getRank().rank == lowestRank + 2
				&& sortedCards.get(3).getRank().rank == lowestRank + 3
				&& sortedCards.get(4).getRank().rank == lowestRank + 4)
				&& sortedCards.get(0).getSuit() == sortedCards.get(1).getSuit()
				&& sortedCards.get(1).getSuit() == sortedCards.get(2).getSuit()
				&& sortedCards.get(2).getSuit() == sortedCards.get(3).getSuit()
				&& sortedCards.get(3).getSuit() == sortedCards.get(4).getSuit())
			return PokerRank.STRAIGHTFLUSH;

		// check four of a kind
		if (sortedCards.get(0).getRank() == sortedCards.get(1).getRank())
			if (sortedCards.get(1).getRank() == sortedCards.get(2).getRank()
					&& sortedCards.get(2).getRank() == sortedCards.get(3).getRank())
				return PokerRank.FOUROFAKIND;
			else if (sortedCards.get(1).getRank() == sortedCards.get(2).getRank()
					&& sortedCards.get(2).getRank() == sortedCards.get(3).getRank()
					&& sortedCards.get(3).getRank() == sortedCards.get(4).getRank())
				return PokerRank.FOUROFAKIND;

		// check full house
		if ((sortedCards.get(0).getRank() == sortedCards.get(1).getRank()
				&& sortedCards.get(1).getRank() != sortedCards.get(2).getRank()
				&& sortedCards.get(2).getRank() == sortedCards.get(3).getRank()
				&& sortedCards.get(3).getRank() == sortedCards.get(4).getRank())
				|| (sortedCards.get(0).getRank() == sortedCards.get(1).getRank()
						&& sortedCards.get(1).getRank() == sortedCards.get(2).getRank()
						&& sortedCards.get(2).getRank() != sortedCards.get(3).getRank()
						&& sortedCards.get(3).getRank() == sortedCards.get(4).getRank())) {
			return PokerRank.FULLHOUSE;
		}

		// check flush
		if (sortedCards.get(0).getSuit() == sortedCards.get(1).getSuit()
				&& sortedCards.get(1).getSuit() == sortedCards.get(2).getSuit()
				&& sortedCards.get(2).getSuit() == sortedCards.get(3).getSuit()
				&& sortedCards.get(3).getSuit() == sortedCards.get(4).getSuit())
			return PokerRank.FLUSH;

		// check straight
		if (sortedCards.get(1).getRank().rank == lowestRank + 1 && sortedCards.get(2).getRank().rank == lowestRank + 2
				&& sortedCards.get(3).getRank().rank == lowestRank + 3
				&& sortedCards.get(4).getRank().rank == lowestRank + 4)
			return PokerRank.STRAIGHT;

		// check three of a kind
		if ((sortedCards.get(0).getRank() == sortedCards.get(1).getRank()
				&& sortedCards.get(1).getRank() == sortedCards.get(2).getRank()
				&& sortedCards.get(2).getRank() != sortedCards.get(3).getRank()
				&& sortedCards.get(3).getRank() != sortedCards.get(4).getRank())
				|| (sortedCards.get(0).getRank() != sortedCards.get(1).getRank()
						&& sortedCards.get(1).getRank() != sortedCards.get(2).getRank()
						&& sortedCards.get(2).getRank() == sortedCards.get(3).getRank()
						&& sortedCards.get(3).getRank() == sortedCards.get(4).getRank())
				|| (sortedCards.get(0).getRank() != sortedCards.get(1).getRank()
						&& sortedCards.get(1).getRank() == sortedCards.get(2).getRank()
						&& sortedCards.get(2).getRank() == sortedCards.get(3).getRank()
						&& sortedCards.get(3).getRank() != sortedCards.get(4).getRank()))
			return PokerRank.THREEOFAKIND;

		// check two pair
		if ((sortedCards.get(0).getRank() == sortedCards.get(1).getRank()
				&& sortedCards.get(1).getRank() != sortedCards.get(2).getRank()
				&& sortedCards.get(2).getRank() == sortedCards.get(3).getRank()
				&& sortedCards.get(3).getRank() != sortedCards.get(4).getRank())
				|| (sortedCards.get(0).getRank() == sortedCards.get(1).getRank()
						&& sortedCards.get(1).getRank() != sortedCards.get(2).getRank()
						&& sortedCards.get(2).getRank() != sortedCards.get(3).getRank()
						&& sortedCards.get(3).getRank() == sortedCards.get(4).getRank())
				|| (sortedCards.get(0).getRank() != sortedCards.get(1).getRank()
						&& sortedCards.get(1).getRank() == sortedCards.get(2).getRank()
						&& sortedCards.get(2).getRank() != sortedCards.get(3).getRank()
						&& sortedCards.get(3).getRank() == sortedCards.get(4).getRank()))
			return PokerRank.TWOPAIR;

		// check one pair
		if ((sortedCards.get(0).getRank() == sortedCards.get(1).getRank()
				&& sortedCards.get(1).getRank() != sortedCards.get(2).getRank()
				&& sortedCards.get(2).getRank() != sortedCards.get(3).getRank()
				&& sortedCards.get(3).getRank() != sortedCards.get(4).getRank())
				|| (sortedCards.get(0).getRank() != sortedCards.get(1).getRank()
						&& sortedCards.get(1).getRank() == sortedCards.get(2).getRank()
						&& sortedCards.get(2).getRank() != sortedCards.get(3).getRank()
						&& sortedCards.get(3).getRank() != sortedCards.get(4).getRank())
				|| (sortedCards.get(0).getRank() != sortedCards.get(1).getRank()
						&& sortedCards.get(1).getRank() != sortedCards.get(2).getRank()
						&& sortedCards.get(2).getRank() == sortedCards.get(3).getRank()
						&& sortedCards.get(3).getRank() != sortedCards.get(4).getRank())
				|| (sortedCards.get(0).getRank() != sortedCards.get(1).getRank()
						&& sortedCards.get(1).getRank() != sortedCards.get(2).getRank()
						&& sortedCards.get(2).getRank() != sortedCards.get(3).getRank()
						&& sortedCards.get(3).getRank() == sortedCards.get(4).getRank()))
			return PokerRank.ONEPAIR;

		// otherwise, high hand
		return PokerRank.HIGHHAND;
	}

	public String toString() {
		return id + " " + cards.get(0).toString() + " " + cards.get(1).toString() + " " + cards.get(2).toString() + " "
				+ cards.get(3).toString() + " " + cards.get(4).toString();
	}

	public List<Card> getCards() {
		return cards;
	}
}

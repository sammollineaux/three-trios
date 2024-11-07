import java.util.ArrayList;
import java.util.List;

/**
 * This class represents a player in the Three Trios game.
 * The Player class implements the IntPlayer interface.
 */
public class Player implements IntPlayer {
  private final String color;
  private final List<Card> hand;

  /**
   * Constructs a new Player with a specified color and an empty hand.
   *
   * @param color the color representing the player (e.g., "Red" or "Blue")
   */
  public Player(String color) {
    this.color = color;
    this.hand = new ArrayList<>();
  }

  public String getColor() {
    return color;
  }

  public void addCardToHand(Card card) {
    hand.add(card);
  }

  /**
   * Removes a card from the player's hand.
   *
   * @param card the card to be removed from the player's hand
   */
  public void removeCardFromHand(Card card) {
    if (hand.isEmpty()) {
      throw new IllegalArgumentException("Hand is empty");
    }
    hand.remove(card);
  }

  public List<Card> getHand() {
    return new ArrayList<>(hand);
  }
}

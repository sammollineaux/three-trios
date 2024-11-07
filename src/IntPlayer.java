import java.util.List;

/**
 * The IntPlayer interface represents a player in the Three Trios game.
 * It provides methods to access player information, manage the player's hand of cards,
 * and modify the cards held by the player.
 */
public interface IntPlayer {

  /**
   * Returns the color associated with this player (e.g., "Red" or "Blue").
   *
   * @return the color of the player
   */
  String getColor();

  /**
   * Returns the list of cards currently held by this player.
   *
   * @return a list of cards in the player's hand
   */
  List<Card> getHand();

  /**
   * Adds a card to the player's hand.
   *
   * @param card the card to be added to the player's hand
   */
  void addCardToHand(Card card);

  /**
   * Removes a card from the player's hand.
   *
   * @param card the card to be removed from the player's hand
   */
  void removeCardFromHand(Card card);
}

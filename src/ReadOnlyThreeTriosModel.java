import java.util.List;

/**
 * Represents a read-only interface for the Three Trios game model.
 * This interface includes observation methods that provide information about the game state
 * without allowing modification, ensuring that views can only observe and not mutate the model.
 */
public interface ReadOnlyThreeTriosModel {

  int[] getGridDimensions();

  Object getCellContents(int row, int col);

  List<Card> getRedPlayerHand();

  List<Card> getBluePlayerHand();

  String getCardOwner(int row, int col);

  boolean isGameOver();

  int getPlayerScore(String color);
}

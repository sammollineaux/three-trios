/**
 * The IntGrid interface represents a basic grid structure in the Three Trios game.
 * This interface provides methods for accessing grid cells, dimensions,
 * validating moves, and placing cards within the grid.
 */
public interface IntGrid {

  /**
   * Returns the number of rows in the grid.
   *
   * @return the number of rows in the grid
   */
  int getRows();

  /**
   * Returns the number of columns in the grid.
   *
   * @return the number of columns in the grid
   */
  int getCols();

  /**
   * Returns the cell at the specified row and column in the grid.
   *
   * @param row the row index of the cell
   * @param col the column index of the cell
   * @return the cell at the specified row and column
   */
  Cell getCell(int row, int col);

  /**
   * Checks if a move is valid at the specified row and column.
   * A move is valid if the cell is a card cell and is currently empty.
   *
   * @param row the row index of the cell
   * @param col the column index of the cell
   * @return true if the move is valid, false otherwise
   */
  boolean isValidMove(int row, int col);

  /**
   * Places a card in the specified cell on the grid for the given player.
   * This method verifies the move’s validity before placing the card.
   *
   * @param row    the row index where the card is to be placed
   * @param col    the column index where the card is to be placed
   * @param card   the card to place in the cell
   * @param player the player who owns the card being placed
   * @throws IllegalArgumentException if the move is invalid
   */
  void placeCard(int row, int col, Card card, Player player);
}

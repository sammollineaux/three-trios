/**
 * A simple text-based view class for rendering the current game state.
 * Displays the grid, indicating card cells, holes, and occupied cells with player ownership.
 * Also displays the current player's hand of cards with attack values.
 */
public class TextView {

  /**
   * Renders the current game state, including the grid layout and the current player's hand.
   * Displays:
   * - Player's turn.
   * - The grid with:
   *   - 'X' for holes where no card can be placed.
   *   - '_' for empty card cells.
   *   - The first letter of the player color for occupied card cells.
   * - The current player's hand with each card's name and attack values for
   * North, South, East, and West.
   *
   * @param grid          the Grid object representing the current game grid
   * @param currentPlayer the Player whose turn it currently is
   */
  public void render(Grid grid, Player currentPlayer) {
    System.out.println("Player: " + currentPlayer.getColor().toUpperCase());


    for (int i = 0; i < grid.getRows(); i++) {
      for (int j = 0; j < grid.getCols(); j++) {
        Cell cell = grid.getCell(i, j);
        if (!cell.isCardCell()) {
          System.out.print(" X ");
        } else if (cell.isEmpty()) {
          System.out.print(" _ ");
        } else {
          System.out.print(" " + cell.getOwner().getColor().charAt(0) + " ");
        }
      }
      System.out.println();
    }
    System.out.println("Hand:");
    for (Card card : currentPlayer.getHand()) {
      System.out.println(card.getName() + " " + card.getAttackValue(Direction.NORTH) + " " +
              card.getAttackValue(Direction.SOUTH) + " " +
              card.getAttackValue(Direction.EAST) + " " +
              card.getAttackValue(Direction.WEST));
    }
  }
}

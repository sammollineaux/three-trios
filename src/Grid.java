/**
 * Represents the grid used in the game, consisting of cells that can be either card cells
 * or holes. This class provides methods to manage and interact with the grid's cells,
 * checking for valid moves, placing cards, and retrieving grid dimensions and cell details.
 */
public class Grid implements IntGrid {
  private final Cell[][] grid;
  private final int rows;
  private final int cols;

  /**
   * Constructs a new Grid object with the specified layout and dimensions.
   * Ensures the grid contains an odd number of card cells.
   * Uses rows and columns starting at 0
   * Uses an invariant here making sure for that all cases the grid will have an odd number of cards
   *
   * @param grid a two-dimensional array representing the layout of the grid
   * @param rows the number of rows in the grid
   * @param cols the number of columns in the grid
   * @throws IllegalArgumentException if the grid does not contain an odd number of card cells
   */
  public Grid(Cell[][] grid, int rows, int cols) {
    int cards = 0;
    for (int i = 0; i < rows; i++) {
      for (int j = 0; j < cols; j++) {
        if (grid[i][j].isCardCell()) {
          cards++;
        }
      }
    }
    if (cards % 2 == 0) {
      throw new IllegalArgumentException("Grid must have an odd number of card cells.");
    }
    this.grid = grid;
    this.rows = rows;
    this.cols = cols;
  }

  public Cell getCell(int row, int col) {
    if (row < 0 || row >= rows || col < 0 || col >= cols) {
      throw new IllegalArgumentException("Invalid cell coordinates");
    }
    return grid[row][col];
  }

  public int getRows() {
    return rows;
  }

  public int getCols() {
    return cols;
  }

  public boolean isValidMove(int row, int col) {
    return grid[row][col].isCardCell() && grid[row][col].isEmpty();
  }

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
  public void placeCard(int row, int col, Card card, Player player) {
    if (!isValidMove(row, col)) {
      throw new IllegalArgumentException("Invalid move.");
    }
    grid[row][col].placeCard(card, player);
  }

  public int[] getDimensions() {
    return new int[]{this.rows, this.cols};
  }

  public Cell getCellContents(int row, int col) {
    if (row < 0 || row >= rows || col < 0 || col >= cols) {
      throw new IllegalArgumentException("Invalid cell coordinates");
    }
    return grid[row][col];
  }
}

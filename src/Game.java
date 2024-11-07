/**
 * This class represents the Three Trios game, managing the grid, players, and the game flow.
 */
public class Game {
  private final Grid grid;
  private final Player redPlayer;
  private final Player bluePlayer;
  private Player currentPlayer;

  /**
   * Constructs a new Game object.
   *
   * @param grid       the grid used
   * @param redPlayer  the red player
   * @param bluePlayer the blue player
   */
  public Game(Grid grid, Player redPlayer, Player bluePlayer) {
    this.grid = grid;
    this.redPlayer = redPlayer;
    this.bluePlayer = bluePlayer;
    this.currentPlayer = redPlayer;
  }

  /**
   * Handles a player's turn by placing a card on the grid and initiating the battle phase.
   *
   * @param row  the row on the grid where the card is placed
   * @param col  the column on the grid where the card is placed
   * @param card the card to be placed
   * @throws IllegalArgumentException if the move is invalid
   */
  public void playTurn(int row, int col, Card card) {
    if (!grid.isValidMove(row, col)) {
      throw new IllegalArgumentException("Invalid move");
    }
    grid.placeCard(row, col, card, currentPlayer);
    currentPlayer.removeCardFromHand(card);
    battlePhase(row, col);

    if (currentPlayer == redPlayer) {
      currentPlayer = bluePlayer;
    } else {
      currentPlayer = redPlayer;
    }
  }

  /**
   * Executes the battle phase after a card is placed on the grid.
   * The placed card battles any opponent cards in adjacent cells, potentially flipping ownership.
   *
   * @param row the row of the placed card
   * @param col the column of the placed card
   */
  private void battlePhase(int row, int col) {
    Card placedCard = grid.getCell(row, col).getCard();
    Player opponent;
    if (currentPlayer == redPlayer) {
      opponent = bluePlayer;
    } else {
      opponent = redPlayer;
    }

    checkBattle(row - 1, col, placedCard, Direction.NORTH, opponent);
    checkBattle(row + 1, col, placedCard, Direction.SOUTH, opponent);
    checkBattle(row, col - 1, placedCard, Direction.WEST, opponent);
    checkBattle(row, col + 1, placedCard, Direction.EAST, opponent);
  }

  /**
   * Checks and executes a battle between the placed card and an adjacent opponent card
   * in a specified direction.
   * If the placed card’s attack value in the specified direction is higher,
   * the opponent's card is flipped.
   *
   * @param adjRow      the row of the adjacent cell
   * @param adjCol      the column of the adjacent cell
   * @param placedCard  the card recently placed by the current player
   * @param direction   the direction of the adjacent cell relative to the placed card
   * @param opponent    the opposing player
   */
  private void checkBattle(int adjRow, int adjCol, Card placedCard, Direction direction,
                           Player opponent) {
    if (grid.isValidMove(adjRow, adjCol)) {
      Cell adjCell = grid.getCell(adjRow, adjCol);
      if (adjCell.getOwner() == opponent) {
        Card adjCard = adjCell.getCard();
        Direction opposite = getOppositeDirection(direction);
        if (placedCard.getAttackValue(direction) > adjCard.getAttackValue(opposite)) {
          adjCell.flip(currentPlayer);
        }
      }
    }
  }

  /**
   * Returns the opposite direction of the specified direction.
   *
   * @param dir the direction for which to get the opposite
   * @return the opposite direction
   * @throws IllegalArgumentException if the specified direction is invalid
   */
  private Direction getOppositeDirection(Direction dir) {
    switch (dir) {
      case NORTH: return Direction.SOUTH;
      case SOUTH: return Direction.NORTH;
      case EAST: return Direction.WEST;
      case WEST: return Direction.EAST;
      default: throw new IllegalArgumentException("Invalid direction.");
    }
  }

  /**
   * Determines the winner of the game by counting the number of cards each player owns on the grid
   * and in their hands. The player with the highest count wins.
   *
   * @param grid       the game grid
   * @param redPlayer  the red player
   * @param bluePlayer the blue player
   * @return the player with the most owned cards, or null if there is a tie
   */
  public static Player checkWinner(IntGrid grid, IntPlayer redPlayer, IntPlayer bluePlayer) {
    int redCount = redPlayer.getHand().size();
    int blueCount = bluePlayer.getHand().size();

    for (int i = 0; i < grid.getRows(); i++) {
      for (int j = 0; j < grid.getCols(); j++) {
        Cell cell = grid.getCell(i, j);
        if (cell.isCardCell() && !cell.isEmpty()) {
          if (cell.getOwner() == redPlayer) {
            redCount++;
          } else if (cell.getOwner() == bluePlayer) {
            blueCount++;
          }
        }
      }
    }
    if (redCount > blueCount) {
      return (Player) redPlayer;
    } else if (blueCount > redCount) {
      return (Player) bluePlayer;
    } else {
      return null;
    }
  }

  /**
   * Checks if the game is over by verifying that all card cells are filled.
   *
   * @return true if the game is over (i.e., all card cells are filled), false otherwise.
   */
  public boolean isGameOver() {
    for (int row = 0; row < grid.getRows(); row++) {
      for (int col = 0; col < grid.getCols(); col++) {
        Cell cell = grid.getCell(row, col);
        if (cell.isCardCell() && cell.isEmpty()) {
          return false;
        }
      }
    }
    return true;
  }
}

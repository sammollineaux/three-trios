/**
 * This class represents a cell in the grid of the Three Trios game.
 * A cell can either be a card cell or a hole. Card cells can hold a card and track its owner.
 */
public class Cell {
  private final boolean isCardCell;
  private Card card;
  private Player ownCard;

  /**
   * Constructs a Cell with the specified type.
   *
   * @param isCardCell true if the cell is a card cell, false if it is a hole
   */
  public Cell(boolean isCardCell) {
    this.isCardCell = isCardCell;
    this.card = null;
    this.ownCard = null;
  }

  /**
   * Returns whether this cell is a card cell.
   *
   * @return true if this cell is a card cell, false otherwise
   */
  public boolean isCardCell() {
    return isCardCell;
  }

  /**
   * Returns whether this cell is empty (i.e., does not contain a card).
   *
   * @return true if the cell is empty, false otherwise
   */
  public boolean isEmpty() {
    return card == null;
  }

  /**
   * Places a card in the cell and assigns an owner to the card.
   *
   * @param card    the card to place in the cell
   * @param ownCard the player who owns the card
   */
  public void placeCard(Card card, Player ownCard) {
    this.card = card;
    this.ownCard = ownCard;
  }

  /**
   * Returns the card placed in this cell.
   *
   * @return the card in the cell, or null if the cell is empty
   */
  public Card getCard() {
    return card;
  }

  /**
   * Returns the owner of the card in this cell.
   *
   * @return the player who owns the card in the cell, or null if the cell is empty
   */
  public Player getOwner() {
    return ownCard;
  }

  /**
   * Changes the ownership of the card in this cell to a new owner.
   *
   * @param newOwner the player who will become the new owner of the card
   */
  public void flip(Player newOwner) {
    this.ownCard = newOwner;
  }
}

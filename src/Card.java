/**
 * This class represents a card in the Three Trios game.
 * Each card has a unique name and four attack values corresponding to the four cardinal directions:
 * North, South, East, and West.
 */
public class Card {
  private final String name;
  private final int north;
  private final int south;
  private final int east;
  private final int west;

  /**
   * Constructs a Card with a specified name and attack values in each direction.
   *
   * @param name  the unique name of the card
   * @param north the attack value on the North side of the card
   * @param south the attack value on the South side of the card
   * @param east  the attack value on the East side of the card
   * @param west  the attack value on the West side of the card
   */
  public Card(String name, int north, int south, int east, int west) {
    this.name = name;
    this.north = north;
    this.south = south;
    this.east = east;
    this.west = west;
  }

  /**
   * Returns the name of the card.
   *
   * @return the name of the card
   */
  public String getName() {
    return name;
  }

  /**
   * Returns the attack value of the card for a specified direction.
   *
   * @param dir the direction for which to retrieve the attack value (NORTH, SOUTH, EAST, or WEST)
   * @return the attack value in the specified direction
   * @throws IllegalArgumentException if the specified direction is invalid
   */
  public int getAttackValue(Direction dir) {
    switch (dir) {
      case NORTH: return north;
      case SOUTH: return south;
      case EAST: return east;
      case WEST: return west;
      default: throw new IllegalArgumentException("Invalid direction.");
    }
  }

  @Override
  public String toString() {
    return String.format("%d\n%d %s %d\n%d", north, west, name, east, south);
  }
}


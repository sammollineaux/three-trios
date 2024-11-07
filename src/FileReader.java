import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/**
 * Utility class for reading configuration files to set up game components,
 * including grids and cards, for the game.
 */
public class FileReader {

  /**
   * Loads a grid configuration from a specified file path.
   * The file should contain rows, columns, and a grid layout with 'C' for card cells
   * and 'X' for hole cells.
   *
   * @param filePath the path to the grid configuration file
   * @return a Grid object representing the loaded grid
   * @throws FileNotFoundException if the file is not found at the specified path
   * @throws IllegalArgumentException if the file contains invalid cell types
   */
  public static Grid loadGrid(String filePath) throws FileNotFoundException {
    Scanner scanner = new Scanner(new File(filePath));

    int rows = scanner.nextInt();
    int cols = scanner.nextInt();
    scanner.nextLine();

    Cell[][] grid = new Cell[rows][cols];

    for (int i = 0; i < rows; i++) {
      String row = scanner.nextLine();
      for (int j = 0; j < cols; j++) {
        char cellType = row.charAt(j);
        if (cellType == 'C') {
          grid[i][j] = new Cell(true);
        } else if (cellType == 'X') {
          grid[i][j] = new Cell(false);
        } else {
          throw new IllegalArgumentException("Invalid cell type in grid configuration file.");
        }
      }
    }

    scanner.close();
    return new Grid(grid, rows, cols);
  }

  /**
   * Loads a list of cards from a specified file path.
   * The file should contain one card per line, with each line containing a name and four
   * attack values (North, South, East, and West).
   *
   * @param filePath the path to the card configuration file
   * @return a List of Card objects representing the loaded cards
   * @throws FileNotFoundException if the file is not found at the specified path
   * @throws IllegalArgumentException if the file contains invalid card data format
   */
  public static List<Card> loadCards(String filePath) throws FileNotFoundException {
    Scanner scanner = new Scanner(new File(filePath));
    List<Card> cards = new ArrayList<>();

    while (scanner.hasNextLine()) {
      String[] cardData = scanner.nextLine().split(" ");
      if (cardData.length != 5) {
        throw new IllegalArgumentException("Invalid card data format.");
      }
      String cardName = cardData[0];
      int north = parseAttackValue(cardData[1]);
      int south = parseAttackValue(cardData[2]);
      int east = parseAttackValue(cardData[3]);
      int west = parseAttackValue(cardData[4]);

      cards.add(new Card(cardName, north, south, east, west));
    }

    scanner.close();
    return cards;
  }

  /**
   * Parses an attack value from a string. The value "A" represents 10, while
   * other values are parsed as integers.
   *
   * @param value the string value to parse
   * @return the parsed integer attack value
   * @throws NumberFormatException if the value is not "A" or a valid integer
   */
  private static int parseAttackValue(String value) {
    if (value.equals("A")) {
      return 10;
    }
    return Integer.parseInt(value);
  }
}

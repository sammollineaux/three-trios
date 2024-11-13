import java.util.ArrayList;
import java.util.List;

public class ThreeTrios {
  public static void main(String[] args) {
    // Define a 3x3 grid layout with five card cells
    Cell[][] gridLayout = new Cell[3][3];
    for (int i = 0; i < 3; i++) {
      for (int j = 0; j < 3; j++) {
        gridLayout[i][j] = new Cell(false); // Assuming a constructor `Cell(boolean isCardCell)`
      }
    }

    List<Card> redHand = new ArrayList<>();
    List<Card> blueHand = new ArrayList<>();

    // Populate red player's hand with sample cards
    redHand.add(new Card("Red Card 1", 3, 4, 5, 2));
    redHand.add(new Card("Red Card 2", 5, 3, 2, 4));
    redHand.add(new Card("Red Card 3", 4, 5, 3, 2));

    // Populate blue player's hand with sample cards
    blueHand.add(new Card("Blue Card 1", 2, 3, 4, 5));
    blueHand.add(new Card("Blue Card 2", 3, 4, 5, 2));
    blueHand.add(new Card("Blue Card 3", 5, 2, 3, 4));
    // Set specific cells as card cells to ensure an odd count
    gridLayout[0][0] = new Cell(true);
    gridLayout[1][1] = new Cell(true);
    gridLayout[2][2] = new Cell(true);
    gridLayout[0][2] = new Cell(true);
    gridLayout[2][0] = new Cell(true);

    // Instantiate the Grid
    Grid grid = new Grid(gridLayout, 3, 3);

    // Create example players for red and blue
    Player redPlayer = new Player("Red"); // Configure as needed (e.g., set up hand)
    Player bluePlayer = new Player("Blue");

    redPlayer.setHand(redHand);
    bluePlayer.setHand(blueHand);

    // Instantiate the game model with the grid and players
    ReadOnlyThreeTriosModel model = new Game(grid, redPlayer, bluePlayer);

    // Instantiate the view with the model
    ThreeTriosView view = new ThreeTriosView(model);

    // Display the view
    view.setVisible(true);
  }
}

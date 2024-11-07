import org.junit.Assert;
import org.junit.Test;

/**
 * The Examples class provides high-level tests and demonstrations for basic gameplay
 * and interactions in the Three Trios game model. It helps readers understand typical
 * game setup and sequence of actions.
 */
public class ExamplesTest {

  @Test
  public void exampleBattlePhase() {
    try {
      // Set up grid with a 5x5 grid and place initial cards
      Grid grid = FileReader.loadGrid(
              "/Users/suie/Desktop/cs3500/HW5/src/files/grid_holes_reach.txt");
      Player redPlayer = new Player("Red");
      Player bluePlayer = new Player("Blue");

      // Add cards to players' hands
      redPlayer.addCardToHand(new Card("RedTiger", 8, 5, 7, 3));
      bluePlayer.addCardToHand(new Card("BlueDragon", 4, 9, 3, 6));

      // Place cards and test battle
      Game game = new Game(grid, redPlayer, bluePlayer);
      game.playTurn(2, 2, bluePlayer.getHand().get(0)); // Blue's initial placement
      game.playTurn(2, 1, redPlayer.getHand().get(0));  // Red's move to initiate battle

    } catch (Exception e) {
      Assert.fail("Exception in exampleBattlePhase: " + e.getMessage());
    }
  }
}

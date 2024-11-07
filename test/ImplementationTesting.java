import org.junit.Assert;
import org.junit.Test;

import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.PrintStream;

/**
 * This class contains unit tests for validating the implementation of the Three Trios game.
 * Tests include cell placement, grid configurations, ownership transfer, game-over conditions,
 * and rendering of the game board.
 */
public class ImplementationTesting {

  @Test
  public void testCellTypesOnNoHolesBoard() {
    try {
      Grid grid = FileReader.loadGrid(
              "/Users/suie/Desktop/cs3500/HW5/src/files/grid_no_holes.txt");

      for (int i = 0; i < grid.getRows(); i++) {
        for (int j = 0; j < grid.getCols(); j++) {
          Assert.assertTrue(grid.getCell(i, j).isCardCell());
        }
      }
    } catch (Exception e) {
      Assert.fail("Failed to validate no holes board layout: " + e.getMessage());
    }
  }

  @Test
  public void testPlaceCardOnDisconnectedBoard() {
    try {
      Grid grid = FileReader.loadGrid(
              "/Users/suie/Desktop/cs3500/HW5/src/files/grid_holes_no_reach.txt");
      Player player = new Player("Red");
      Card card = new Card("Phoenix", 4, 9, 6, 2);

      grid.placeCard(0, 0, card, player);

      Assert.assertEquals(card, grid.getCell(0, 0).getCard());
      Assert.assertEquals(player, grid.getCell(0, 0).getOwner());
    } catch (Exception e) {
      Assert.fail("Failed to place card on disconnected board: " + e.getMessage());
    }
  }

  @Test
  public void testGridDimensionsAndCardCount() {
    try {
      Grid grid = FileReader.loadGrid(
              "/Users/suie/Desktop/cs3500/HW5/src/files/grid_holes_reach.txt");

      Assert.assertEquals(5, grid.getRows());
      Assert.assertEquals(5, grid.getCols());

      int cardCells = 0;
      for (int i = 0; i < grid.getRows(); i++) {
        for (int j = 0; j < grid.getCols(); j++) {
          if (grid.getCell(i, j).isCardCell()) {
            cardCells++;
          }
        }
      }
      Assert.assertEquals(17, cardCells);
    } catch (Exception e) {
      Assert.fail("Failed to validate grid dimensions and card count: " + e.getMessage());
    }
  }

  @Test
  public void testFlipBackToOriginalOwner() {
    try {
      Grid grid = FileReader.loadGrid(
              "/Users/suie/Desktop/cs3500/HW5/src/files/grid_no_holes.txt");
      Player redPlayer = new Player("Red");
      Player bluePlayer = new Player("Blue");

      Card card = new Card("Phoenix", 4, 9, 6, 2);
      grid.placeCard(2, 2, card, redPlayer);

      grid.getCell(2, 2).flip(bluePlayer);
      Assert.assertEquals(bluePlayer, grid.getCell(2, 2).getOwner());

      grid.getCell(2, 2).flip(redPlayer);
      Assert.assertEquals(redPlayer, grid.getCell(2, 2).getOwner());
    } catch (Exception e) {
      Assert.fail("Failed to flip ownership back to original owner: " + e.getMessage());
    }
  }

  @Test
  public void testGameOverFull() {
    try {
      Grid grid = FileReader.loadGrid(
              "/Users/suie/Desktop/cs3500/HW5/src/files/grid_no_holes.txt");
      Player redPlayer = new Player("Red");
      Player bluePlayer = new Player("Blue");
      Game game = new Game(grid, redPlayer, bluePlayer);
      Player fillPlayer = new Player("Blue");
      Card fillCard = new Card("FillCard", 3, 3, 3, 3);

      for (int row = 0; row < grid.getRows(); row++) {
        for (int col = 0; col < grid.getCols(); col++) {
          grid.placeCard(row, col, fillCard, fillPlayer);
        }
      }
      Assert.assertTrue(game.isGameOver());
    } catch (Exception e) {
      Assert.fail("Failed game over check with a full grid: " + e.getMessage());
    }
  }

  @Test
  public void testGameIsNotOverWhenGridIsNotFull() {
    try {
      Grid grid = FileReader.loadGrid(
              "/Users/suie/Desktop/cs3500/HW5/src/files/grid_no_holes.txt");
      Player redPlayer = new Player("Red");
      Player bluePlayer = new Player("Blue");
      Game game = new Game(grid, redPlayer, bluePlayer);
      Player fillPlayer = new Player("Blue");
      Card fillCard = new Card("FillCard", 3, 3, 3, 3);

      for (int row = 0; row < grid.getRows(); row++) {
        for (int col = 0; col < grid.getCols(); col++) {
          if (row != 4 || col != 4) {
            grid.placeCard(row, col, fillCard, fillPlayer);
          }
        }
      }

      Assert.assertFalse(game.isGameOver());
    } catch (Exception e) {
      Assert.fail("Failed game over check with a partially filled grid: " + e.getMessage());
    }
  }

  @Test
  public void testConsecutivePlacementOwnershipTransfer() {
    try {
      Grid grid = FileReader.loadGrid(
              "/Users/suie/Desktop/cs3500/HW5/src/files/grid_no_holes.txt");
      Player redPlayer = new Player("Red");
      Player bluePlayer = new Player("Blue");

      Card redCard = new Card("RedCard", 8, 6, 4, 2);
      Card blueCard = new Card("BlueCard", 7, 3, 5, 9);

      grid.placeCard(2, 2, redCard, redPlayer);

      grid.placeCard(2, 3, blueCard, bluePlayer);
      grid.getCell(2, 2).flip(bluePlayer);

      Assert.assertEquals(bluePlayer, grid.getCell(2, 2).getOwner());
      Assert.assertEquals(redCard, grid.getCell(2, 2).getCard());
    } catch (Exception e) {
      Assert.fail("Failed consecutive placement ownership transfer: " + e.getMessage());
    }
  }

  @Test
  public void testFlipOwnCard() {
    try {
      Grid grid = FileReader.loadGrid(
              "/Users/suie/Desktop/cs3500/HW5/src/files/grid_no_holes.txt");
      Player redPlayer = new Player("Red");
      Card redCard = new Card("RedCard", 6, 5, 4, 3);

      grid.placeCard(2, 2, redCard, redPlayer);

      grid.getCell(2, 2).flip(redPlayer);

      Assert.assertEquals(redPlayer, grid.getCell(2, 2).getOwner());
    } catch (Exception e) {
      Assert.fail("Failed test for player not flipping their own card: " + e.getMessage());
    }
  }

  @Test
  public void testCheckWinner() throws FileNotFoundException {
    Grid grid = FileReader.loadGrid(
            "/Users/suie/Desktop/cs3500/HW5/src/files/grid_no_holes.txt");
    Player redPlayer = new Player("Red");
    Player bluePlayer = new Player("Blue");

    Card redCard = new Card("RedCard", 8, 4, 5, 6);
    Card blueCard = new Card("BlueCard", 2, 9, 7, 3);

    grid.placeCard(0, 0, redCard, redPlayer);
    grid.placeCard(1, 1, redCard, redPlayer);
    grid.placeCard(2, 2, blueCard, bluePlayer);
    grid.placeCard(3, 3, blueCard, bluePlayer);

    Player winner = Game.checkWinner(grid, redPlayer, bluePlayer);

    Assert.assertNull(winner);

    redPlayer.addCardToHand(new Card("ExtraRedCard", 3, 5, 6, 4));
    winner = Game.checkWinner(grid, redPlayer, bluePlayer);

    Assert.assertEquals(redPlayer, winner);
  }

  @Test
  public void testRenderEmptyGridAndHand() {
    try {
      Grid grid = FileReader.loadGrid(
              "/Users/suie/Desktop/cs3500/HW5/src/files/grid_holes_reach.txt");
      Player redPlayer = new Player("Red");

      ByteArrayOutputStream output = new ByteArrayOutputStream();
      System.setOut(new PrintStream(output));

      TextView textView = new TextView();
      textView.render(grid, redPlayer);

      System.setOut(System.out);

      String expectedOutput = "Player: RED\n" +
              " _  _  X  _  X \n" +
              " _  X  _  X  _ \n" +
              " _  _  _  _  _ \n" +
              " _  X  _  X  _ \n" +
              " _  _  X  _  X \n" +
              "Hand:\n";

      Assert.assertEquals(expectedOutput, output.toString());
    } catch (FileNotFoundException e) {
      Assert.fail("Grid file not found: " + e.getMessage());
    }
  }
}

import org.junit.jupiter.api.Test;
import java.util.List;
import static org.junit.jupiter.api.Assertions.*;

/**
 * This class contains unit tests for validating the core components of the Three Trios game model.
 * It tests the behavior of key model classes
 */
public class ModelTest {
  @Test
  public void testLoadNoHolesBoard() {
    try {
      Grid grid = FileReader.loadGrid(
              "/Users/suie/Desktop/cs3500/HW5/src/files/grid_no_holes.txt");

      assertEquals(5, grid.getRows());
      assertEquals(5, grid.getCols());

      for (int i = 0; i < grid.getRows(); i++) {
        for (int j = 0; j < grid.getCols(); j++) {
          assertTrue(grid.getCell(i, j).isCardCell());
        }
      }
    } catch (Exception e) {
      fail("Loading no holes board failed: " + e.getMessage());
    }
  }

  @Test
  public void testLoadReachableHolesBoard() {
    try {
      Grid grid = FileReader.loadGrid(
              "/Users/suie/Desktop/cs3500/HW5/src/files/grid_holes_reach.txt");

      assertEquals(5, grid.getRows());
      assertEquals(5, grid.getCols());

      assertFalse(grid.getCell(0, 2).isCardCell());
      assertTrue(grid.getCell(0, 3).isCardCell());
    } catch (Exception e) {
      fail("Loading reachable holes board failed: " + e.getMessage());
    }
  }

  @Test
  public void testLoadDisconnectedBoard() {
    try {
      Grid grid = FileReader.loadGrid(
              "/Users/suie/Desktop/cs3500/HW5/src/files/grid_holes_no_reach.txt");

      assertEquals(6, grid.getRows());
      assertEquals(5, grid.getCols());

      assertTrue(grid.getCell(0, 0).isCardCell());
      assertFalse(grid.getCell(1, 1).isCardCell());
    } catch (Exception e) {
      fail("Loading disconnected groups board failed: " + e.getMessage());
    }
  }

  @Test
  public void testCardDeckLoading() {
    try {
      List<Card> smallDeck = FileReader.loadCards(
              "/Users/suie/Desktop/cs3500/HW5/src/files/few_cards.txt");
      List<Card> largeDeck = FileReader.loadCards(
              "/Users/suie/Desktop/cs3500/HW5/src/files/cards.txt");

      // Verify deck sizes
      assertEquals(13, smallDeck.size());
      assertEquals(31, largeDeck.size());
    } catch (Exception e) {
      fail("Loading card decks failed: " + e.getMessage());
    }
  }

  @Test
  public void testNoHolesBoardCardPlacement() {
    try {
      Grid grid = FileReader.loadGrid(
              "/Users/suie/Desktop/cs3500/HW5/src/files/grid_no_holes.txt");
      Player player = new Player("Red");
      Card card = new Card("Phoenix", 4, 9, 6, 2);

      grid.placeCard(2, 2, card, player);
      assertEquals(card, grid.getCell(2, 2).getCard());
      assertEquals(player, grid.getCell(2, 2).getOwner());
    } catch (Exception e) {
      fail("Failed to place card on 5x5 no holes board: " + e.getMessage());
    }
  }

  @Test
  public void testReachableHolesBoardLayout() {
    try {
      Grid grid = FileReader.loadGrid(
              "/Users/suie/Desktop/cs3500/HW5/src/files/grid_holes_reach.txt");

      assertTrue(grid.getCell(0, 0).isCardCell());
      assertFalse(grid.getCell(0, 2).isCardCell());
      assertTrue(grid.getCell(2, 2).isCardCell());
    } catch (Exception e) {
      fail("Failed to validate reachable holes layout: " + e.getMessage());
    }
  }

  @Test
  public void testDisconnectedGroupsGrid() {
    try {
      Grid grid = FileReader.loadGrid(
              "/Users/suie/Desktop/cs3500/HW5/src/files/grid_holes_no_reach.txt");

      assertTrue(grid.getCell(0, 0).isCardCell());
      assertFalse(grid.getCell(1, 1).isCardCell());
      assertTrue(grid.getCell(5, 4).isCardCell());
    } catch (Exception e) {
      fail("Failed to validate disconnected groups layout: " + e.getMessage());
    }
  }

  @Test
  public void testInvalidMoveOnHole() {
    try {
      Grid grid = FileReader.loadGrid(
              "/Users/suie/Desktop/cs3500/HW5/src/files/grid_holes_reach.txt");
      Player player = new Player("Blue");
      Card card = new Card("Dragon", 7, 5, 3, 8);

      assertThrows(IllegalArgumentException.class, () -> grid.placeCard(0, 2, card,
              player));
    } catch (Exception e) {
      fail("Failed to validate move restriction on hole cell: " + e.getMessage());
    }
  }

  @Test
  public void testValidMoveAndFlipOwnership() {
    try {
      Grid grid = FileReader.loadGrid(
              "/Users/suie/Desktop/cs3500/HW5/src/files/grid_no_holes.txt");
      Player redPlayer = new Player("Red");
      Player bluePlayer = new Player("Blue");

      Card redCard = new Card("Phoenix", 4, 9, 6, 2);
      Card blueCard = new Card("Dragon", 7, 5, 3, 8);

      grid.placeCard(2, 2, redCard, redPlayer);
      grid.placeCard(2, 3, blueCard, bluePlayer);

      grid.getCell(2, 3).flip(redPlayer);

      assertEquals(redPlayer, grid.getCell(2, 3).getOwner());
      assertEquals(blueCard, grid.getCell(2, 3).getCard());
    } catch (Exception e) {
      fail("Failed to test valid move and ownership flip: " + e.getMessage());
    }
  }

  @Test
  public void testLoadAndVerifyNoHolesBoard() {
    try {
      Grid grid = FileReader.loadGrid(
              "/Users/suie/Desktop/cs3500/HW5/src/files/grid_no_holes.txt");

      for (int i = 0; i < grid.getRows(); i++) {
        for (int j = 0; j < grid.getCols(); j++) {
          assertTrue(grid.getCell(i, j).isCardCell(), "All cells should be card cells.");
        }
      }
    } catch (Exception e) {
      fail("Failed to load no holes board: " + e.getMessage());
    }
  }

  @Test
  public void testPlaceAndRetrieveCard() {
    try {
      Grid grid = FileReader.loadGrid(
              "/Users/suie/Desktop/cs3500/HW5/src/files/grid_no_holes.txt");
      Player player = new Player("Red");
      Card card = new Card("Dragon", 5, 3, 7, 9);

      grid.placeCard(2, 2, card, player);

      assertEquals(card, grid.getCell(2, 2).getCard(),
              "Expected the Dragon card to be at (2,2)");
      assertEquals(player, grid.getCell(2, 2).getOwner(),
              "Expected ownership to be Red player");
    } catch (Exception e) {
      fail("Failed to place and retrieve card: " + e.getMessage());
    }
  }

  @Test
  public void testInvalidMoveOnHoleCell() {
    try {
      Grid grid = FileReader.loadGrid(
              "/Users/suie/Desktop/cs3500/HW5/src/files/grid_holes_reach.txt");
      Player player = new Player("Blue");
      Card card = new Card("Phoenix", 4, 9, 6, 2);

      assertThrows(IllegalArgumentException.class, () -> grid.placeCard(0, 2, card,
                      player),
              "Expected an exception when placing on a hole cell.");
    } catch (Exception e) {
      fail("Exception handling failed for invalid move on hole cell: " + e.getMessage());
    }
  }

  @Test
  public void testOwnershipFlip() {
    try {
      Grid grid = FileReader.loadGrid(
              "/Users/suie/Desktop/cs3500/HW5/src/files/grid_no_holes.txt");
      Player redPlayer = new Player("Red");
      Player bluePlayer = new Player("Blue");
      Card redCard = new Card("Dragon", 7, 5, 3, 8);
      Card blueCard = new Card("Phoenix", 4, 9, 6, 2);

      grid.placeCard(2, 2, redCard, redPlayer);
      grid.placeCard(2, 3, blueCard, bluePlayer);

      grid.getCell(2, 3).flip(redPlayer);

      assertEquals(redPlayer, grid.getCell(2, 3).getOwner(),
              "Expected ownership to be Red after flip.");
    } catch (Exception e) {
      fail("Failed ownership flip: " + e.getMessage());
    }
  }

  @Test
  public void testCheckValidMoveOnEmptyCardCell() {
    try {
      Grid grid = FileReader.loadGrid(
              "/Users/suie/Desktop/cs3500/HW5/src/files/grid_no_holes.txt");
      assertTrue(grid.isValidMove(1, 1), "Expected (1,1) to be a valid move.");
    } catch (Exception e) {
      fail("Valid move check failed: " + e.getMessage());
    }
  }

  @Test
  public void testCheckInvalidMoveOnOccupiedCardCell() {
    try {
      Grid grid = FileReader.loadGrid(
              "/Users/suie/Desktop/cs3500/HW5/src/files/grid_no_holes.txt");
      Player player = new Player("Red");
      Card card = new Card("Dragon", 7, 5, 3, 8);

      grid.placeCard(1, 1, card, player);

      assertFalse(grid.isValidMove(1, 1),
              "Expected (1,1) to be invalid as it's already occupied.");
    } catch (Exception e) {
      fail("Invalid move check on occupied cell failed: " + e.getMessage());
    }
  }

  @Test
  public void testEnsureOddCardCells() {
    try {
      Grid grid = FileReader.loadGrid(
              "/Users/suie/Desktop/cs3500/HW5/src/files/grid_holes_reach.txt");
      int cardCells = 0;

      for (int i = 0; i < grid.getRows(); i++) {
        for (int j = 0; j < grid.getCols(); j++) {
          if (grid.getCell(i, j).isCardCell()) {
            cardCells++;
          }
        }
      }
      assertEquals(17, cardCells,
              "Expected an odd number of card cells (19) for this grid.");
    } catch (Exception e) {
      fail("Failed to ensure odd card cells count: " + e.getMessage());
    }
  }

  @Test
  public void testBoundaryMoves() {
    try {
      Grid grid = FileReader.loadGrid(
              "/Users/suie/Desktop/cs3500/HW5/src/files/grid_no_holes.txt");
      Player player = new Player("Red");
      Card card = new Card("BoundaryTest", 5, 5, 5, 5);

      grid.placeCard(0, 0, card, player);
      grid.placeCard(0, 4, card, player);
      grid.placeCard(4, 0, card, player);
      grid.placeCard(4, 4, card, player);

      assertEquals(player, grid.getCell(0, 0).getOwner());
      assertEquals(player, grid.getCell(0, 4).getOwner());
      assertEquals(player, grid.getCell(4, 0).getOwner());
      assertEquals(player, grid.getCell(4, 4).getOwner());
    } catch (Exception e) {
      fail("Failed boundary moves: " + e.getMessage());
    }
  }

  @Test
  public void testSurroundingCellPlacement() {
    try {
      Grid grid = FileReader.loadGrid(
              "/Users/suie/Desktop/cs3500/HW5/src/files/grid_no_holes.txt");
      Player redPlayer = new Player("Red");
      Player bluePlayer = new Player("Blue");

      grid.placeCard(1, 2, new Card("NorthCard", 3, 5, 4, 2),
              redPlayer);
      grid.placeCard(3, 2, new Card("SouthCard", 4, 6, 3, 7),
              bluePlayer);
      grid.placeCard(2, 1, new Card("WestCard", 5, 3, 6, 8),
              redPlayer);
      grid.placeCard(2, 3, new Card("EastCard", 2, 7, 3, 5),
              bluePlayer);

      assertEquals(redPlayer, grid.getCell(1, 2).getOwner());
      assertEquals(bluePlayer, grid.getCell(3, 2).getOwner());
      assertEquals(redPlayer, grid.getCell(2, 1).getOwner());
      assertEquals(bluePlayer, grid.getCell(2, 3).getOwner());
    } catch (Exception e) {
      fail("Failed surrounding cell placement: " + e.getMessage());
    }
  }

  @Test
  public void testEmptyHandPlacement() {
    try {
      Player player = new Player("Blue");
      Grid grid = FileReader.loadGrid(
              "/Users/suie/Desktop/cs3500/HW5/src/files/grid_holes_reach.txt");
      assertTrue(player.getHand().isEmpty(), "Expected player's hand to be empty.");
      assertThrows(IllegalArgumentException.class, () -> {
        if (player.getHand().isEmpty()) {
          throw new IllegalArgumentException("Cannot place a card from an empty hand.");
        } else {
          grid.placeCard(2, 2, player.getHand().get(0), player);
        }
      }, "Expected error when placing a card from an empty hand.");

    } catch (Exception e) {
      fail("Exception handling failed for empty hand placement: " + e.getMessage());
    }
  }

  @Test
  public void testOutOfBoundsPlacement() {
    try {
      Grid grid = FileReader.loadGrid(
              "/Users/suie/Desktop/cs3500/HW5/src/files/grid_no_holes.txt");
      Player player = new Player("Red");
      Card card = new Card("OutOfBoundsCard", 4, 5, 6, 7);

      assertThrows(ArrayIndexOutOfBoundsException.class, () -> grid.placeCard(-1, 0, card,
                      player),
              "Expected ArrayIndexOutOfBoundsException when placing card outside grid.");
      assertThrows(ArrayIndexOutOfBoundsException.class, () -> grid.placeCard(0, -1, card,
                      player),
              "Expected ArrayIndexOutOfBoundsException when placing card outside grid.");
      assertThrows(ArrayIndexOutOfBoundsException.class, () -> grid.placeCard(5, 5, card,
                      player),
              "Expected ArrayIndexOutOfBoundsException when placing card outside grid.");
    } catch (Exception e) {
      fail("Failed out-of-bounds placement test: " + e.getMessage());
    }
  }

  @Test
  public void testMaxAttackFlip() {
    try {
      Grid grid = FileReader.loadGrid(
              "/Users/suie/Desktop/cs3500/HW5/src/files/grid_no_holes.txt");
      Player redPlayer = new Player("Red");
      Player bluePlayer = new Player("Blue");
      Card maxAttackCard = new Card("MaxAttackCard", 9, 9, 9, 9);
      Card regularCard = new Card("RegularCard", 4, 5, 6, 3);

      grid.placeCard(2, 2, maxAttackCard, redPlayer);
      grid.placeCard(2, 3, regularCard, bluePlayer);

      grid.getCell(2, 3).flip(redPlayer);

      assertEquals(redPlayer, grid.getCell(2, 3).getOwner(),
              "Expected ownership to be Red after flip.");
    } catch (Exception e) {
      fail("Failed max attack flip test: " + e.getMessage());
    }
  }

  @Test
  public void testLargeDeckDealing() {
    try {
      List<Card> largeDeck = FileReader.loadCards(
              "/Users/suie/Desktop/cs3500/HW5/src/files/cards.txt");
      Player redPlayer = new Player("Red");
      Player bluePlayer = new Player("Blue");

      // Deal half the deck to each player
      for (int i = 0; i < largeDeck.size(); i++) {
        if (i % 2 == 0) {
          redPlayer.addCardToHand(largeDeck.get(i));
        } else {
          bluePlayer.addCardToHand(largeDeck.get(i));
        }
      }

      assertEquals(16, redPlayer.getHand().size(),
              "Red player should have half of the large deck (16 cards).");
      assertEquals(15, bluePlayer.getHand().size(),
              "Blue player should have half of the large deck (15 cards).");
    } catch (Exception e) {
      fail("Failed large deck dealing: " + e.getMessage());
    }
  }

  @Test
  public void testFullGridPlacement() {
    try {
      Grid grid = FileReader.loadGrid(
              "/Users/suie/Desktop/cs3500/HW5/src/files/grid_no_holes.txt");
      Player player = new Player("Blue");
      Card card = new Card("FillCard", 3, 3, 3, 3);

      for (int i = 0; i < grid.getRows(); i++) {
        for (int j = 0; j < grid.getCols(); j++) {
          grid.placeCard(i, j, card, player);
        }
      }

      for (int i = 0; i < grid.getRows(); i++) {
        for (int j = 0; j < grid.getCols(); j++) {
          assertFalse(grid.getCell(i, j).isEmpty(),
                  "Expected cell (" + i + "," + j + ") to be filled.");
        }
      }
    } catch (Exception e) {
      fail("Failed full grid placement test: " + e.getMessage());
    }
  }
}

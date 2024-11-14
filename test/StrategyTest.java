import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.io.FileNotFoundException;

public class StrategyTest {
    private Game game;
    @Test
    void testFlipMost(){
        try {
            // Set up grid with a 5x5 grid and place initial cards
            Grid grid = FileReader.loadGrid(
                    "/Users/sammollineaux/IdeaProjects/three-trios-1/src/files/grid_no_holes.txt");
            Player redPlayer = new Player("Red");
            Player bluePlayer = new Player("Blue");

            // Add cards to players' hands
            redPlayer.addCardToHand(new Card("RedTiger", 8, 5, 7, 3));
            redPlayer.addCardToHand(new Card("RedLion", 7, 4, 4, 7));
            redPlayer.addCardToHand(new Card("RedPanther", 9, 6, 8, 9));
            redPlayer.addCardToHand(new Card("RedCat", 6, 3, 6, 4));
            redPlayer.addCardToHand(new Card("RedPanther", 7, 7, 3, 2));

            bluePlayer.addCardToHand(new Card("BlueDragon", 4, 9, 3, 6));
            bluePlayer.addCardToHand(new Card("BlueSnake", 6, 3, 8, 7));
            bluePlayer.addCardToHand(new Card("BlueShark", 5, 6, 5, 2));
            bluePlayer.addCardToHand(new Card("BlueCrocodile", 8, 4, 2, 8));
            bluePlayer.addCardToHand(new Card("BlueLizard", 6, 4, 5, 6));

            // Initialize game
            Game game = new Game(grid, redPlayer, bluePlayer);
            game.playTurn(2,1,game.getBluePlayerHand().get(0));
            game.playTurn(2,3,game.getBluePlayerHand().get(1));
            game.playTurn(2,4,game.getBluePlayerHand().get(2));

            Assertions.assertEquals(0, game.getFlipCount(0 , 2, 2));
            Assertions.assertEquals(1, game.getFlipCount(1, 2,2));
            Assertions.assertEquals(3, game.getFlipCount(2, 2,2));

            ThreeTriosStrategyImpl strategy = new ThreeTriosStrategyImpl();
            strategy.flipMost("Red", game);

            Assertions.assertEquals(redPlayer.getHand().get(2), game.getCellContents(2,2));

        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    @Test
    void testBestCorner() {
        // Set up grid with a 5x5 grid and place initial cards
        Grid grid = null;
        try {
            grid = FileReader.loadGrid(
                    "/Users/sammollineaux/IdeaProjects/three-trios-1/src/files/grid_no_holes.txt");
            Player redPlayer = new Player("Red");
            Player bluePlayer = new Player("Blue");

            // Add cards to players' hands
            redPlayer.addCardToHand(new Card("RedTiger", 8, 5, 7, 3)); //0 //1
            redPlayer.addCardToHand(new Card("RedLion", 7, 4, 4, 7)); //-4 //4
            redPlayer.addCardToHand(new Card("RedPanther", 9, 6, 8, 9)); //2 //8
            redPlayer.addCardToHand(new Card("RedCat", 6, 3, 6, 4)); //-3 //0
            redPlayer.addCardToHand(new Card("RedPanther", 7, 7, 3, 2)); //2 //2

            bluePlayer.addCardToHand(new Card("BlueDragon", 4, 9, 3, 6));
            bluePlayer.addCardToHand(new Card("BlueSnake", 6, 3, 8, 7));
            bluePlayer.addCardToHand(new Card("BlueShark", 5, 6, 5, 2));
            bluePlayer.addCardToHand(new Card("BlueCrocodile", 8, 4, 2, 8));
            bluePlayer.addCardToHand(new Card("BlueLizard", 6, 4, 5, 6));

            // Initialize game
            Game game = new Game(grid, redPlayer, bluePlayer);
            game.playTurn(0,1,game.getBluePlayerHand().get(0)); //6
            game.playTurn(1,0,game.getBluePlayerHand().get(1)); //6
            game.playTurn(0,3, game.getBluePlayerHand().get(3)); //2
            game.playTurn(1,4, game.getBluePlayerHand().get(2)); //5
            game.playTurn(4,0, game.getRedPlayerHand().get(0));
            game.playTurn(4,4, game.getRedPlayerHand().get(0));

            ThreeTriosStrategyImpl strategy = new ThreeTriosStrategyImpl();
            strategy.bestCorner("Red", game);

            Assertions.assertEquals(redPlayer.getHand().get(2),game.getCellContents(0,4));
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }


    }
}

Overview

Three Trios is a strategic two-player card game where players compete for control of a grid by
placing cards with directional attack values. Each player places a card in a cell and battles adjacent opponent cards, attempting to flip them to their control. The game ends when all card cells on the grid are filled, and the player with the most owned cards wins.

Highlights
	Objective: Control the most cells on the grid by strategically placing and flipping cards.
	Components: Grid-based layout, player-owned cards, and simple, rule-driven battles.
	Setup: Game configurations are flexible, allowing custom grid layouts and card decks
	       loaded from configuration files.

The following example demonstrates setting up a game with players,
loading a grid and card configurations, and starting gameplay.

import java.util.List;

public class Main {
    public static void main(String[] args) {
        try {
            // Load grid and cards
            Grid grid = FileReader.loadGrid("path/to/5x5_grid_with_holes.txt");
            List<Card> cards = FileReader.loadCards("path/to/cards.txt");

            // Initialize players
            Player redPlayer = new Player("Red");
            Player bluePlayer = new Player("Blue");

            // Distribute cards
            redPlayer.addCardToHand(cards.get(0));
            bluePlayer.addCardToHand(cards.get(1));

            // Set up the game
            Game game = new Game(grid, redPlayer, bluePlayer);
            TextView view = new TextView();
            view.render(grid, redPlayer);

            // Execute a sample turn
            game.playTurn(0, 0, redPlayer.getHand().get(0));
            view.render(grid, bluePlayer);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

Key Components

1. Game Flow - Game
The Game class orchestrates the gameplay, managing turns, card placements, and battle outcomes.
It enforces game rules and ends the game when the grid is fully occupied.

2. Grid Layout - Grid and Cell
The Grid represents the game board, with each Cell as a card cell or a hole.
 Card cells can be filled by player cards, while holes block placement.

3. Players and Cards - Player and Card
	Player: Holds a collection of cards (the player’s hand) and owns cells during the game.
	Card: Represents individual cards with directional attack values used in gameplay.

4. Configuration - FileReader
The FileReader loads grid and card data from configuration files,
making it easy to set up game layouts and decks.

5. Display - TextView
The TextView provides a simple, text-based display of the game state,
showing the grid with player-owned cells and the current player’s hand of cards.
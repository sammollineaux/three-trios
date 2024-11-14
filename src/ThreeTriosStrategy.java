public interface ThreeTriosStrategy {
    /**
     * makes the move that will flip the most cards
     * @param color the color of the player making the move
     */
    void flipMost (String color, Game game);

    /**
     * makes the move with optimal card to the optimal corner
     * @param color the color of the player making the move
     */
    void bestCorner (String color, Game game);
}

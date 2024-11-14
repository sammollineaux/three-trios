import java.util.List;

public class ThreeTriosStrategyImpl implements ThreeTriosStrategy {
    @Override
    public void flipMost(String color, Game game) {
        // var declarations
        int dim[] = game.getGridDimensions();
        int bestIdx = 0;
        int bestCoords[] = {0, 0};
        int bestFlipCount = 0;

        // creates playerHand based on color of player making turn
        List<Card> playerHand = color.equals("Red") ? game.getRedPlayerHand() : game.getBluePlayerHand();

        // goes through each cell on board
        // any cell that is a valid move is tested to check
        // if it could potentially be the optimal move
        for (int i = 0; i < dim[0]; i++) {
            for (int j = 0; j < dim[1]; j++) {
                Cell currentCell = (Cell) game.getCellContents(i, j);
                if (currentCell.isCardCell() && currentCell.isEmpty()) {
                    int highestFlips = 0;
                    int highestIdx = 0;

                    for (int k = 0; k < playerHand.size(); k++) {
                        int count = game.getFlipCount(0, i, j);
                        if (count > highestFlips) {
                            highestIdx = k;
                            highestFlips = count;
                        }
                    }

                    // if the number of flips is beaten
                    // it is set as highest and its info saved
                    if (highestFlips > bestFlipCount) {
                        bestFlipCount = highestFlips;
                        bestIdx = highestIdx;
                        bestCoords[0] = i;
                        bestCoords[1] = j;
                    }
                }
            }
        }

        // makes the most optimal move for flipping cards
        // in accordance with the color provided
        if (color.equals("Red")) {
            game.playTurn(bestCoords[0], bestCoords[1], game.getRedPlayerHand().get(bestIdx));
        } else {
            game.playTurn(bestCoords[0], bestCoords[1], game.getBluePlayerHand().get(bestIdx));
        }
    }


    @Override
    public void bestCorner(String color, Game game) {
        int dim[] = game.getGridDimensions();
        int bestIdx = 0;
        int bestCoords[] = {0,0};
        int bestAggregateDifferential = 0;
        List<Card> hand = List.of();
        int handSize = 0;

        if(color.equals("Red")){
            hand = game.getRedPlayerHand();
            handSize = game.getRedPlayerHand().size();
        }
        if(color.equals("Blue")){
            hand = game.getBluePlayerHand();
            handSize = game.getBluePlayerHand().size();
        }

        //test top-left
        for(int i = 0; i < handSize; i++){
            if(game.getCellContents(0, 0) != null){
                break;
            }
            Card current = hand.get(i);
            Card eastCard = (Card) game.getCellContents(0, 1);
            int eastAtck = eastCard.getAttackValue(Direction.WEST);

            Card southCard = (Card) game.getCellContents(1,0);
            int southAtck = southCard.getAttackValue(Direction.NORTH);

            int aggregate = (current.getAttackValue(Direction.EAST) - eastAtck) +
                    (current.getAttackValue(Direction.SOUTH) - southAtck);

            if(aggregate > bestAggregateDifferential){
                bestAggregateDifferential = aggregate;
                bestIdx = i;
                bestCoords[0] = 0;
                bestCoords[1] = 0;
            }
        }

        //test top-right
        for(int i = 0; i < handSize; i++){
            if(game.getCellContents(0, dim[1]) != null){
                break;
            }
            Card current = hand.get(i);
            Card westCard = (Card) game.getCellContents(0, dim[1] - 1);
            int westAtck = westCard.getAttackValue(Direction.EAST);

            Card southCard = (Card) game.getCellContents(1,dim[1]);
            int southAtck = southCard.getAttackValue(Direction.NORTH);

            int aggregate = (current.getAttackValue(Direction.WEST) - westAtck) +
                    (current.getAttackValue(Direction.SOUTH) - southAtck);

            if(aggregate > bestAggregateDifferential){
                bestAggregateDifferential = aggregate;
                bestIdx = i;
                bestCoords[0] = dim[0];
                bestCoords[1] = 0;
            }
        }

        //test bottom-left
        for(int i = 0; i < handSize; i++){
            if(game.getCellContents(dim[0], 0) != null){
                break;
            }
            Card current = hand.get(i);
            Card eastCard = (Card) game.getCellContents(dim[0], 1);
            int eastAtck = eastCard.getAttackValue(Direction.WEST);

            Card northCard = (Card) game.getCellContents(dim[0] -1,0);
            int northAtck = northCard.getAttackValue(Direction.SOUTH);

            int aggregate = (current.getAttackValue(Direction.EAST) - eastAtck) +
                    (current.getAttackValue(Direction.NORTH) - northAtck);

            if(aggregate > bestAggregateDifferential){
                bestAggregateDifferential = aggregate;
                bestIdx = i;
                bestCoords[0] = 0;
                bestCoords[1] = dim[1];
            }
        }

        //test bottom-right
        for(int i = 0; i < handSize; i++){
            if(game.getCellContents(dim[0], dim[1]) != null){
                break;
            }
            Card current = hand.get(i);
            Card westCard = (Card) game.getCellContents(dim[0], dim[1]);
            int westAtck = westCard.getAttackValue(Direction.EAST);

            Card northCard = (Card) game.getCellContents(dim[0] -1,dim[1]);
            int northAtck = northCard.getAttackValue(Direction.SOUTH);

            int aggregate = (current.getAttackValue(Direction.WEST) - westAtck) +
                    (current.getAttackValue(Direction.NORTH) - northAtck);

            if(aggregate > bestAggregateDifferential){
                bestAggregateDifferential = aggregate;
                bestIdx = i;
                bestCoords[0] = dim[0];
                bestCoords[1] = dim[1];
            }
        }

        if(color.equals("Red")){
            game.playTurn(bestCoords[0], bestCoords[1], game.getRedPlayerHand().get(bestIdx));
        }
        if(color.equals("Blue")){
            game.playTurn(bestCoords[0], bestCoords[1], game.getBluePlayerHand().get(bestIdx));
        }
    }
}
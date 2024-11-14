import java.io.FileNotFoundException;
import java.util.List;

public interface ThreeTriosModel extends ReadOnlyThreeTriosModel {

  void playTurn(int row, int col, Card card);

  void setupGame(String gridFilePath, String cardFilePath) throws FileNotFoundException;

  int getFlipCount(int cardIndex, int row, int col);
}

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.List;

/**
 * The main view class for the Three Trios game, extending JFrame and implementing ThreeTriosFrame.
 * This class sets up the game interface with a grid panel, player hands, and a current player label.
 * It can also update the view to reflect changes when cards are placed or flipped.
 */
public class ThreeTriosView extends JFrame implements ThreeTriosFrame {
  private final ReadOnlyThreeTriosModel model;
  private JPanel selectedCardButton = null;
  private final GridPanel gridPanel;
  private final HandPanel redHandPanel;
  private final HandPanel blueHandPanel;

  /**
   * Constructs a new ThreeTriosView with the given model.
   *
   * @param model the read-only model representing the state of the game
   */
  public ThreeTriosView(ReadOnlyThreeTriosModel model) {
    super("Three Trios Game");
    this.model = model;
    this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    this.setPreferredSize(new Dimension(1000, 800));

    JPanel mainPanel = new JPanel(new BorderLayout());

    gridPanel = new GridPanel();
    mainPanel.add(gridPanel, BorderLayout.CENTER);

    redHandPanel = new HandPanel("Red", model.getRedPlayerHand());
    blueHandPanel = new HandPanel("Blue", model.getBluePlayerHand());

    mainPanel.add(redHandPanel, BorderLayout.WEST);
    mainPanel.add(blueHandPanel, BorderLayout.EAST);

    JLabel currentPlayerLabel = new JLabel("Current Player: RED", SwingConstants.CENTER);
    mainPanel.add(currentPlayerLabel, BorderLayout.NORTH);

    this.add(mainPanel);
    this.pack();
    this.setVisible(true);

    mainPanel.revalidate();
    mainPanel.repaint();
  }

  /**
   * Updates the view to reflect changes in the model, such as cards being placed or flipped.
   */
  public void updateView() {
    gridPanel.updateGrid();
    redHandPanel.updateHand(model.getRedPlayerHand());
    blueHandPanel.updateHand(model.getBluePlayerHand());
  }

  /**
   * A nested class representing the grid panel in the game view.
   * Displays the game grid with clickable cells.
   */
  private class GridPanel extends JPanel implements ThreeTriosGridPanel {

    /**
     * Constructs a new GridPanel and initializes its layout based on the model's grid dimensions.
     */
    public GridPanel() {
      int[] dimensions = model.getGridDimensions();
      this.setLayout(new GridLayout(dimensions[0], dimensions[1]));
      populateGrid();
    }

    /**
     * Populates the grid with buttons representing each cell.
     */
    private void populateGrid() {
      int[] dimensions = model.getGridDimensions();
      for (int row = 0; row < dimensions[0]; row++) {
        for (int col = 0; col < dimensions[1]; col++) {
          JButton cellButton = new JButton();
          cellButton.setBackground(Color.LIGHT_GRAY);
          cellButton.setPreferredSize(new Dimension(80, 80));
          final int currentRow = row;
          final int currentCol = col;

          cellButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
              System.out.println("Grid cell clicked at: (" + currentRow + ", " + currentCol + ")");
            }
          });
          this.add(cellButton);
        }
      }
    }

    /**
     * Updates the grid to reflect the current state of the model, including placed and flipped cards.
     */
    public void updateGrid() {
      this.removeAll();
      populateGrid();
      int[] dimensions = model.getGridDimensions();
      for (int row = 0; row < dimensions[0]; row++) {
        for (int col = 0; col < dimensions[1]; col++) {
          Object card = model.getCellContents(row, col);
          if (card != null) {
            JPanel cardPanel = createCardPanel((Card) card);
            this.add(cardPanel);
          }
        }
      }
      this.revalidate();
      this.repaint();
    }
  }

  /**
   * A nested class representing the panel for a player's hand of cards.
   * Displays cards vertically and allows selection of individual cards.
   */
  private class HandPanel extends JPanel implements ThreeTriosHandPanel {
    private final String playerColor;

    /**
     * Constructs a new HandPanel for a player.
     *
     * @param playerColor the color of the player (e.g., "Red" or "Blue")
     * @param hand the list of cards in the player's hand
     */
    public HandPanel(String playerColor, List<Card> hand) {
      this.playerColor = playerColor;
      this.setLayout(new GridLayout(hand.size(), 1)); // Adjust layout to match the number of cards
      this.setPreferredSize(new Dimension(100, 800));

      if (playerColor.equals("Red")) {
        this.setBackground(Color.RED);
      } else if (playerColor.equals("Blue")) {
        this.setBackground(Color.BLUE);
      }
      this.setOpaque(true);
      populateHand(hand);
    }

    /**
     * Populates the hand panel with card panels representing each card in the player's hand.
     *
     * @param hand the list of cards in the player's hand
     */
    private void populateHand(List<Card> hand) {
      this.removeAll();
      for (int i = 0; i < hand.size(); i++) {
        Card card = hand.get(i);
        JPanel cardPanel = createCardPanel(card);
        cardPanel.setPreferredSize(new Dimension(80, 80));

        if (playerColor.equals("Red")) {
          cardPanel.setBackground(Color.PINK);
        } else if (playerColor.equals("Blue")) {
          cardPanel.setBackground(Color.CYAN);
        }

        int cardIndex = i;
        cardPanel.addMouseListener(new MouseAdapter() {
          @Override
          public void mouseClicked(MouseEvent e) {
            System.out.println(playerColor + " card clicked at index: " + cardIndex);
            highlightCard(cardPanel);
          }
        });

        this.add(cardPanel);
      }

      this.revalidate();
      this.repaint();
    }

    /**
     * Updates the hand panel with the current list of cards.
     *
     * @param hand the updated list of cards in the player's hand
     */
    public void updateHand(List<Card> hand) {
      populateHand(hand);
    }
  }

  /**
   * Highlights a selected card by setting a border around it.
   *
   * @param cardButton the JPanel representing the card to be highlighted
   */
  private void highlightCard(JPanel cardButton) {
    if (selectedCardButton != null) {
      selectedCardButton.setBorder(BorderFactory.createEmptyBorder());
    }
    selectedCardButton = cardButton;
    selectedCardButton.setBorder(BorderFactory.createLineBorder(Color.BLACK, 6));
  }

  /**
   * Creates a JPanel representing a card with labels for each attack direction.
   *
   * @param card the card to be displayed
   * @return a JPanel containing the card's attack values
   */
  private JPanel createCardPanel(Card card) {
    JPanel panel = new JPanel(new BorderLayout());
    panel.setBackground(Color.WHITE);
    panel.setBorder(BorderFactory.createLineBorder(Color.BLACK, 1));

    JLabel northLabel = new JLabel(String.valueOf(card.getAttackValue(Direction.NORTH)), SwingConstants.CENTER);
    northLabel.setFont(new Font("Arial", Font.BOLD, 14));
    panel.add(northLabel, BorderLayout.NORTH);

    JLabel westLabel = new JLabel(String.valueOf(card.getAttackValue(Direction.WEST)), SwingConstants.LEFT);
    westLabel.setFont(new Font("Arial", Font.BOLD, 14));
    panel.add(westLabel, BorderLayout.WEST);

    JLabel eastLabel = new JLabel(String.valueOf(card.getAttackValue(Direction.EAST)), SwingConstants.RIGHT);
    eastLabel.setFont(new Font("Arial", Font.BOLD, 14));
    panel.add(eastLabel, BorderLayout.EAST);

    JLabel southLabel = new JLabel(String.valueOf(card.getAttackValue(Direction.SOUTH)), SwingConstants.CENTER);
    southLabel.setFont(new Font("Arial", Font.BOLD, 14));
    panel.add(southLabel, BorderLayout.SOUTH);

    return panel;
  }
}
import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.List;

public class ThreeTriosView extends JFrame implements ThreeTriosFrame {
  private final ReadOnlyThreeTriosModel model;
  private JPanel selectedCardButton = null;

  public ThreeTriosView(ReadOnlyThreeTriosModel model) {
    super("Three Trios Game");
    this.model = model;
    this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    this.setPreferredSize(new Dimension(1000, 800));

    // Main layout
    JPanel mainPanel = new JPanel(new BorderLayout());

    // Grid panel setup
    GridPanel gridPanel = new GridPanel();
    mainPanel.add(gridPanel, BorderLayout.CENTER);

    // Player hand panels setup
    HandPanel redHandPanel = new HandPanel(
            "Red", model.getRedPlayerHand());
    HandPanel blueHandPanel = new HandPanel(
            "Blue", model.getBluePlayerHand());

    mainPanel.add(redHandPanel, BorderLayout.WEST);
    mainPanel.add(blueHandPanel, BorderLayout.EAST);

    // Current player label at the top
    JLabel currentPlayerLabel = new JLabel("Current Player: RED", SwingConstants.CENTER);
    mainPanel.add(currentPlayerLabel, BorderLayout.NORTH);

    this.add(mainPanel);
    this.pack();
    this.setVisible(true);

    // Ensure all panels are updated
    mainPanel.revalidate();
    mainPanel.repaint();
  }

  private class GridPanel extends JPanel implements ThreeTriosGridPanel {
    public GridPanel() {
      int[] dimensions = model.getGridDimensions();
      this.setLayout(new GridLayout(dimensions[0], dimensions[1]));
      populateGrid();
    }

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
  }

  private class HandPanel extends JPanel implements ThreeTriosHandPanel {
    private final String playerColor;

    public HandPanel(String playerColor, List<Card> hand) {
      this.playerColor = playerColor;
      this.setLayout(new GridLayout(hand.size(), 1)); // Adjust layout to match the number of cards
      this.setPreferredSize(new Dimension(100, 800));

      if (playerColor.equals("Red")) {
        this.setBackground(Color.RED); // or Color.RED if you prefer a stronger red shade
      } else if (playerColor.equals("Blue")) {
        this.setBackground(Color.BLUE); // or Color.BLUE if you prefer a stronger blue shade
      }
      this.setOpaque(true);
      populateHand(hand);
    }

    private void populateHand(List<Card> hand) {
      for (int i = 0; i < hand.size(); i++) {
        Card card = hand.get(i);
        JPanel cardPanel = createCardPanel(card);
        cardPanel.setPreferredSize(new Dimension(80, 80));

        // Set the card color based on the player's color
        if (playerColor.equals("Red")) {
        cardPanel.setBackground(Color.PINK); // Change to Color.RED for a stronger shade
        } else if (playerColor.equals("Blue")) {
          cardPanel.setBackground(Color.CYAN); // Change to Color.BLUE for a stronger shade
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

  private void highlightCard(JPanel cardButton) {
    if (selectedCardButton != null) {
      selectedCardButton.setBorder(BorderFactory.createEmptyBorder());
    }
    selectedCardButton = cardButton;
    selectedCardButton.setBorder(BorderFactory.createLineBorder(Color.BLACK, 6));
  }
}
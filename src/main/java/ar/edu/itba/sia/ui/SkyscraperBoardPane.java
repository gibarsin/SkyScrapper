package ar.edu.itba.sia.ui;

import ar.edu.itba.sia.game.Border;
import ar.edu.itba.sia.game.SkyscraperBoard;
import java.util.Objects;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Rectangle;
import javafx.scene.shape.StrokeType;
import javafx.scene.text.Text;

public class SkyscraperBoardPane extends GridPane {

  private static final int PADDING_SIZE = 1;

  private static final String BACKGROUND_COLOR = "#F3F3F3";
  private static final Paint BACKGROUND_PAINT = Paint.valueOf(BACKGROUND_COLOR);

  private static final int TILE_SIZE = 30;
  private static final Paint TILE_BG_PAINT = Color.WHITE;
  private static final Paint TILE_FG_PAINT = Color.BLACK;
  private static final Paint TILE_CH_PAINT = Color.ORANGERED;

  private final int size;

  private boolean created;

  public SkyscraperBoardPane(final SkyscraperBoard board) {
    Objects.requireNonNull(board);

    this.size = board.getSize();

    this.setStyle("-fx-background-color:" + BACKGROUND_COLOR + ";");
    this.setHgap(PADDING_SIZE);
    this.setVgap(PADDING_SIZE);

    for (int i = 0; i < size + 2; i++) {
      for (int j = 0; j < size + 2; j++) {
        if (i >= 1 && i <= size && j >= 1 && j <= size) {
          this.add(newTilePane(), j, i);
        } else if ((i > 0 && i < size + 1 && (j == 0 || j == size + 1))
            || ((i == size + 1 || i == 0) && (j > 0 && j < size + 1))) {
          this.add(newBorderPane(), j, i);
        }
      }
    }

    for (int i = 0; i < size; i++) {
      changeBorderValue(Border.TOP, i, board.getVisibility(Border.TOP, i));
      changeBorderValue(Border.BOTTOM, i, board.getVisibility(Border.BOTTOM, i));
      changeBorderValue(Border.LEFT, i, board.getVisibility(Border.LEFT, i));
      changeBorderValue(Border.RIGHT, i, board.getVisibility(Border.RIGHT, i));
    }

    this.display(board);
    this.created = true;
  }

  private static void changePaneValue(final Pane pane, final int value) {
    final Text text = (Text) pane.getChildren().get(1);

    if (value == 0) {
      text.setText("");
    } else {
      text.setText(String.valueOf(value));
    }
  }

  private static Pane newPane(final Paint fg, final Paint bg, final Paint st) {
    final Rectangle rectangle = new Rectangle(TILE_SIZE, TILE_SIZE);
    rectangle.setFill(bg);
    if (st != null) {
      rectangle.setStrokeType(StrokeType.INSIDE);
      rectangle.setStroke(st);
    }

    final Text text = new Text();
    text.setFill(fg);

    return new StackPane(rectangle, text);
  }

  private static Pane newBorderPane() {
    return newPane(TILE_FG_PAINT, BACKGROUND_PAINT, null);
  }

  private static Pane newTilePane() {
    return newPane(TILE_FG_PAINT, TILE_BG_PAINT, TILE_FG_PAINT);
  }

  public void display(final SkyscraperBoard board) {
    Objects.requireNonNull(board);
    if (board.getSize() != size) {
      throw new IllegalArgumentException("Invalid board");
    }

    for (int i = 0; i < size; i++) {
      for (int j = 0; j < size; j++) {
        changeTileValue(i, j, board.getValue(i, j));
      }
    }
  }

  private void changeTileValue(final int i, final int j, final int value) {
    final Pane pane = (Pane) this.getChildren().get((i + 1) * (size + 2) + (j + 1) - 2);
    final Rectangle rectangle = (Rectangle) pane.getChildren().get(0);
    final Text text = (Text) pane.getChildren().get(1);

    if (created && !text.getText().equals(value != 0 ? String.valueOf(value) : "")) {
      rectangle.setFill(TILE_CH_PAINT);
    } else {
      rectangle.setFill(TILE_BG_PAINT);
    }

    changePaneValue(pane, value);
  }

  private void changeBorderValue(final Border border, final int index, final int value) {
    Objects.requireNonNull(border);
    if (index < 0 || index >= size) {
      throw new IllegalArgumentException("Index must be between 0 and " + (size - 1));
    }

    int gridPosition;

    switch (border) {
      case TOP:
        gridPosition = index;
        break;

      case BOTTOM:
        gridPosition = (size + 1) * (size + 2) + index - 2;
        break;

      case LEFT:
        gridPosition = (index + 1) * (size + 2) - 2;
        break;

      case RIGHT:
        gridPosition = (index + 1) * (size + 2) + (size + 1) - 2;
        break;

      default:
        throw new IllegalStateException("Missing enum case");
    }

    changePaneValue((Pane) this.getChildren().get(gridPosition), value);
  }
}

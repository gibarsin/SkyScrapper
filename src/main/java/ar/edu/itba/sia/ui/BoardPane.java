package ar.edu.itba.sia.ui;

import ar.edu.itba.sia.game.Border;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Rectangle;
import javafx.scene.shape.StrokeType;
import javafx.scene.text.Text;

public class BoardPane extends GridPane {
  private static final int PADDING_SIZE = 1;

  private static final String BACKGROUND_COLOR = "#F3F3F3";
  private static final Paint BACKGROUND_PAINT = Paint.valueOf(BACKGROUND_COLOR);

  private static final int TILE_SIZE = 30;
  private static final Paint TILE_BG_PAINT = Paint.valueOf("FFFFFF");
  private static final Paint TILE_FG_PAINT = Paint.valueOf("000000");

  private final int size;

  public BoardPane(final int size) {
    this.size = size;

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
  }

  public void changeTileValue(final int i, final int j, final int value) {
    if (i < 0 || i >= size) {
      throw new IllegalArgumentException("Row index must be between 0 and " + (size - 1));
    }

    if (j < 0 || j >= size) {
      throw new IllegalArgumentException("Column index must be between 0 and " + (size - 1));
    }

    changePaneValue((Pane) this.getChildren().get((i + 1) * (size + 2) + (j + 1) - 2), value);
  }

  public void changeBorderValue(final Border border, final int index, final int value) {
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
        throw new IllegalArgumentException("Invalid border");
    }

    changePaneValue((Pane) this.getChildren().get(gridPosition), value);
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
}

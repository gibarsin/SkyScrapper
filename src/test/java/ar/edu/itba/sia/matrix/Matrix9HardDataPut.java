package ar.edu.itba.sia.matrix;

import ar.edu.itba.sia.game.Point;
import java.util.LinkedList;
import java.util.List;

public class Matrix9HardDataPut extends Matrix9HardData {
  private static final String METHOD = "PUT";

  @Override
  protected String getMethod() {
    return METHOD;
  }

  @Override
  protected int[][] getMatrix() {
    return new int[][]{
        {0, 0, 3, 4, 0, 0, 0, 0, 0},
        {0, 4, 0, 8, 0, 0, 5, 0, 0},
        {0, 9, 0, 0, 3, 2, 0, 0, 0},
        {4, 0, 0, 1, 5, 0, 8, 0, 6},
        {0, 0, 0, 0, 0, 0, 0, 0, 0},
        {6, 0, 0, 0, 2, 0, 0, 0, 0},
        {0, 0, 1, 0, 7, 0, 0, 0, 0},
        {1, 0, 0, 0, 0, 8, 0, 2, 4},
        {0, 5, 0, 0, 0, 0, 0, 0, 3}
    };
  }

  @Override
  protected List<Point> getFixedCells() {
    return new LinkedList<>();
  }
}

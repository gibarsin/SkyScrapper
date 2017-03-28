package ar.edu.itba.sia.matrix;

import ar.edu.itba.sia.game.Point;
import java.util.LinkedList;
import java.util.List;

public class Matrix6HardDataPut extends Matrix6HardData {
  private static final String METHOD = "PUT";

  @Override
  protected String getMethod() {
    return METHOD;
  }

  @Override
  protected int[][] getMatrix() {
    return new int[][]{
        {0, 0, 0, 0, 0, 0},
        {0, 0, 0, 0, 0, 0},
        {0, 0, 0, 0, 0, 0},
        {0, 0, 0, 0, 0, 0},
        {0, 0, 0, 0, 0, 0},
        {0, 0, 0, 0, 0, 0}
    };
  }

  @Override
  protected List<Point> getFixedCells() {
    return new LinkedList<>();
  }
}

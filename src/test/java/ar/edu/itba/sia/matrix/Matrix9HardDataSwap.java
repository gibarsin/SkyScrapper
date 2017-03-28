package ar.edu.itba.sia.matrix;

import ar.edu.itba.sia.game.Point;
import java.util.LinkedList;
import java.util.List;

public class Matrix9HardDataSwap extends Matrix9HardData {
  private static final String METHOD = "SWAP";

  @Override
  protected String getMethod() {
    return METHOD;
  }

  @Override
  protected int[][] getMatrix() {
    return new int[][]{
        {1, 2, 3, 4, 5, 6, 7, 8, 9},
        {1, 4, 2, 8, 3, 6, 5, 7, 9},
        {1, 9, 4, 5, 3, 2, 7, 8, 6},
        {4, 2, 3, 1, 5, 7, 8, 9, 6},
        {1, 2, 3, 4, 5, 6, 7, 8, 9},
        {6, 1, 3, 4, 2, 5, 7, 8, 9},
        {2, 3, 1, 4, 7, 5, 6, 8, 9},
        {1, 3, 5, 6, 7, 8, 9, 2, 4},
        {1, 5, 2, 4, 6, 7, 8, 9, 3}
    };
  }

  @Override
  protected List<Point> getFixedCells() {
    final List<Point> fixedCells = new LinkedList<>();
    fixedCells.add(new Point(0, 2));
    fixedCells.add(new Point(0, 3));
    fixedCells.add(new Point(1, 1));
    fixedCells.add(new Point(1, 3));
    fixedCells.add(new Point(1, 6));
    fixedCells.add(new Point(2, 1));
    fixedCells.add(new Point(2, 4));
    fixedCells.add(new Point(2, 5));
    fixedCells.add(new Point(3, 0));
    fixedCells.add(new Point(3, 3));
    fixedCells.add(new Point(3, 4));
    fixedCells.add(new Point(3, 6));
    fixedCells.add(new Point(3, 8));
    fixedCells.add(new Point(5, 0));
    fixedCells.add(new Point(5, 4));
    fixedCells.add(new Point(6, 2));
    fixedCells.add(new Point(6, 4));
    fixedCells.add(new Point(7, 0));
    fixedCells.add(new Point(7, 5));
    fixedCells.add(new Point(7, 7));
    fixedCells.add(new Point(7, 8));
    fixedCells.add(new Point(8, 1));
    fixedCells.add(new Point(8, 8));
    return fixedCells;
  }
}

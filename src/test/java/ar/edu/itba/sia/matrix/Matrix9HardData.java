package ar.edu.itba.sia.matrix;

import ar.edu.itba.sia.IdeMain;
import ar.edu.itba.sia.game.ArrayVisibility;
import ar.edu.itba.sia.game.BoardValidator;
import ar.edu.itba.sia.game.BoardValidatorImpl;
import ar.edu.itba.sia.game.Point;
import ar.edu.itba.sia.game.SkyscraperBoardImpl;
import ar.edu.itba.sia.game.SkyscraperProblem;
import ar.edu.itba.sia.game.SkyscraperState;
import ar.edu.itba.sia.game.Visibility;
import ar.edu.itba.sia.gps.api.GPSProblem;
import ar.edu.itba.sia.game.heuristic.Heuristic;
import java.util.List;

public abstract class Matrix9HardData {

  private static final int SIZE = 9;

  private static final Visibility visibility = new ArrayVisibility.Builder(
      SIZE,
      new int[]{2, 3, 5, 3, 2, 2, 3, 3, 1},
      new int[]{2, 3, 2, 1, 2, 3, 3, 5, 6},
      new int[]{2, 5, 2, 3, 4, 2, 1, 3, 2},
      new int[]{1, 3, 2, 3, 2, 3, 5, 4, 4}
  ).build();

  private static final BoardValidator boardValidator = new BoardValidatorImpl(SIZE);
  private static final List<Heuristic> heuristics = IdeMain.initHeuristics(boardValidator);

  private final GPSProblem problem = new SkyscraperProblem(
      new SkyscraperBoardImpl(getMatrix(), getFixedCells(), visibility),
      IdeMain.getRules(SIZE), heuristics, true, boardValidator
  );

  private static final SkyscraperState solvedState = new SkyscraperState(
      new SkyscraperBoardImpl(new int[][]{
          {8, 2, 3, 4, 1, 7, 6, 5, 9},
          {3, 4, 6, 8, 9, 1, 5, 7, 2},
          {5, 9, 7, 6, 3, 2, 1, 4, 8},
          {4, 7, 2, 1, 5, 9, 8, 3, 6},
          {2, 6, 8, 7, 4, 5, 3, 9, 1},
          {6, 1, 5, 3, 2, 4, 9, 8, 7},
          {9, 8, 1, 2, 7, 3, 4, 6, 5},
          {1, 3, 9, 5, 6, 8, 7, 2, 4},
          {7, 5, 4, 9, 8, 6, 2, 1, 3}
      }, visibility)
  );

  private final Object[] data = new Object[]{
      SIZE + "x" + SIZE + ": " + getMethod(),
      problem,
      solvedState,
      getMethod()
  };

  public Object[] getData() {
    return data;
  }

  protected abstract String getMethod();

  protected abstract int[][] getMatrix();

  protected abstract List<Point> getFixedCells();
}

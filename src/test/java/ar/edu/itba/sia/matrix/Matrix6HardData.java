package ar.edu.itba.sia.matrix;

import ar.edu.itba.sia.ui.IdeMain;
import ar.edu.itba.sia.game.ArrayVisibility;
import ar.edu.itba.sia.game.BoardValidator;
import ar.edu.itba.sia.game.BoardValidatorImpl;
import ar.edu.itba.sia.game.Point;
import ar.edu.itba.sia.game.SkyscraperBoardImpl;
import ar.edu.itba.sia.game.SkyscraperProblem;
import ar.edu.itba.sia.game.SkyscraperState;
import ar.edu.itba.sia.game.Visibility;
import ar.edu.itba.sia.game.heuristic.Heuristic;
import ar.edu.itba.sia.gps.api.GPSProblem;
import java.util.List;

public abstract class Matrix6HardData {

  private static final int SIZE = 6;

  private static final Visibility visibility = new ArrayVisibility.Builder(
      SIZE,
      new int[]{0, 0, 0, 0, 0, 4},
      new int[]{3, 0, 2, 3, 0, 0},
      new int[]{2, 0, 0, 1, 0, 0},
      new int[]{4, 4, 0, 0, 2, 0}
  ).build();

  private static final BoardValidator boardValidator = new BoardValidatorImpl(SIZE);
  private static final List<Heuristic> heuristics = IdeMain.initHeuristics(boardValidator);
  private static final SkyscraperState solvedState = new SkyscraperState(
      new SkyscraperBoardImpl(new int[][]{
          {5, 3, 6, 4, 2, 1},
          {1, 6, 3, 5, 4, 2},
          {2, 1, 4, 6, 3, 5},
          {6, 2, 1, 3, 5, 4},
          {4, 5, 2, 1, 6, 3},
          {3, 4, 5, 2, 1, 6}
      }, visibility)
  );
  private final GPSProblem problem = new SkyscraperProblem(
      new SkyscraperBoardImpl(getMatrix(), getFixedCells(), visibility),
      IdeMain.getRules(SIZE), heuristics, true, boardValidator
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

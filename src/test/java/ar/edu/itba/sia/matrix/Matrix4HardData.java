package ar.edu.itba.sia.matrix;

import ar.edu.itba.sia.Main;
import ar.edu.itba.sia.game.ArrayVisibility;
import ar.edu.itba.sia.game.BoardValidator;
import ar.edu.itba.sia.game.BoardValidatorImpl;
import ar.edu.itba.sia.game.Point;
import ar.edu.itba.sia.game.SkyscraperBoardImpl;
import ar.edu.itba.sia.game.SkyscraperProblem;
import ar.edu.itba.sia.game.SkyscraperState;
import ar.edu.itba.sia.game.Visibility;
import ar.edu.itba.sia.gps.api.GPSProblem;
import ar.edu.itba.sia.gps.api.H;
import java.util.List;

public abstract class Matrix4HardData {
  private static final int SIZE = 4;

  private static final Visibility visibility = new ArrayVisibility.Builder(
      SIZE,
      new int[]{0, 3, 0, 0},
      new int[]{2, 0, 0, 0},
      new int[]{0, 0, 0, 0},
      new int[]{2, 0, 0, 2}
  ).build();

  private static final BoardValidator boardValidator = new BoardValidatorImpl(SIZE);
  private static final List<H> heuristics = Main.initHeuristics(boardValidator);

  private final GPSProblem problem = new SkyscraperProblem(
      new SkyscraperBoardImpl(getMatrix(), getFixedCells(), visibility),
      Main.getRules(SIZE), heuristics, true, boardValidator
  );

  private static final SkyscraperState solvedState = new SkyscraperState(
      new SkyscraperBoardImpl(new int[][]{
          {3, 1, 4, 2},
          {1, 3, 2, 4},
          {4, 2, 3, 1},
          {2, 4, 1, 3}
      }, visibility)
  );
  private final Object[] data = new Object[]{SIZE + "x" + SIZE + ": " + getMethod(), problem, solvedState};

  protected abstract String getMethod();

  protected abstract int[][] getMatrix();

  protected abstract List<Point> getFixedCells();

  public Object[] getData() {
    return data;
  }
}

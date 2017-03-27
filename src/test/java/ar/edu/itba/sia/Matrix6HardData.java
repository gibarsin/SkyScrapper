package ar.edu.itba.sia;

import ar.edu.itba.sia.game.ArrayVisibility;
import ar.edu.itba.sia.game.SkyscraperBoardImpl;
import ar.edu.itba.sia.game.SkyscraperProblem;
import ar.edu.itba.sia.game.SkyscraperState;
import ar.edu.itba.sia.game.Visibility;
import ar.edu.itba.sia.gps.api.GPSProblem;
import ar.edu.itba.sia.gps.strategy.SearchStrategy;

public class Matrix6HardData {

  private static final int SIZE = 6;

  private static final Visibility visibility = new ArrayVisibility.Builder(
      SIZE,
      new int[]{0, 1, 4, 4, 0, 4},
      new int[]{0, 2, 0, 0, 0, 0},
      new int[]{0, 0, 0, 4, 2, 0},
      new int[]{0, 0, 3, 0, 4, 0}
  ).build();

  private static final GPSProblem problem = new SkyscraperProblem(
      new SkyscraperBoardImpl(new int[][]{
          {0, 0, 0, 0, 0, 0},
          {0, 0, 0, 0, 0, 0},
          {0, 0, 0, 0, 0, 0},
          {0, 0, 0, 0, 0, 0},
          {0, 0, 0, 0, 0, 0},
          {0, 0, 0, 0, 0, 0}
      }, visibility),
      Main.getRules(SIZE)
  );

  private static final SkyscraperState solvedState = new SkyscraperState(
      new SkyscraperBoardImpl(new int[][]{
          {4, 6, 2, 1, 5, 3},
          {5, 4, 3, 2, 6, 1},
          {6, 2, 5, 3, 1, 4},
          {1, 3, 4, 6, 2, 5},
          {3, 1, 6, 5, 4, 2},
          {2, 5, 1, 4, 3, 6}
      }, visibility)
  );

  public static Object[] getDataFor(SearchStrategy strategy) {
    return new Object[]{problem, strategy, solvedState};
  }
}

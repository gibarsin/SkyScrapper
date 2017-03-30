package ar.edu.itba.sia.matrix;

import ar.edu.itba.sia.Main;
import ar.edu.itba.sia.game.ArrayVisibility;
import ar.edu.itba.sia.game.BoardValidator;
import ar.edu.itba.sia.game.BoardValidatorImpl;
import ar.edu.itba.sia.game.SkyscraperBoardImpl;
import ar.edu.itba.sia.game.SkyscraperProblem;
import ar.edu.itba.sia.game.SkyscraperState;
import ar.edu.itba.sia.game.Visibility;
import ar.edu.itba.sia.gps.api.GPSProblem;
import ar.edu.itba.sia.gps.api.H;
import java.util.List;

public class Matrix9HardData {

  private static final int SIZE = 9;

  private static final Visibility visibility = new ArrayVisibility.Builder(
      SIZE,
      new int[]{2, 3, 5, 3, 2, 2, 3, 3, 1},
      new int[]{2, 3, 2, 1, 2, 3, 3, 5, 6},
      new int[]{2, 5, 2, 3, 4, 2, 1, 3, 2},
      new int[]{1, 3, 2, 3, 2, 3, 5, 4, 4}
  ).build();

  private static final BoardValidator boardValidator = new BoardValidatorImpl(SIZE);
  private static final List<H> heuristics = Main.initHeuristics(boardValidator);

  private static final GPSProblem problem = new SkyscraperProblem(
      new SkyscraperBoardImpl(new int[][]{
          {0, 0, 3, 4, 0, 0, 0, 0, 0},
          {0, 4, 0, 8, 0, 0, 5, 0, 0},
          {0, 9, 0, 0, 3, 2, 0, 0, 0},
          {4, 0, 0, 1, 5, 0, 8, 0, 6},
          {0, 0, 0, 0, 0, 0, 0, 0, 0},
          {6, 0, 0, 0, 2, 0, 0, 0, 0},
          {0, 0, 1, 0, 7, 0, 0, 0, 0},
          {1, 0, 0, 0, 0, 8, 0, 2, 4},
          {0, 5, 0, 0, 0, 0, 0, 0, 3}
      }, visibility),
      Main.getRules(SIZE), heuristics, boardValidator
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
  private static final Object[] data = new Object[]{SIZE + "x" + SIZE, problem, solvedState};

  public static Object[] getData() {
    return data;
  }
}

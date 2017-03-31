package ar.edu.itba.sia;

import ar.edu.itba.sia.game.ArrayVisibility;
import ar.edu.itba.sia.game.BoardValidator;
import ar.edu.itba.sia.game.BoardValidatorImpl;
import ar.edu.itba.sia.game.SkyscraperBoard;
import ar.edu.itba.sia.game.SkyscraperBoardImpl;
import ar.edu.itba.sia.game.SkyscraperProblem;
import ar.edu.itba.sia.game.Visibility;
import ar.edu.itba.sia.game.heuristic.IdealAmountOfSwapsHeuristic;
import ar.edu.itba.sia.game.rules.SkyscraperPutRule;
import ar.edu.itba.sia.game.rules.SkyscraperSwapRule;
import ar.edu.itba.sia.gps.api.GPSProblem;
import ar.edu.itba.sia.gps.api.GPSRule;
import ar.edu.itba.sia.gps.api.H;
import ar.edu.itba.sia.gps.core.GPSEngine;
import ar.edu.itba.sia.gps.strategy.SearchStrategy;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class Main {

  private static final int SIZE = 4;

  public static void main(String[] args) {
//    final int[][] originalMatrix = new int[][]{
//        {4, 3, 2, 1},
//        {1, 2, 4, 3},
//        {2, 1, 3, 4},
//        {3, 4, 1, 2}
//    };
//    final int[][] matrix = new int[][]{
//        {1, 2, 3, 4},
//        {2, 3, 4, 1},
//        {3, 4, 1, 2},
//        {4, 1, 2, 3}
//    };
    final int[][] matrix = new int[][]{
        {1, 3, 2, 1},
        {4, 2, 4, 3},
        {2, 1, 3, 4},
        {3, 4, 1, 2}
    };
    final Visibility visibility = new ArrayVisibility.Builder(
        matrix.length,
        new int[]{1, 2, 2, 3},
        new int[]{2, 1, 3, 2},
        new int[]{1, 3, 3, 2},
        new int[]{4, 2, 1, 2}
    ).build();

    final SkyscraperBoard board = new SkyscraperBoardImpl(matrix, visibility);
    final List<GPSRule> rules = getRules(matrix.length);
    final BoardValidator boardValidator = new BoardValidatorImpl(board.getSize());
    final List<H> heuristics = initHeuristics(boardValidator);
    final GPSProblem problem =
        new SkyscraperProblem(board, rules, heuristics, true, boardValidator);
    final GPSEngine engine = new GPSEngine(problem, SearchStrategy.GREEDY);
    engine.findSolution();

//    Application.launch(SkyscraperUI.class, args);
  }

  public static List<GPSRule> getRules(int size) {
    final List<GPSRule> rules = new ArrayList<>();

    for (int i = 0; i < size; i++) {
      for (int j = 0; j < size; j++) {
        for (int n = 1; n <= size; n++) {
          rules.add(new SkyscraperPutRule(i, j, n));
        }
      }
    }

    for (int row1 = 0; row1 < size; row1++) {
      for (int col1 = 0; col1 < size; col1++) {
        for (int row2 = row1; row2 < size; row2++) {
          for (int col2 = col1 ; col2 < size; col2++) {
            if (row1 != row2 || col1 != col2) {
              rules.add(new SkyscraperSwapRule(row1, col1, row2, col2));
            }
          }
        }
      }
    }

    return rules;
  }

  public static List<H> initHeuristics(final BoardValidator boardValidator) {
    final List<H> heuristics = new LinkedList<>();
    // swap heuristic - admissible
    heuristics.add(new IdealAmountOfSwapsHeuristic(boardValidator));
    return heuristics;
  }
}

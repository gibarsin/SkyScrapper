package ar.edu.itba.sia;

import ar.edu.itba.sia.game.ArrayVisibility;
import ar.edu.itba.sia.game.SkyscraperBoard;
import ar.edu.itba.sia.game.SkyscraperBoardImpl;
import ar.edu.itba.sia.game.SkyscraperProblem;
import ar.edu.itba.sia.game.Visibility;
import ar.edu.itba.sia.game.rules.SkyscraperPutRule;
import ar.edu.itba.sia.gps.api.GPSProblem;
import ar.edu.itba.sia.gps.api.GPSRule;
import ar.edu.itba.sia.gps.core.GPSEngine;
import ar.edu.itba.sia.gps.core.SearchStrategy;
import java.util.ArrayList;
import java.util.List;

public class Main {
  private static final int SIZE = 4;

  public static void main(String[] args) {
    final int[][] originalMatrix = new int[][]{
        {4, 3, 2, 1},
        {1, 2, 4, 3},
        {2, 1, 3, 4},
        {3, 4, 1, 2}
    };
    final int[][] matrix = new int[][]{
        {4, 3, 2, 1},
        {1, 0, 0, 3},
        {0, 0, 0, 0},
        {3, 4, 1, 2}
    };
    final Visibility visibility = new ArrayVisibility.Builder(
        SIZE,
        new int[]{1, 2, 2, 3},
        new int[]{2, 1, 3, 2},
        new int[]{1, 3, 3, 2},
        new int[]{4, 2, 1, 2}
    ).build();
    final SkyscraperBoard board = new SkyscraperBoardImpl(matrix, visibility);
    GPSRule putRule = new SkyscraperPutRule();
    List<GPSRule> rules = new ArrayList<>();
    rules.add(putRule);
    GPSProblem problem = new SkyscraperProblem(board, rules);
    GPSEngine engine = new GPSEngine();
    engine.engine(problem, SearchStrategy.BFS);

//    Application.launch(SkyscraperUI.class, args);
  }
}

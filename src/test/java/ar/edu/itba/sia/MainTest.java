package ar.edu.itba.sia;

import ar.edu.itba.sia.game.ArrayVisibility;
import ar.edu.itba.sia.game.SkyscraperBoard;
import ar.edu.itba.sia.game.SkyscraperBoardImpl;
import ar.edu.itba.sia.game.SkyscraperProblem;
import ar.edu.itba.sia.game.Visibility;
import ar.edu.itba.sia.gps.api.GPSProblem;
import ar.edu.itba.sia.gps.api.GPSRule;
import ar.edu.itba.sia.gps.core.GPSEngine;
import ar.edu.itba.sia.gps.core.SearchStrategy;
import java.util.List;
import org.junit.Test;

public class MainTest {

  @Test
  public void test001() throws Exception {
    final int[][] matrix = new int[][]{
        {4, 3, 2, 1},
        {3, 2, 1, 4},
        {2, 1, 4, 3},
        {1, 4, 3, 2}
    };
    final Visibility visibility = new ArrayVisibility.Builder(
        matrix.length,
        new int[]{1, 2, 2, 3},
        new int[]{2, 1, 3, 2},
        new int[]{1, 3, 3, 2},
        new int[]{4, 2, 1, 2}
    ).build();
    final SkyscraperBoard board = new SkyscraperBoardImpl(matrix, visibility);
    final List<GPSRule> rules = Main.getRules(matrix.length);
    final GPSProblem problem = new SkyscraperProblem(board, rules);

    for (SearchStrategy strategy : SearchStrategy.values()) {
      final GPSEngine engine = new GPSEngine();
      engine.engine(problem, strategy);
    }
  }

  @Test
  public void test002() throws Exception {
    final int[][] matrix = new int[][]{
        {0, 0, 0, 2},
        {1, 0, 0, 0},
        {0, 0, 0, 0},
        {0, 0, 0, 0}
    };
    final Visibility visibility = new ArrayVisibility.Builder(
        matrix.length,
        new int[]{0, 3, 0, 0},
        new int[]{2, 0, 0, 0},
        new int[]{0, 0, 0, 0},
        new int[]{2, 0, 0, 2}
    ).build();
    final SkyscraperBoard board = new SkyscraperBoardImpl(matrix, visibility);
    final List<GPSRule> rules = Main.getRules(matrix.length);
    final GPSProblem problem = new SkyscraperProblem(board, rules);

    for (SearchStrategy strategy : SearchStrategy.values()) {
      final GPSEngine engine = new GPSEngine();
      engine.engine(problem, strategy);
    }
  }

  @Test
  public void test003() throws Exception {
    final int[][] matrix = new int[][]{
        {0, 0, 2, 0, 0},
        {0, 0, 0, 0, 0},
        {0, 0, 0, 0, 0},
        {0, 0, 0, 0, 0},
        {0, 0, 0, 0, 0}
    };
    final Visibility visibility = new ArrayVisibility.Builder(
        matrix.length,
        new int[]{0, 0, 0, 0, 0},
        new int[]{3, 0, 0, 0, 0},
        new int[]{2, 0, 3, 0, 4},
        new int[]{1, 3, 3, 2, 0}
    ).build();
    final SkyscraperBoard board = new SkyscraperBoardImpl(matrix, visibility);
    final List<GPSRule> rules = Main.getRules(matrix.length);
    final GPSProblem problem = new SkyscraperProblem(board, rules);

    for (SearchStrategy strategy : SearchStrategy.values()) {
      final GPSEngine engine = new GPSEngine();
      engine.engine(problem, strategy);
    }
  }

//  @Test
//  public void test004() throws Exception {
//    final int[][] matrix = new int[][]{
//        {0, 0, 0, 0, 0, 0},
//        {0, 0, 0, 0, 0, 0},
//        {0, 0, 0, 0, 0, 0},
//        {0, 0, 0, 0, 0, 0},
//        {0, 0, 0, 0, 0, 0},
//        {0, 0, 0, 0, 0, 0}
//    };
//    final Visibility visibility = new ArrayVisibility.Builder(
//        matrix.length,
//        new int[]{0, 0, 0, 0, 0, 0},
//        new int[]{3, 0, 0, 0, 0, 0},
//        new int[]{2, 0, 3, 0, 4, 0},
//        new int[]{1, 3, 3, 2, 0, 0}
//    ).build();
//    final SkyscraperBoard board = new SkyscraperBoardImpl(matrix, visibility);
//    final List<GPSRule> rules = Main.getRules(matrix.length);
//    final GPSProblem problem = new SkyscraperProblem(board, rules);
//
//    for (SearchStrategy strategy : SearchStrategy.values()) {
//      final GPSEngine engine = new GPSEngine();
//      engine.engine(problem, strategy);
//    }
//  }
}
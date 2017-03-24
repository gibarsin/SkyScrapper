package ar.edu.itba.sia.game;

import org.junit.Assert;
import org.junit.Test;

public class SkyscraperProblemTest {

  private static final int SIZE = 4;

  private final static Visibility visibility = new ArrayVisibility.Builder(
      SIZE,
      new int[]{1, 2, 2, 3},
      new int[]{2, 1, 3, 2},
      new int[]{1, 3, 3, 2},
      new int[]{4, 2, 1, 2}
  ).build();

  @Test
  public void test001IsGoalForEmptyMatrixShouldBeFalse() {
    final int[][] emptyMatrix = new int[][]{
        {0, 0, 0, 0},
        {0, 0, 0, 0},
        {0, 0, 0, 0},
        {0, 0, 0, 0}
    };
    final SkyscraperBoard board = new SkyscraperBoardImpl(emptyMatrix, visibility);
    final SkyscraperProblem problem = new SkyscraperProblem(board);

    Assert.assertFalse(problem.isGoal(problem.getInitState()));
  }

  @Test
  public void test002IsGoalForAnswerMatrixShouldBeTrue() {
    final int[][] answerMatrix = new int[][]{
        {4, 3, 2, 1},
        {1, 2, 4, 3},
        {2, 1, 3, 4},
        {3, 4, 1, 2}
    };
    final SkyscraperBoard board = new SkyscraperBoardImpl(answerMatrix, visibility);
    final SkyscraperProblem problem = new SkyscraperProblem(board);

    Assert.assertTrue(problem.isGoal(problem.getInitState()));
  }

  @Test
  public void test003IsGoalForIncorrectMatrixShouldBeFalse() {
    final int[][] incorrectAnswerMatrix = new int[][]{
        {4, 3, 2, 2},
        {1, 2, 4, 3},
        {2, 1, 3, 4},
        {3, 4, 1, 2}
    };
    final SkyscraperBoard board = new SkyscraperBoardImpl(incorrectAnswerMatrix, visibility);
    final SkyscraperProblem problem = new SkyscraperProblem(board);

    Assert.assertFalse(problem.isGoal(problem.getInitState()));
  }
}
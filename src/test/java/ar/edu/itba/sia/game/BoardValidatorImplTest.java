package ar.edu.itba.sia.game;

import org.junit.Assert;
import org.junit.Test;

public class BoardValidatorImplTest {
  private static final int SIZE = 4;

  @Test
  public void test001CorrectMatrix() {
    final int[][] matrix = new int[][]{
        {4, 3, 2, 1},
        {1, 2, 4, 3},
        {2, 1, 3, 4},
        {3, 4, 1, 2}
    };
    final Visibility visibility = new ArrayVisibility.Builder(SIZE,
        new int[]{1, 2, 2, 3},
        new int[]{2, 1, 3, 2},
        new int[]{1, 3, 3, 2},
        new int[]{4, 2, 1, 2}
    ).build();
    final SkyscraperBoard board = new SkyscraperBoardImpl(matrix, visibility);
    final BoardValidator boardValidator = new BoardValidatorImpl(board.getSize());

    Assert.assertEquals(0, boardValidator.countConflicts(board));
    Assert.assertEquals(false, boardValidator.areConflictsOn(board));
  }

  @Test
  public void test002RepetitionConflictLeftBorder() {
    final int[][] matrix = new int[][]{
        {3, 3, 3, 1}, // one repetition here, one per column 0 and column 2
        {1, 2, 4, 3},
        {2, 1, 3, 4},
        {3, 4, 1, 2}
    };
    final Visibility visibility = new ArrayVisibility.Builder(SIZE,
        new int[]{1, 2, 2, 3},
        new int[]{1, 1, 3, 2},
        new int[]{1, 3, 3, 2},
        new int[]{2, 2, 1, 2}
    ).build();
    final SkyscraperBoard board = new SkyscraperBoardImpl(matrix, visibility);
    final BoardValidator boardValidator = new BoardValidatorImpl(board.getSize());

    Assert.assertEquals(3, boardValidator.countConflicts(board));
    Assert.assertEquals(true, boardValidator.areConflictsOn(board));
  }

  @Test
  public void test003LeftVisibilityConflictBorder() {
    final int[][] matrix = new int[][]{
        {4, 3, 2, 1},
        {1, 2, 4, 3},
        {2, 1, 3, 4},
        {3, 4, 1, 2}
    };
    final Visibility visibility = new ArrayVisibility.Builder(SIZE,
        new int[]{1, 2, 2, 3},
        new int[]{2, 1, 3, 2},
        new int[]{2, 3, 3, 2},
        new int[]{4, 2, 1, 2}
    ).build();
    final SkyscraperBoard board = new SkyscraperBoardImpl(matrix, visibility);
    final BoardValidator boardValidator = new BoardValidatorImpl(board.getSize());

    Assert.assertEquals(1, boardValidator.countConflicts(board));
    Assert.assertEquals(true, boardValidator.areConflictsOn(board));
  }

  @Test
  public void test004RightVisibilityConflictBorder() {
    final int[][] matrix = new int[][]{
        {4, 3, 2, 1},
        {1, 2, 4, 3},
        {2, 1, 3, 4},
        {3, 4, 1, 2}
    };
    final Visibility visibility = new ArrayVisibility.Builder(SIZE,
        new int[]{1, 2, 2, 3},
        new int[]{2, 1, 3, 2},
        new int[]{1, 3, 3, 2},
        new int[]{3, 2, 1, 2}
    ).build();
    final SkyscraperBoard board = new SkyscraperBoardImpl(matrix, visibility);
    final BoardValidator boardValidator = new BoardValidatorImpl(board.getSize());

    Assert.assertEquals(1, boardValidator.countConflicts(board));
    Assert.assertEquals(true, boardValidator.areConflictsOn(board));
  }

  @Test
  public void test005TopVisibilityConflictBorder() {
    final int[][] matrix = new int[][]{
        {4, 3, 2, 1},
        {1, 2, 4, 3},
        {2, 1, 3, 4},
        {3, 4, 1, 2}
    };
    final Visibility visibility = new ArrayVisibility.Builder(SIZE,
        new int[]{2, 2, 2, 3},
        new int[]{2, 1, 3, 2},
        new int[]{1, 3, 3, 2},
        new int[]{4, 2, 1, 2}
    ).build();
    final SkyscraperBoard board = new SkyscraperBoardImpl(matrix, visibility);
    final BoardValidator boardValidator = new BoardValidatorImpl(board.getSize());

    Assert.assertEquals(1, boardValidator.countConflicts(board));
    Assert.assertEquals(true, boardValidator.areConflictsOn(board));
  }

  @Test
  public void test006BottomVisibilityConflictBorder() {
    final int[][] matrix = new int[][]{
        {4, 3, 2, 1},
        {1, 2, 4, 3},
        {2, 1, 3, 4},
        {3, 4, 1, 2}
    };
    final Visibility visibility = new ArrayVisibility.Builder(SIZE,
        new int[]{1, 2, 2, 3},
        new int[]{2, 1, 3, 1},
        new int[]{1, 3, 3, 2},
        new int[]{4, 2, 1, 2}
    ).build();
    final SkyscraperBoard board = new SkyscraperBoardImpl(matrix, visibility);
    final BoardValidator boardValidator = new BoardValidatorImpl(board.getSize());

    Assert.assertEquals(1, boardValidator.countConflicts(board));
    Assert.assertEquals(true, boardValidator.areConflictsOn(board));
  }

  @Test
  public void test007MultipleConflicts() {
    final int[][] matrix = new int[][]{
        {2, 1, 4, 4},
        {3, 2, 4, 2},
        {1, 3, 2, 2},
        {3, 4, 1, 1}
    };
    final Visibility visibility = new ArrayVisibility.Builder(SIZE,
        new int[]{1, 2, 2, 3},
        new int[]{2, 1, 3, 2},
        new int[]{1, 3, 3, 2},
        new int[]{4, 2, 1, 2}
    ).build();
    final SkyscraperBoard board = new SkyscraperBoardImpl(matrix, visibility);
    final BoardValidator boardValidator = new BoardValidatorImpl(board.getSize());

    /*
     * No conflicts on:
     * - Repetitions
     *   - Second column
     * - Visibility
     *   - Left: fourth row
     *   - Right: second and fourth row
     *   - Top: none
     *   - Bottom: second and third
     *
     * max_conflicts = (1+1+2+2) * 4 = 24
     * total_conflicts = 24 - 6 = 18
     */
    Assert.assertEquals(18, boardValidator.countConflicts(board));
    Assert.assertEquals(true, boardValidator.areConflictsOn(board));
  }
}

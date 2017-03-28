package ar.edu.itba.sia.game;

import static ar.edu.itba.sia.game.BoardValidatorImpl.Flow.COL;
import static ar.edu.itba.sia.game.BoardValidatorImpl.Flow.ROW;

import java.util.HashMap;
import java.util.Map;
import java.util.stream.IntStream;

public class BoardValidatorImpl implements BoardValidator{
  public enum Flow {
    COL, ROW
  }

  private static class FlowWithBorders {
    private final Flow flow;
    private final Map<Border, int[]> borders;

    private FlowWithBorders(final Flow flow, final Map<Border, int[]> borders) {
      this.flow = flow;
      this.borders = borders;
    }

    /* package-private */ Map<Border, int[]> getBorders() {
      return borders;
    }

    /* package-private */ Flow getFlow() {
      return flow;
    }
  }

  private static final int NUMBER_OF_VISIBILITY_CHECKS_PER_ROW = 2;
  private static final int NUMBER_OF_VISIBILITY_CHECKS_PER_COL = 2;

  private final Map<Flow, FlowWithBorders> flowsWithBorders;

  public BoardValidatorImpl(final int size) {
    this.flowsWithBorders = new HashMap<>();
    this.flowsWithBorders.put(ROW, initFlow(ROW, size));
    this.flowsWithBorders.put(COL, initFlow(COL, size));
  }

  // Taken from: http://stackoverflow.com/questions/24010109/java-8-stream-reverse-order
  private static IntStream reverseRange(@SuppressWarnings("SameParameterValue") final int from,
      final int to) {
    return IntStream.range(from, to).map(i -> to - i + from - 1);
  }

  private FlowWithBorders initFlow(final Flow flow, final int n) {
    final int[] range = IntStream.range(0, n).toArray();
    final int[] reverseRange = reverseRange(0, n).toArray();

    final Map<Border, int[]> borders = new HashMap<>();
    borders.put(flow == COL ? Border.TOP : Border.LEFT, range);
    borders.put(flow == COL ? Border.BOTTOM : Border.RIGHT, reverseRange);
    return new FlowWithBorders(flow, borders);
  }

  @Override
  public boolean areConflictsOn(final SkyscraperBoard board) {
    for (final FlowWithBorders flow : flowsWithBorders.values()) {
      if (areRepetitionOrVisibilityConflictsOn(flow, board)) {
        return true;
      }
    }
    return false;
  }

  private boolean areRepetitionOrVisibilityConflictsOn(final FlowWithBorders flow, final SkyscraperBoard board) {
    for (int k = 0; k < board.getSize(); k++) {
      if (isRepetitionConflictOn(flow, k, board) || areVisibilityConflictsOn(flow, k, board)) {
        return true;
      }
    }
    return false;
  }

  private boolean areVisibilityConflictsOn(final FlowWithBorders flow, final int k,
      final SkyscraperBoard board) {
    final Map<Border, int[]> borders = flow.getBorders();
    for (final Border border : borders.keySet()) {
      if (isVisibilityConflictOn(k, board, border, borders.get(border))) {
        return true;
      }
    }
    return false;
  }

  @Override
  public int countConflicts(final SkyscraperBoard board) {
    int conflictsCounter = 0;
    for (final FlowWithBorders flow : flowsWithBorders.values()) {
      conflictsCounter += countRepetitionConflictsOn(flow, board);
      conflictsCounter += countVisibilityConflictsOn(flow, board);
    }
    checkValidConflictsCounter(conflictsCounter, board);
    return conflictsCounter;
  }

  private int countRepetitionConflictsOn(final FlowWithBorders flow, final SkyscraperBoard board) {
    int conflictsCounter = 0;
    for (int k = 0; k < board.getSize(); k++) {
      conflictsCounter += isRepetitionConflictOn(flow, k, board) ? 1 : 0;
    }
    return conflictsCounter;
  }

  private int countVisibilityConflictsOn(final FlowWithBorders flow, final SkyscraperBoard board) {
    final int n = board.getSize();
    final Map<Border, int[]> borders = flow.getBorders();
    // per row, check from both sides
    int conflictsCounter = 0;
    for (int k = 0; k < n; k++) {
      for (final Border border : borders.keySet()) {
        conflictsCounter += isVisibilityConflictOn(k, board, border, borders.get(border)) ? 1 : 0;
      }
    }
    return conflictsCounter;
  }

  private boolean isRepetitionConflictOn(final FlowWithBorders flow, final int k,
      final SkyscraperBoard board) {
    final int n = board.getSize();
    final int[] values = new int[n+1]; // numbers from 1 to n, inclusive
    for (int m = 0 ; m < n ; m++) {
      final int cValue = (flow.getFlow() == COL) ? board.getValue(m, k) : board.getValue(k, m);
      if (values[cValue] > 0) {
        return true;
      }
      values[cValue] = cValue;
    }
    return false;
  }

  /**
   *
   * @param k The 'line' to be analyzed (could be a row or a col depending on border)
   * @param board The board containing all skyscrapers and visibilities
   * @param border The border which visibility is to be analyzed
   * @param range The range of column indexes that are going to be used for testing visibility
   * @return true if visibility from the given border on the specified row, using
   *         the given range on the specified board, exactly matches the correct visibility
   *         of the given board; false otherwise
   */
  private boolean isVisibilityConflictOn(final int k,
      final SkyscraperBoard board, final Border border, final int[] range) {

    if (!board.hasVisibility(border, k)) {
      return false;
    }

    int maxHeight = 0;
    int seen = 0;
    for (final int m : range) {
      final int currentValue = isColumnBorder(border) ? board.getValue(m, k) : board.getValue(k, m);
      if (currentValue > maxHeight) {
        seen ++;
        maxHeight = currentValue;
      }
    }

    return seen != board.getVisibility(border, k);
  }

  private boolean isColumnBorder(final Border border) {
    return flowsWithBorders.get(COL).getBorders().containsKey(border);
  }

  private void checkValidConflictsCounter(final int conflictsCounter,
      final SkyscraperBoard board) {
    if (conflictsCounter > maxPossibleConflictsOn(board)){
      throw new IllegalStateException();
    }
  }

  private int maxPossibleConflictsOn(final SkyscraperBoard board) {
    final int n = board.getSize();
    // one per repeats in row; one per repeats in col;
    // one per row visibility; one per col visibility
    return (1 + 1 + NUMBER_OF_VISIBILITY_CHECKS_PER_ROW + NUMBER_OF_VISIBILITY_CHECKS_PER_COL) * n;
  }
}

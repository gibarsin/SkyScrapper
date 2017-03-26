package ar.edu.itba.sia.game;

import ar.edu.itba.sia.gps.api.GPSProblem;
import ar.edu.itba.sia.gps.api.GPSRule;
import ar.edu.itba.sia.gps.api.GPSState;
import java.awt.Point;
import java.util.List;
import java.util.Objects;
import java.util.stream.IntStream;

public class SkyscraperProblem implements GPSProblem {

  private final SkyscraperState initialState;
  private final List<GPSRule> rules;

  public SkyscraperProblem(final SkyscraperBoard initialBoard, final List<GPSRule> rules) {
    this.initialState = new SkyscraperState(Objects.requireNonNull(initialBoard), getFirstEmptyPosition(initialBoard));
    this.rules = rules;
  }

  private Point getFirstEmptyPosition(SkyscraperBoard board) {
    for (int i = 0; i < board.getSize(); i++) {
      for (int j = 0; j < board.getSize(); j++) {
        if(board.isEmpty(i, j)){
          return new Point(i, j);
        }
      }
    }
    return null;
  }

  @Override
  public GPSState getInitState() {
    return initialState;
  }

  @Override
  public boolean isGoal(final GPSState state) {
    final SkyscraperState ssState = (SkyscraperState) state;
    final SkyscraperBoard board = ssState.getBoard();

    return board.isFull() && !areCrossConflicts(board) && !areVisibilityConflicts(board);
  }

  private boolean areVisibilityConflicts(final SkyscraperBoard board) {
    return IntStream.range(0, board.getSize()).parallel()
        .anyMatch(v -> !hasCorrectRowVisibility(board, v) || !hasCorrectColumnVisibility(board, v));
  }

  private boolean hasCorrectRowVisibility(final SkyscraperBoard board, final int row) {
    int maxHeight = 0;
    int seen = 0;

    if (board.hasVisibility(Border.LEFT, row)) {
      final int visibility = board.getVisibility(Border.LEFT, row);
      for (int j = 0; j < board.getSize(); j++) {
        final int currValue = board.getValue(row, j);

        if (currValue > maxHeight) {
          seen++;
          maxHeight = currValue;

          if (seen > visibility) {
            return false;
          }
        }
      }
    }

    maxHeight = 0;
    seen = 0;

    if (board.hasVisibility(Border.RIGHT, row)) {
      final int visibility = board.getVisibility(Border.RIGHT, row);
      for (int j = board.getSize() - 1; j >= 0; j--) {
        final int currValue = board.getValue(row, j);

        if (currValue > maxHeight) {
          seen++;
          maxHeight = currValue;

          if (seen > visibility) {
            return false;
          }
        }
      }
    }

    return true;
  }

  private boolean hasCorrectColumnVisibility(final SkyscraperBoard board, final int column) {
    int maxHeight = 0;
    int seen = 0;

    if (board.hasVisibility(Border.TOP, column)) {
      final int visibility = board.getVisibility(Border.TOP, column);
      for (int i = 0; i < board.getSize(); i++) {
        final int currValue = board.getValue(i, column);

        if (currValue > maxHeight) {
          seen++;
          maxHeight = currValue;

          if (seen > visibility) {
            return false;
          }
        }
      }
    }

    maxHeight = 0;
    seen = 0;

    if (board.hasVisibility(Border.BOTTOM, column)) {
      final int visibility = board.getVisibility(Border.BOTTOM, column);
      for (int i = board.getSize() - 1; i >= 0; i--) {
        final int currValue = board.getValue(i, column);

        if (currValue > maxHeight) {
          seen++;
          maxHeight = currValue;

          if (seen > visibility) {
            return false;
          }
        }
      }
    }

    return true;
  }

  private boolean areCrossConflicts(final SkyscraperBoard board) {
    for (int i = 0; i < board.getSize(); i++) {
      for (int j = 0; j < board.getSize(); j++) {
        if (!isUniqueInRow(board, i, j) || !isUniqueInColumn(board, i, j)) {
          return true;
        }
      }
    }

    return false;
  }

  @Override
  public List<GPSRule> getRules() {
    return rules;
  }

  @Override
  public Integer getHValue(final GPSState state) {
    return 1; // TODO: Implement heuristics
  }

  /**
   * This method assumes that none of the values in the board are empty
   *
   * @return true if the value is unique in its column; false else
   */
  private boolean isUniqueInColumn(final SkyscraperBoard board, final int row, final int col) {
    final int value = board.getValue(row, col);

    for (int i = 0; i < row; i++) {
      if (value == board.getValue(i, col)) {
        return false;
      }
    }

    for (int i = row + 1; i < board.getSize(); i++) {
      if (value == board.getValue(i, col)) {
        return false;
      }
    }

    return true;
  }

  /**
   * This method assumes that none of the values in the board are empty
   *
   * @return true if the value is unique in its row; else false
   */
  private boolean isUniqueInRow(final SkyscraperBoard board, final int row, final int col) {
    final int value = board.getValue(row, col);

    for (int j = 0; j < col; j++) {
      if (value == board.getValue(row, j)) {
        return false;
      }
    }

    for (int j = col + 1; j < board.getSize(); j++) {
      if (value == board.getValue(row, j)) {
        return false;
      }
    }

    return true;
  }
}

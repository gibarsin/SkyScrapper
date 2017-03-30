/*
  TODO:
  https://github.com/gibarsin/SkyScrapper/pull/6#discussion_r108921564
  https://github.com/gibarsin/SkyScrapper/pull/6#discussion_r108922776
  https://github.com/gibarsin/SkyScrapper/pull/6#discussion_r108925347
  https://github.com/gibarsin/SkyScrapper/pull/6#discussion_r108926148
  https://github.com/gibarsin/SkyScrapper/pull/6#discussion_r108928975
  https://github.com/gibarsin/SkyScrapper/pull/6#discussion_r108929112
  https://github.com/gibarsin/SkyScrapper/pull/6#discussion_r108930498
  https://github.com/gibarsin/SkyScrapper/pull/6#discussion_r108930640
  https://github.com/gibarsin/SkyScrapper/pull/6#discussion_r108931189
  https://github.com/gibarsin/SkyScrapper/pull/6#discussion_r108931296
  https://github.com/gibarsin/SkyScrapper/pull/6#discussion_r108932343
  https://github.com/gibarsin/SkyScrapper/pull/6#discussion_r108932453
  https://github.com/gibarsin/SkyScrapper/pull/6#discussion_r108932653
 */

package ar.edu.itba.sia.game.rules;

import ar.edu.itba.sia.game.Border;
import ar.edu.itba.sia.game.SkyscraperBoard;
import ar.edu.itba.sia.game.SkyscraperState;
import ar.edu.itba.sia.gps.api.GPSRule;
import ar.edu.itba.sia.gps.api.GPSState;
import java.awt.Point;
import java.util.Optional;

public class SkyscraperPutRule implements GPSRule {

  private static final int COST = 1;
  private static final String RULE_NAME = "PUT";
  private final String name;

  private final int row;
  private final int col;
  private final int number;

  public SkyscraperPutRule(final int row, final int col, final int number) {
    this.row = row;
    this.col = col;
    this.number = number;
    this.name = RULE_NAME + " (" + row + ", " + col + ") -> " + number;
  }

  @Override
  public Integer getCost() {
    return COST;
  }

  @Override
  public String getName() {
    return name;
  }

  /**
   * @param state The previous state of the problem.
   * @return An Optional, containing the new state if it could be computed
   *         or an empty optional otherwise.
   *
   * @implNote The new state is computed if the last empty position on the board
   *           equals the position where this rule will be applied.
   */
  @Override
  public Optional<GPSState> evalRule(final GPSState state) {
    final SkyscraperState skyscraperState = (SkyscraperState) state;
    final SkyscraperBoard board = skyscraperState.getBoard();

    final Point firstEmptyPosition = skyscraperState.getFirstEmptyPosition();
    if(firstEmptyPosition != null && firstEmptyPosition.x == row && firstEmptyPosition.y == col) {
      if (canSetValue(skyscraperState, row, col, number)) {
        return Optional.of(new SkyscraperState(board.setValue(row, col, number)));
      }
    }

    return Optional.empty();
  }

  /**
   * @param state The previous state of the problem
   * @param row The row to insert the new value
   * @param col The col to insert the new value
   * @return true if value met the restrictions to be inserted; false otherwise
   */
  private boolean canSetValue(final SkyscraperState state, final int row, final int col,
                                      final int number) {
    final SkyscraperBoard board = state.getBoard();

    for (int i = 0; i < board.getSize(); i++) {
      if (board.getValue(i, col) == number || board.getValue(row, i) == number) {
        return false;
      }
    }

    return checkVisibility(state, row, col, number);
  }

  private boolean checkVisibility(final SkyscraperState state, final int row, final int col,
      final int number) {
    final SkyscraperBoard board = state.getBoard();

    if (board.hasVisibility(Border.LEFT, row)) {
      final int visibilityLeft = board.getVisibility(Border.LEFT, row);
      final int countLeft = countLeftRowVisibility(board, row, col, number);

      if (state.getRowLastEmpty()[row] == col && countLeft != visibilityLeft) {
        return false;
      } else if (countLeft > visibilityLeft) {
        return false;
      }
    }

    if (board.hasVisibility(Border.TOP, col)) {
      final int visibilityTop = board.getVisibility(Border.TOP, col);
      final int countTop = countTopColumnVisibility(board, row, col, number);

      if (state.getColLastEmpty()[col] == row && countTop != visibilityTop) {
        return false;
      } else if (countTop > visibilityTop) {
        return false;
      }
    }

    if (board.hasVisibility(Border.RIGHT, row) && state.getRowLastEmpty()[row] == col) {
      final int visibilityRight = board.getVisibility(Border.RIGHT, row);
      final int countRight = countRightRowVisibility(board, row, col, number);

      if (countRight != visibilityRight) {
        return false;
      }
    }

    if (board.hasVisibility(Border.BOTTOM, col) && state.getColLastEmpty()[col] == row) {
      final int visibilityBottom = board.getVisibility(Border.BOTTOM, col);
      final int countBottom = countBottomColumnVisibility(board, row, col, number);

      if (countBottom != visibilityBottom) {
        return false;
      }
    }

    return true;
  }

  private int countLeftRowVisibility(final SkyscraperBoard board, final int row, final int col,
                                     final int number) {
    int max = 0, count = 0;

    for (int i = 0; i < col; i++) {
      if (board.getValue(row, i) > max) {
        max = board.getValue(row, i);
        count++;
      }
    }

    if (number > max) {
      max = number;
      count++;
    }

    for (int i = col + 1; i < board.getSize(); i++) {
      if (board.getValue(row, i) > max) {
        max = board.getValue(row, i);
        count++;
      }
    }

    return count;
  }

  private int countRightRowVisibility(final SkyscraperBoard board, final int row, final int col,
                                      final int number) {
    int max = 0, count = 0;

    for (int i = board.getSize() - 1; i > col; i--) {
      if (board.getValue(row, i) > max) {
        max = board.getValue(row, i);
        count++;
      }
    }

    if (number > max) {
      max = number;
      count++;
    }

    for (int i = col - 1; i >= 0; i--) {
      if (board.getValue(row, i) > max) {
        max = board.getValue(row, i);
        count++;
      }
    }

    return count;
  }

  private int countTopColumnVisibility(final SkyscraperBoard board, final int row,
                                       final int col, final int number) {
    int max = 0, count = 0;

    for (int i = 0; i < row; i++) {
      if (board.getValue(i, col) > max) {
        max = board.getValue(i, col);
        count++;
      }
    }

    if (number > max) {
      max = number;
      count++;
    }

    for (int i = row + 1; i < board.getSize(); i++) {
      if (board.getValue(i, col) > max) {
        max = board.getValue(i, col);
        count++;
      }
    }

    return count;
  }

  private int countBottomColumnVisibility(final SkyscraperBoard board, final int row,
                                          final int col, final int number) {
    int max = 0, count = 0;

    for (int i = board.getSize() - 1; i > row; i--) {
      if (board.getValue(i, col) > max) {
        max = board.getValue(i, col);
        count++;
      }
    }

    if (number > max) {
      max = number;
      count++;
    }

    for (int i = row - 1; i >= 0; i--) {
      if (board.getValue(i, col) > max) {
        max = board.getValue(i, col);
        count++;
      }
    }

    return count;
  }
}

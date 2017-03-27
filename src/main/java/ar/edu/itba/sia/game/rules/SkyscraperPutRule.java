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
    this.name = RULE_NAME + " " + row + " " + col + " " + number;
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
   * @return An Optional, containing the new state if it could be computed or an empty optional otherwise.
   * (The new state is computed if the last empty position on the board equals the position where this
   * rule was created to insert).
   */
  @Override
  public Optional<GPSState> evalRule(final GPSState state) {

    SkyscraperState skyscraperState = (SkyscraperState) state;
    SkyscraperBoard board = skyscraperState.getBoard();
    SkyscraperBoard newBoard = null;
    GPSState newState = null;

    Point firstEmptyPosition = skyscraperState.getFirstEmptyPosition();
    if(firstEmptyPosition != null && firstEmptyPosition.x == row && firstEmptyPosition.y == col){
      newBoard = setValue(board, row, col, number); // Can return null
    }

    if (newBoard != null) {
      newState = new SkyscraperState(newBoard, getFirstEmptyPosition(newBoard));
    }
    return Optional.ofNullable(newState);
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

  private Point getLastEmptyPosition(SkyscraperBoard board){
    for (int i = board.getSize() - 1; i <= 0; i++) {
      for (int j = board.getSize() - 1; j <= 0; j++) {
        if(board.isEmpty(i, j)){
          return new Point(i, j);
        }
      }
    }
    return null;
  }

  /**
   * @param board The old board
   * @param row The row to insert the new value
   * @param col The col to insert the new value
   * @return A new board with the inserted value or null if the value did not meet the restrictions
   */
  private SkyscraperBoard setValue(final SkyscraperBoard board, final int row, final int col,
      final int number) {

    for (int i = 0; i < board.getSize(); i++) {
      if (board.getValue(i, col) == number || board.getValue(row, i) == number) {
        return null;
      }
    }

    if (!checkVisibility(board, row, col, number)) {
      return null;
    }
    return board.setValue(row, col, number);
  }

  private boolean checkVisibility(final SkyscraperBoard board, final int row, final int col,
      final int number) {

    if (!checkLeftRowVisibility(board, row, col, number) ||
        !checkTopColumnVisibility(board, row, col, number)) {
      return false;
    }

    // TODO: Reemplazar los if de abajo por:
    // state.getColLastEmpty[col] == row && ! checkBottomColumnVisibility(board, row, col, number)
    // state.getRowLastEmpty[row] == col && ! checkRightRowVisibility(board, row, col, number)
    // Es necesario pasar cosas del state

    if(row == board.getSize() - 1 && !checkBottomColumnVisibility(board, row, col, number)) {
        return false;
    }

    if(col == board.getSize() -1  && ! checkRightRowVisibility(board, row, col, number)) {
      return false;
    }

    return true;
  }

  private boolean checkLeftRowVisibility(final SkyscraperBoard board, final int row, final int col,
      final int number) {
    int left = board.getVisibility(Border.LEFT, row);
    int max = 0, count = 0;

    if (left == 0) { // Check there's a visibility restriction for that border
      return true;
    }

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

    return count <= left;
  }

  private boolean checkRightRowVisibility(final SkyscraperBoard board, final int row, final int col,
      final int number) {
    int right = board.getVisibility(Border.RIGHT, row);
    int max = 0, count = 0;

    if (right == 0) {
      return true;
    }

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

    return count <= right;
  }

  private boolean checkTopColumnVisibility(final SkyscraperBoard board, final int row,
      final int col, final int number) {
    int top = board.getVisibility(Border.TOP, col);
    int max = 0, count = 0;

    if (top == 0) { // Check there's a visibility restriction for that border
      return true;
    }

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

    return count <= top;
  }

  private boolean checkBottomColumnVisibility(final SkyscraperBoard board, final int row,
      final int col, final int number) {
    int bottom = board.getVisibility(Border.BOTTOM, col);
    int max = 0, count = 0;

    if (bottom == 0) {
      return true;
    }

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

    return count <= bottom;
  }

}

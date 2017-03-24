package ar.edu.itba.sia.game.rules;

import ar.edu.itba.sia.game.Border;
import ar.edu.itba.sia.game.SkyscraperBoard;
import ar.edu.itba.sia.game.SkyscraperState;
import ar.edu.itba.sia.gps.api.GPSRule;
import ar.edu.itba.sia.gps.api.GPSState;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class Put implements GPSRule {

  final private static int COST= 1;
  final private static String NAME = "Put";

  @Override
  public Integer getCost() {
    return COST;
  }

  @Override
  public String getName() {
    return NAME;
  }

  /**
   *
   * @param state The previous state of the problem.
   * @return A new state with the updated board containing a new value.
   */
  @Override
  public Optional<GPSState> evalRule(final GPSState state) {

    SkyscraperBoard board = ((SkyscraperState)state).getBoard();
    SkyscraperBoard newBoard = null;
    GPSState newState = null;

    int size = board.getSize();
    boolean found = false;

    for(int i=0; i<size && !found; i++){
      for(int j=0; j<size && !found; j++){
        if(board.isEmpty(i, j)){
          newBoard = setValue(board, i, j);
          if(newBoard != null){
            found = true;
          }
        }
      }
    }

    if(found){
      newState = new SkyscraperState(newBoard);
    }
    return Optional.ofNullable(newState);
  }

  /**
   *
   * @param board The old board
   * @param row The row to insert the new value
   * @param col The col to insert the new value
   * @return A new board with the inserted value or null if the value did not meet the restrictions
   */
  private SkyscraperBoard setValue(final SkyscraperBoard board, final int row, final int col) {

    List<Integer> numbers = IntStream.range(1, board.getSize()).boxed().collect(Collectors.toList());

    // Remove the numbers already in use in the same col & row where the new value is inserted
    for(int i=0; i<board.getSize(); i++){
      numbers.remove(board.getValue(i, col));
      numbers.remove(board.getValue(row, i));
    }

    for(Integer number: numbers) {
      if(checkVisibility(board, row, col, number)) {
        return board.setValue(row, col, number);
      }
    }

    return null;
  }

  private boolean checkVisibility(final SkyscraperBoard board, final int row, final int col, final int number) {
    return checkLeftRowVisibility(board, row, col, number) &&
        checkRightRowVisibility(board, row, col, number) &&
        checkTopColumnVisibility(board, row, col, number) &&
        checkBottomColumnVisibility(board, row, col, number);
  }

  private boolean checkLeftRowVisibility(final SkyscraperBoard board, final int row, final int col, final int number) {
    int left = board.getVisibility(Border.LEFT, row);
    int max = 0, count = 0;

    if(left == 0){ // Check there's a visibility restriction for that border
      return true;
    }

    for(int i=0; i<col; i++) {
      if( board.getValue(row, i)  > max) {
        max = board.getValue(row, i);
        count ++;
      }
    }
    if( number  > max) {
      max = number;
      count ++;
    }
    for(int i=col+1; i<board.getSize(); i++) {
      if( board.getValue(row, i)  > max) {
        max = board.getValue(row, i);
        count ++;
      }
    }

    if(count > left) {
      return false;
    }
    return true;
  }

  private boolean checkRightRowVisibility(final SkyscraperBoard board, final int row, final int col, final int number) {
    int right = board.getVisibility(Border.RIGHT, row);
    int max = 0, count = 0;

    if(right == 0) {
      return true;
    }

    for(int i=board.getSize()-1; i>col; i--) {
      if( board.getValue(row, i)  > max) {
        max = board.getValue(row, i);
        count ++;
      }
    }
    if( number  > max) {
      max = number;
      count ++;
    }
    for(int i=col-1; i>=0; i--) {
      if( board.getValue(row, i)  > max) {
        max = board.getValue(row, i);
        count ++;
      }
    }

    if(count > right) {
      return false;
    }
    return true;
  }

  private boolean checkTopColumnVisibility(final SkyscraperBoard board, final int row, final int col, final int number) {
    int top = board.getVisibility(Border.TOP, col);
    int max = 0, count = 0;

    if(top == 0){ // Check there's a visibility restriction for that border
      return true;
    }

    for(int i=0; i<row; i++) {
      if( board.getValue(i, col)  > max) {
        max = board.getValue(i, col);
        count ++;
      }
    }
    if( number  > max) {
      max = number;
      count ++;
    }
    for(int i=row+1; i<board.getSize(); i++) {
      if( board.getValue(i, col)  > max) {
        max = board.getValue(i, col);
        count ++;
      }
    }

    if(count > top) {
      return false;
    }
    return true;
  }

  private boolean checkBottomColumnVisibility(final SkyscraperBoard board, final int row, final int col, final int number) {
    int bottom = board.getVisibility(Border.BOTTOM, col);
    int max = 0, count = 0;

    if(bottom == 0) {
      return true;
    }

    for(int i=board.getSize()-1; i>row; i--) {
      if( board.getValue(i, col)  > max) {
        max = board.getValue(i, col);
        count ++;
      }
    }
    if( number  > max) {
      max = number;
      count ++;
    }
    for(int i=row-1; i>=0; i--) {
      if( board.getValue(i, col)  > max) {
        max = board.getValue(col, i);
        count ++;
      }
    }

    if(count > bottom) {
      return false;
    }
    return true;
  }

}

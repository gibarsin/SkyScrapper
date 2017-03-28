package ar.edu.itba.sia.game.heuristic;

import ar.edu.itba.sia.game.BoardValidator;
import ar.edu.itba.sia.game.SkyscraperBoard;
import ar.edu.itba.sia.game.SkyscraperState;
import ar.edu.itba.sia.gps.api.GPSState;
import ar.edu.itba.sia.gps.api.H;

public class IdealAmountOfSwapsHeuristic implements H {
  private static final int MAX_CONFLICTS_SOLVED_BY_SWAP = 12;
  private final BoardValidator boardValidator;

  public IdealAmountOfSwapsHeuristic(final BoardValidator boardValidator) {
    this.boardValidator = boardValidator;
  }

  @Override
  public Integer getValue(final GPSState state) {
    final SkyscraperState ssState = (SkyscraperState) state;
    final SkyscraperBoard board = ssState.getBoard();

    final int a = boardValidator.countConflicts(board);
    final int b = MAX_CONFLICTS_SOLVED_BY_SWAP;
    // ceil for ints:
    // http://stackoverflow.com/questions/7139382/java-rounding-up-to-an-int-using-math-ceil
    return a / b + ((a % b == 0) ? 0 : 1);
  }
}

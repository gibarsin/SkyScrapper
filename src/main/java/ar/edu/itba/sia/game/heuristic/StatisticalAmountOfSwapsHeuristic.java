package ar.edu.itba.sia.game.heuristic;

import ar.edu.itba.sia.game.BoardValidator;
import ar.edu.itba.sia.game.SkyscraperBoard;
import ar.edu.itba.sia.game.SkyscraperState;
import ar.edu.itba.sia.game.rules.SkyscraperSwapRule;
import ar.edu.itba.sia.gps.api.GPSState;

public class StatisticalAmountOfSwapsHeuristic implements Heuristic {
  private final BoardValidator boardValidator;

  public StatisticalAmountOfSwapsHeuristic(final BoardValidator boardValidator) {
    this.boardValidator = boardValidator;
  }

  @Override
  public Integer getValue(final GPSState state) {
    final SkyscraperState ssState = (SkyscraperState) state;
    final SkyscraperBoard board = ssState.getBoard();

    return boardValidator.countConflicts(board) * SkyscraperSwapRule.COST;
  }

  @Override
  public boolean isAdmissible() {
    return false;
  }
}

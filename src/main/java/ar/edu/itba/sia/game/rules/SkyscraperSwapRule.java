/*
  TODO:
  https://github.com/gibarsin/SkyScrapper/pull/6#discussion_r108933023
 */

package ar.edu.itba.sia.game.rules;

import ar.edu.itba.sia.game.SkyscraperBoard;
import ar.edu.itba.sia.game.SkyscraperState;
import ar.edu.itba.sia.gps.api.GPSRule;
import ar.edu.itba.sia.gps.api.GPSState;
import java.util.Optional;

public class SkyscraperSwapRule implements GPSRule {
  private static final int COST = 12;
  private static final String RULE_NAME = "SWAP";
  private final String name;

  private final int row1;
  private final int col1;
  private final int row2;
  private final int col2;

  public SkyscraperSwapRule(final int row1, final int col1, final int row2, final int col2) {
    this.row1 = row1;
    this.col1 = col1;
    this.row2 = row2;
    this.col2 = col2;
    this.name = RULE_NAME + " (" + row1 + ", " + col1 + ") <-> (" + row2 + ", " + col2 + ")";
  }

  @Override
  public String toString() {
    return "\nRule: " + name + "\n\n";
  }

  @Override
  public Integer getCost() {
    return COST;
  }

  @Override
  public String getName() {
    return name;
  }

  @Override
  public Optional<GPSState> evalRule(final GPSState state) {
    final SkyscraperBoard board = ((SkyscraperState) state).getBoard();

    if (!board.isFull() || !board.isValidSwap(row1, col1, row2, col2)) {
      return Optional.empty();
    }

    return Optional.of(new SkyscraperState(board.swapValue(row1, col1, row2, col2)));
  }
}

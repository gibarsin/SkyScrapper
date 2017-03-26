package ar.edu.itba.sia.game;

import ar.edu.itba.sia.gps.api.GPSState;
import java.util.Objects;

public class SkyscraperState implements GPSState {

  private final SkyscraperBoard board;

  public SkyscraperState(final SkyscraperBoard board) {
    this.board = Objects.requireNonNull(board);
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }

    if (o == null || getClass() != o.getClass()) {
      return false;
    }

    SkyscraperState that = (SkyscraperState) o;

    return board.equals(that.board);
  }

  @Override
  public int hashCode() {
    return board.hashCode();
  }

  public SkyscraperBoard getBoard() {
    return board;
  }
}

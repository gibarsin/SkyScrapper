package ar.edu.itba.sia.game;

import ar.edu.itba.sia.gps.api.GPSState;
import java.awt.Point;
import java.util.Objects;

public class SkyscraperState implements GPSState {

  private final SkyscraperBoard board;
  private final Point firstEmptyPosition;

  public SkyscraperState(final SkyscraperBoard board) {
    this.board = Objects.requireNonNull(board);
    this.firstEmptyPosition = null;
  }

  public SkyscraperState(final SkyscraperBoard board, final Point firstEmptyPosition) {
    this.board = Objects.requireNonNull(board);
    this.firstEmptyPosition = firstEmptyPosition;
  }

  public SkyscraperBoard getBoard() {
    return board;
  }

  public Point getFirstEmptyPosition() {
    return firstEmptyPosition;
  }

  @Override
  public String toString() {
    return board.toString();
  }

  @Override
  public boolean equals(final Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }

    final SkyscraperState that = (SkyscraperState) o;

    return board.equals(that.board);
  }

  @Override
  public int hashCode() {
    return board.hashCode();
  }
}

package ar.edu.itba.sia.game;

public class Point {
  private final int row;
  private final int col;

  public Point(final int row, final int col) {
    this.row = row;
    this.col = col;
  }

  public int getRow() {
    return row;
  }

  public int getCol() {
    return col;
  }

  public boolean equals(final int row, final int col) {
    return this.row == row && this.col == col;
  }

  @Override
  public boolean equals(final Object o) {
    if (this == o) {
      return true;
    }
    if (!(o instanceof Point)) {
      return false;
    }

    final Point point = (Point) o;

    return row == point.row && col == point.col;
  }

  @Override
  public int hashCode() {
    int result = row;
    result = 31 * result + col;
    return result;
  }
}

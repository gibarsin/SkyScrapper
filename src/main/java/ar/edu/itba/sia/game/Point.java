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
}

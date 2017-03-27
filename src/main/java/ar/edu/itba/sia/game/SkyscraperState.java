package ar.edu.itba.sia.game;

import ar.edu.itba.sia.gps.api.GPSState;
import java.awt.Point;
import java.util.Objects;

public class SkyscraperState implements GPSState {

  private final SkyscraperBoard board;
  private final Point firstEmptyPosition;
  private static int[] rowLastEmpty;
  private static int[] colLastEmpty;

  public SkyscraperState(final SkyscraperBoard board, final Point firstEmptyPosition) {
    this.board = Objects.requireNonNull(board);
    this.firstEmptyPosition = firstEmptyPosition;
  }

  public SkyscraperState(final SkyscraperBoard board) {
    this.board = Objects.requireNonNull(board);
    if(board.isFull()){
      this.firstEmptyPosition = null;
      this.rowLastEmpty = null;
      this.colLastEmpty = null;
    } else{
      this.firstEmptyPosition = findFirstEmptyPosition(board);
      this.rowLastEmpty = findRowLastEmpty(board);
      this.colLastEmpty = findColLastEmpty(board);
    }
  }

  public SkyscraperBoard getBoard() {
    return board;
  }

  public Point getFirstEmptyPosition() {
    return firstEmptyPosition;
  }

  public static int[] getRowLastEmpty() {
    return rowLastEmpty;
  }

  public static int[] getColLastEmpty() {
    return colLastEmpty;
  }

  private Point findFirstEmptyPosition(SkyscraperBoard board) {
    for (int i = 0; i < board.getSize(); i++) {
      for (int j = 0; j < board.getSize(); j++) {
        if(board.isEmpty(i, j)){
          return new Point(i, j);
        }
      }
    }
    return null;
  }

  private int[] findRowLastEmpty(SkyscraperBoard board){
    int[] row = new int[board.getSize()];
    boolean found = false;
    for (int i = 0; i < board.getSize(); i++) {
      for (int j = board.getSize() - 1; j >= 0 && !found; j--) {
        if(board.isEmpty(i, j)){
          row[i] = j;
          found = true;
        }
      }
      if(!found){
        row[i] = -1;
      }
      found = false;
    }
    return row;
  }

  private int[] findColLastEmpty(SkyscraperBoard board){
    int[] col = new int[board.getSize()];
    boolean found = false;
    for (int j = 0; j < board.getSize(); j++) {
      for (int i = board.getSize() - 1; i >= 0 && !found; i--) {
        if(board.isEmpty(i, j)){
          col[j] = i;
          found = true;
        }
      }
      if(!found){
        col[j] = -1;
      }
      found = false;
    }
    return col;
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

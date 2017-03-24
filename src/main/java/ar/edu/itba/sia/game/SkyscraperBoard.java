package ar.edu.itba.sia.game;

import java.util.Arrays;

public class SkyscraperBoard implements Integer2DBoard, Visibility {

  private final ArrayVisibility visibility;
  private final int[][] matrix;

  public SkyscraperBoard(final int n, final ArrayVisibility visibility) {
    this.visibility = visibility;
    this.matrix = new int[n][n];
  }

  @Override
  public int getSize() {
    return matrix.length;
  }

  @Override
  public int getValue(final int row, final int column) {
    return matrix[row][column];
  }

  @Override
  public void setValue(final int row, final int column, final int value) {
    matrix[row][column] = value;
  }

  @Override
  public boolean isEmpty(final int row, final int column) {
    return matrix[row][column] == 0;
  }

  @Override
  public int getVisibility(final Border border, final int position) {
    return visibility.getVisibility(border, position);
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }

    if (o == null || getClass() != o.getClass()) {
      return false;
    }

    SkyscraperBoard that = (SkyscraperBoard) o;

    if (!visibility.equals(that.visibility)) {
      return false;
    }

    return Arrays.deepEquals(matrix, that.matrix);
  }

  @Override
  public int hashCode() {
    int result = visibility.hashCode();

    result = 31 * result + Arrays.deepHashCode(matrix);

    return result;
  }
}

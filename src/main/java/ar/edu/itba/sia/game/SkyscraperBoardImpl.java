package ar.edu.itba.sia.game;

import java.util.Arrays;
import java.util.Objects;
import java.util.stream.IntStream;

public class SkyscraperBoardImpl implements SkyscraperBoard {

  private final Visibility visibility;
  private final int[][] matrix;

  public SkyscraperBoardImpl(final int[][] matrix, final Visibility visibility) {
    this.visibility = Objects.requireNonNull(visibility);
    final int rowsLength = Objects.requireNonNull(matrix).length;
    for (int i = 0 ; i < rowsLength ; i++) {
      if (matrix[i].length != rowsLength) {
        throw new IllegalArgumentException("Matrix should be square");
      }
    }
    this.matrix = matrix;
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
  public SkyscraperBoard setValue(final int row, final int column, final int value) {
    final int[][] newMatrix = new int[matrix.length][];
    IntStream.range(0, matrix.length).parallel().forEach(i -> newMatrix[i] = Arrays.copyOf(matrix[i], matrix[i].length));
    newMatrix[row][column] = value;
    return new SkyscraperBoardImpl(newMatrix, visibility);
  }

  @Override
  public boolean isEmpty(final int row, final int column) {
    return matrix[row][column] == 0;
  }

  @Override
  public void print() {
    for(int[] rows : matrix) {
      for(int value : rows) {
        System.out.print(value + "\t");
      }
      System.out.println();
    }
    System.out.println("---");
  }

  @Override
  public boolean hasVisibility(final Border border, final int position) {
    return visibility.hasVisibility(border, position);
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

    SkyscraperBoardImpl that = (SkyscraperBoardImpl) o;

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

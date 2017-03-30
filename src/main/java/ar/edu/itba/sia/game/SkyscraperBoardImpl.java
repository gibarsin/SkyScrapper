package ar.edu.itba.sia.game;

import java.util.Arrays;
import java.util.Objects;
import java.util.stream.IntStream;

public class SkyscraperBoardImpl implements SkyscraperBoard {

  private final Visibility visibility;
  private final int[][] matrix;
  private final int emptySpaces;

  public SkyscraperBoardImpl(final int[][] matrix, final Visibility visibility) {
    this.visibility = Objects.requireNonNull(visibility);
    this.emptySpaces = checkMatrix(matrix);
    this.matrix = new int[matrix.length][];
    // TODO: cloneMatrix method (search all usages) #6
    IntStream.range(0, matrix.length).parallel()
        .forEach(i -> this.matrix[i] = Arrays.copyOf(matrix[i], matrix[i].length));
  }

  private SkyscraperBoardImpl(final int[][] matrix, final Visibility visibility, int emptySpaces) {
    this.visibility = visibility;
    this.matrix = matrix;
    this.emptySpaces = emptySpaces;
  }

  private static int checkMatrix(final int[][] matrix) {
    final int n = Objects.requireNonNull(matrix).length;
    int emptySpaces = 0;

    for (int i = 0; i < n; i++) {
      if (matrix[i].length != n) {
        throw new IllegalArgumentException("Matrix should be square");
      }

      for (int j = 0; j < n; j++) {
        final int value = matrix[i][j];

        if (value < 0 || value > n) {
          throw new IllegalArgumentException("Matrix values should be between 0 and " + n);
        }

        if (matrix[i][j] == 0) {
          emptySpaces++;
        }
      }
    }

    return emptySpaces;
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
    final int newEmptySpaces = isEmpty(row, column) ? emptySpaces - 1 : emptySpaces;

    IntStream.range(0, matrix.length).parallel()
        .forEach(i -> newMatrix[i] = Arrays.copyOf(matrix[i], matrix[i].length));
    newMatrix[row][column] = value;

    // TODO: https://github.com/gibarsin/SkyScrapper/pull/6#discussion_r108918178
    return new SkyscraperBoardImpl(newMatrix, visibility, newEmptySpaces);
  }

  @Override
  public boolean isEmpty(final int row, final int column) {
    return matrix[row][column] == 0;
  }

  @Override
  public boolean isFull() {
    return emptySpaces == 0;
  }

  @Override
  public SkyscraperBoard swapValue(final int row1, final int column1, final int row2,
      final int column2) {
    final int[][] newMatrix = new int[matrix.length][];

    IntStream.range(0, matrix.length).parallel()
        .forEach(i -> newMatrix[i] = Arrays.copyOf(matrix[i], matrix[i].length));

    final int aux = newMatrix[row1][column1];
    newMatrix[row1][column1] = newMatrix[row2][column2];
    newMatrix[row2][column2] = aux;

    return new SkyscraperBoardImpl(newMatrix, visibility, emptySpaces);
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
  public String toString() {
    final StringBuilder sb = new StringBuilder();

    for (int[] rows : matrix) {
      for (int value : rows) {
        sb.append(value).append("\t");
      }
      sb.append("\n");
    }
    sb.append("---\n");

    return sb.toString();
  }

  @Override
  public boolean equals(final Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }

    final SkyscraperBoardImpl that = (SkyscraperBoardImpl) o;

    if (emptySpaces != that.emptySpaces) {
      return false;
    }

    if (!visibility.equals(that.visibility)) {
      return false;
    }

    return Arrays.deepEquals(matrix, that.matrix);
  }

  @Override
  public int hashCode() {
    int result = visibility.hashCode();
    result = 31 * result + Arrays.deepHashCode(matrix);
    result = 31 * result + emptySpaces;
    return result;
  }
}

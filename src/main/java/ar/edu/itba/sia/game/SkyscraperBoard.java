package ar.edu.itba.sia.game;

public class SkyscraperBoard implements Integer2DBoard, Visibility {

  private final ArrayVisibility visibility;
  private final int[][] matrix;

  public SkyscraperBoard(int n, ArrayVisibility visibility) {
    this.visibility = visibility;
    this.matrix = new int[n][n];
  }

  @Override
  public int getValue(int row, int column) {
    return matrix[row][column];
  }

  @Override
  public void setValue(int row, int column, int value) {
    matrix[row][column] = value;
  }

  @Override
  public boolean isEmpty(int row, int column) {
    return matrix[row][column] == 0;
  }

  @Override
  public int getVisibility(Border border, int position) {
    return visibility.getVisibility(border, position);
  }
}

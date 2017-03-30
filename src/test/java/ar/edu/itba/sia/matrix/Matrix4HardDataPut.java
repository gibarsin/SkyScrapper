package ar.edu.itba.sia.matrix;

public class Matrix4HardDataPut extends Matrix4HardData {
  private static final String METHOD = "PUT";

  @Override
  protected String getMethod() {
    return METHOD;
  }

  @Override
  protected int[][] getMatrix() {
    return new int[][]{
        {0, 0, 0, 2},
        {1, 0, 0, 0},
        {0, 0, 0, 0},
        {0, 0, 0, 0}
    };
  }

}

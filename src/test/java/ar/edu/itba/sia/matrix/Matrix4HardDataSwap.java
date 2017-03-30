package ar.edu.itba.sia.matrix;

public class Matrix4HardDataSwap extends Matrix4HardData {
  private static final String METHOD = "SWAP";

  @Override
  protected String getMethod() {
    return METHOD;
  }

  @Override
  protected int[][] getMatrix() {
    return new int[][]{
        {3, 4, 1, 2},
        {1, 3, 1, 4},
        {2, 3, 2, 4},
        {3, 2, 4, 1}
    };
  }
}

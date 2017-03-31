package ar.edu.itba.sia.core.helper;

import java.util.Arrays;
import org.junit.Assert;
import org.junit.Test;

public class FullMatrixTest {
  private static final int SIZE = 3;

//  private static final int[][] matrix = new int[][] {
//      {0, 1, 0},
//      {0, 0, 0},
//      {2, 0, 0}
//  };

  private static final int[][] solutionMatrix = new int[][] {
      {3, 1, 2},
      {1, 2, 3},
      {2, 3, 1}
  };

  @Test
  public void testFullMatrix() throws Exception {
    final FullMatrix fullMatrix = new FullMatrix(SIZE);
    fullMatrix.fix(0, 1, 1);
    fullMatrix.fix(2, 0, 2);

    Assert.assertTrue(Arrays.deepEquals(solutionMatrix, fullMatrix.getCompleteMatrix()));
  }
}

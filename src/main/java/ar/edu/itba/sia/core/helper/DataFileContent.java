package ar.edu.itba.sia.core.helper;

import ar.edu.itba.sia.game.Point;
import ar.edu.itba.sia.game.Visibility;
import java.util.List;
import java.util.Map;

public class DataFileContent {
  private final int[][] matrix;
  private final List<Point> fixedCells;
  private final Visibility visibility;
  private final FullMatrix fullMatrix;

  public DataFileContent(final int[][] matrix,
      final List<Point> fixedCells, final Visibility visibility,
      final FullMatrix fullMatrix) {
    this.matrix = matrix;
    this.fixedCells = fixedCells;
    this.visibility = visibility;
    this.fullMatrix = fullMatrix;
  }

  public int[][] getMatrix() {
    return matrix;
  }

  public List<Point> getFixedCells() {
    return fixedCells;
  }

  public Visibility getVisibility() {
    return visibility;
  }

  public FullMatrix getFullMatrix() {
    return fullMatrix;
  }
}

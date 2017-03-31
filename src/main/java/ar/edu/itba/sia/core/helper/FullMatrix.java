package ar.edu.itba.sia.core.helper;

import ar.edu.itba.sia.game.Point;
import java.util.Deque;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;
import java.util.stream.IntStream;

public class FullMatrix {
  private static final int ONE = 1;
  private final Deque<Point> nextToFix;
  private final int n;
  private final Map<Integer, Map<Integer, Available>> fullMatrix;

  public FullMatrix(final int n) {
    this.nextToFix = new LinkedList<>();
    this.n = n;
    this.fullMatrix = new HashMap<>();
    for (int i = 0; i < n; i++) {
      final Map<Integer, Available> availablePerColumn = new HashMap<>();
      fullMatrix.put(i, availablePerColumn);
      for (int j = 0; j < n; j++) {
        availablePerColumn.put(j, new Available(n));
      }
    }
  }

  public final Deque<Integer> getAvailableFor(final int row, final int col) {
    return fullMatrix.get(row).get(col).getAvailable();
  }

  public int[][] getCompleteMatrix() {
    while (isNextToFix()) {
      fixNext();
    }
    return getMatrix();
  }

  public final boolean isFixed(final int row, final int col) {
    return fullMatrix.get(row).get(col).isFixed();
  }

  public final void fix(final int row, final int col, final int value) {
    final Available available = fullMatrix.get(row).get(col);
    if (available.isFixed()) {
      throw new IllegalArgumentException("Value already fixed for (" + row + ":" + col + ")");
    }
    available.setValue(value);

    // disable this possible value on the row
    // disable this possible value on the col
    for (int i = 0 ; i < n ; i++) {
      discard(row, i, value);
      discard(i, col, value);
    }
  }

  private void discard(final int row, final int col, final int value) {
    final Deque<Integer> availableForRow = getAvailableFor(row, col);
    availableForRow.remove(value);
    if (availableForRow.size() == ONE) {
      final Point p = new Point(row, col);
      if (!nextToFix.contains(p)) {
        nextToFix.push(p);
      }
    }
  }

  public void fixNext() {
    final Point p = nextToFix.pollFirst();
    final int row = p.getRow();
    final int col = p.getCol();
    final int value = fullMatrix.get(row).get(col).getAvailable().poll();
    fix(row, col, value);
  }

  public boolean isNextToFix() {
    if (!nextToFix.isEmpty()) {
      return true;
    }

    Point next = null;
    Available minAvailable = null;

    for (int i = 0; i < n; i++) {
      for (int j = 0; j < n; j++) {
        final Available available = fullMatrix.get(i).get(j);
        if (available.isFixed()) {
          continue;
        }
        if (minAvailable == null ||
            available.getAvailable().size() < minAvailable.getAvailable().size()) {
          minAvailable = available;
          next = new Point(i, j);
        }
      }
    }

    // there is a next to fix only if all available cells are already fixed
    if (next != null) {
      nextToFix.push(next);
    }

    return !nextToFix.isEmpty();
  }

  public int[][] getMatrix() {
    final int[][] matrix = new int[n][n];
    for (int i = 0; i < n; i++) {
      for (int j = 0; j < n; j++) {
        matrix[i][j] = fullMatrix.get(i).get(j).getValue();
      }
    }
    return matrix;
  }

  private static class Available {
    private static final int EMPTY = 0;

    private final Deque<Integer> available;
    private int value = 0;

    private Available(final int n) {
      this.available = new LinkedList<>();
      IntStream.range(1, n+1).forEach(available::add);
    }

    private boolean isFixed() {
      return value != EMPTY;
    }

    public Deque<Integer> getAvailable() {
      return available;
    }

    public int getValue() {
      return value;
    }

    public void setValue(final int value) {
      available.clear();
      this.value = value;
    }
  }
}

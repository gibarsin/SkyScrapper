package ar.edu.itba.sia.game;

import java.util.Arrays;
import java.util.Objects;

public class ArrayVisibility implements Visibility {
  private final int[] up;
  private final int[] down;
  private final int[] left;
  private final int[] right;

  private ArrayVisibility(int[] up, int[] down, int[] left, int[] right) {
    this.up = up;
    this.down = down;
    this.left = left;
    this.right = right;
  }

  public class Builder {
    private final int[] up;
    private final int[] down;
    private final int[] left;
    private final int[] right;

    public Builder(int n) {
      this.up = new int[n];
      this.down = new int[n];
      this.left = new int[n];
      this.right = new int[n];
    }

    public Builder(int n, int[] up, int[] down, int[] left, int[] right) {
      Objects.requireNonNull(up);
      Objects.requireNonNull(down);
      Objects.requireNonNull(left);
      Objects.requireNonNull(right);
      if (up.length != n || down.length != n || left.length != n || right.length != n) {
        throw new IllegalArgumentException("Arrays size must be " + n);
      }

      this.up = Arrays.copyOf(up, up.length);
      this.down = Arrays.copyOf(down, down.length);
      this.left = Arrays.copyOf(left, left.length);
      this.right = Arrays.copyOf(right, right.length);
    }

    public Builder withValue(Border border, int position, int value) {
      Objects.requireNonNull(border);
      if (value < 1 || value > up.length) {
        throw new IllegalArgumentException("Value must be between 1 and " + up.length);
      }

      int[] array = null;

      switch (border) {
        case TOP:
          array = up;
          break;
        case BOTTOM:
          array = down;
          break;
        case LEFT:
          array = left;
          break;
        case RIGHT:
          array = right;
          break;
      }

      array[position] = value;

      return this;
    }

    public ArrayVisibility build() {
      return new ArrayVisibility(up, down, left, right);
    }
  }

  @Override
  public int getVisibility(Border border, int position) {
    Objects.requireNonNull(border);

    int[] array = null;

    switch (border) {
      case TOP:
        return up[position];
      case BOTTOM:
        return down[position];
      case LEFT:
        return left[position];
      case RIGHT:
        return right[position];
    }

    throw new IllegalStateException("Missing switch case");
  }
}

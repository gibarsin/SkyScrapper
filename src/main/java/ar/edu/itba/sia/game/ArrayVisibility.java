package ar.edu.itba.sia.game;

import java.util.Arrays;
import java.util.Objects;

public class ArrayVisibility implements Visibility {
  private final int[] top;
  private final int[] bottom;
  private final int[] left;
  private final int[] right;

  private ArrayVisibility(final int[] top, final int[] bottom, final int[] left,
                          final int[] right) {
    this.top = top;
    this.bottom = bottom;
    this.left = left;
    this.right = right;
  }

  public class Builder {
    private final int[] top;
    private final int[] bottom;
    private final int[] left;
    private final int[] right;

    public Builder(final int n) {
      this.top = new int[n];
      this.bottom = new int[n];
      this.left = new int[n];
      this.right = new int[n];
    }

    public Builder(final int n, final int[] top, final int[] bottom, final int[] left,
                   final int[] right) {
      if (!validArray(n, top) || !validArray(n, bottom) || !validArray(n, left)
          || !validArray(n, right)) {
        throw new IllegalArgumentException("Invalid initial values");
      }

      this.top = Arrays.copyOf(top, top.length);
      this.bottom = Arrays.copyOf(bottom, bottom.length);
      this.left = Arrays.copyOf(left, left.length);
      this.right = Arrays.copyOf(right, right.length);
    }

    public Builder withValue(final Border border, final int position, final int value) {
      Objects.requireNonNull(border);
      if (value < 0 || value > top.length) {
        throw new IllegalArgumentException("Value must be between 0 and " + top.length);
      }

      int[] array;
      switch (border) {
        case TOP:
          array = top;
          break;

        case BOTTOM:
          array = bottom;
          break;

        case LEFT:
          array = left;
          break;

        case RIGHT:
          array = right;
          break;

        default:
          throw new IllegalStateException("Missing enum case");
      }

      array[position] = value;

      return this;
    }

    public ArrayVisibility build() {
      return new ArrayVisibility(top, bottom, left, right);
    }
  }

  @Override
  public int getVisibility(final Border border, final int position) {
    Objects.requireNonNull(border);

    int[] array;
    switch (border) {
      case TOP:
        array = top;
        break;

      case BOTTOM:
        array = bottom;
        break;

      case LEFT:
        array = left;
        break;

      case RIGHT:
        array = right;
        break;

      default:
        throw new IllegalStateException("Missing enum case");
    }

    return array[position];
  }

  private static boolean validArray(final int n, final int[] array) {
    Objects.requireNonNull(array);

    return array.length == n
        && Arrays.stream(array).noneMatch(value -> value < 0 || value > n);
  }
}

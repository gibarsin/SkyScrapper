package ar.edu.itba.sia.game;

import java.util.Arrays;
import java.util.Objects;

public class ArrayVisibility implements Visibility {
  private final int[] up;
  private final int[] down;
  private final int[] left;
  private final int[] right;

  private ArrayVisibility(final int[] up, final int[] down, final int[] left, final int[] right) {
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

    public Builder(final int n) {
      this.up = new int[n];
      this.down = new int[n];
      this.left = new int[n];
      this.right = new int[n];
    }

    public Builder(final int n, final int[] up, final int[] down, final int[] left,
                   final int[] right) {
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

    public Builder withValue(final Border border, final int position, final int value) {
      if (value < 1 || value > up.length) {
        throw new IllegalArgumentException("Value must be between 1 and " + up.length);
      }

      int[] array;
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

        default:
          throw new IllegalArgumentException("Invalid border");
      }

      array[position] = value;

      return this;
    }

    public ArrayVisibility build() {
      return new ArrayVisibility(up, down, left, right);
    }
  }

  @Override
  public int getVisibility(final Border border, final int position) {
    Objects.requireNonNull(border);

    int[] array;
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

      default:
        throw new IllegalArgumentException("Invalid border");
    }

    return array[position];
  }
}

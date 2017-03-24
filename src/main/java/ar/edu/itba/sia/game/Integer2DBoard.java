package ar.edu.itba.sia.game;

public interface Integer2DBoard {
  int getSize();

  int getValue(final int row, final int column);

  void setValue(final int row, final int column, final int value);

  boolean isEmpty(final int row, final int column);

  default void removeValue() {
    throw new UnsupportedOperationException();
  }
}

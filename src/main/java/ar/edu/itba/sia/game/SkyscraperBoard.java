package ar.edu.itba.sia.game;

public interface SkyscraperBoard extends Visibility {
  int getSize();

  int getValue(final int row, final int column);

  SkyscraperBoard setValue(final int row, final int column, final int value);

  boolean isEmpty(final int row, final int column);

  void print();
}

package ar.edu.itba.sia.game;

public interface SkyscraperBoard extends Visibility {

  int getSize();

  int getValue(final int row, final int column);

  boolean isEmpty(final int row, final int column);

  boolean isFull();

  SkyscraperBoard setValue(final int row, final int column, final int value);

  SkyscraperBoard swapValue(final int row1, final int column1, final int row2, final int column2);
}

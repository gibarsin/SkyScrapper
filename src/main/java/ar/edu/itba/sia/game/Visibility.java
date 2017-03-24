package ar.edu.itba.sia.game;

public interface Visibility {

  boolean hasVisibility(final Border border, final int position);

  int getVisibility(final Border border, final int position);
}

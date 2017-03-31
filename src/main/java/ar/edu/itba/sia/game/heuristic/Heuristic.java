package ar.edu.itba.sia.game.heuristic;

import ar.edu.itba.sia.gps.api.GPSState;

public interface Heuristic {
  boolean isAdmissible();

  Integer getValue(GPSState state);
}

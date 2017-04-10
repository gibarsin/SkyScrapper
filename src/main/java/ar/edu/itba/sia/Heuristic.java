package ar.edu.itba.sia;

import ar.edu.itba.sia.gps.api.GPSState;

public interface Heuristic {

    Integer getHValue(final GPSState gpsState);

    Integer getCost();

    String getName();
}

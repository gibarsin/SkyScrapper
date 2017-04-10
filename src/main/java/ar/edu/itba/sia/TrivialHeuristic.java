package ar.edu.itba.sia;

import ar.edu.itba.sia.gps.api.GPSState;

public class TrivialHeuristic implements Heuristic {
    @Override
    public Integer getHValue(GPSState gpsState) {
        Board board = (Board) gpsState;
        return board.getEmptyCells();
    }

    @Override
    public Integer getCost() {
        return 1;
    }

    @Override
    public String getName() {
        return "Trivial Heuristic";
    }
}

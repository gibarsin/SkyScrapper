package ar.edu.itba.sia;

import ar.edu.itba.sia.gps.api.GPSState;

public class SureSemiSureAdmissibleHeuristic implements Heuristic{

    private static final int EMPTY_WEIGHT = 20;
    private static final int SEMI_SURE_WEIGHT = 5;
    private static final int ASSUMP_WEIGHT = 10;

    private Integer idealValue;
    private Integer n;

    public SureSemiSureAdmissibleHeuristic(final Board initialState){
        this.n = initialState.getSize();
    }
    @Override
    public Integer getHValue(GPSState gpsState) {
        Board board = (Board) gpsState;
        return board.getEmptyCells() * EMPTY_WEIGHT * n + board.getSemiSure() * SEMI_SURE_WEIGHT * n + board.getAssumptions() * ASSUMP_WEIGHT * n;
    }

    @Override
    public Integer getCost() {
        return EMPTY_WEIGHT * n;
    }

    @Override
    public String getName() {
        return "Least Assumptions";
    }
}

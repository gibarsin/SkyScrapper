package ar.edu.itba.sia;

import ar.edu.itba.sia.gps.api.GPSState;

public class SureSemiSureHeuristic implements Heuristic{

    private static final int SURE_WEIGHT = 5;
    private static final int SEMI_SURE_WEIGHT = 4;
    private static final int ASSUMP_WEIGHT = 3;

    private Integer idealValue;
    private Integer size;

    public SureSemiSureHeuristic(final Board initialState){
        this.idealValue = getIdealValue(initialState.getSize());
        this.size = initialState.getSize();
    }
    @Override
    public Integer getHValue(GPSState gpsState) {
        Board board = (Board) gpsState;
        return idealValue - (board.getSure() * SURE_WEIGHT + board.getSemiSure() * SEMI_SURE_WEIGHT + ((board.getAssumptions() > 1) ? board.getAssumptions() * -ASSUMP_WEIGHT : board.getAssumptions() * ASSUMP_WEIGHT));
    }

    @Override
    public Integer getCost() {
        return 1;
    }

    @Override
    public String getName() {
        return "Least Assumptions";
    }

    private Integer getIdealValue(final int n) {
        return n * n * SURE_WEIGHT;
    }
}

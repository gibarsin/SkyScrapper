package ar.edu.itba.sia;

import ar.edu.itba.sia.gps.api.GPSState;

public class LeastAssumptions implements Heuristic{

    private static final int SURE_WEIGHT = 50;
    private static final int ASSUMP_WEIGHT = 10;
    private static final int FULL_WEIGHT = 20;

    private Integer idealValue;

    public LeastAssumptions(final Board initialState){
        this.idealValue = getIdealValue(initialState.getSize());
    }
    @Override
    public Integer getHValue(GPSState gpsState) {
        Board board = (Board) gpsState;
        if (board.getEmptyCells() == 0)
            return 0;
        return idealValue - (board.getSure() * SURE_WEIGHT + (board.getAssumptions()+board.getSemiSure()) * ASSUMP_WEIGHT + (board.getFullCols() + board.getFullRows()) * FULL_WEIGHT);
        //
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
        return n * n * SURE_WEIGHT + 2 * n * FULL_WEIGHT;
    }
}

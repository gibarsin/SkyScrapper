package ar.edu.itba.sia;

import java.util.List;

import ar.edu.itba.sia.gps.api.GPSProblem;
import ar.edu.itba.sia.gps.api.GPSRule;
import ar.edu.itba.sia.gps.api.GPSState;

public class Game implements GPSProblem {

    private Board initialState;
    private List<GPSRule> rules;
    private final Heuristic heuristic;

    public Game(final Board initialState, final List<GPSRule> rules, final Heuristic heuristic) {
        this.initialState = initialState;
        this.rules = rules;
        this.heuristic = heuristic;
    }

    public GPSState getInitState() {
        return initialState;
    }

    public boolean isGoal(final GPSState gpsState) {
        Board board = (Board) gpsState;
        return board.isFull() && board.isValid();
    }

    public List<GPSRule> getRules() {
        return this.rules;
    }

    public Integer getHValue(final GPSState gpsState) {
        return heuristic.getHValue(gpsState);
    }
}
